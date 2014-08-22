/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Mauro
 * Class used for inserting new city's evaluations.
 */
public class CityEvaluationsServlet extends AbstractDatabaseServlet {

	/**
	 * doGet not used
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException { }
	
	/**
	 * Insert the new city evaluation into the database
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Verify logged user
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "JuventinoDOC");
		
		// Setup bean and the database connection
		Connection con = null;
		Message m = null;
		
		// Populate the bean
		ValutazioneCittaBean val = new ValutazioneCittaBean();
		val.setNomeUtenteStudente(lu.getUser());
		BeanUtilities.populateBean(val, req);
		
		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneCittaDatabase.createValutazioneCitta(con, val);
			DbUtils.close(con);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
				.append("/erasmus-advisor/city?name=")
				.append(val.getNomeCitta())
				.append("&country=")
				.append(val.getStatoCitta());
		
			resp.sendRedirect(builder.toString());
			
			
		} catch (SQLException e) {
			// Error management
			m = new Message("Error while submitting evaluations.","XXX", e.getMessage());
			req.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}	
		
	}
}