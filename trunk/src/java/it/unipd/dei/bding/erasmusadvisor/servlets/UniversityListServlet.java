package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
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
 * Servlet for getting lists of university
 * 
 * Mapped to /university/list
 * @author Alessandro, Luca
 *
 */
public class UniversityListServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 1462509389265503855L;

	/**
	 * Gets a list of universities, filtered or not.  
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String operation = req.getParameter("operation");
		String filterByCountry = req.getParameter("filterByCountry");
		String filterByCity = req.getParameter("filterByCity");

		/* SEARCH */
		if (operation != null && !operation.isEmpty() && operation.equals("search")) {
			
			ArrayList<UniversitaBean> universityList = new ArrayList<UniversitaBean>();
			
			if (filterByCountry != null && !filterByCountry.isEmpty()) {
				/* List all the universities */
				
				universityList = null; // get university where stato = filterByCountry
				
			} else if (filterByCity != null && !filterByCity.isEmpty()) {
				/* List all the universities */
				
				universityList = null; // get university where citta = filterByCity
				
			} else {
				/* List all the universities */
				
				universityList = new ArrayList<UniversitaBean>(); // get all
				
			}
			
			/* Send the list of universities (if any found, with the selected criteria) */
			req.setAttribute("universityList", universityList);
			
			/* Show results to the JSP page. */
			getServletContext().getRequestDispatcher("/jsp/search_university.jsp").forward(
					req, resp);
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
		else { /* NO OPERATION */
			
			/* Redirect to the Search JSP page */

			List<CittaBean> cities = null;
			Connection conn = null;
			Message m = null;
			
			try {
				conn = DS.getConnection();
				cities = CittaDatabase.getAllSortByCountry(conn);
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the search page.", "XXX", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
	
			if (m == null)
			{
				req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));
				getServletContext().getRequestDispatcher("/jsp/search_university.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}

	}
	
	private JsonArray convertToJson(List<UniversitaBean> nameList) {
		// eg. {"id":"Botaurus stellaris","label":"Great Bittern","value":"Great Bittern"}
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