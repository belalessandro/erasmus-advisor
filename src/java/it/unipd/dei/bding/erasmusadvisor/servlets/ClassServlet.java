package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.PartecipazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ProfessoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.SvolgimentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.UserDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneInsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Teaching;
import it.unipd.dei.bding.erasmusadvisor.resources.TeachingEvaluationAverage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;


/**
 * Manages a specific Class.
 * 
 * <p> Base URL: /class
 * 
 * <p> Accepts: GET, POST
 * 
 * @see UniversityServlet
 * @author Luca, Nicola, Mauro
 */
public class ClassServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
    private static final String AJAX = "ajax";
    
	private static final long serialVersionUID = 1L;

	/**
	 * Provides a class visualization and list of evaluations.
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
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: LOGGED
		 */
		if ( lu == null ) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to search form. */
			resp.sendRedirect(req.getContextPath() + "/class/list");
			return;
		}
		
		// models and beans
		Teaching results = null;
		Message m = null;
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		List<String> flows = null;
		
		// database connection
		Connection conn = null;
		boolean evalEnabled = false;
					
		/**
		 * Gets data from database
		 */
		try {
			conn = DS.getConnection();
			int classID = Integer.parseInt(ID);
			results = InsegnamentoDatabase.getInsegnamento(conn, classID);
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			if (lu.isStudent())
			{
				flows = PartecipazioneDatabase.getFlowsForAcknowledgment(conn, lu.getUser(), classID);

				// determina se abilitare l'inserimento della valutazione
				if (PartecipazioneDatabase.checkParticipation(conn, results.getInsegnamento().getNomeUniversita(), lu.getUser()))
				{
					if (!(ValutazioneInsegnamentoDatabase.checkEvaluation(conn, lu.getUser(), classID)))
					{
						evalEnabled = true;
					}
				}
			}
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "E200", "Please, contact the admin.");
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
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
			req.setAttribute("classBean", results.getInsegnamento());
			req.setAttribute("language", results.getLingua());
			req.setAttribute("evaluations", results.getValutazioni());
			req.setAttribute("professors", results.getProfessori());

			req.setAttribute("flows", flows);
			
			req.setAttribute("languageDomain", languageDomain);
			req.setAttribute("areaDomain", areaDomain);
			req.setAttribute("evaluationsAvg", new TeachingEvaluationAverage(results.getValutazioni()));

			if (lu.isStudent() && evalEnabled == true)
			{
				req.setAttribute("evalEnabled", "enabled");
			}
			else
			{
				req.setAttribute("evalEnabled", "notEnabled");
			}
			
			getServletContext().getRequestDispatcher("/jsp/show_class.jsp").forward(req, resp);
						
		} 
		else { // Error page
			req.setAttribute("message", m);
			errorForward(req, resp);
		}
	}
	
	/**
	 * Submit an operation form
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
	protected void  doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		// Operation parameter
		String operation = null;
		
		if(req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest"))
			operation = "ajax";
		else
			operation = req.getParameter("operation");
	
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		boolean allowed = false;
		
		if (lu.isCoord() || lu.isFlowResp())
			allowed = true; // XXX
		
		/** 
		 * OPERATION DISPATCHER 
		 */
		if (operation != null && // Permissions required: LOGGED
				operation.equals(AJAX) && (lu != null))
		{
			report(req, resp);
		}
		else if (operation != null && // Permissions required: LOGGED
				operation.equals(INSERT) && (lu != null))
		{	
			insert(req, resp, lu);
		} 
		else if (operation != null && // Permissions required: FlowManager, Coordinator
				operation.equals(DELETE) && (allowed))
		{
			delete(req, resp);
		}
		else if(operation != null && // Permissions required: FlowManager, Coordinator
				operation.equals(EDIT) && (allowed))
		{	
			edit(req, resp);
			
		} 
		else {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
		}
		
	}

	
	/**
	 * Handle the report for a class. 
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
	private void report(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// get JSON object
		response.setContentType("application/json");
		JsonReader reader = Json.createReader(request.getInputStream());
		JsonObject json = reader.readObject();
		reader.close();
		
		// modify the instance into the database
		Message m = null;
		Connection con = null;
		
		try {
			con = DS.getConnection();
			
			InsegnamentoDatabase.changeClassStatus(con, "REPORTED", json.getInt("id"));
			
			DbUtils.close(con);
			
		} catch (SQLException e) {
			m = new Message("Error while reporting the thesis.", "E200", "Please, contact the admin.");
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}
		
		// writing the JSON object to the page
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		builder.add("report", "success");
		JsonObject out = builder.build();
		
		JsonWriter writer = Json.createWriter(response.getOutputStream());
		writer.writeObject(out);
		writer.close();
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
		InsegnamentoBean insegnamentoBean =  new InsegnamentoBean();
		//List<SvolgimentoBean> svolgimentoBean = new ArrayList<SvolgimentoBean>();
		
		// models
		Message m = null;
		
		// Populating beans from FORM parameters
		try {
			insegnamentoBean.setNome(request.getParameter("Nome"));
			insegnamentoBean.setCrediti(Integer.parseInt(request.getParameter("Crediti")));
			insegnamentoBean.setNomeUniversita(request.getParameter("NomeUniversita"));
			insegnamentoBean.setPeriodoErogazione(Integer.parseInt(request.getParameter("PeriodoErogazione")));
			insegnamentoBean.setAnnoCorso(Integer.parseInt(request.getParameter("AnnoCorso")));
			insegnamentoBean.setNomeArea(request.getParameter("NomeArea"));
			insegnamentoBean.setNomeLingua(request.getParameter("NomeLingua"));
			
		} catch (NumberFormatException ex) {
			m = new Message("Invalid input parameters.", "E100", "Please, contact the admin.");
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		}
		
		// Setting status
		if (lu.isFlowResp() || lu.isCoord()) 
			insegnamentoBean.setStato("VERIFIED");
		else
			insegnamentoBean.setStato("NOT VERIFIED");
		
		String[] profNames = request.getParameterValues("professorName");
		String[] profSurnames = request.getParameterValues("professorSurname");

		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			
			if (lu.isStudent()) { // Checks whether the student has the permissions to insert
				boolean studentAllowed = UserDatabase.canStudentInsert(conn, lu.getUser(), 
						insegnamentoBean.getNomeUniversita());
				if ( ! studentAllowed )
					throw new SQLException("Students can not insert classes of universities they do not know!");
			}
			
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			int idInsegnamento = InsegnamentoDatabase.createInsegnamento(conn, insegnamentoBean);

			if (profNames != null && profSurnames != null && profNames.length == profSurnames.length) {
				for (int j=0; j<profNames.length; j++) {
					String nome = profNames[j];
					String cognome = profSurnames[j];
					String universita = insegnamentoBean.getNomeUniversita();
					
					if (nome != null && cognome != null && !nome.isEmpty() && !cognome.isEmpty() 
							&& universita != null) {
						nome = nome.trim();
						cognome = cognome.trim();
						
						// Select or insert professor
						int idProfessore = ProfessoreDatabase
								.selectOrInsertProfessore(conn, nome, cognome, universita);
						
						// Create the relation between professor and class
						SvolgimentoBean s = new SvolgimentoBean();
						s.setIdProfessore(idProfessore);
						s.setIdInsegnamento(idInsegnamento);
						SvolgimentoDatabase.createSvolgimento(conn, s);
					}
				}
			}
			
			DbUtils.commitAndClose(conn); // COMMIT
			
			insegnamentoBean.setId(idInsegnamento);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK
			
			/** Primary key error */
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) { 
				
				m = new Message("Operation not allowed: Duplicate data", "E300", 
						"This class is already present in the database!");
			} /** Foreign key error */
			else if (e.getSQLState() != null && e.getSQLState().equals("23503")) { 
				
				m = new Message("University not found", "E300", 
						"The university you specified is not present in the database!");
			} 
			else { 
				m = new Message("Error while inserting a new class.", "E200", "Please, contact the admin.");
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
			.append("/class?id=")
			.append(insegnamentoBean.getId());
		response.sendRedirect(builder.toString());	
    }

	/**
     * Handles a delete post request.
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
		String name = req.getParameter("name"); // name serve solo per la visualizzazione in entity_deleted
		
		if (id != null && !id.isEmpty() && name != null && !name.isEmpty()) 
		{
			int results;
			try {
				conn = DS.getConnection();
				results = InsegnamentoDatabase.deleteInsegnamento(conn, Integer.parseInt(id));				
				if (results > 0 )
				{
					String deletedEntity = name;
					req.setAttribute("deletedEntity", deletedEntity);
					getServletContext().getRequestDispatcher("/jsp/entity_deleted.jsp").forward(req, resp);
				}
				
			} 
			catch (SQLException e)
			{
				m = new Message("Error while deleting the class.", "E200", "Please, contact the admin.");
				req.setAttribute("message", m);
				errorForward(req, resp);
			}
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
		} 
		else {
			m = new Message("Error while deleting the class.", "E100", "Bad parameters.");
			req.setAttribute("message", m);
			errorForward(req, resp);
		}
    }
    
    /**
     * Handles an edit post request.
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
    private void edit(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException, ServletException 
    {
    	// Populate beans
		InsegnamentoBean insegnamentoBean = new InsegnamentoBean();
		BeanUtilities.populateBean(insegnamentoBean, request);
		
		// Sets additional fields
		insegnamentoBean.setStato("VERIFIED"); // because it is a FlowManager or Coordinator
					
		String[] professorName = request.getParameterValues("professorName");
		String[] professorSurname = request.getParameterValues("professorSurname");
		
		// Required variables
		Connection con = null;
		Message m = null;
		
		try {
			con = DS.getConnection();
			
			// remove old class from Svolgimento
			SvolgimentoDatabase.deleteSvolgimentoByClassId(con, insegnamentoBean.getId());
			
			
			// update the class
			InsegnamentoDatabase.updateInsegnamento(con, insegnamentoBean);
			
			// check if the professor still existing, otherwise insert the new professor 
			// and then insert the corresponding row in Svolgimento
			int id = 0;
			SvolgimentoBean svolgimentoBean = new SvolgimentoBean();
			
			for(int i = 0; i < professorName.length; i++)
			{	
				if(!professorName[i].trim().equals("") && !professorSurname[i].trim().equals(""))
				{
					id = ProfessoreDatabase.selectOrInsertProfessore(con,
							professorName[i],
							professorSurname[i],
							insegnamentoBean.getNomeUniversita());
					
					svolgimentoBean.setIdInsegnamento(insegnamentoBean.getId());
					svolgimentoBean.setIdProfessore(id);
					
					SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
				}
			}
			
			// closing the connection
			DbUtils.close(con);
			
			// Creating response path and redirect to the new page
			StringBuilder builder = new StringBuilder()
			.append(request.getContextPath())
			.append("/class?id=")
			.append(insegnamentoBean.getId())
			.append("&edited=success");
			
			response.sendRedirect(builder.toString());
			
		} catch (SQLException e) {
			// Error management
			m = new Message("Error while editing " + insegnamentoBean.getNome() + " instance.","XXX", "Please, contact the admin.");
			request.setAttribute("message", m);
			
			errorForward(request, response);
			return;
		} finally {
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
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
	
}
