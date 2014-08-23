/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.FlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetCertificatiLinguisticiValues;
import it.unipd.dei.bding.erasmusadvisor.database.InteresseDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.RiconoscimentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Flow;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luca
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
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn"); 
		

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
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_class.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		Flow results = null;
		Message m = null;
		List<CertificatiLinguisticiBean> certificatesDomain = null;
		List<CorsoDiLaureaBean> possibileCourses = null;
		List<InsegnamentoBean> recognisedClasses = null;
		long interests = 0;

		// the connection to database
		Connection conn = null;
		
		try {
			conn = DS.getConnection();
			results = FlussoDatabase.getFlusso(DS, ID);
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			possibileCourses = CorsoDiLaureaDatabase.getPossibleCourses(conn, results.getResponsabile());
			interests = InteresseDatabase.getCountInteresseByFlusso(conn, ID);
			recognisedClasses = RiconoscimentoDatabase.getInsegnamentiRiconosciuti(conn, ID);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "XXX", ex.getMessage());
		} 
		
		
		/**
		 *  Send the university model to the appropriate output
		 *
		 */
		// Handle normal response (e.g. forward and/or set message as attribute).

		if (m == null && results != null) 
		{
			/** 
			 * Show results to the JSP page. 
			 */
			req.setAttribute("flow", results.getFlusso());
			req.setAttribute("manager", results.getResponsabile());
			req.setAttribute("origins", results.getCorsi());
			req.setAttribute("evaluations", results.getListaValutazioni());
			req.setAttribute("certificates", results.getCertificati());
			req.setAttribute("interests", interests);
			req.setAttribute("recognisedClasses", recognisedClasses);

			req.setAttribute("certificatesDomain", certificatesDomain);
			req.setAttribute("possibileCourses", possibileCourses);
			req.setAttribute("evaluationsAvg", new FlowEvaluationAverage(results.getListaValutazioni()));
			
			getServletContext().getRequestDispatcher("/jsp/show_flow.jsp").forward(req, resp);
						
		} 
		else { // Error page
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}

	}
}
