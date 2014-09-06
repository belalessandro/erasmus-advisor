package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.CountryCityListBean;
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
 * Pre-processes the Insert FORM of a new University.
 * 
 * It returns the JSP page insert_unversity.jsp, populated with the
 * required fields
 * 
 * <p> Base URL: /university/insert
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: (none)
 * 
 * @author Alessandro, Luca
 */
public class InsertUniversityServlet extends AbstractDatabaseServlet {
	
	private static final long serialVersionUID = -8032658403183722097L;

	/**
	 * Forwards the pre-loaded data to the insert form
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
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: FlowManager, Coordinator
		 */
		if (! (lu.isCoord() || lu.isFlowResp())  ) {
			req.setAttribute("message", 
					new Message("Not authorized.", "E200", ""));
			errorForward(req, resp);
			return;
		} 

		// beans, db connection, model
		List<CittaBean> cities = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			cities = CittaDatabase.getAllSortByCountry(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the university.", "E200", "Please, contact the admin.");
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{
			// forward to the insert FORM
			req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));
			getServletContext().getRequestDispatcher("/jsp/insert_university.jsp").forward(req, resp);
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
