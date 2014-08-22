package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.University;
import it.unipd.dei.bding.erasmusadvisor.resources.UniversityEvaluationsAverage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;

/**
 * Mapped to /university
 * @author Alessandro, Luca
 *
 */
public class UniversityServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 1462509389265503855L;

	/**
	 * Get the details of a specific university
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String univName = req.getParameter("name");

		if (univName == null || univName.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_university.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		University results = null;
		Message m = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			results = UniversitaDatabase.searchUniversityModelByName(conn, univName);
			DbUtils.close(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the university.",
					"XXX", ex.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		
		
		/**
		 *  Send the university model to the appropriate output (Ajax or normal)
		 *
		 */
		
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
			// Handle Ajax response (e.g. return JSON data object).

			resp.setContentType("application/json");
			if (results != null) {
				/* NOT IMPLEMENTED YET */
				JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
				jsonWriter.writeObject(convertToJson(results));
				jsonWriter.close();
			}

		} else {
			// Handle normal response (e.g. forward and/or set message as attribute).

			if (m == null && results != null) {
				/** 
				 * Show results to the JSP page. 
				 *
				 */

				req.setAttribute("university", results.getUniversita());
				req.setAttribute("evaluations", results.getListaValutazioni());
				req.setAttribute("evaluationsAvg", new UniversityEvaluationsAverage(results.getListaValutazioni()));

				getServletContext().getRequestDispatcher("/jsp/show_university.jsp").forward(req, resp);
								
			}
			else 
			{ // Error page
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}

	}
	
	/**
	 * Insert or update the university sent with a POST form
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn"); 

		String operation = req.getParameter("operation");
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {
			/* Error or not authorized. */
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			return;
		} else if (operation.equals("insert") ) {
			/**
			 * Insert a new University 
			 */
			
			// bean, model and database connection
			UniversitaBean uni = new UniversitaBean(); 
			Message m = null;
			Connection conn = null;
			
			// Populate bean from the FORM submitted
			BeanUtilities.populateBean(uni, req);
			
			try {
				// Start of database operation
				conn = DS.getConnection();
				UniversitaDatabase.createUniversita(conn, uni);
				DbUtils.close(conn);
				// End of database operation
				
			} catch (SQLException ex) {
				m = new Message("Error while inserting the university.",
						"XXX", ex.getMessage());
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(
					req, resp); // ERROR PAGE
				return;
			} finally {
				DbUtils.closeQuietly(conn);
			}
			
		} else if (operation.equals("update") ) {
			/**
			 * Updates an existing University 
			 */
			//UniversitaDatabase.updateUniversita(conn, uni);
		}
		
		resp.sendRedirect(req.getParameter("returnTo"));
	}

	private JsonObject convertToJson(University uni) {
		/* NOT IMPLEMENTED YET */
		JsonObject json = Json.createObjectBuilder()
			     .add("firstName", "John")
			     .add("lastName", "Smith")
			     .add("age", 25)
			     .add("address", Json.createObjectBuilder()
			         .add("streetAddress", "21 2nd Street")
			         .add("city", "New York")
			         .add("state", "NY")
			         .add("postalCode", "10021"))
			     .add("phoneNumber", Json.createArrayBuilder()
			         .add(Json.createObjectBuilder()
			             .add("type", "home")
			             .add("number", "212 555-1234"))
			         .add(Json.createObjectBuilder()
			             .add("type", "fax")
			             .add("number", "646 555-4567")))
			     .build();
		
		return json;
	}
}
