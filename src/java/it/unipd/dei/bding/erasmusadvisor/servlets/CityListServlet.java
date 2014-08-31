package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetStatoValues;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchRow;
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
 * Mapped to /city/list
 * 
 * @author Alessandro, Luca
 * 
 */
public class CityListServlet extends AbstractDatabaseServlet {
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
		req.setAttribute("countries", countries);

		if (stato != null)
			req.setAttribute("searchedCountry", stato);
		if (siglaLingua != null)
			req.setAttribute("searchedLanguage", siglaLingua);

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_city.jsp").forward(req, resp);
	}
	
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
			m = new Message("Error while getting the city list.", "XXX", ex.getMessage());
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

	
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
