package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneUniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

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
 * Servlet used for inserting new class' evaluations.
 * 
 * <p> Base URL: /city/evaluations
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: INSERT, DELETE
 * 
 * @author Mauro, Luca, Alessandro
 */
public class UniversityEvaluationsServlet extends AbstractDatabaseServlet 
{

	
	private static final long serialVersionUID = -3739887046591409010L;

	/**
	 * Handles insert / delete operation FORM.
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
		else if (operation.equals("insert"))
		{
			insert(req, resp, lu);
		} 
		else if (operation.equals("delete"))
		{
			delete(req, resp, lu);
		}	
		
	}

	/**
	 * Method for deleting an evaluation.
	 * Redirect to /student/evaluations
	 * 
	 * @param req 
	 * 			request object 
	 * @param resp 
	 * 			response object
	 * @param lu 
	 * 			the user currently logged
	 * @throws ServletException
	 * 			if any error occurs while executing the servlet
	 * @throws IOException
	 * 			if any error occurs in the client/server communication.
	 */
	private void delete (HttpServletRequest req, HttpServletResponse resp, LoggedUser lu)
			throws ServletException, IOException
	{		
		// Setup bean and the database connection
		Connection con = null;
		Message m = null;
		
		String name = req.getParameter("name");
		
		try
		{
			con = DS.getConnection();
			ValutazioneUniversitaDatabase.deleteEvaluation(con, lu.getUser(), name);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
				.append(req.getContextPath())
				.append("/student/evaluations");
		
			resp.sendRedirect(builder.toString());

		}
		catch (SQLException e) 
		{
			// Error management
			e.printStackTrace();
			m = new Message("Error while deleting the evaluation.","", e.getMessage());
			req.setAttribute("message", m);
			errorForward(req, resp); 
			return;
		} 
		finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	/**
	 * Method used for inserting a new evaluation for the 
	 * current class.
	 * Redirect to the city evaluated.
	 * 
	 * @param req 
	 * 			request object 
	 * @param resp 
	 * 			response object
	 * @param lu 
	 * 			the user currently logged
	 * @throws ServletException
	 * 			if any error occurs while executing the servlet
	 * @throws IOException
	 * 			if any error occurs in the client/server communication.
	 */
	private void insert (HttpServletRequest req, HttpServletResponse resp, LoggedUser lu)
			throws ServletException, IOException
	{
		// Setup bean and the database connection
		Connection con = null;
		Message m = null;
		
		// Populate the bean
		ValutazioneUniversitaBean val = new ValutazioneUniversitaBean();
		val.setNomeUtenteStudente(lu.getUser());
		BeanUtilities.populateBean(val, req);
		
		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneUniversitaDatabase.createValutazioneUniversita(con, val);
			DbUtils.close(con);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
				.append(req.getContextPath())
				.append("/university?name=")
				.append(URLEncoder.encode(val.getNomeUniversita(), "utf-8"));
		
			resp.sendRedirect(builder.toString());
			
			
		} catch (SQLException e) {
			// Error management

			/** Primary key error */
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) { 
				
				m = new Message("No duplicate evaluations allowed", "E300", 
						"You have already submitted an evaluation!");
			} else {
				m = new Message("Error while submitting evaluations.","E200", e.getMessage());
			}
			req.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}	
		
	}

	/**
	 * Method used for forwarding to the error page
	 * with the appropriate message.
	 * 
	 * @param req 
	 * 			request object 
	 * @param resp 
	 * 			response object
	 * @throws ServletException
	 * 			if any error occurs while executing the servlet
	 * @throws IOException
	 * 			if any error occurs in the client/server communication.
	 */
    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  
    {	
    	getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }

}
