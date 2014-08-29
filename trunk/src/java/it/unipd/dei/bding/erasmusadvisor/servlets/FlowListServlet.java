/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.FlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetCertificatiLinguisticiValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetStatoValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetUniversitaValues;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.CountryCityListBean;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowSearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * @author Luca
 *
 */
public class FlowListServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private final static String SEARCH = "search";
	
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
	
	private void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

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
			
			results = FlussoDatabase.filterFlowBy(conn, stato, citta, durata, 
					minPosti, nomeCertificato, livelloCertificato);
			
			// Pre-charging form values
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			cities = CittaDatabase.getAllSortByCountry(conn);
			
		} catch (SQLException ex) {
			m = new Message("Error while getting the flow list.",
					"XXX", ex.getMessage());
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
	
	private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
	
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
			m = new Message("Error while getting the search page.", "XXX", ex.getMessage());
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
