package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.CountryCityListBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet for getting lists of university, used in search university page
 * and in autocomplete field throught ajax request.
 * 
 * Mapped to /university/list
 * @author Alessandro, Luca, Nicola
 *

 */
public class UniversityListServlet extends AbstractDatabaseServlet {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1542213213154354L;
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
				e.printStackTrace();
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
	
	private void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Incoming parameters for the search filter
		String country = req.getParameter("country");
		String city = req.getParameter("city");
		String str;
		
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
		if (results==null || results.isEmpty()) str = "No results for "+city+" in "+country+".";
		else str = "Results for "+city+" in "+country+" :";
		req.setAttribute("str", str);
		req.setAttribute("results", results);
		req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));

		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_university.jsp").forward(req, resp);
	}
	
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

	
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the university.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
	
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
