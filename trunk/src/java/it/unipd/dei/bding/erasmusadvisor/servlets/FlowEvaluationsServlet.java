/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneFlussoDatabase;
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
 * @author Mauro, Luca
 *
 */
public class FlowEvaluationsServlet extends AbstractDatabaseServlet 
{
	private static final long serialVersionUID = 2580713092841797453L;
	
	/**
	 * Insert the new flow evaluation into the database
	 * @param req client request
	 * @param resp server response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Verify logged user
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "JuventinoDOC");

		String operation = req.getParameter("operation");
		
		if (operation == null || operation.isEmpty() || !lu.isStudent()) {
			// Error
			Message m = null;
			m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		else if (operation.equals("insert"))
		{
			insert(req, resp, lu);
		} 
		else if (operation.equals("delete"))
		{
			delete(req, resp, lu);
		}	
		
	}

	private void delete (HttpServletRequest req, HttpServletResponse resp, LoggedUser lu)
			throws ServletException, IOException
	{
		// Setup bean and the database connection
		Connection con = null;
		Message m = null;
		
		String id = req.getParameter("id");
		
		try
		{
			con = DS.getConnection();
			ValutazioneFlussoDatabase.deleteEvaluation(con, lu.getUser(), id);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
				.append("/erasmus-advisor/student/evaluations");
		
			resp.sendRedirect(builder.toString());

		}
		catch (SQLException e) 
		{
			// Error management
			e.printStackTrace();
			m = new Message("Error while deleting the evaluation.","", e.getMessage());
			req.setAttribute("message", m);
			errorForward(req, resp); 
			return;
		} 
		finally {
			DbUtils.closeQuietly(con);
		}
		
	}
	
	private void insert (HttpServletRequest req, HttpServletResponse resp, LoggedUser lu)
			throws ServletException, IOException
	{
		// Setup bean and the database connection
		Connection con = null;
		Message m = null;
		
		// Populate the bean
		ValutazioneFlussoBean val = new ValutazioneFlussoBean();
		val.setNomeUtenteStudente(lu.getUser());
		BeanUtilities.populateBean(val, req);
		
		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneFlussoDatabase.creaValutazioneFlusso(con, val);

			DbUtils.close(con);
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
				.append("/erasmus-advisor/flow?id=")
				.append(val.getIdFlusso());
		
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

	// Error management
    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  
    {	
    	getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }


}
