/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.TeachingEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Teaching;
import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

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
public class ClassServlet extends AbstractDatabaseServlet 
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_class.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		Teaching results = null;
		Message m = null;
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		
		// database connection
		Connection conn = null;

					
		try {
			conn = DS.getConnection();
			results = InsegnamentoDatabase.getInsegnamento(conn, Integer.parseInt(ID));
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}
		
		
		/**
		 *  Send the university model to the appropriate output
		 *
		 */
		// Handle normal response (e.g. forward and/or set message as attribute).

		if (m == null && results != null) 
		{
			/** 
			 * Show results to the JSP page. 
			 */
			req.setAttribute("classBean", results.getInsegnamento());
			req.setAttribute("language", results.getLingua());
			req.setAttribute("evaluations", results.getValutazioni());
			req.setAttribute("professors", results.getProfessori());

			req.setAttribute("languageDomain", languageDomain);
			req.setAttribute("areaDomain", areaDomain);
			req.setAttribute("evaluationsAvg", new TeachingEvaluationAverage(results.getValutazioni()));
			
			getServletContext().getRequestDispatcher("/jsp/show_class.jsp").forward(req, resp);
						
		} 
		else { // Error page
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}

	}
}
