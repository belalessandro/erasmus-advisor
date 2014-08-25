package it.unipd.dei.bding.erasmusadvisor.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServlet extends AbstractDatabaseServlet 
{
	private static final long serialVersionUID = 3912881071054796343L;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");


		HttpSession session = request.getSession();
		session.removeAttribute("loggedUser");
		request.logout();
		getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request, response);
	}
}
