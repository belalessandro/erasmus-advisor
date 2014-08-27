package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

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
		String flow = req.getParameter("flowID");
		String user = req.getParameter("userName");
		
		Connection conn = null;
		Message m = null;

		if (operation.equals("delete"))
		{
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
	}
}