package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
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
 * Pre-processes the Insert FORM of a new City.
 * 
 * It returns the JSP page insert_city.jsp, populated with the
 * required fields
 * 
 * <p> Base URL: /city/insert
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: (none)
 * 
 * @see InsertCityServlet
 * @author Luca
 */
public class InsertCityServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = -4729886437975080823L;

	/**
	 * Forwards the pre-loaded data to the insert form
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
		

		List<LinguaBean> languageDomain = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the city.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{
			// forward to the insert FORM
			req.setAttribute("languageDomain", languageDomain);
			getServletContext().getRequestDispatcher("/jsp/insert_city.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
}
