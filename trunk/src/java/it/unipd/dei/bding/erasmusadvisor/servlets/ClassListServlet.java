package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.TeachingSearchRow;

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
 * Manages lists of Class.
 * 
 * <p> Base URL: /class/list
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: SEARCH
 * 
 * @see UniversityListServlet
 * @author Nicola, Luca
 */
public class ClassListServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private final static String SEARCH = "search";
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2535254543154354L;


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
		// Gets operation parameter
		String operation = req.getParameter("operation");
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: LOGGED
		 */
		if (lu == null ) {
			req.setAttribute("message", 
					new Message("Not authorized.", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		/** 
		 * OPERATION DISPATCHER 
		 */
		else if (operation != null && ! operation.isEmpty() 
				&& operation.equals(SEARCH)  ) {
			/**
			 * SEARCH
			 */
			search(req, resp);

		} 
		else {
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
		String yearStr = req.getParameter("year");
		String periodStr = req.getParameter("semester");
		String language = req.getParameter("language");
		
		//Pre-processing
		Integer year = (yearStr != null ? Integer.parseInt(yearStr) : null);
		Integer period = (periodStr != null ? Integer.parseInt(periodStr) : null);
		
		// model
		Message m = null;
		List<TeachingSearchRow> results = null;
		List<AreaBean> areaDomain = null;
		List<LinguaBean> languageDomain = null;
		List<UniversitaBean> universityDomain = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			
			results = InsegnamentoDatabase.searchInsegnamento(conn, area, university, year, period, language);
			
			// Pre-charging form values
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			universityDomain = GetUniversitaValues.getDomain(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the classes list.",
					"XXX", "Please, contact the admin.");
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
		if (year != null)
			req.setAttribute("searchedYear", year);
		if (period != null)
			req.setAttribute("searchedPeriod", period);
		if (language != null)
			req.setAttribute("searchedLanguage", language);
		
		if (area == null && university == null && year == null && period==null && language == null)
			req.setAttribute("allClasses", "allClasses");

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_class.jsp").forward(req, resp);
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
			m = new Message("Error while getting the classes list.", "XXX", "Please, contact the admin.");
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
		getServletContext().getRequestDispatcher("/jsp/search_class.jsp").forward(req, resp);
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
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}