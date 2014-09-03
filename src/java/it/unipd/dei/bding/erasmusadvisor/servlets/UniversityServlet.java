package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.University;
import it.unipd.dei.bding.erasmusadvisor.resources.UniversityEvaluationsAverage;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages a specific University.
 * 
 * <p> Base URL: /university
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: INSERT, EDIT, DELETE
 * 
 * @author Luca, Alessandro
 */

public class UniversityServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";

	private static final long serialVersionUID = 1462509389265503855L;
	

	/**
	 * Get the details of a specific university
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
			throws ServletException, IOException {
		
		String univName = req.getParameter("name");

		if (univName == null || univName.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_university.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		University results = null;
		Message m = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			results = UniversitaDatabase.searchUniversityModelByName(conn, univName);
			DbUtils.close(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the university.",
					"XXX", ex.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		
		
		/**
		 *  Send the university model to the appropriate output (Ajax or normal)
		 *
		 */
		
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
			// Handle Ajax response (e.g. return JSON data object).

			resp.setContentType("application/json");
			if (results != null) {
				/* NOT IMPLEMENTED */
			}

		} else {
			// Handle normal response (e.g. forward and/or set message as attribute).

			if (m == null && results != null) {
				/** 
				 * Show results to the JSP page. 
				 *
				 */

				req.setAttribute("university", results.getUniversita());
				req.setAttribute("evaluations", results.getListaValutazioni());
				req.setAttribute("evaluationsAvg", new UniversityEvaluationsAverage(results.getListaValutazioni()));

				getServletContext().getRequestDispatcher("/jsp/show_university.jsp").forward(req, resp);
								
			}
			else 
			{ // Error page
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}

	}
	
	/**
	 * Handles an operation FORM
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Gets operation parameter
		String operation = req.getParameter("operation");
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: FlowManager, Coordinator
		 */
		if (! (lu.isCoord() || lu.isFlowResp()) || operation == null || operation.isEmpty() ) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		/** 
		 * OPERATION DISPATCHER 
		 */
		else if (operation.equals(INSERT))
		{			
			insert(req, resp);
		}
		else if (operation.equals(DELETE))
		{			
			delete(req, resp);
		
		}
		else if (operation.equals(EDIT)) 
		{
			edit(req, resp);
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
	private void insert(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException  {
		
		// database connection
		Connection conn = null;
		
		// entity beans
		UniversitaBean uni = new UniversitaBean(); 
		
		// model
		Message m = null;
		
		// Auto-Populate beans from the FORM submitted
		BeanUtilities.populateBean(uni, request);
		uni.setNome(request.getParameter("nome"));

		// set check-box value
		String alloggi = request.getParameter("presenzaAlloggi");
		uni.setPresenzaAlloggi(alloggi == null ? false : true);
		
		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			UniversitaDatabase.createUniversita(conn, uni);
			
			DbUtils.commitAndClose(conn); // COMMIT
			
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) {
				m = new Message("Operation not allowed: Duplicate data", "E300", 
						"This university is already present in the database!");
			} else {
				m = new Message("Error while inserting a new university.", "E200", e.getMessage());
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
				.append("/university?name=")
				.append(URLEncoder.encode(uni.getNome(), "utf-8"));
		response.sendRedirect(builder.toString());
    }

	/**
     * Handles a delete request.
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
		
		String name = req.getParameter("name"); 
		
		if (name != null && !name.isEmpty()) 
		{
			int results;
			try {
				conn = DS.getConnection();
				results = UniversitaDatabase.deleteUniversita(conn, name);				
				if (results > 0 )
				{
					String deletedEntity = name;
					req.setAttribute("deletedEntity", deletedEntity);
					getServletContext().getRequestDispatcher("/jsp/entity_deleted.jsp").forward(req, resp);
				}
				
			} 
			catch (SQLException e)
			{
				m = new Message("Error while deleting the university.", "", e.getMessage());
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
		} 
		else {
			// An error maybe?
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
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
		// TODO: check the user is type coordinator
    	Connection con = null;
    	Message m = null;
    	
		// get the parameters
		UniversitaBean universita = new UniversitaBean();
		BeanUtilities.populateBean(universita, request);
		
		String hasResidence = request.getParameter("presenzaAlloggi");
		if(hasResidence == null)
			universita.setPresenzaAlloggi(false);
		else
			universita.setPresenzaAlloggi(true);
		
		// Old name of the university
		String old_name = request.getParameter("old_name");
		
		try {
			con = DS.getConnection();
			
			// edit the university
			UniversitaDatabase.updateUniversita(con, universita, old_name);
			
			DbUtils.close(con);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
					.append(request.getContextPath())
					.append("/university?name=")
					.append(URLEncoder.encode(universita.getNome(), "utf-8"))
					.append("&edited=success");
			response.sendRedirect(builder.toString());
				
		} catch (SQLException e) {
			m = new Message("Error while editing a new university.", "XXX", e.getMessage());
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
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
