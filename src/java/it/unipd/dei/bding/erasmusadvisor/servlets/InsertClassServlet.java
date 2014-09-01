package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
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
 * Pre-processes the Insert FORM of a new Class.
 * 
 * It returns the JSP page insert_class.jsp, populated with the
 * required fields
 * 
 * <p> Base URL: /class/insert
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: (none)
 * 
 * @see InsertUniversityServlet
 * @author Luca
 */
public class InsertClassServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 184518310339821216L;

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
		List<AreaBean> areaDomain = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{
			// forward to the insert FORM
			req.setAttribute("languageDomain", languageDomain);
			req.setAttribute("areaDomain", areaDomain);
			getServletContext().getRequestDispatcher("/jsp/insert_class.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
}
