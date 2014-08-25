package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.database.StudenteDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

public class StudentProfileServlet extends AbstractDatabaseServlet 
{
	/**
	 * (Autorizzazioni: solo STUDENTE)
	 * 
	 * mappato su /student/profile
	 * 
	 * quando riceve GET
	 * 			-> ritorna su user_profile.jsp tutti i campi collegati allo studente loggato
	 * 
	 * 
	 * quando riceve POST
	 *   		-> Se operazione è "update" modifica i campi relativi allo studente loggato
	 *   
	 *   @author: luca
	 */
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		Connection conn = null;
		Message m = null;
		Student stud = null;
		
		HttpSession session = req.getSession(false);
		if (session == null)
		{
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
		LoggedUser lu = (LoggedUser)req.getSession().getAttribute("loggedUser");
		
		try {
			conn = DS.getConnection();
			stud = StudenteDatabase.getStudent(conn, lu.getUser());

		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the user profile page.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{
			req.setAttribute("student", stud.getStudente());
			req.setAttribute("iscrizione", stud.getIscrizione());
			req.setAttribute("course", stud.getCorso());
			getServletContext().getRequestDispatcher("/jsp/user_profile.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
}
