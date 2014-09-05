package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet used for inserting new city's evaluations.
 * 
 * <p> Base URL: /city/evaluations
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: INSERT, DELETE
 * 
 * @author Mauro, Luca
 */
public class CityEvaluationsServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
	private static final String DELETE = "delete";

	private static final long serialVersionUID = 5260727560731303514L;

	/**
	 * Handles insert / delete operation FORM
	 * 
	 * @param request 
	 *                              request from the client
	 * @param response 
	 *                              response to the client 
	 * @throws ServletException
	 *                              if any error occurs while executing the servlet
	 * @throws IOException
	 *                      if any error occurs in the client/server communication.
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Gets operation parameter
		String operation = req.getParameter("operation");

		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");

		/**
		 * Authorization check. Permissions required: STUDENT
		 */
		if (!lu.isStudent() || operation == null || operation.isEmpty() ) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		/** 
		 * OPERATION DISPATCHER 
		 */
		else if (operation.equals(INSERT))
		{
			insert(req, resp, lu);
		} 
		else if (operation.equals(DELETE))
		{
			delete(req, resp, lu);
		}       

	}
	/**
	 * Method for deleting an evaluation.
	 * Redirect to /student/evaluations
	 * 
	 * @param req request object 
	 * @param resp response object
	 * @param lu the user currently logged
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete (HttpServletRequest req, HttpServletResponse resp, LoggedUser lu)
			throws ServletException, IOException
			{
		// Setup bean and the database connection
		Connection con = null;
		Message m = null;

		String city = req.getParameter("city");
		String country = req.getParameter("country");

		try
		{
			con = DS.getConnection();
			ValutazioneCittaDatabase.deleteEvaluation(con, lu.getUser(), city, country);

			// Creating response path
			StringBuilder builder = new StringBuilder()
			.append(req.getContextPath())
			.append("/student/evaluations");

			resp.sendRedirect(builder.toString());

		}
		catch (SQLException e) 
		{
			// Error management
			m = new Message("Error while deleting the evaluation.","", e.getMessage());
			req.setAttribute("message", m);
			errorForward(req, resp); 
			return;
		} 
		finally {
			DbUtils.closeQuietly(con);
		}

			}

	/**
	 * Method used for inserting a new evaluation for the 
	 * current city.
	 * Redirect to the city evaluated.
	 * 
	 * @param req request object 
	 * @param resp response object
	 * @param lu the user currently logged
	 * @throws ServletException
	 * @throws IOException
	 */
	private void insert(HttpServletRequest req, HttpServletResponse resp, LoggedUser lu)
			throws ServletException, IOException
			{

		// Database connection, model and beans
		Connection con = null;

		// Model 
		Message m = null;

		// Beans
		ValutazioneCittaBean val = new ValutazioneCittaBean();

		// Populates bean
		BeanUtilities.populateBean(val, req);

		// Sets additional fields
		val.setNomeUtenteStudente(lu.getUser());

		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneCittaDatabase.createValutazioneCitta(con, val);
			DbUtils.close(con);

			// Creating response path
			StringBuilder builder = new StringBuilder()
			.append(req.getContextPath())
			.append("/city?name=")
			.append(val.getNomeCitta())
			.append("&country=")
			.append(val.getStatoCitta());

			resp.sendRedirect(builder.toString());

		} 
		catch (SQLException e) {
			// Error management

			/** Primary key error */
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) { 

				m = new Message("No duplicate evaluations allowed", "E300", 
						"You have already submitted an evaluation!");
			} else if (e.getSQLState() != null && e.getSQLState().equals("EA005")) { 
				/** Trigger EA005 error */
				m = new Message("Evaluation not allowed.", "E300", 
						"You can not evaluate flows you did not participate in!");
			} else if (e.getSQLState() != null && e.getSQLState().equals("23514")) { 
				/**0 Star ERROR*/
				m = new Message("Evaluation not allowed.", "E300", 
						"Evaluations must be from 1 to 5 stars!");
			} else {
				m = new Message("Error while submitting evaluations.","E200", e.getMessage());
			}
			req.setAttribute("message", m);

			errorForward(req, resp);
			return;
		} 
		finally {
			DbUtils.closeQuietly(con); // *always* closes the connection
		}
			}

	/**
	 * Method used for forwarding to the error page
	 * with the appropriate message.
	 * 
	 * @param req request object 
	 * @param resp response object
	 * @throws ServletException
	 * @throws IOException
	 */
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException  
			{   
		getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
			}
}