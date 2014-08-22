package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.EstensioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.GestioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CreateDocumentazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CreateFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CreateOrigineDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Mapped to /thesis
 * @author Nicola
 *
 */
public class ThesisServlet extends AbstractDatabaseServlet {

	private static final long serialVersionUID = 77657689265503855L;

	/**
	 * Get the details of a specific thesis or redirects to the insert form-page
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) 
		{
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_thesis.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		
		// model
		Thesis results = null;
		Message m = null;
		
		// database connection
		Connection conn = null;

		try {
			conn = DS.getConnection();
			results = ArgomentoTesiDatabase.getArgomentoTesiByID(conn, ID);
			DbUtils.close(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the university.","XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		/**
		 *  Send the university model to the appropriate output (Ajax or normal)
		 *
		 */
		
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) 
		{
			// Handle Ajax response (e.g. return JSON data object).
			resp.setContentType("application/json");
			if (results != null) {
				/* NOT IMPLEMENTED YET */
				JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
				jsonWriter.writeObject(convertToJson(results));
				jsonWriter.close();
			}

		} 
		else {
			// Handle normal response (e.g. forward and/or set message as attribute).
			if (m == null && results != null) 
			{
				/** 
				 * Show results to the JSP page.
				 */
				req.setAttribute("thesis", results.getArgomentoTesi());
				req.setAttribute("professors", results.getProfessori());
				req.setAttribute("aree", results.getAree());
				req.setAttribute("lingue", results.getLingue());
				req.setAttribute("evaluations", results.getListaValutazioni());

				getServletContext().getRequestDispatcher("/jsp/show_thesis.jsp").forward(req, resp);
			}
			else { // Error page
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}

	}
	
	/**
	 * Insert or update the thesis sent with a POST form
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn");
		String operation = req.getParameter("operation");
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {
			/* Error or not authorized. */
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			return;
		} else if (operation.equals("insert") ) {
			// entity beans
			ArgomentoTesiBean a  =  new ArgomentoTesiBean();
			ProfessoreBean[] p = null;
			GestioneBean[] g  = null;
			EstensioneBean[] e = null;
			LinguaTesiBean[] l = null;
			
			// data models, connection
			Message m = null;
			Connection conn = null;
			
			// Populate bean from the FORM submitted
			BeanUtilities.populateBean(a, req);
			
			try {
				// Start of database operation
				conn = DS.getConnection();
				int idTesi= ArgomentoTesiDatabase.createArgomentoTesi(conn, a);
				
				/*String[] paramProf = req.getParameterValues("professori[]");
				if (paramProf != null) {
					p = new ProfessoreBean[paramProf.length];
					g = new GestioneBean[paramProf.length];
					for (int j=0; j<paramProf.length; j++) {
						p[j] = new ProfessoreBean();
								
						String nomeProf = paramProf[j].split(" ")[0];
						String cognomeProf = paramProf[j].split(" ")[1]; 
						
						p[j].setNome(nomeProf);
						p[j].setCognome(cognomeProf);
						int idProf = ProfessoreDatabase.createProfessore(conn, p[j]); 
						
						g[j].setIdArgomentoTesi(idTesi);
						g[j].setIdProfessore(idProf);
						GestioneDatabase.createGestione(conn, g[j]);
					}
				}
				
				String[] paramEst = req.getParameterValues("aree[]");
				if (paramProf != null) {
					e = new EstensioneBean[paramEst.length];
					for (int j=0; j<paramEst.length; j++) {
						e[j] = new EstensioneBean();
								
						String area = paramEst[j];
						
						e[j].setIdArgomentoTesi(idTesi);
						e[j].setArea(area);
						
						EstensioneDatabase.createEstensione(conn, p[j]);
					}
				}
				
				String[] paramLingue = req.getParameterValues("lingue[]");
				if (paramLingue != null) {
					l = new LinguaTesiBean[paramLingue.length];
					for (int i=0; i<paramLingue.length; i++) {
						l[i] = new LinguaTesiBean();
						String sigla = paramLingue[i];
						l[i].setSiglaLingua(sigla);
						l[i].setIdArgomentoTesi(idTesi);

						LinguaTesiDatabase.createLingua(conn, l[i]);
					}
				}*/
				
				DbUtils.close(conn);
				// End of database operation
				
		} catch (SQLException ex) {
			m = new Message("Error while inserting the Thesis.",
					"XXX", ex.getMessage());
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(
				req, resp); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(conn);
		}
			
		} else if (operation.equals("update") ) {
			/*
			 * Updates an existing University 
			 */
			//bookRepo.updateBook(id, title, description, price, pubDate);
		}

		resp.sendRedirect(req.getParameter("returnTo"));
	}
	
	private JsonObject convertToJson(Thesis tesi) {
		/* NOT IMPLEMENTED YET */
		JsonObject json = Json.createObjectBuilder()
			     .add("firstName", "John")
			     .add("lastName", "Smith")
			     .add("age", 25)
			     .add("address", Json.createObjectBuilder()
			         .add("streetAddress", "21 2nd Street")
			         .add("city", "New York")
			         .add("state", "NY")
			         .add("postalCode", "10021"))
			     .add("phoneNumber", Json.createArrayBuilder()
			         .add(Json.createObjectBuilder()
			             .add("type", "home")
			             .add("number", "212 555-1234"))
			         .add(Json.createObjectBuilder()
			             .add("type", "fax")
			             .add("number", "646 555-4567")))
			     .build();
		
		return json;
	}
}
