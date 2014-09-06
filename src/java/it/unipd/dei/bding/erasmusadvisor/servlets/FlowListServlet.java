package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.FlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetCertificatiLinguisticiValues;
import it.unipd.dei.bding.erasmusadvisor.resources.CountryCityListBean;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowSearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages lists of Flows.
 * 
 * <p> Base URL: /flow/list
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: SEARCH
 * 
 * @see UniversityListServlet
 * @author Alessandro, Luca
 */
public class FlowListServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private final static String SEARCH = "search";
	
	private static final long serialVersionUID = 3682178126915951453L;
	
	/**
	 * Gets the list
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
		// incoming parameters
		String operation = req.getParameter("operation");
		
		if (operation != null && !operation.isEmpty()
				&& operation.equals(SEARCH)) {
			/**
			 * SEARCH
			 */
			search(req, resp);

		} else {
			/**
			 * default: PRELOAD FORM
			 */
			preload(req, resp);

		}
	}

	/**
	 * Handles the search operation
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
	private void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");

		// Incoming parameters for the search filter
		String stato = req.getParameter("country");
		String citta = req.getParameter("city");
		String durataStr = req.getParameter("length");
		String minPostiStr = req.getParameter("minSeats");
		String certificate = req.getParameter("certificate");
		
		// Pre-processing parameters
		Integer durata = (durataStr != null ? Integer.parseInt(durataStr) : null);
		Integer minPosti = (minPostiStr != null ? Integer.parseInt(minPostiStr) : null);
		String nomeCertificato = null;
		String livelloCertificato = null;
		if (certificate != null)
		{
			nomeCertificato = certificate.split("-")[0].trim(); // "ITA - B2" -> ITA
			livelloCertificato = certificate.split("-")[1].trim(); // "ITA - B2" -> B2
		}

		// model
		Message m = null;
		List<FlowSearchRow> results = null;
		List<CertificatiLinguisticiBean> certificatesDomain = null;
		List<CittaBean> cities = null;
		
		// database connection
		Connection conn = null;

		try {

			conn = DS.getConnection();
			
			results = FlussoDatabase.filterFlowBy(conn, lu, stato, citta, durata, 
					minPosti, nomeCertificato, livelloCertificato);
			
			// Pre-charging form values
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			cities = CittaDatabase.getAllSortByCountry(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the flow list.",
					"E200", "Please, contact the admin.");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} finally {
			DbUtils.closeQuietly(conn); // *always* close the connection
		}
		

		// Send data to the view 
		req.setAttribute("results", results);
		req.setAttribute("certificatesDomain", certificatesDomain);
		req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));
		
		if (stato != null)
			req.setAttribute("searchedCountry", stato);
		if (citta != null)
			req.setAttribute("searchedCity", citta);
		if (durataStr != null)
			req.setAttribute("searchedLenght", durataStr);
		if (minPostiStr != null)
			req.setAttribute("searchedSeats", minPostiStr);
		if (certificate != null)
			req.setAttribute("searchedCert", certificate);
		
		if (stato == null && citta == null && durataStr == null && minPostiStr == null && certificate == null)
			req.setAttribute("allFlows", "allFlows");
		
		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_flow.jsp").forward(req, resp);
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
	
	/**
	 * Preloads the data.
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
	private void preload(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// model, beans and connection
		List<CertificatiLinguisticiBean> certificatesDomain = null;
		List<CittaBean> cities = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			cities = CittaDatabase.getAllSortByCountry(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the search page.", "XXX", "Please, contact the admin.");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		// Send data to the view 
		req.setAttribute("certificatesDomain", certificatesDomain);
		req.setAttribute("cities", (new CountryCityListBean()).initialize(cities));
		
		/* Forward to the Search JSP page */
		getServletContext().getRequestDispatcher("/jsp/search_flow.jsp").forward(req, resp);
	}
}
