package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.UserBean;
import it.unipd.dei.bding.erasmusadvisor.database.UserDatabase;
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

/**
 * Servlet used for managing student's, coordinator's and flowmanager's
 * login. 
 * 
 * <p> Base URL: /login
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: LOGIN
 * 
 * @author Luca
 */

public class LoginServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Handles a login operation FORM
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
	protected void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// Gets the current session (if exists)
		HttpSession session = request.getSession(false);
		
		// Check: User must not be already logged! 
		if (session != null && session.getAttribute("loggedUser") != null) {
			request.setAttribute("message", new Message("Operation impossibile.",
					"E200", "You must logout, before doing another login!"));
			errorForward(request, response); // Error forward!
			return;
		}
		
		response.setContentType("text/html;charset=UTF-8");

		// Parameters
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");

		// The connection
		Connection conn = null;

		// models
		Message m = null;

		try {
			
			// Database connection
			conn = DS.getConnection();
			
			//Finds the user in the database
			UserDatabase userDb = new UserDatabase();
			UserBean user = userDb.login(conn, email);
			
			if (user != null && user.isAttivo()) {
				try {
					//Checks the password
					boolean correct = user.checkPassword(pass);
					if (correct) {
						// TODO: deve aggiornare l'ultimo accesso nel database!!!!
						
						//Starts the session
						session = request.getSession(true);
						LoggedUser logged = new LoggedUser(user.getType(), user.getNomeUtente());
						session.setAttribute("loggedUser", logged);
						
						// luca: traferisce il controllo alla index
						//getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
						
						StringBuilder builder = new StringBuilder()
						.append(request.getContextPath())
						.append("/index");
				
						response.sendRedirect(builder.toString());
						return;
					}
					else
					{
						m = new Message("Incorrect password.", "E200", "Retry, please.");
						request.setAttribute("message", m);
				    	//request.setAttribute("errorType", "userNotLogged");	
						//errorForward(request, response);
						getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
					}
				} 
				catch (IllegalStateException e) {
					m = new Message("Server error! Please contact an admin", "E200", "");	
			    	request.setAttribute("errorType", "userNotLogged");	
					errorForward(request, response);	
				}
			} 
			else if(!user.isAttivo())
			{
				m = new Message("Your account is disabled, please contact the Erasmus Coordinator of your University.", "E200", "");
				request.setAttribute("message", m);
		    	request.setAttribute("errorType", "userNotLogged");	
				errorForward(request, response);
			}
		} 
		catch (SQLException e) {
			m = new Message("Incorrect email or password.", "E200", "Retry, please.");
			request.setAttribute("message", m);
	    	//request.setAttribute("errorType", "userNotLogged");	
			//errorForward(request, response);
			getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
		} 
		finally {
			DbUtils.closeQuietly(conn);
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

    	request.getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}