/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.PartecipazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ProfessoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.SvolgimentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.TeachingEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Teaching;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;
import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.PartecipazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;


/**
 * @author Luca
 *
 */
public class ClassServlet extends AbstractDatabaseServlet 
{
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
    private static final String AJAX = "ajax";
    
	private static final long serialVersionUID = 1L;

	/**
	 * Provides a class visualization and list of evaluations.
	 * @param req request by the client
	 * @param resp response to the client 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) {
			/* Redirect to insert form. */
			resp.sendRedirect(req.getContextPath() + "/jsp/insert_class.jsp");
			return;
		}
		
		/**
		 *  Gets the university model from the database
		 */
		

		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "user"); 
		
		// model
		Teaching results = null;
		Message m = null;
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		List<PartecipazioneBean> flows = null;
		
		// database connection
		Connection conn = null;
					
		try {
			conn = DS.getConnection();
			results = InsegnamentoDatabase.getInsegnamento(conn, Integer.parseInt(ID));
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			// TODO i flussi in questa lista dovrebbero essere quelli a cui ha partecipato l'utente
			// meno quelli che già riconoscono l'insegnamento
			flows = PartecipazioneDatabase.getFlows(conn, lu.getUser());
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the class.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(conn); // always closes the connection 
		}
		
		
		/**
		 *  Send the university model to the appropriate output
		 *
		 */
		// Handle normal response (e.g. forward and/or set message as attribute).

		if (m == null && results != null) 
		{
			/** 
			 * Show results to the JSP page. 
			 */
			req.setAttribute("classBean", results.getInsegnamento());
			req.setAttribute("language", results.getLingua());
			req.setAttribute("evaluations", results.getValutazioni());
			req.setAttribute("professors", results.getProfessori());

			req.setAttribute("flows", flows);
			
			req.setAttribute("languageDomain", languageDomain);
			req.setAttribute("areaDomain", areaDomain);
			req.setAttribute("evaluationsAvg", new TeachingEvaluationAverage(results.getValutazioni()));
			
			getServletContext().getRequestDispatcher("/jsp/show_class.jsp").forward(req, resp);
						
		} 
		else { // Error page
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
	
	/**
	 * Manage edit class requests.
	 * @param req request by the client
	 * @param resp response to the client
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void  doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn"); 
		Message m = null;
		String operation = null;
		
		if(req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest"))
			operation = "ajax";
		else
			operation = req.getParameter("operation");
	
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) 
		{
			// Error
			m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
		} 
		else if (operation.equals(INSERT))
		{	
			insert(req, resp);
		} 
		else if (operation.equals(DELETE))
		{
			delete(req, resp);
		}
		else if (operation.equals(AJAX))
		{
			report(req, resp);
		}
		else if(operation.equals(EDIT))
		{	
			edit(req, resp);
			
		}
		
	}

	
	/**
	 * Handle the report for a class. 
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void report(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// get json object
		response.setContentType("application/json");
		JsonReader reader = Json.createReader(request.getInputStream());
		JsonObject json = reader.readObject();
		reader.close();
		
		// modify the instance into the database
		Message m = null;
		Connection con = null;
		
		try {
			con = DS.getConnection();
			
			InsegnamentoDatabase.changeClassStatusToReported(con, json.getInt("id"));
			
			DbUtils.close(con);
			
		} catch (SQLException e) {
			m = new Message("Error while reporting the thesis.", "XXX", e.getMessage());
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}
		
		// writing the json object to the page
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		builder.add("report", "success");
		JsonObject out = builder.build();
		
		JsonWriter writer = Json.createWriter(response.getOutputStream());
		writer.writeObject(out);
		writer.close();
	}
	

	/**
	 * Handle logic for insert operation...
	 * @param request
	 * @param response
	 */
	private void insert(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException  {
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn"); 

		
		// the connection to database
		Connection conn = null;
		
		// entity beans
		InsegnamentoBean insegnamentoBean =  new InsegnamentoBean();
		//List<SvolgimentoBean> svolgimentoBean = new ArrayList<SvolgimentoBean>();
		
		// models
		Message m = null;
		
		// Populating beans from FORM parameters
		//BeanUtilities.populateBean(insegnamentoBean, request); // automatic
		
		insegnamentoBean.setNome(request.getParameter("Nome"));
		insegnamentoBean.setCrediti(Integer.parseInt(request.getParameter("Crediti")));
		insegnamentoBean.setNomeUniversita(request.getParameter("NomeUniversita"));
		insegnamentoBean.setPeriodoErogazione(Integer.parseInt(request.getParameter("PeriodoErogazione")));
		insegnamentoBean.setAnnoCorso(Integer.parseInt(request.getParameter("AnnoCorso")));
		insegnamentoBean.setNomeArea(request.getParameter("NomeArea"));
		insegnamentoBean.setNomeLingua(request.getParameter("NomeLingua"));
		
		insegnamentoBean.setStato("NOT VERIFIED"); // Setting status
		// TODO: in base all'autorhization
		
		String[] profNames = request.getParameterValues("professorName");
		String[] profSurnames = request.getParameterValues("professorSurname");
		

		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			int idInsegnamento = InsegnamentoDatabase.createInsegnamento(conn, insegnamentoBean);

			if (profNames != null && profSurnames != null && profNames.length == profSurnames.length) {
				for (int j=0; j<profNames.length; j++) {
					String nome = profNames[j];
					String cognome = profSurnames[j];
					String universita = insegnamentoBean.getNomeUniversita();
					
					if (nome != null && cognome != null && !nome.isEmpty() && !cognome.isEmpty() 
							&& universita != null) {
						nome = nome.trim();
						cognome = cognome.trim();
						
						// Select or insert professor
						int idProfessore = ProfessoreDatabase
								.selectOrInsertProfessore(conn, nome, cognome, universita);
						
						// Create the relation between professor and class
						SvolgimentoBean s = new SvolgimentoBean();
						s.setIdProfessore(idProfessore);
						s.setIdInsegnamento(idInsegnamento);
						SvolgimentoDatabase.createSvolgimento(conn, s);
					}
				}
			}
			
			DbUtils.commitAndClose(conn); // COMMIT
			
			insegnamentoBean.setId(idInsegnamento);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK
			
			m = new Message("Error while inserting a new class.", "XXX", e.getMessage());
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
			.append("/class?id=")
			.append(insegnamentoBean.getId());
		response.sendRedirect(builder.toString());	
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException, IOException 
    {

		Connection conn = null;
		Message m = null;
		
		String id = req.getParameter("id");
		String name = req.getParameter("name"); // name serve solo per la visualizzazione in entity_deleted
		
		if (id != null && !id.isEmpty() && name != null && !name.isEmpty()) 
		{
			int results;
			try {
				conn = DS.getConnection();
				results = InsegnamentoDatabase.deleteInsegnamento(conn, Integer.parseInt(id));				
				if (results > 0 )
				{
					String deletedEntity = name;
					req.setAttribute("deletedEntity", deletedEntity);
					getServletContext().getRequestDispatcher("/jsp/entity_deleted.jsp").forward(req, resp);
				}
				
			} 
			catch (SQLException e)
			{
				m = new Message("Error while deleting the class.", "", e.getMessage());
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
     * Handle an edit post request. It modifies an instance of entity ArgomentoTesi
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void edit(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException, ServletException 
    {
    	// Populate beans
		InsegnamentoBean insegnamentoBean = new InsegnamentoBean();
		BeanUtilities.populateBean(insegnamentoBean, request);
		
		// TODO ale: se e' resp. flusso bisogna impostare lo stato verified
		// altrimenti NOT VERIFIED!!
		// insegnamentoBean.setStato("NOT VERIFIED"); // Setting status
					
		String[] professorName = request.getParameterValues("professorName");
		String[] professorSurname = request.getParameterValues("professorSurname");
		
		// Required variables
		Connection con = null;
		Message m = null;
		
		try {
			con = DS.getConnection();
			
			// remove old class from Svolgimento
			SvolgimentoDatabase.deleteSvolgimentoByClassId(con, insegnamentoBean.getId());
			
			
			// update the class
			InsegnamentoDatabase.updateInsegnamento(con, insegnamentoBean);
			
			// check if the professor still existing, otherwise insert the new professor 
			// and then insert the corresponding row in Svolgimento
			int id = 0;
			SvolgimentoBean svolgimentoBean = new SvolgimentoBean();
			
			for(int i = 0; i < professorName.length; i++)
			{	
				if(!professorName[i].trim().equals("") && !professorSurname[i].trim().equals(""))
				{
					id = ProfessoreDatabase.selectOrInsertProfessore(con,
							professorName[i],
							professorSurname[i],
							insegnamentoBean.getNomeUniversita());
					
					svolgimentoBean.setIdInsegnamento(insegnamentoBean.getId());
					svolgimentoBean.setIdProfessore(id);
					
					SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
				}
			}
			
			// closing the connection
			DbUtils.close(con);
			
			// Creating response path and redirect to the new page
			StringBuilder builder = new StringBuilder()
			.append("/erasmus-advisor/class?id=")
			.append(insegnamentoBean.getId())
			.append("&edited=success");
			
			response.sendRedirect(builder.toString());
			
		} catch (SQLException e) {
			// Error management
			m = new Message("Error while editing " + insegnamentoBean.getNome() + " instance.","XXX", e.getMessage());
			request.setAttribute("message", m);
			
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(request, response); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

    private void errorForward(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException  {
    	// Error management
        	
    	//Message m = new Message("Error while updating the city.","XXX", "");
    	//request.setAttribute("message", m);
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
	
}
