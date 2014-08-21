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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

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
		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_STUDENT, "mario.rossi");
		
//		resp.setContentType("text/html");
//		PrintWriter out = resp.getWriter();
//		
//		out.println("<!DOCTYPE html>");
//		out.println("<html><head><meta charset=\"utf-8\"></head>");
//		out.println("<body>");
//		out.println("<h1>Hello World!</h1>");
//		out.println("</body>");
//		out.println("</html>");
//		out.flush();
//		out.close();
		
		
		// Check evaluation category
		String typeOfEvaluation = req.getParameter("TypeOfEvaluation");
		//...
	
		
		// Setup bean and the database connection
		ValutazioneCittaBean val = new ValutazioneCittaBean();
		// TODO: recuperare il nome utente in qualche modo
		val.setNomeUtenteStudente("JuventinoDOC");
		val.setDataInserimento(Date.valueOf(LocalDate.now()));
			
		Connection con = null;
		Message m = null;
		
		// Populate the bean
		BeanUtilities.populateBean(val, req);
		
		
		try {
			// Starting database operations
			con = DS.getConnection();
			ValutazioneCittaDatabase.createValutazioneCitta(con, val);
			DbUtils.close(con);
			
			getServletContext().getRequestDispatcher("/jsp/show_city.jsp").forward(req, resp);
			
		} catch (SQLException e) {
			m = new Message("Error while submitting evaluations.","XXX", e.getMessage());
			req.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}
		
		
		// Populate the bean
// 		
// 		out.println(val.getNomeUtenteStudente());
// 		out.println(val.getDataInserimento().toString());
// 		out.println(val.getNomeCitta());
// 		out.println(val.getStatoCitta());
//		out.println(val.getCostoDellaVita());
//		out.println(val.getDisponibilitaAlloggi());
//		out.println(val.getVivibilitaUrbana());
//		out.println(val.getVitaSociale());
//		out.println(val.getCommento());
		
		
		
		
		
		// Save parameters to the DB
		
		
		
	}
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		// TODO: DA SESSIONE
//		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_STUDENT, "erick.burn");
//
//		if (!lu.isStudent()) { // Not authorized
//			resp.sendRedirect("/login");
//			return;
//		}
//		
//		String id = req.getParameter("id");
//
//		if (id != null && !id.isEmpty()) {
//			FlussoBean f= new FlussoBean();// GET Flusso.lookupBookById(id);
//			req.setAttribute("book", book);
//			req.setAttribute("bookPubDate", dateFormat.format(book.getPubDate()));
//		}
//
//		/* Redirect to book-form. */
//		getServletContext().getRequestDispatcher("/WEB-INF/pages/book-form.jsp").forward(
//				request, response);
//		
//		
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		// TODO: DA SESSIONE
//		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_STUDENT, "erick.burn");
//
//		if (!lu.isStudent()) { // Not authorized
//			resp.sendRedirect("/login");
//			return;
//		}
//				
//	}

}
