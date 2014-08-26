/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;
import it.unipd.dei.bding.erasmusadvisor.resources.University;
import it.unipd.dei.bding.erasmusadvisor.resources.UniversityEvaluationsAverage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Luca, Nicola
 *
 */
public class ThesisListServlet extends AbstractDatabaseServlet 
{
	 
	private static final long serialVersionUID = 24559389234503855L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String operation = req.getParameter("operation");
		Message m = null;
		
		// database connection
		Connection conn = null;

		if (operation != null && !operation.isEmpty()
				&& operation.equals("search")) 
		{
			String area = req.getParameter("area");
			String uni = req.getParameter("university");
			// model
			List<ArgomentoTesiBean> results = null;
			
			try {

				conn = DS.getConnection();
				//results = ArgomentoTesiDatabase.searchArgomentoTesiBy(conn, uni, area);
				results = ArgomentoTesiDatabase.searchArgomentoTesiBy(conn, uni, area);
				
			} catch (SQLException ex) {
				m = new Message("Error while getting the university.",
						"XXX", ex.getMessage());
			} finally {
				DbUtils.closeQuietly(conn);
			}
			
			if (m == null && results != null) {
				/** 
				 * Show results to the JSP page. 
				 *
				 *//*
				List<Thesis> list = new ArrayList<Thesis>();
				scrivi.println("tesiiiiiiiiii");
				for(int i=0;i<results.size();i++)
				{
					list.add(new Thesis (results.get(i), null, null, null, null)); 
				}*/
				req.setAttribute("theses", results);
				req.setAttribute("areaSearch",area);
				req.setAttribute("universitySearch", uni);
								
			}
		}
			
			 //Redirect to the Search JSP page 
			List<LinguaBean> languageDomain = null;
			List<AreaBean> areaDomain = null;
			List<UniversitaBean> universities = null;
			
			try {
				conn = DS.getConnection();
				languageDomain = GetLinguaValues.getLinguaDomain(conn);
				areaDomain = GetAreaValues.getAreaDomain(conn);
				universities = GetUniversitaValues.getDomain(conn);
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the search page.", "XXX", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
	
			if (m == null)
			{
				req.setAttribute("languageDomain", languageDomain);
				req.setAttribute("areaDomain", areaDomain);
				req.setAttribute("universities", universities);
				getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
	
	/*protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String operation = req.getParameter("operation");
		if (operation != null && !operation.isEmpty()
				&& operation.equals("search")) {
	
		}
		else { 
			 Redirect to the Search JSP page 
			
			List<LinguaBean> languageDomain = null;
			List<AreaBean> areaDomain = null;
			List<UniversitaBean> universities = null;
			Connection conn = null;
			Message m = null;
			
			try {
				conn = DS.getConnection();
				languageDomain = GetLinguaValues.getLinguaDomain(conn);
				areaDomain = GetAreaValues.getAreaDomain(conn);
				universities = GetUniversitaValues.getDomain(conn);
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the search page.", "XXX", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
	
			if (m == null)
			{
				req.setAttribute("languageDomain", languageDomain);
				req.setAttribute("areaDomain", areaDomain);
				req.setAttribute("universities", universities);
				getServletContext().getRequestDispatcher("/jsp/search_thesis.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
	}*/
}