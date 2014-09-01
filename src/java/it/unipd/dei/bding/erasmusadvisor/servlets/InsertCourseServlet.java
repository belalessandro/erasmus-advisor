package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetTipoLaureaValues;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.TipoLaureaBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Pre-processes the Insert FORM of a new Course.
 * 
 * It returns the JSP page insert_course.jsp, populated with the
 * required fields
 * 
 * <p> Base URL: /course/insert
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: (none)
 * 
 * @see InsertUniversityServlet
 * @author Luca
 */
public class InsertCourseServlet extends AbstractDatabaseServlet {
	
	private static final long serialVersionUID = -685011637010768625L;

	/**
	 * Forwards the pre-loaded data to the insert form
	 * 
	 * @param request 
	 * 				request from the client
	 * @param response 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

		List<TipoLaureaBean> degreeDomain = null;
		List<AreaBean> areaDomain = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			degreeDomain = GetTipoLaureaValues.getDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the course.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{
			// forward to the insert FORM
			req.setAttribute("degreeDomain", degreeDomain);
			req.setAttribute("areaDomain", areaDomain);
			getServletContext().getRequestDispatcher("/jsp/insert_course.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
}