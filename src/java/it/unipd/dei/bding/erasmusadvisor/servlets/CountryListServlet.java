package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.database.GetStatoValues;
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
 * Servlet for getting lists of Country
 * 
 * <p> Base URL: /country/list
 * 
 * <p> Accepts: AJAX Requests *only*
 * 
 * @author Nicola
 */
public class CountryListServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 124559389265503855L;

	/**
	 * Returns a list of countries 
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
			throws ServletException, IOException {
		
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
			// Handle Ajax response (e.g. return JSON data object).

			/**
			 * GET A LIST OF COUNTRIES from DB
			 * 
			 */
			
			String startingWith = req.getParameter("term");
			
			// database connection
			Connection conn = null;
			
			// model
			List<String> nameList = null;
			
			try {
				conn = DS.getConnection();
					nameList = GetStatoValues.getDomainStartingWith(conn, startingWith);
				
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
				Message m = null;
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}

	}
	
	/**
     * Converts the bean list to a JSON Object
     * 
	 * @param nameList the list
	 */
	private JsonArray convertToJson(List<String> nameList) {
		// eg. {"id":"Botaurus stellaris","label":"Great Bittern","value":"Great Bittern"}
		JsonArrayBuilder jb = Json.createArrayBuilder();
		
		for (String stato : nameList) {
			jb.add(Json.createObjectBuilder()
				     .add("id", stato)
				     .add("label", stato)
				     .add("value", stato));
		}
		
		return jb.build();
	}
}
