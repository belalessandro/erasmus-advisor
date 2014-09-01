package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetCertificatiLinguisticiValues;
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
 * Pre-processes the Insert FORM of a new Flow.
 * 
 * It returns the JSP page insert_flow.jsp, populated with the
 * required fields
 * 
 * <p> Base URL: /flow/insert
 * 
 * <p> Accepts: GET
 * 
 * <p> Operations: (none)
 * 
 * @see InsertUniversityServlet
 * @author Luca
 */
public class InsertFlowServlet extends AbstractDatabaseServlet {
	
	private static final long serialVersionUID = 4109125705340314063L;

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
		// TODO: notare che manca l'istruzione che recupera i corsi selezionabili in base al responsabil di flusso	

		//LoggedUser lu = (LoggedUser) req.getSession().getAttribute("loggedUser");
		// if !lu.isResp()... => ERROR
		
		ResponsabileFlussoBean flowResp = new ResponsabileFlussoBean();
		flowResp.setNomeUtente("pilu");
		
		List<CertificatiLinguisticiBean> certificatesDomain = null;
		List<CorsoDiLaureaBean> possibleCourses = null;
		Connection conn = null;
		Message m = null;
		
		try {
			conn = DS.getConnection();
			certificatesDomain = GetCertificatiLinguisticiValues.getCertificatiLinguisticiDomain(conn);
			possibleCourses = CorsoDiLaureaDatabase.getPossibleCourses(conn, flowResp);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the flow.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}

		if (m == null)
		{
			// forward to the insert FORM
			req.setAttribute("certificatesDomain", certificatesDomain);
			req.setAttribute("possibleCourses", possibleCourses);
			getServletContext().getRequestDispatcher("/jsp/insert_flow.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
}
