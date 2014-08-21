/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Alessandro
 *
 */
public class ThesisListServlet extends AbstractDatabaseServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String univName = req.getParameter("univName");
		String area =req.getParameter("area");

		if (univName == null || univName.isEmpty() || area == null || area.isEmpty()) {
			/* Redirect to search form. */
			getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(
					req, resp);
		}
			Thesis results = null;
			Message m = null;
			
			// database connection
			Connection conn = null;		
			try {
				conn = DS.getConnection();
				results = ArgomentoTesiDatabase.searchArgomentoTesiBy(DS, univName, area);
				DbUtils.close(conn);
				
			} catch (SQLException ex) {
				m = new Message("Error while getting the university.",
						"XXX", ex.getMessage());
			} finally {
				DbUtils.closeQuietly(conn);
			}
		
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
			// Handle Ajax response (e.g. return JSON data object).

			resp.setContentType("application/json");
			if (results != null) {
				/* NOT IMPLEMENTED YET */
				JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
				jsonWriter.writeObject(convertToJson(results));
				jsonWriter.close();
			}

		} else {
			// Handle normal response (e.g. forward and/or set message as attribute).

			if (m == null && results != null) {
				/** 
				 * Show results to the JSP page. 
				 *
				 */
				req.setAttribute("university", results.getUniversita());
				req.setAttribute("evaluations", results.getListaValutazioni());

				getServletContext().getRequestDispatcher("/jsp/show_thesis.jsp").forward(
						req, resp);
				
			} else { // Error page
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(
						req, resp);
			}
		}	
	}
}
