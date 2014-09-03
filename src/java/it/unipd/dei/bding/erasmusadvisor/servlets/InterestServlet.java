package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/*
 * (Autorizzazioni: solo STUDENTE)
 * 
 * mappato su /student/interests
 * 
 * quando riceve POST
 *   		-> Se operazione è "delete" rimuove l'interesse collegato allo studente loggato e IdFlusso come parametro
 *   		-> Se operazione è "insert" inserisce l'interesse collegato allo studente loggato e IdFlusso come parametro
 */

/**
 * Manages all interests related to a student. 
 * 
 * <p> Base URL: /student/interests
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: DELETE, INSERT
 * 
 * @author Luca
 */

public class InterestServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String DELETE = "delete";

	private static final long serialVersionUID = -177735757618533981L;
	
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
			throws ServletException, IOException 
	{		
		
		
		// Gets operation parameter
		String operation = req.getParameter("operation");
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: STUDENT
		 */
		if (!lu.isStudent() || operation == null || operation.isEmpty() ) {
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
			delete(req, resp, lu);
		}
		else if (operation.equals(INSERT))
		{
			insert(req, resp, lu);
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
	private void insert(HttpServletRequest req, HttpServletResponse resp, LoggedUser lu) 
			throws ServletException, IOException  {
		
		// Gets parameters
		String flow = req.getParameter("flowID");
		
		long updatedInterests = 0;
		
		// Connection and model
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			InteresseDatabase.addInterest(conn, flow, lu.getUser());
			updatedInterests = InteresseDatabase.getCountInteresseByFlusso(conn, flow);
		} 
		catch (SQLException ex) 
		{
			m = new Message("Error while inserting your interest.", "", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}
		
		if (m == null)
		{	
			/** 
			 * Show results to the JSP page. 
			 *
			 */
			resp.setContentType("text/plain"); 
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(updatedInterests + "");
			//resp.setContentType("application/json");
			//JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
			//jsonWriter.writeObject(Json.createObjectBuilder().add("updatedInterests", updatedInterests).build());
			//jsonWriter.close();
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
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
    private void delete(HttpServletRequest req, HttpServletResponse resp, LoggedUser lu) 
    		throws ServletException, IOException 
    {
    	
    	// Connection and model
		Connection conn = null;
		Message m = null;
		
		// Gets parameters
    	String flow = req.getParameter("flowID");
		long updatedInterests = 0;
		
		int results = 0;
		try {
			conn = DS.getConnection();
			results = InteresseDatabase.removeInterest(conn, flow, lu.getUser());
			updatedInterests = InteresseDatabase.getCountInteresseByFlusso(conn, flow);

		} 
		catch (SQLException ex) 
		{
			m = new Message("Error while deleting your interest.", "", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}
		
		if (m == null && results > 0)
		{
			resp.setContentType("text/plain"); 
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(updatedInterests + "");
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
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
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}