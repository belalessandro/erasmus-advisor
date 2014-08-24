/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetStatoValues;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Alessandro, Luca
 * 
 */
public class CityListServlet extends AbstractDatabaseServlet {
	/*
	 * (Autorizzazioni: )
	 * 
	 * mappato su /city/list
	 * 
	 * quando riceve GET
	 * 
	 * 
	 * quando riceve POST
	 */

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
		{

		String operation = req.getParameter("operation");
		if (operation != null && !operation.isEmpty()
				&& operation.equals("search")) {

		}
		else { 
			/* Redirect to the Search JSP page */
			
			List<LinguaBean> languageDomain = null;
			List<String> countries = null;
			Connection conn = null;
			Message m = null;
			
			try {
				conn = DS.getConnection();
				languageDomain = GetLinguaValues.getLinguaDomain(conn);
				countries = GetStatoValues.getValues(conn);
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the search page.", "XXX", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}

			if (m == null)
			{
				req.setAttribute("languageDomain", languageDomain);
				req.setAttribute("countries", countries);
				getServletContext().getRequestDispatcher("/jsp/search_city.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
	}
}
