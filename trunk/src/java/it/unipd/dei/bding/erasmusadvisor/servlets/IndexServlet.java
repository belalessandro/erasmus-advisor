package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.InterestBean;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Luca
 *
 */

public class IndexServlet extends AbstractDatabaseServlet 
{

	private static final long serialVersionUID = -397214779654035607L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{

		Connection conn = null;
		Message m = null;
		List<InterestBean> interests = null;

		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "user"); 
		
		try {
			conn = DS.getConnection();
			interests = InteresseDatabase.getInterestInformationsFromUser(conn, lu.getUser());
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the index page.", "", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{		
			req.setAttribute("interests", interests);
			getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
}
