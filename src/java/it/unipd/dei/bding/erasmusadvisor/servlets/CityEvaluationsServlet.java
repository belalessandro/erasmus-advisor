package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private static final long serialVersionUID = 5260727560731303514L;

	/**
	 * Handles insert / delete operation FORM
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Verify logged user
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "JuventinoDOC");

		String operation = req.getParameter("operation");
		
		if (operation == null || operation.isEmpty() || !lu.isStudent()) {
			// Error
			Message m = null;
			m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		else if (operation.equals("insert"))
		{
			insert(req, resp, lu);
		} 
		else if (operation.equals("delete"))
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
				.append("/erasmus-advisor/student/evaluations");
		
			resp.sendRedirect(builder.toString());

		}
		catch (SQLException e) 
		{
			// Error management
			e.printStackTrace();
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

		// Setup bean and the database connection
		Connection con = null;
		Message m = null;
		
		// Populate the bean
		ValutazioneCittaBean val = new ValutazioneCittaBean();
		val.setNomeUtenteStudente(lu.getUser());
		BeanUtilities.populateBean(val, req);
		
		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneCittaDatabase.createValutazioneCitta(con, val);
			DbUtils.close(con);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
				.append("/erasmus-advisor/city?name=")
				.append(val.getNomeCitta())
				.append("&country=")
				.append(val.getStatoCitta());
		
			resp.sendRedirect(builder.toString());

		} 
		catch (SQLException e) {
			// Error management
			m = new Message("Error while submitting evaluations.","XXX", e.getMessage());
			req.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
			return;
		} 
		finally {
			DbUtils.closeQuietly(con);
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