/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessandro
 *
 */
public class FlowServlet extends AbstractDatabaseServlet {
//
//	private static final long serialVersionUID = -8697374672940193755L;
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
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
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		
//		// TODO: DA SESSIONE
//		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn"); 
//		
//
//		String idFlusso = req.getParameter("idFlow");
//		if (idFlusso == null || idFlusso.isEmpty()) {
//			bookRepo.addBook(title, description, price, pubDate);
//		} else {
//			bookRepo.updateBook(id, title, description, price, pubDate);
//		}
//
//		response.sendRedirect(request.getContextPath() + "/book/");
//	}

	/**
	 * Insert or update the flow sent with a POST form
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn"); 
		

		String operation = req.getParameter("operation");
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {
			/* Error or not authorized. */
			Message m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			return;
		} else if (operation.equals("insert") ) {
			/*
			 * Insert a new University 
			 */
			/* Redirect to existing servlet for Insert flow...... TODO spostare qui */
			getServletContext().getRequestDispatcher("/insert-flow").forward(
					req, resp);
			return;
		} else if (operation.equals("update") ) {
			/*
			 * Updates an existing University 
			 */
			//bookRepo.updateBook(id, title, description, price, pubDate);
		}

		resp.sendRedirect(req.getParameter("returnTo"));
	}
}
