package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneInsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneUniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;


/**
 * Servlet used for manage and display all student's evaluations.
 * 
 * <p> Base URL: /city/evaluations
 * 
 * <p> Accepts: GET
 * 
 * @author Luca
 */
public class StudentEvaluationsServlet extends AbstractDatabaseServlet 
{
	private static final long serialVersionUID = 2083328547201501964L;

	/**
	 * Get all evaluations given by the logged student.
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
		
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: STUDENT
		 */
		if (!lu.isStudent() ) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		
		
		// Database Connection
		Connection conn = null;
		Message m = null;
		
		// Data model
		List<ValutazioneCittaBean> cities = null;
		List<ValutazioneFlussoBean> flows = null;
		List<ValutazioneInsegnamentoBean> classes = null;
		List<ValutazioneTesiBean> thesis = null;
		List<ValutazioneUniversitaBean> universities = null;
		
		try {
			conn = DS.getConnection();
			
			// Filling entities
			cities = ValutazioneCittaDatabase.getEvalByUser(conn, lu.getUser());
			flows = ValutazioneFlussoDatabase.getEvalByUser(conn, lu.getUser());
			classes = ValutazioneInsegnamentoDatabase.getEvalByUser(conn, lu.getUser());
			thesis = ValutazioneTesiDatabase.getEvalByUser(conn, lu.getUser());
			universities = ValutazioneUniversitaDatabase.getEvalByUser(conn, lu.getUser());
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the evaluations resume page.", "", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{	
			// Update the view with the content retrieved
			req.setAttribute("cities", cities);
			req.setAttribute("classes", classes);
			req.setAttribute("flows", flows);
			req.setAttribute("thesis", thesis);
			req.setAttribute("universities", universities);
			getServletContext().getRequestDispatcher("/jsp/user_evaluations.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
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
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
