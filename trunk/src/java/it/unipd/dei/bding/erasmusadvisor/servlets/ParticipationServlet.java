package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.PartecipazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

public class ParticipationServlet extends AbstractDatabaseServlet 
{

	private static final long serialVersionUID = 7749343141345811031L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{

		String operation = req.getParameter("operation");

		// TODO bisogna usare la sessione per determinare l'utente, questo parametro non è necessario
		String user = (new LoggedUser(UserType.STUDENTE, "user")).getUser();
		
		Connection conn = null;
		Message m = null;

		if (operation.equals("insert"))
		{
			String flow = req.getParameter("flowID");
			String startDateString = req.getParameter("date_from");
			String endDateString = req.getParameter("date_to");
			
			System.out.println(startDateString + " " + endDateString);
			
			SimpleDateFormat f = new SimpleDateFormat("mm/dd/yyyy");
			
			try {
				Date startDate = new Date(f.parse(startDateString).getTime());
				Date endDate = new Date(f.parse(endDateString).getTime());
				
				
				conn = DS.getConnection();
				PartecipazioneDatabase.addParticipation(conn, flow, user, startDate,endDate);

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
}
