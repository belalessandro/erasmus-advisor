package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.ThesisSearchRow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Mapped to /city/list
 * 
 * @author Nicola, Luca
 * 
 */
public class ThesisListServlet extends AbstractDatabaseServlet {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1542351353154354L;
	/**
	 * Operation constants
	 */
	private final static String SEARCH = "search";

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
		{
		// incoming parameters
		String operation = req.getParameter("operation");
		
		if (operation != null && !operation.isEmpty()
				&& operation.equals(SEARCH)) {
			/**
			 * SEARCH
			 */
			search(req, resp);

		} else {
			/**
			 * default: PRELOAD FORM
			 */
			preload(req, resp);

		}
	}
	
	private void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Incoming parameters for the search filter
		String area = req.getParameter("area");
		String university = req.getParameter("university");
		String level = req.getParameter("level");
		String language = req.getParameter("language");
		
		// model
		Message m = null;
		List<ThesisSearchRow> results = null;
		List<AreaBean> areaDomain = null;
		List<LinguaBean> languageDomain = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			
			results = ArgomentoTesiDatabase.searchArgomentoTesi(conn, area, university ,level, language);
			
			// Pre-charging form values
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the thesis list.",
					"XXX", ex.getMessage());
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} finally {
			DbUtils.closeQuietly(conn); // *always* close the connection
		}
		

		// Send data to the view 
		req.setAttribute("results", results);
		req.setAttribute("languageDomain", languageDomain);
		req.setAttribute("areaDomain", areaDomain);

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(req, resp);
	}
	
	private void preload(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		List<AreaBean> areaDomain = null;
		List<LinguaBean> languageDomain = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the thesis list.", "XXX", ex.getMessage());
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		// Send data to the view 
		req.setAttribute("languageDomain", languageDomain);
		req.setAttribute("areaDomain", areaDomain);
		
		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(req, resp);
	}

	
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
