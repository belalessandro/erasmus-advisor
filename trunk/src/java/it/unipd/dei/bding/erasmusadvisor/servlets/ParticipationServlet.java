package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.PartecipazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Servlet used for managing a specific participation
 * of a student to a flow.
 * <p> Base URL: /flow/participation
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: INSERT
 * 
 * @author Luca
 */
public class ParticipationServlet extends AbstractDatabaseServlet 
{

	private static final long serialVersionUID = 7749343141345811031L;

	/**
	 * Handles an operation FORM
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
			throws ServletException, IOException 
	{

		Connection conn = null;
		Message m = null;
		
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

		// insert operation
		else if (operation.equals("insert"))
		{
			String flow = req.getParameter("flowID");
			String startDateString = req.getParameter("date_from");
			String endDateString = req.getParameter("date_to");
			
			SimpleDateFormat f = new SimpleDateFormat("mm/dd/yyyy");
			
			try {
				Date startDate = new Date(f.parse(startDateString).getTime());
				Date endDate = new Date(f.parse(endDateString).getTime());
				
				
				conn = DS.getConnection();
				PartecipazioneDatabase.addParticipation(conn, flow, lu.getUser(), startDate,endDate);

			} 
			catch (SQLException | ParseException ex) 
			{
				m = new Message("Error while adding your partecipation.", "", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
			
			if (m == null)
			{
				// Creating response path
				StringBuilder builder = new StringBuilder()
					.append("/erasmus-advisor/flow?id=")
					.append(flow);
			
				resp.sendRedirect(builder.toString());
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
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
