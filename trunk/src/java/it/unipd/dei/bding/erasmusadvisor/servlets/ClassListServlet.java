/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetStatoValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
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
 * @author Luca
 *
 */
public class ClassListServlet extends AbstractDatabaseServlet 
{
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
			List<AreaBean> areaDomain = null;
			List<UniversitaBean> universities = null;
			Connection conn = null;
			Message m = null;
			
			try {
				conn = DS.getConnection();
				languageDomain = GetLinguaValues.getLinguaDomain(conn);
				areaDomain = GetAreaValues.getAreaDomain(conn);
				universities = GetUniversitaValues.getDomain(conn);
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
				req.setAttribute("areaDomain", areaDomain);
				req.setAttribute("universities", universities);
				getServletContext().getRequestDispatcher("/jsp/search_class.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
	}
}
