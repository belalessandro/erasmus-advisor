package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;
import it.unipd.dei.bding.erasmusadvisor.database.CreateDocumentazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CreateFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CreateOrigineDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Form processing for the creation of a new Flow
 * 
 * @author Alessandro
 * @version 1.0
 */
public class InsertFlowServlet extends AbstractDatabaseServlet {
	
	private static final long serialVersionUID = 4109125705340314063L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn"); 

		if (!lu.isFlowResp()) { // Not authorized
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}
			
		// entity beans
		FlussoBean f  = null;
		DocumentazioneBean[] d = null;
		OrigineBean[] o  = null;
		
		// data models
		Message m = null;
		

		try{
			f = new FlussoBean();
			f.setId(request.getParameter("name"));
			f.setDestinazione(request.getParameter("university"));
			f.setRespFlusso(lu.getUser());
			f.setPostiDisponibili(Integer.parseInt(request.getParameter("seats")));
			f.setDurata(Integer.parseInt(request.getParameter("length")));
			f.setDettagli(request.getParameter("details"));
			//String s = "";
			//if (true) throw new SQLException( request.getParameter("name"), "" );
			
			new CreateFlussoDatabase(DS.getConnection(), f).createFlusso(); // TODO: FARE OPERAZIONE UNA TRANSAZIONE UNICA?
			
			String[] paramCert = request.getParameterValues("certificate[]");
			if (paramCert != null) {
				d = new DocumentazioneBean[paramCert.length];
				for (int j=0; j<paramCert.length; j++) {
					d[j] = new DocumentazioneBean();
							
					String nomeCertificato = paramCert[j].split(":")[0]; // ITA:B2 -> ITA
					String livelloCertificato = paramCert[j].split(":")[1]; // ITA:B2 -> B2

					d[j].setNomeCertificato(nomeCertificato);
					d[j].setLivelloCertificato(livelloCertificato);
					d[j].setIdFlusso(f.getId());

					new CreateDocumentazioneDatabase(DS.getConnection(), d[j]).createDocumentazione(); // MOLTO SPORCO: apre una connessione per ogni insert
				}
			}
			
			String[] paramOrigin = request.getParameterValues("origin[]");
			if (paramOrigin != null) {
				o = new OrigineBean[paramOrigin.length];
				for (int i=0; i<paramOrigin.length; i++) {
					o[i] = new OrigineBean();
					o[i].setIdCorso(Integer.parseInt(paramOrigin[i]));
					o[i].setIdFlusso(f.getId());

					new CreateOrigineDatabase(DS.getConnection(), o[i]).createOrigine(); // MOLTO SPORCO: apre una connessione per ogni insert
				}
			}
			
			m = new Message("Flow " + f.getId() + " inserted successfully.");
			
			//new CreateStudenteDatabase(DS.getConnection(), s).createStudente(); 
			
		} catch (NumberFormatException ex) {
			m = new Message("Cannot create the flow. Invalid input parameters.", 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message("Cannot create the flow: id " + f.getId() + " already exists.", 
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the flow: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		
		
		if (!m.isError()) {
			// forwards the control to ....
			//request.getRequestDispatcher("/jsp/insert_flow.jsp").forward(request, response);
			request.getSession().setAttribute("message", m);
			response.sendRedirect("jsp/insert_flow.jsp?notify=success");
		} else { // ERROR
			// stores the message as a request attribute
			request.setAttribute("message", m);
			
			// come back to flow insertion JSP page
			request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		}
			
	}
}
