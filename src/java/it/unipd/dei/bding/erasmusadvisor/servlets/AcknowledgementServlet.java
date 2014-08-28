package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.database.RiconoscimentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

public class AcknowledgementServlet  extends AbstractDatabaseServlet 
{

	private static final long serialVersionUID = 6205996354245462055L;

	// il controllo errori di questa classe è inesistente
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String operation = req.getParameter("operation");

		Connection conn = null;
		Message m = null;

		if (operation.equals("insert"))
		{
			String flow = req.getParameter("flowID");
			int classId = Integer.parseInt(req.getParameter("classID"));
	
			try {
				conn = DS.getConnection();
				RiconoscimentoDatabase.addRiconoscimento(conn, flow, classId);
			} 
			catch (SQLException ex) 
			{
				ex.printStackTrace();
				m = new Message("Error while setting the acknoledgement.", "", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
			
			if (m == null)
			{
				resp.setContentType("text/plain"); 
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write("success");
			}
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}

}
