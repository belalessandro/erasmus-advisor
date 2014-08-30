package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.UserBean;
import it.unipd.dei.bding.erasmusadvisor.database.UserDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

public class LoginServlet extends AbstractDatabaseServlet {

	/**
	 * Login servlet
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=UTF-8");

		String email = request.getParameter("email");
		String pass = request.getParameter("pass");

		Connection conn = null;

		Message m = null;

		try {
			UserDatabase userDb = new UserDatabase();

			conn = DS.getConnection();
			//Finds the user in the database
			UserBean user = userDb.login(conn, email);
			
			if (user != null) {
				try {
					//Checks the password
					boolean correct = user.checkPassword(pass);
					if (correct) {
						//Starts the session
						HttpSession session = request.getSession(true);
						LoggedUser logged = new LoggedUser(user.getType(), user.getNomeUtente());
						session.setAttribute("loggedUser", logged);
						
						// luca: traferisce il controllo alla index
						getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
						
						//StringBuilder builder = new StringBuilder()
						//.append("/erasmus-advisor/index");
				
						//response.sendRedirect(builder.toString());
						//return;
					}
				} 
				catch (IllegalStateException e) {
					m = new Message("Server error! Please contact an admin");
				}
			} 
			else {

			}
		} 
		catch (SQLException e) {
			m = new Message("Email or password incorrect!");
			request.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(
					request, response);
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}

	}
}