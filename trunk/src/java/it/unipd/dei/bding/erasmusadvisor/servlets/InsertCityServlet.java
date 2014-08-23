/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for pre-processing the Insert FORM of a new City
 * It returns the JSP page insert_city.jsp, populated with the
 * required fields
 * 
 * * Notice: Only doGet(..) is allowed here! *
 * 
 * Mapped to /city/insert-form
 * 
 * @author Alessandro
 *
 */
public class InsertCityServlet extends AbstractDatabaseServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		// attribs here...
		//request.setAttribute("bohhhh", m);
		
		// forward to the insert FORM
		req.getRequestDispatcher("/jsp/insert_city.jsp").forward(req, resp);
	}
}
