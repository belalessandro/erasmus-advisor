package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.DocumentazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.FlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetCertificatiLinguisticiValues;
import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.OrigineDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.RiconoscimentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Flow;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages a specific Flow.
 * 
 * <p> Base URL: /flow
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: INSERT, EDIT, DELETE
 * 
 * @see UniversityServlet
 * @author Luca
 */
public class FlowServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 6073649577892013101L;
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
	
    /**
	 * Get the details of a specific flow.
	 * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to the search form. */
			resp.sendRedirect(req.getContextPath() + "/flow/list");
			return;
		}
		
		// model
		Flow results = null;
		Message m = null;
		List<CertificatiLinguisticiBean> certificatesDomain = null;
		List<CorsoDiLaureaBean> possibleCourses = null;
		List<InsegnamentoBean> recognisedClasses = null;
		long interests = 0;

		// the connection to database
		Connection conn = null;

		/**
		 *  Gets the data from the database
		 */
		try {
			conn = DS.getConnection();
			results = FlussoDatabase.getFlusso(conn, ID);
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			possibleCourses = CorsoDiLaureaDatabase.getPossibleCourses(conn, results.getResponsabile());
			interests = InteresseDatabase.getCountInteresseByFlusso(conn, ID);
			recognisedClasses = RiconoscimentoDatabase.getInsegnamentiRiconosciuti(conn, ID);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the flow.", "E200", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		
		// Handle normal response (e.g. forward and/or set message as attribute).

		if (m == null && results != null) 
		{
			/** 
			 * Show results to the JSP page. 
			 */
			req.setAttribute("flow", results.getFlusso());
			req.setAttribute("manager", results.getResponsabile());
			req.setAttribute("origins", results.getCorsi());
			req.setAttribute("evaluations", results.getListaValutazioni());
			req.setAttribute("certificates", results.getCertificati());
			req.setAttribute("interests", interests);
			req.setAttribute("recognisedClasses", recognisedClasses);
			req.setAttribute("certificatesDomain", certificatesDomain);
			req.setAttribute("possibleCourses", possibleCourses);
			req.setAttribute("evaluationsAvg", new FlowEvaluationAverage(results.getListaValutazioni()));
			
			getServletContext().getRequestDispatcher("/jsp/show_flow.jsp").forward(req, resp);
						
		} 
		else { // Error page
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}

	/**
	 * Handles a POST form and performs the specified operation.
	 * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Gets operation parameter
		String operation = req.getParameter("operation");
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: FlowManager
		 */
		if (!lu.isFlowResp() || operation == null || operation.isEmpty() ) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		/** 
		 * OPERATION DISPATCHER 
		 */
		else if (operation.equals(DELETE))
		{		
			delete(req, resp);
		} 
		else if (operation.equals(INSERT)) // TODO: FlowManager puo' eliminare solo i propri
		{		
			insert(req, resp, lu);
		} 
		else if (operation.equals(EDIT) )  // TODO: FlowManager puo' eliminare solo i propri
		{
			edit(req, resp, lu);
		}
	}
	

	/**
	 * Handles logic for insert operation.
	 * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
	private void insert(HttpServletRequest request, HttpServletResponse response, LoggedUser lu) 
			throws ServletException, IOException  {

		
		// the connection to database
		Connection conn = null;
		
		// entity beans
		FlussoBean flussoBean  = null;
		List<DocumentazioneBean> documentazioneBeanList = new ArrayList<DocumentazioneBean>();
		List<OrigineBean> origineBeanList = new ArrayList<OrigineBean>();
		
		
		// models
		Message m = null;
		
		// Populating beans from FORM parameters
		flussoBean = new FlussoBean();
		try {
			flussoBean.setId(request.getParameter("name"));
			flussoBean.setDestinazione(request.getParameter("university"));
			flussoBean.setPostiDisponibili(Integer.parseInt(request.getParameter("seats")));
			flussoBean.setDurata(Integer.parseInt(request.getParameter("length")));
			flussoBean.setDettagli(request.getParameter("details"));
		
		} catch (NumberFormatException ex) {
			m = new Message("Invalid input parameters.", "E100", ex.getMessage());
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		}
		
		// Additional settings for the bean
		flussoBean.setRespFlusso(lu.getUser());
		flussoBean.setAttivo(false); // TODO: in base a chi lo inserisce 
		
		String[] paramCert = request.getParameterValues("certificate[]");
		if (paramCert != null) {
			for (int j=0; j<paramCert.length; j++) {
				DocumentazioneBean d = new DocumentazioneBean();
						
				String nomeCertificato = paramCert[j].split("-")[0].trim(); // "ITA - B2" -> ITA
				String livelloCertificato = paramCert[j].split("-")[1].trim(); // "ITA - B2" -> B2

				d.setNomeCertificato(nomeCertificato);
				d.setLivelloCertificato(livelloCertificato);
				d.setIdFlusso(flussoBean.getId());
				
				documentazioneBeanList.add(d);
			}
		}
		
		String[] paramOrigin = request.getParameterValues("origin[]");
		if (paramOrigin != null) {
			for (int i=0; i<paramOrigin.length; i++) {
				OrigineBean o = new OrigineBean();
				o.setIdCorso(Integer.parseInt(paramOrigin[i]));
				o.setIdFlusso(flussoBean.getId());
				
				origineBeanList.add(o);
			}
		}

		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			FlussoDatabase.createFlusso(conn, flussoBean);
			for (DocumentazioneBean d : documentazioneBeanList) {
				DocumentazioneDatabase.createDocumentazione(conn, d);
			}
			for (OrigineBean o : origineBeanList) {
				OrigineDatabase.createOrigine(conn, o);
			}
			
			DbUtils.commitAndClose(conn); // COMMIT
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK

			/** Primary key error */
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) { 
				
				m = new Message("Operation not allowed: Duplicate data", "E300", 
						"This flow is already present in the database!");
			} /** Foreign key error */
			else if (e.getSQLState() != null && e.getSQLState().equals("23503")) { 
				
				m = new Message("University not found", "E300", 
						"The university you specified is not present in the database!");
			} 
			else { 
				m = new Message("Error while inserting a new Flow.", "E200", e.getMessage());
			}
			
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		}
		finally {
			DbUtils.closeQuietly(conn); // *always* closes DB connection
		}
		
		
		// Success!
		// Creating response path
		StringBuilder builder = new StringBuilder()
			.append(request.getContextPath())
			.append("/flow?id=")
			.append(flussoBean.getId());
		response.sendRedirect(builder.toString());	
    }

	/**
	 * Handles logic for delete operation.
	 * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
    private void delete(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException, IOException 
    {

		Connection conn = null;
		Message m = null;
		
		String id = req.getParameter("id");
		
		if (id != null && !id.isEmpty()) 
		{
			int results;
			try {
				conn = DS.getConnection();
				results = FlussoDatabase.deleteFlusso(conn, id);				
				if (results > 0 )
				{
					String deletedEntity = id;
					req.setAttribute("deletedEntity", deletedEntity);
					getServletContext().getRequestDispatcher("/jsp/entity_deleted.jsp").forward(req, resp);
				}
				
			} 
			catch (SQLException e)
			{
				m = new Message("Error while deleting the flow.", "", e.getMessage());
				req.setAttribute("message", m);
				errorForward(req, resp);
			}
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
		} 
		else {
			m = new Message("Bad parameters.", "E100", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
		}
    }
    
	/**
	 * Handles logic for edit operation.
	 * 
	 * @param req
	 * 				request from the client
	 * @param resp 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
    private void edit(HttpServletRequest req, HttpServletResponse resp, LoggedUser lu) 
    		throws ServletException, IOException {

		// required fields
		Message m = null;
		Connection con = null;
		
		// Populate beans
		FlussoBean flussoBean = new FlussoBean();
		BeanUtilities.populateBean(flussoBean, req);
		flussoBean.setRespFlusso(lu.getUser());
		flussoBean.setDestinazione(req.getParameter("destinazione"));
		flussoBean.setDurata(Integer.parseInt(req.getParameter("durata")));
		flussoBean.setPostiDisponibili(Integer.parseInt(req.getParameter("postiDisponibili")));
		flussoBean.setDettagli(req.getParameter("dettagli"));
		String old_id = req.getParameter("old_id");
		
		// get required parameters
		String[] origins = req.getParameterValues("origins[]");
		String[] certificates = req.getParameterValues("certificates[]");
		String[] certificatesName = new String[certificates.length];
		String[] certificatesLevel = new String[certificates.length];
		String[][] tmp = new String[certificates.length][];
		
		// split certificates values in names and levels
		for(int i = 0; i < certificates.length; i++)
			tmp[i] = certificates[i].split("-");
		
		// assigning to appropriate variables
		for(int i = 0; i < certificates.length; i++)
		{
			certificatesName[i] = tmp[i][0].trim();
			certificatesLevel[i] = tmp[i][1].trim();		
		}

		
		try {
			con = DS.getConnection();
			
			// delete origin course
			OrigineDatabase.deleteOrigineByFlowId(con, flussoBean.getId());
			
			
			// delete doc relative to the flow
			DocumentazioneDatabase.deleteDocumentazioneByFlowId(con, flussoBean.getId());
			
			// update flusso
			FlussoDatabase.updateFlusso(con, flussoBean, old_id);
			
			// insert new origin courses
			OrigineBean ob = new OrigineBean();
			if(origins != null && origins.length > 0)
			{
				for(int i = 0; i < origins.length; i++)
				{
					ob.setIdCorso(Integer.parseInt(origins[i]));
					ob.setIdFlusso(flussoBean.getId());
					OrigineDatabase.createOrigine(con, ob);
				}	
			}
			
			// insert new documentation
			DocumentazioneBean db = new DocumentazioneBean();
			if(certificates != null && certificates.length > 0)
			{
				for(int i = 0; i < certificates.length; i++)
				{
					db.setIdFlusso(flussoBean.getId());
					db.setNomeCertificato(certificatesName[i]);
					db.setLivelloCertificato(certificatesLevel[i]);
					
					DocumentazioneDatabase.createDocumentazione(con, db);
				}
			}
			
			DbUtils.close(con);
			
			// creating response path and redirect to the new page
			StringBuilder builder = new StringBuilder()
			.append("/erasmus-advisor/flow?id=")
			.append(flussoBean.getId())
			.append("&edited=success");
			
			resp.sendRedirect(builder.toString());
			
		} catch (SQLException e) {
			// Error management
			m = new Message("Error while editing " + flussoBean.getId() + " " + flussoBean.getDestinazione() + " instance.",
					"E200", e.getMessage());
			req.setAttribute("message", m);
			
			errorForward(req, resp);
			return;
		}
		finally {
			DbUtils.closeQuietly(con);
		}
    }

	/**
     * Handles error forwarding between pages.
     * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
