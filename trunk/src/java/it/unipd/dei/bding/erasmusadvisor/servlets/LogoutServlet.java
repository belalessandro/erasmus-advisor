package it.unipd.dei.bding.erasmusadvisor.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet used for the logout operation.
 * 
 * <p> Base URL: /logout
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: LOGOUT
 * 
 * @author Federico, Luca
 */

public class LogoutServlet extends AbstractDatabaseServlet 
{
	private static final long serialVersionUID = 3912881071054796343L;

	/**
	 * Handles a logout operation FORM
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
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");

		// get the session
		HttpSession session = request.getSession();
		session.removeAttribute("loggedUser");
		
		// logout the user
		request.logout();
		
		// redirect
		getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
	}
}
