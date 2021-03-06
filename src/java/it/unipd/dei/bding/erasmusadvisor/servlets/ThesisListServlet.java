package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
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
 * Manages lists of Thesis.
 * 
 * <p> Base URL: /city/list
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: SEARCH
 * 
 * @see UniversityListServlet
 * @author Nicola, Luca
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

	/**
	 * Gets the list
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
	
	/**
	 * Handles the search operation
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
		List<UniversitaBean> universityDomain = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			
			results = ArgomentoTesiDatabase.searchArgomentoTesi(conn, area, university ,level, language);
			
			// Pre-charging form values
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			universityDomain = GetUniversitaValues.getDomain(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the thesis list.",
					"XXX","Please, contact the admin.");
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
		req.setAttribute("universities", universityDomain);

		if (area != null)
			req.setAttribute("searchedArea", area);
		if (university != null)
			req.setAttribute("searchedUniversity", university);
		if (level != null)
			req.setAttribute("searchedLevel", level);
		if (language != null)
			req.setAttribute("searchedLanguage", language);
		
		
		if (area == null && university == null && level == null && language == null)
			req.setAttribute("allThesis", "allThesis");

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(req, resp);
	}
	
	/**
	 * Preloads the data
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
	private void preload(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		List<AreaBean> areaDomain = null;
		List<LinguaBean> languageDomain = null;
		List<UniversitaBean> universityDomain = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			universityDomain = GetUniversitaValues.getDomain(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the thesis list.", "XXX", "Please, contact the admin.");
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
		req.setAttribute("universities", universityDomain);
		
		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(req, resp);
	}

	/**
     * Handles error forwarding between pages.
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
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
