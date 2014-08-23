/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.resources.City;
import it.unipd.dei.bding.erasmusadvisor.resources.CityEvaluationsAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.json.*;

import org.apache.commons.dbutils.DbUtils;

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
	 *   		-> Se operazione è "edit"   edita l'entita citta (deve comparire solo con coordinatore)
	 */
	
	/**
	 * Get the details of a specific city or redirects to the insert page
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String city = req.getParameter("name");
		String country = req.getParameter("country");

		if (city != null && ! city.isEmpty() && country != null && ! country.isEmpty()) 
		{
			// model
			City results = null;
			List<LinguaBean> languageDomain = null;
			Message m = null;
			
			// the connection to database
			Connection conn = null;
			
			try {
				conn = DS.getConnection();
				results = new CittaDatabase().searchCityByName(conn, city, country);
				languageDomain = GetLinguaValues.getLinguaDomain(conn);
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the city.", "XXX", ex.getMessage());
			} 
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
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
			
		// the connection to database
		Connection conn = null;
		Message m = null;
		
		if (operation.equals("delete"))
		{
			// mauro: spostato qui perché così separiamo bene i parametri di delete e edit
			String city = req.getParameter("city");
			String country = req.getParameter("country");
			
			
			
			if (city != null && ! city.isEmpty() && country != null && ! country.isEmpty()) 
			{
				int results;
				try {
					conn = DS.getConnection();
					results = new CittaDatabase().deleteCity(conn, city, country);
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
				finally {
					DbUtils.closeQuietly(conn); // always closes the connection 
				}
			} 
			else {
				// An error maybe?
			}
		}
		else if(operation.equals("edit")) 
		{
			// TODO: Check user is type coordinator
			
			// get parameters
			String new_name = req.getParameter("new_name");
			String new_country = req.getParameter("new_country");
			String old_name = req.getParameter("old_name");
			String old_country = req.getParameter("old_country");
			
			String[] languages = req.getParameterValues("language[]");
			
			// edit the entity
			try {
				// starting database operations
				conn = DS.getConnection();
				
				ArrayList<LinguaBean> linguaDomainList = (ArrayList<LinguaBean>) new GetLinguaValues().getLinguaDomain(conn);
				ArrayList<LinguaCittaBean> linguaCittaBeanList = new ArrayList<LinguaCittaBean>();
				
				// populate the bean
				int k = 0;
				int i = 0;
				PrintWriter w = resp.getWriter();
//				w.println("<html>");
//				w.println("<body>");
				while(i < linguaDomainList.size() && k < languages.length)
				{
					
					
					// represents "sigla"
					LinguaBean current = linguaDomainList.get(i);
					if(languages[k].equals(current.getSigla()))
					{
//						w.println("<h2>Current = " +  current.getNome() + "</h2>");
						LinguaCittaBean linguaCittaBean = new LinguaCittaBean();
						linguaCittaBean.setNomeCitta(new_name);
						linguaCittaBean.setSiglaLingua(current.getSigla());
						linguaCittaBean.setStatoCitta(new_country);
						
						linguaCittaBeanList.add(linguaCittaBean);
						k++;
						
						// Do again the search for the next element
						i = 0;
					}
					
					i++;
				}
				

//				w.println("</body>");
//				w.println("</html>");
//				w.flush();
//				w.close();
				// n = # of row updated
				
				int n = new CittaDatabase().editCity(conn, new_name, new_country, old_name, old_country, linguaCittaBeanList);
				DbUtils.close(conn);
				
				if(n == 1)
				{
					// success
					// Creating response path
					StringBuilder builder = new StringBuilder()
						.append("/erasmus-advisor/city?name=")
						.append(new_name)
						.append("&country=")
						.append(new_country)
						.append("&edited=success");
						resp.sendRedirect(builder.toString());	
				}
				else
				{
					// Error management
					m = new Message("Error while updating the city.","XXX", "");
					req.setAttribute("message", m);
					
					getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
					return;
				}
					
					
				} catch (SQLException e) {
					// Error management
					m = new Message("Error while submitting evaluations.","XXX", e.getMessage());
					req.setAttribute("message", m);
					
					getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
					return;
				} finally {
					DbUtils.closeQuietly(conn);
				}
			}
	}
}

