package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CoordinatoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ResponsabileFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Notifications;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;


/**
 * Servlet used for managing all coordinator's and
 * flow manager's notifications.
 * 
 * <p> Base URL: /notifications
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: DELETE
 * 
 * @author Mauro
 */
public class NotificationsServlet extends AbstractDatabaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Operation constants
	 */
    private static final String AJAX = "ajax";
    
    /**
     * Default constructor. 
     */
    public NotificationsServlet() {  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: FlowManager, Coordinator
		 */
		if (! (lu.isCoord() || lu.isFlowResp())) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		
		// required variables
		Notifications notifications = null;
		Connection con = null;
		Message m = null;
		
		if(lu.isCoord())
		{
			try {
				con = DS.getConnection();
				
				notifications = CoordinatoreDatabase.getNotifications(con, lu.getUser());
				
				DbUtils.close(con);
			} catch (SQLException e) {
				m = new Message("Error while getting notifications.", "XXX", "Please, contact the admin.");
				req.setAttribute("message", m);
				errorForward(req, resp);
				return;
			} finally {
				DbUtils.closeQuietly(con);
			}
			
			if(m == null && notifications != null)
			{
				// send parameters
				req.setAttribute("flowManagers", notifications.getResponsabiliFlusso());
				req.setAttribute("classes", notifications.getInsegnamenti());
				req.setAttribute("classProfessors", notifications.getProfessoriInsegnamenti());
				req.setAttribute("theses", notifications.getArgomentiTesi());
				req.setAttribute("thesisProfessors", notifications.getProfessoriTesi());
				
				// send the results to the relatve JSP page
				getServletContext().getRequestDispatcher("/jsp/user_notifications.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		} else if(lu.isFlowResp())
		{
			try {
				con = DS.getConnection();
				
				notifications = ResponsabileFlussoDatabase.getNotifications(con, lu.getUser());
				
				DbUtils.close(con);
			} catch (SQLException e) {
				m = new Message("Error while getting notifications.", "XXX", "Please, contact the admin.");
				req.setAttribute("message", m);
				
				PrintWriter w = resp.getWriter();
				w.println("<hmtl>");
				w.println("<body>");
				w.println("<p>" + e.getMessage() + "</p>");
				w.println("</body>");
				w.println("<hmtl>");
				w.flush();
				w.close();
				
				errorForward(req, resp);
				return;
			} finally {
				DbUtils.closeQuietly(con);
			}
			
			if(m == null && notifications != null)
			{
				// send parameters
				req.setAttribute("classes", notifications.getInsegnamenti());
				req.setAttribute("classProfessors", notifications.getProfessoriInsegnamenti());
				req.setAttribute("theses", notifications.getArgomentiTesi());
				req.setAttribute("thesisProfessors", notifications.getProfessoriTesi());
				
				// send the results to the relatve JSP page
				getServletContext().getRequestDispatcher("/jsp/user_notifications.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		// Gets user from session
		HttpSession session = request.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		String operation = null;
		
		if(request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest"))
			operation = "ajax";
		else
			operation = request.getParameter("operation");
		
		if (operation == null || operation.isEmpty()) {
			// Error or not authorized. 
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
			return;
		} 
		else if (operation.equals(AJAX)) 
		{	
			acceptDiscardEntity(lu, request, response);
		}
	}
	
	/**
	 * Manages all the accepting and discarding operations performed
	 * by a flow manager or a coordinator. A json object is sent back to the 
	 * origin page to get the view up to date with the model.
	 * 
	 * @param lu logged user
	 * @param request request object
	 * @param response response object
	 * @throws ServletException
	 * @throws IOException
	 */
	private void acceptDiscardEntity(LoggedUser lu, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// get json object
		response.setContentType("application/json");
		JsonReader reader = Json.createReader(request.getInputStream());
		JsonObject json = reader.readObject();
		reader.close();
		
		// modify the instance into the database
		Message m = null;
		Connection con = null;
		
		// get parameters
		String execute = json.getString("execute");
		String type = json.getString("type");
		
		// preparing to write json response
		JsonObjectBuilder builder = Json.createObjectBuilder();
		try {
			con = DS.getConnection();
			
			// checking the operation to perform
			if(execute.equals("accept"))
			{
				// checking the user
				if (type.equals("flowmanager")) {
					if(lu.isCoord())
					{
						ResponsabileFlussoDatabase.enableResponsabileFlusso(con, json.getString("primarykey"));
						builder.add("flowmanager","enabled");
					}
				} else if (type.equals("class")) {
					if(lu.isFlowResp())
					{
						InsegnamentoDatabase.changeClassStatus(con, "VERIFIED", json.getInt("primarykey"));
						builder.add("class","enabled");
					}
				} else if (type.equals("thesis")) {
					if(lu.isFlowResp())
					{
						ArgomentoTesiDatabase.changeThesisStatus(con, "VERIFIED", json.getInt("primarykey"));
						builder.add("thesis","enabled");
					}
				}
			}
			else if(execute.equals("discard"))
			{
				// checking the user
				if (type.equals("flowmanager")) {
					if(lu.isCoord())
					{
						ResponsabileFlussoDatabase.disableResponsabileFlusso(con, json.getString("primarykey"));
						builder.add("flowmanager","disabled");
					}
				} else if (type.equals("class")) {
					if(lu.isFlowResp())
					{
						InsegnamentoDatabase.changeClassStatus(con, "DISABLED", json.getInt("primarykey"));
						builder.add("class","disabled");
					}
				} else if (type.equals("thesis")) {
					if(lu.isFlowResp())
					{
						ArgomentoTesiDatabase.changeThesisStatus(con, "DISABLED", json.getInt("primarykey"));
						builder.add("thesis","disabled");
					}
				}
			}
			
			DbUtils.close(con);
		} catch (SQLException e) {
			m = new Message("Error while accepting/discarding the " + type, "XXX","Please, contact the admin.");
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}	
		
		// writing the json object to the page
		JsonObject out = builder.build();
		
		JsonWriter writer = Json.createWriter(response.getOutputStream());
		writer.writeObject(out);
		writer.close();
	}
	
	
	/**
	 * Function useful to redirect to the error page.
	 * @param request request object
	 * @param response response object
	 * @throws ServletException 
	 * @throws IOException
	 */
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); 
    }

}
