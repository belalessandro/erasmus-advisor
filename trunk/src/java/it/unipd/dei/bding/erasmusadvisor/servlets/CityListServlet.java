package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetStatoValues;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

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
 * Manages lists of City.
 * 
 * <p> Base URL: /city/list
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: SEARCH
 * 
 * @see UniversityListServlet
 * @author Alessandro, Luca
 */
public class CityListServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private final static String SEARCH = "search";

	private static final long serialVersionUID = -7957292442185949670L;

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
		String stato = req.getParameter("country");
		String siglaLingua = req.getParameter("language");
		

		// model
		Message m = null;
		List<CitySearchRow> results = null;
		List<LinguaBean> languageDomain = null;
		List<String> countries = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			
			if (siglaLingua != null && stato != null)
			{ 	// Filter By SiglaLingua AND Stato
				results = CittaDatabase.filterCityByStatoLingua(conn, stato, siglaLingua);
			}
			else if (siglaLingua != null) { // Filter By SiglaLingua
				results = CittaDatabase.filterCityBySiglaLingua(conn, siglaLingua);
			} 
			else if (stato != null) { // Filter By Stato
				results = CittaDatabase.filterCityByStato(conn, stato);
			}
			else
			{ 	// Filters OFF
				results = CittaDatabase.filterCity(conn);
			}
			
			// Pre-charging form values
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			countries = GetStatoValues.getValues(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the city list.",
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
		req.setAttribute("countries", countries);

		if (stato != null)
			req.setAttribute("searchedCountry", stato);
		if (siglaLingua != null)
			req.setAttribute("searchedLanguage", siglaLingua);

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_city.jsp").forward(req, resp);
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
			m = new Message("Error while getting the city list.", "XXX", "Please, contact the admin.");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		// Send data to the view 
		req.setAttribute("languageDomain", languageDomain);
		req.setAttribute("countries", countries);
		
		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_city.jsp").forward(req, resp);
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
