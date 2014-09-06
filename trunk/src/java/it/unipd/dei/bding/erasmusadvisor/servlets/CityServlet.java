package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.LinguaCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.PartecipazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneCittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.City;
import it.unipd.dei.bding.erasmusadvisor.resources.CityEvaluationsAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages a specific City.
 * 
 * <p> Base URL: /city
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: INSERT, EDIT, DELETE
 * 
 * @see UniversityServlet
 * @author Luca, Alessandro
 */
public class CityServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
    
	private static final long serialVersionUID = 3531286954851445885L;
    
	/**
	 * Get the details of a specific city
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
			throws ServletException, IOException 
	{

		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: LOGGED
		 */
		if ( lu == null ) {
			req.setAttribute("message", 
					new Message("Not authorized.", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		
		// Gets input parameters
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
			boolean evalEnabled = false;
			
			/**
			 * Gets the data from database
			 */
			try {
				conn = DS.getConnection();
				results = new CittaDatabase().searchCityByName(conn, city, country);
				languageDomain = GetLinguaValues.getLinguaDomain(conn);
				

				// determina se abilitare l'inserimento della valutazione
				if (lu.isStudent())
				{
					if (PartecipazioneDatabase.checkParticipation(conn, city, country, lu.getUser()))
					{
						if (!(ValutazioneCittaDatabase.checkEvaluation(conn, lu.getUser(), city, country)))
						{
							evalEnabled = true;
						}
					}
				}
			} 
			catch (SQLException ex) {
				m = new Message("Error while getting the city.", "XXX", "Please, contact the admin.");
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
				
				if (lu.isStudent() && evalEnabled == true)
				{
					req.setAttribute("evalEnabled", "enabled");
				}
				else
				{
					req.setAttribute("evalEnabled", "notEnabled");
				}

				/* Show results to the JSP page. */
				getServletContext().getRequestDispatcher("/jsp/show_city.jsp").forward(req, resp);
			}
			else
			{
				req.setAttribute("message", m);
				errorForward(req, resp);
			}

		} 
		else {
			/* Redirects to the search form. */
			StringBuilder builder = new StringBuilder()
				.append(req.getContextPath())
				.append( "/city/list");
			
			resp.sendRedirect(builder.toString());	
		}

	}
	
	/**
	 * Submit an operation form
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
	protected void  doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		// Gets operation parameter
		String operation = req.getParameter("operation");
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 * Authorization check. Permissions required: FlowManager, Coordinator
		 */
		if (! (lu.isCoord() || lu.isFlowResp()) || operation == null || operation.isEmpty() ) {
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
			return;
		} 
		/** 
		 * OPERATION DISPATCHER 
		 */
		else if (operation.equals(INSERT))
		{
			insert(req, resp);
		} 
		else if (operation.equals(DELETE))
		{
			delete(req, resp);
		}
		else if(operation.equals(EDIT)) 
		{
			edit(req, resp);
		}
	}
	
	/**
	 * Handles logic for insert operation.
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
	private void insert(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException  {
		// the connection to database
		Connection conn = null;
		
		// entity beans
		CittaBean cittaBean = new CittaBean();
		List<LinguaCittaBean> linguaCittaBeanList = new ArrayList<LinguaCittaBean>();
		
		// models
		Message m = null;
		
		// Incoming FORM parameters
		String city = request.getParameter("Nome");
		String country = request.getParameter("Stato");
		String[] siglaLingua = request.getParameterValues("LinguaCitta[]");
		
		// Populating beans
		cittaBean.setNome(city);
		cittaBean.setStato(country);
		
		if (siglaLingua != null) {
			for(int i=0;i<siglaLingua.length;i++)
			{
				LinguaCittaBean l = new LinguaCittaBean(); 
				l.setNomeCitta(city);
				l.setStatoCitta(country);
				l.setSiglaLingua(siglaLingua[i]);
				linguaCittaBeanList.add(l);
			}
		}

		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			CittaDatabase.createCitta(conn, cittaBean);
			for (LinguaCittaBean l : linguaCittaBeanList) {
				LinguaCittaDatabase.createLinguaCitta(conn, l);
			}
			
			DbUtils.commitAndClose(conn); // COMMIT
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) {
				m = new Message("Operation not allowed: Duplicate data", "E300", 
						"The couple city-country is already present in the database!");
			} else {
				m = new Message("Error while inserting a new city.", "E200", "Please, contact the admin.");
			}
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		}
		finally {
			DbUtils.closeQuietly(conn); // *always* closes DB connection
		}
		
		
		// Success!
		// Creating response path
		StringBuilder builder = new StringBuilder()
			.append(request.getContextPath())
			.append("/city?name=")
			.append(city)
			.append("&country=")
			.append(country);
		response.sendRedirect(builder.toString());	
    }

	/**
	 * Handles logic for delete operation.
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
    private void delete(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException, IOException 
    {

		Connection conn = null;
		Message m = null;
		
		String city = req.getParameter("city");
		String country = req.getParameter("country");
		
		if (city != null && ! city.isEmpty() && country != null && ! country.isEmpty()) 
		{
			int results;
			try {
				conn = DS.getConnection();
				results = CittaDatabase.deleteCity(conn, city, country);				
				if (results > 0 )
				{
					String deletedEntity = city + " (" + country + ")";
					req.setAttribute("deletedEntity", deletedEntity);
					getServletContext().getRequestDispatcher("/jsp/entity_deleted.jsp").forward(req, resp); // ERROR PAGE
				}
				
			} 
			catch (SQLException e)
			{

				m = new Message("Error while deleting the city.", "XXX", "Please, contact the admin.");
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
		} 
		else {
			// An error maybe?
		}
    }
    
	/**
	 * Handles logic for edit operation.
	 * 
	 * @param req
	 * 				request from the client
	 * @param resp 
	 * 				response to the client 
	 * @throws ServletException
	 * 			 	if any error occurs while executing the servlet
	 * @throws IOException
	 *  			if any error occurs in the client/server communication.
	 */
    private void edit(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {

		// get parameters
		String new_name = req.getParameter("new_name");
		String new_country = req.getParameter("new_country");
		String old_name = req.getParameter("old_name");
		String old_country = req.getParameter("old_country");
		
		String[] languages = req.getParameterValues("language[]");
		

		// the connection to database
		Connection conn = null;
		Message m = null;
		
		// edit the entity
		try {
			// starting database operations
			conn = DS.getConnection();
			
			ArrayList<LinguaBean> linguaDomainList = (ArrayList<LinguaBean>) GetLinguaValues.getLinguaDomain(conn);
			ArrayList<LinguaCittaBean> linguaCittaBeanList = new ArrayList<LinguaCittaBean>();
			
			// populate the bean
			int k = 0;
			int i = 0;

			while(i < linguaDomainList.size() && k < languages.length)
			{
				// represents "sigla"
				LinguaBean current = linguaDomainList.get(i);
				if(languages[k].equals(current.getSigla()))
				{
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
			
			// n = # of row updated
			int n = new CittaDatabase().editCity(conn, new_name, new_country, old_name, old_country, linguaCittaBeanList);
			DbUtils.close(conn);
			
			if(n == 1)
			{
				// success
				// Creating response path
				StringBuilder builder = new StringBuilder()
					.append(req.getContextPath())
					.append("/city?name=")
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
				
				
		} 
		catch (SQLException e) 
		{
			// Error management
			m = new Message("Error while editing the city.","E200", "");
			req.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
			return;
		} 
		finally 
		{
			DbUtils.closeQuietly(conn);
		}
    }
    
    /**
     * Handles error forwarding between pages.
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
    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}

