package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.InterestBean;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Provide access to the index page.
 * 
 * <p> Base URL: /index
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: Setting content
 * 
 * @author Luca
 */

public class IndexServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = -397214779654035607L;

	/**
	 * Get the details of a specific user's index page
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

		// database connection
		Connection conn = null;
		Message m = null;
		
		// model
		List<InterestBean> interests = null;

		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");

		try {
			conn = DS.getConnection();
			interests = InteresseDatabase.getInterestInformationsFromUser(conn, 
					lu.getUser());
		} catch (SQLException ex) {
			m = new Message("Error while getting the index page.", "",
					ex.getMessage());
		} finally {
			DbUtils.closeQuietly(conn); 
		}

		/**
		 *  Send the interests to the user home page
		 *
		 */
		if (m == null) {
			req.setAttribute("interests", interests);
			getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(
					req, resp);
		} else {
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(
					req, resp);
		}
	}
}
