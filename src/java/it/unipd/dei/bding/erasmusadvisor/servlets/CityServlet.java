/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.City;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luca
 *
 */
public class CityServlet extends AbstractDatabaseServlet {
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
	 * Get the details of a specific university or redirects to the insert form-page
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String city = req.getParameter("city");
		String country = req.getParameter("country");

		if (city != null && ! city.isEmpty() && country != null && ! country.isEmpty()) 
		{
			City results;
			try {
				results = new CittaDatabase().searchCityByName(DS, city, country);

				req.setAttribute("city", results.getCity());
				req.setAttribute("evaluations", results.getEvalutationList());
				req.setAttribute("languages", results.getLanguagesList());
				
			} 
			catch (SQLException e) {
				// TODO CATTURARE GLI ERRORI OPPORTUNAMENTE (usare redirect/message..)
				e.printStackTrace();
			}

			/* Show results to the JSP page. */
			getServletContext().getRequestDispatcher("/jsp/show_city.jsp").forward(req, resp);
		} 
		else {
			/* Redirect to insert form. */
			getServletContext().getRequestDispatcher("/jsp/insert_city.jsp").forward(req, resp);
		}

	}
}
