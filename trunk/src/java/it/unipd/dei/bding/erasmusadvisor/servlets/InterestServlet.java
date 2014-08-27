package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

public class InterestServlet extends AbstractDatabaseServlet 
{

	private static final long serialVersionUID = -177735757618533981L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{		
		String operation = req.getParameter("operation");
		
		Connection conn = null;
		Message m = null;

		if (operation.equals("delete"))
		{
			String flow = req.getParameter("flowID");
			// TODO bisogna usare la sessione per determinare l'utente, questo parametro non è necessario
			String user = req.getParameter("userName");
			
			int results;
			try {
				conn = DS.getConnection();
				results = InteresseDatabase.removeInterest(conn, flow, user);

				if (results > 0)
				{
					return;
				}
			} 
			catch (SQLException ex) 
			{
				// non mi piace...
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
		}
		else if (operation.equals("insert"))
		{
			String flow = req.getParameter("flowID");
			// TODO
			String user = (new LoggedUser(UserType.STUDENTE, "user")).getUser();
			long updatedInterests = 0;
			
			try {
				conn = DS.getConnection();
				InteresseDatabase.addInterest(conn, flow, user);
				updatedInterests = InteresseDatabase.getCountInteresseByFlusso(conn, flow);
			} 
			catch (SQLException ex) 
			{
				m = new Message("Error while inserting you interest.", "", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
			
			if (m == null)
			{		
				resp.setContentType("text/plain"); 
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(updatedInterests + "");
				//resp.setContentType("application/json");
				//JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
				//jsonWriter.writeObject(Json.createObjectBuilder().add("updatedInterests", updatedInterests).build());
				//jsonWriter.close();
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
	}
}