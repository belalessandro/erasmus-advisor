/**
 * 
 */
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

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Luca
 *
 */
public class FlowServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_class.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		Flow results = null;
		Message m = null;
		List<CertificatiLinguisticiBean> certificatesDomain = null;
		List<CorsoDiLaureaBean> possibleCourses = null;
		List<InsegnamentoBean> recognisedClasses = null;
		long interests = 0;

		// the connection to database
		Connection conn = null;
		
		try {
			conn = DS.getConnection();
			results = FlussoDatabase.getFlusso(DS, ID);
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			possibleCourses = CorsoDiLaureaDatabase.getPossibleCourses(conn, results.getResponsabile());
			interests = InteresseDatabase.getCountInteresseByFlusso(conn, ID);
			recognisedClasses = RiconoscimentoDatabase.getInsegnamentiRiconosciuti(conn, ID);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "XXX", ex.getMessage());
		} 
		
		
		/**
		 *  Send the university model to the appropriate output
		 *
		 */
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
	 * Insert or update the flow sent with a POST form
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		resp.setCharacterEncoding("UTF-8");
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "pilu"); 
		
		// required fields
		Message m = null;
		Connection con = null;
		
		// get the operation
		String operation = req.getParameter("operation");
		
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {
			
			// Error
			m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
			
		} 
		else if (operation.equals(INSERT))
		{
			/**
			 * INSERT OPERATION
			 */
			
			insert(req, resp);
		
		} 
		else if (operation.equals("edit") ) {
		
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
				m = new Message("Error while editing " + flussoBean.getId() + " " + flussoBean.getDestinazione() + " instance.","XXX", e.getMessage());
				req.setAttribute("message", m);
				
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
				return;
			}
			finally {
				DbUtils.closeQuietly(con);
			}
			
		}
	}
	

	/**
	 * Handle logic for insert operation...
	 * @param request
	 * @param response
	 */
	private void insert(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException  {
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn"); 

		
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
		flussoBean.setId(request.getParameter("name"));
		flussoBean.setDestinazione(request.getParameter("university"));
		flussoBean.setPostiDisponibili(Integer.parseInt(request.getParameter("seats")));
		flussoBean.setDurata(Integer.parseInt(request.getParameter("length")));
		flussoBean.setDettagli(request.getParameter("details"));
		
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
			m = new Message("Error while inserting a new flow.", "XXX", e.getMessage());
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

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        //handle logic for delete operation...
    }
    
    private void edit(HttpServletRequest request, HttpServletResponse response) {
        //handle logic for edit operation...
    }

    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
