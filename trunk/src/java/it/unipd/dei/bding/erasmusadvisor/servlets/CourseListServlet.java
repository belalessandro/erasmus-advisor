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
	 * Gets a list of courses 
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
			
			// database connection
			Connection conn = null;
			
			// model
			List<CorsoDiLaureaBean> nameList = null;
			
			try {
				conn = DS.getConnection();
				
				if (startingWith != null && !startingWith.isEmpty())
					nameList = GetCorsoDiLaureaValues.getDomainStartingWith(conn, startingWith);
				else 
					nameList = GetCorsoDiLaureaValues.getDomain(conn);
				
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
	
	private JsonArray convertToJson(List<CorsoDiLaureaBean> nameList) {
		// eg. {"id":"Botaurus stellaris","label":"Great Bittern","value":"Great Bittern"}
		JsonArrayBuilder jb = Json.createArrayBuilder();
		
		for (CorsoDiLaureaBean corso : nameList) {
			jb.add(Json.createObjectBuilder()
				     .add("id", corso.getNome())
				     .add("label", corso.getNome())
				     .add("value", corso.getNome()));
		}
		
		return jb.build();
	}
}
