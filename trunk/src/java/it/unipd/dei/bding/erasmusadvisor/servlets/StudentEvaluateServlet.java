/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;


/**
 * @author Alessandro
 *
 */
public class StudentEvaluateServlet extends AbstractDatabaseServlet {

	/**
	 * Something
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
	/**
	 * Something
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Verify logged user
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "JuventinoDOC");
		

		
		//...
	
		
		// Setup bean and the database connection
		ValutazioneCittaBean val = new ValutazioneCittaBean();
		val.setNomeUtenteStudente(lu.getUser());

			
		Connection con = null;
		Message m = null;
		
		// Populate the bean
		BeanUtilities.populateBean(val, req);
		
		
//		resp.setContentType("text/html");
//		
//		PrintWriter out = resp.getWriter();
//		
//		
//		out.println("<!DOCTYPE html>");
//		out.println("<html><head><meta charset=\"utf-8\"></head>");
//		out.println("<body>");
//		out.println("<h1>Hello World!</h1>");
//		out.println("<h2>" + req.getMethod() + "</h2>");
//		out.println("<h2>" + req.getRequestURI() + "</h2>");
//		out.println("<h2>" + req.getRequestURL() + "</h2>");
//		out.println("<h2>" + req.getServletPath() + "</h2>");
//		out.println("</body>");
//		out.println("</html>");
//		out.flush();
//		out.close();
		
		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneCittaDatabase.createValutazioneCitta(con, val);
			DbUtils.close(con);
			
			req.setAttribute("city", val.getNomeCitta());
			req.setAttribute("country", val.getStatoCitta());
			StringBuilder builder = new StringBuilder()
				.append("/erasmus-advisor/city?city=")
				.append(val.getNomeCitta())
				.append("&country=")
				.append(val.getStatoCitta());
		
			resp.sendRedirect(builder.toString());
			
			
		} catch (SQLException e) {
			m = new Message("Error while submitting evaluations.","XXX", e.getMessage());
			req.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}	
		
	}
}
