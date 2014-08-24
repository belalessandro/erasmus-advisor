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

public class LogoutServlet extends AbstractDatabaseServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		Message m = null;

		HttpSession session = request.getSession();
		request.removeAttribute("loggedUser");
		request.logout();
		getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(
				request, response);
	}
}
