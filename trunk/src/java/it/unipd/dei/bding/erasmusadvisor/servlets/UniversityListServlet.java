package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.CountryCityListBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet for getting lists of university.
 *  
 * It is used in the university search page
 * and in the auto-complete field, through Ajax requests.
 * 
 * <p> Base URL: /university/list
 * 
 * <p> Accepts: GET, AJAX
 * 
 * <p> Operations: SEARCH
 * 
 * @see UniversityListServlet
 * @author Alessandro, Luca, Nicola
 */
public class UniversityListServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private final static String SEARCH = "search";
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1542213213154354L;
	
	/**
	 * Gets the list of universities
	 * 
	 * <p> Ajax requests are recognized through the Header 
	 * "X-Requested-With" set to "XMLHttpRequest"
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

		} else if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
			// Handle Ajax response (e.g. return JSON data object).
			/**
			 * GET A LIST OF UNIVERSITIES from DB
			 * 
			 */	
			String startingWith = req.getParameter("term");
			
			// database connection
			Connection conn = null;
			
			// model
			List<UniversitaBean> nameList = null;
			
			try {
				conn = DS.getConnection();
				
				if (startingWith != null && !startingWith.isEmpty())
					nameList = GetUniversitaValues.getDomainStartingWith(conn, startingWith);
				else 
					nameList = GetUniversitaValues.getDomain(conn);
				
			} catch (SQLException e) {
				// Do nothing..
			} 
			finally {
				DbUtils.closeQuietly(conn);
			}
			
			resp.setContentType("application/json");
			if (nameList != null) {
				/* NOT IMPLEMENTED YET */
				JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
				jsonWriter.writeArray(convertToJson(nameList));
				jsonWriter.close();
			}
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
		String country = req.getParameter("country");
		String city = req.getParameter("city");
		
		// model
		Message m = null;
		List<UniversitaBean> results = null;
		List<CittaBean> cities = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			
			results = UniversitaDatabase.searchUniversityByCity(conn, country, city);
			cities = CittaDatabase.getAllSortByCountry(conn);
			
			
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
		req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));
		
		if (country != null)
			req.setAttribute("searchedCountry", country);
		if (city != null)
			req.setAttribute("searchedCity", city);
		
		
		if (country == null && city == null)
			req.setAttribute("allUniversities", "allUniversities");

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_university.jsp").forward(req, resp);
	}
	
	/**
	 * Preloads the data, such as all the available cities 
	 * the user can filter by
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
		
		List<CittaBean> cities = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			cities = CittaDatabase.getAllSortByCountry(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the university list.", "XXX", ex.getMessage());
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		// Send data to the view 
		req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));
		
		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_university.jsp").forward(req, resp);
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
        	
    	//Message m = new Message("Error while updating the university.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
	
	/**
     * Converts the List of UniversitaBean to a JSON Object
     * 
	 * @param nameList the list of universities
	 */
	private JsonArray convertToJson(List<UniversitaBean> nameList) {
		JsonArrayBuilder jb = Json.createArrayBuilder();
		
		for (UniversitaBean uni : nameList) {
			jb.add(Json.createObjectBuilder()
				     .add("id", uni.getNome())
				     .add("label", uni.getNome())
				     .add("value", uni.getNome()));
		}
		
		return jb.build();
	}
}
