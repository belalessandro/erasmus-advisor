package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetCorsoDiLaureaValues;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
 * Servlet for getting lists of courses
 * 
 * Mapped to /course/list
 * @author Nicola
 */
//Nb: This Servlet is only for Ajax Request

public class CourseListServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 124559389265503855L;

	/**
	 * Returns a list of courses 
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
			// Handle Ajax response (e.g. return JSON data object).

			/**
			 * GET A LIST OF COURSES from DB
			 * 
			 */
			
			String startingWith = req.getParameter("term");
			String university = req.getParameter("university");
			
			// database connection
			Connection conn = null;
			
			// model
			List<CorsoDiLaureaBean> nameList = null;
			
			// Json objects
			JsonWriter jsonWriter = null;
			
			try {
				conn = DS.getConnection();
				
				if (startingWith != null && !startingWith.isEmpty() && university != null && !university.isEmpty())
					nameList = GetCorsoDiLaureaValues.getDomainStartingWith(conn, startingWith, university);
				else 
				{
					// tell the user to select a university
					if(university == null || university.isEmpty())
					{
						jsonWriter = Json.createWriter(resp.getWriter());
						jsonWriter.writeObject(convertToJson("error","university"));
						jsonWriter.close();
					}
					else
						nameList = GetCorsoDiLaureaValues.getDomain(conn, university);
				}
				
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
				
				jsonWriter = Json.createWriter(resp.getWriter());
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
	 * Create a new json object to send
	 * @param key key of the json
	 * @param value value of the json
	 * @return the json object
	 */
	private JsonObject convertToJson(String key, String value)
	{
		JsonObjectBuilder jb = Json.createObjectBuilder();
		
		jb.add(key,value);
		
		return jb.build();
		
	}
	
	/**
	 * Set up the json with the appropriate values after courses are selected.
	 * @param nameList courses list
	 * @return json array to send
	 */
	private JsonArray convertToJson(List<CorsoDiLaureaBean> nameList) {
		// eg. {"id":"Botaurus stellaris","label":"Great Bittern","value":"Great Bittern"}
		JsonArrayBuilder jb = Json.createArrayBuilder();
		
		/*
		 * NEW VERSION
		 */
		for (CorsoDiLaureaBean corso : nameList) {
			jb.add(Json.createObjectBuilder()
				     .add("id", corso.getNome())
				     .add("label", corso.getNome())
				     .add("value", corso.getNome() + " (" + corso.getLivello() + ")"));
		}
		
		return jb.build();
	}
	
	
	
//	private JsonArray convertToJson(List<CorsoDiLaureaBean> nameList) {
//		// eg. {"id":"Botaurus stellaris","label":"Great Bittern","value":"Great Bittern"}
//		JsonArrayBuilder jb = Json.createArrayBuilder();
//		
//		for (CorsoDiLaureaBean corso : nameList) {
//			jb.add(Json.createObjectBuilder()
//				     .add("id", corso.getNome())
//				     .add("label", corso.getNome())
//				     .add("value", corso.getNome()));
//		}
//		
//		return jb.build();
//	}
}
