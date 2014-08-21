/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.ClassEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Class;
import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Luca
 *
 */
public class ClassServlet extends AbstractDatabaseServlet 
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String ID = req.getParameter("ID");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_class.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		Class results = null;
		Message m = null;
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		
		try {
			System.out.println("fuori query");
			results = InsegnamentoDatabase.getInsegnamento(DS, ID);
			languageDomain = GetLinguaValues.getLinguaDomain(DS);
			areaDomain = GetAreaValues.getAreaDomain(DS);
			System.out.println("fine db");
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "XXX", ex.getMessage());
		} 
		
		
		/**
		 *  Send the university model to the appropriate output (Ajax or normal)
		 *
		 */
		// Handle normal response (e.g. forward and/or set message as attribute).

		if (m == null && results != null) 
		{
			/** 
			 * Show results to the JSP page. 
			 */
			req.setAttribute("classBean", results.getInsegnamento());
			req.setAttribute("language", results.getLingua());
			req.setAttribute("evaluations", results.getValutazioni());
			req.setAttribute("professors", results.getProfessori());

			req.setAttribute("languageDomain", languageDomain);
			req.setAttribute("areaDomain", areaDomain);
			req.setAttribute("evaluationsAvg", new ClassEvaluationAverage(results.getValutazioni()));
			
			getServletContext().getRequestDispatcher("/jsp/show_class.jsp").forward(req, resp);
						
		} 
		else { // Error page
			req.setAttribute("message", m);
			System.out.println("error page");
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}

	}
}
