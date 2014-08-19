package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for getting lists of university
 * 
 * Mapped to /university/list
 * @author Alessandro
 *
 */
public class UniversityListServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 1462509389265503855L;

	/**
	 * Gets a list of universities, filtered or not.  
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String operation = req.getParameter("operation");
		String filterByCountry = req.getParameter("filterByCountry");
		String filterByCity = req.getParameter("filterByCity");

		/* SEARCH */
		if (operation != null && !operation.isEmpty() && operation.equals("search")) {
			
			ArrayList<UniversitaBean> universityList = new ArrayList<UniversitaBean>();
			
			if (filterByCountry != null && !filterByCountry.isEmpty()) {
				/* List all the universities */
				
				universityList = null; // get university where stato = filterByCountry
				
			} else if (filterByCity != null && !filterByCity.isEmpty()) {
				/* List all the universities */
				
				universityList = null; // get university where citta = filterByCity
				
			} else {
				/* List all the universities */
				
				universityList = new ArrayList<UniversitaBean>(); // get all
				
			}
			
			/* Send the list of universities (if any found, with the selected criteria) */
			req.setAttribute("universityList", universityList);
			
			/* Show results to the JSP page. */
			getServletContext().getRequestDispatcher("/jsp/search_university.jsp").forward(
					req, resp);
		} else { /* NO OPERATION */
			
			/* Redirect to the Search JSP page */
			resp.sendRedirect(req.getContextPath() + "/jsp/search_university.jsp");
		}

	}

}
