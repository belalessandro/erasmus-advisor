package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.UserBean;
import it.unipd.dei.bding.erasmusadvisor.database.UserDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

public class LoginServlet extends AbstractDatabaseServlet {

	/**
	 * CLASSE DI ESEMPIO: Potrebbe essere necessaria una seria revision
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		String email = request.getParameter("email");
		String pass = request.getParameter("pass");

		Connection conn = null;

		Message m = null;

		try {
			UserDatabase userDb = new UserDatabase();

			conn = DS.getConnection();

			UserBean user = userDb.login(conn, email);
			if (user != null) {
				try {
					String sessionId = user.checkPassword(pass);
					if (sessionId != null) {
						Cookie cookie = new Cookie("sessionId", sessionId);
						response.addCookie(cookie);
						getServletContext().getRequestDispatcher(
								"/jsp/index.jsp").forward(request, response);
						return;
					}
				} catch (IllegalStateException e) {
					m = new Message("Server error! Please contact an admin");
				}
			} else {

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block: cosa succede in caso di errore
			// SQL?
			e.printStackTrace();
		} finally {
			m = new Message("Email or password incorrect!");
			request.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(
					request, response);
			DbUtils.closeQuietly(conn);
		}

	}
}