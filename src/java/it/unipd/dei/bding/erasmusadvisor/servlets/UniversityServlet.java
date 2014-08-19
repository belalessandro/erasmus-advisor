package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * Mapped to /university
 * @author Alessandro
 *
 */
public class UniversityServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 1462509389265503855L;

	/**
	 * Get the details of a specific university or redirects to the insert form-page
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String univName = req.getParameter("univName");

		if (univName != null && !univName.isEmpty()) {
			University results;
			try {
				results = new UniversitaDatabase().searchUniversityModelByName(DS, univName);

				req.setAttribute("university", results.getUniversita());
				req.setAttribute("evaluations", results.getListaValutazioni());
			} catch (SQLException e) { // TODO CATTURARE GLI ERRORI OPPORTUNAMENTE (usare redirect/message..)
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/* Show results to the JSP page. */
			getServletContext().getRequestDispatcher("/jsp/show_university.jsp").forward(
					req, resp);
			// MAPPARE su JSP TRAMITE:
			// <c:forEach var="ev" items="${evaluations}">
			//					${ev.SoddEsperienza}
			//					${ev.Sodd..}
			//					${ev.Sodd...}
			// </c:forEach>
		} else {
			
			/* Redirect to insert form. */
			getServletContext().getRequestDispatcher("/jsp/insert_university.jsp").forward(
					req, resp);
		}

	}
	
	/**
	 * Insert or update the university sent with a POST form
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn"); 

		String operation = req.getParameter("operation");
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {
			/* Error or not authorized. */
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			return;
		} else if (operation.equals("insert") ) {
			/*
			 * Insert a new University 
			 */
			//bookRepo.addBook(title, description, price, pubDate);
			
		} else if (operation.equals("update") ) {
			/*
			 * Updates an existing University 
			 */
			//bookRepo.updateBook(id, title, description, price, pubDate);
		}

		resp.sendRedirect(req.getParameter("returnTo"));
	}

}
