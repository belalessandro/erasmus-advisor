/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessandro
 *
 */
public class StudentEvaluateServlet extends AbstractDatabaseServlet {
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
