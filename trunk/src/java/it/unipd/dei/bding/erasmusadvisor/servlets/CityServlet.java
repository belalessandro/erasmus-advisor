/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.resources.City;
import it.unipd.dei.bding.erasmusadvisor.resources.CityEvaluationsAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.json.*;

/**
 * @author Luca
 *
 */
public class CityServlet extends AbstractDatabaseServlet 
{
	/*
	 * (Autorizzazioni: GET Tutti, 
	 * 					POST SOLO Resp.Flusso)
	 * 
	 * mappato su /city
	 * 
	 * quando riceve GET
	 * 			-> Se c'è un id restituisce e visualizza la citta' su show_city.jsp
	 * 			-> altrimenti: se è resp.flusso 
	 * 						-> mostra il form di insert_city.jsp 
	 * 						-> altrimenti: redirect su /city/list
	 * 
	 * quando riceve POST
	 *   		-> Se operazione è "remove" rimuove l'interesse collegato allo studente loggato e IdFlusso come parametro
	 *   		-> Se operazione è "insert" inserisce l'interesse collegato allo studente loggato e IdFlusso come parametro
	 */
	
	/**
	 * Get the details of a specific city or redirects to the insert page
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String city = req.getParameter("city");
		String country = req.getParameter("country");

		if (city != null && ! city.isEmpty() && country != null && ! country.isEmpty()) 
		{
			City results = null;
			List<LinguaBean> languageDomain = null;
			Message m = null;
			
			try {
				results = new CittaDatabase().searchCityByName(DS, city, country);
				languageDomain = GetLinguaValues.getLinguaDomain(DS);
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the city.", "XXX", ex.getMessage());
			}
			
			if (m == null && results != null)
			{

				req.setAttribute("city", results.getCity());
				req.setAttribute("evaluations", results.getEvalutationList());
				req.setAttribute("languages", results.getLanguagesList());
				
				req.setAttribute("languageDomain", languageDomain);
				req.setAttribute("evaluationsAvg", new CityEvaluationsAverage(results.getEvalutationList()));

				/* Show results to the JSP page. */
				getServletContext().getRequestDispatcher("/jsp/show_city.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}

		} 
		else {
			/* Redirect to insert form. */
			getServletContext().getRequestDispatcher("/jsp/insert_city.jsp").forward(req, resp);
		}

	}
	
	protected void  doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String operation = req.getParameter("operation");
		String city = req.getParameter("city");
		String country = req.getParameter("country");
				
		if (operation.equals("delete"))
		{
			if (city != null && ! city.isEmpty() && country != null && ! country.isEmpty()) 
			{
				int results;
				try {
					results = new CittaDatabase().deleteCity(DS, city, country);
					// bisogna passare anche un parametro deletedEntity con valore
					// city + " (" + country + ")"
					
					if (results > 0 )
					{
						System.out.println("si");
						resp.setContentType("application/json");  
				        PrintWriter out = resp.getWriter();
				        out.println("{");
				        out.println("\"url\": \"/entity_deleted.jsp\"");
				        out.println("}");
				        out.close();
					}
					
				} 
				catch (SQLException e) {
					// TODO CATTURARE GLI ERRORI OPPORTUNAMENTE (usare redirect/message..)
					e.printStackTrace();
				}
			} 
			else {
				/* Nothing happens. */
			}
		}
	}
}

