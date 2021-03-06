package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.EstensioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.GestioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaTesiBean;
import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.EstensioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GestioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.LinguaTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.PartecipazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ProfessoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.UserDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ValutazioneTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;
import it.unipd.dei.bding.erasmusadvisor.resources.ThesisEvaluationsAverage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages a specific Thesis.
 * 
 * <p> Base URL: /thesis
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: INSERT, UPDATE, DELETE, AJAX
 * 
 * @see UniversityServlet
 * @author Alessandro, Nicola, Luca
 */
public class ThesisServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String AJAX = "ajax";

	private static final long serialVersionUID = 77657689265503855L;

	/**
	 * Get the details of a specific thesis.
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
		String ID = req.getParameter("id");

		if (ID == null || ID.isEmpty()) 
		{
			/* Redirect to the search form. */
			resp.sendRedirect(req.getContextPath() + "/thesis/list");
			return;
		}

		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		/**
		 *  Gets the thesis model from the database
		 */
		
		// model
		Thesis results = null;
		Message m = null;
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		boolean evalEnabled = false;
		
		// database connection
		Connection conn = null;

		try {
			conn = DS.getConnection();
			results = ArgomentoTesiDatabase.getArgomentoTesi(conn, ID);
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			// determina se abilitare l'inserimento della valutazione
			if (lu.isStudent())
			{
				if (PartecipazioneDatabase.checkParticipation(conn, results.getArgomentoTesi().getNomeUniversita(), lu.getUser()))
				{
					if (!(ValutazioneTesiDatabase.checkEvaluation(conn, lu.getUser(), Integer.parseInt(ID))))
					{
						evalEnabled = true;
					}
				}
			}
			DbUtils.close(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the thesis.","XXX", "Please, contact the admin.");
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		/**
		 *  Send the thesis model to the appropriate output (Ajax or normal)
		 *
		 */
		if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) 
		{
			// Handle Ajax response (e.g. return JSON data object).
			
			/* NOT IMPLEMENTED */
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
				req.setAttribute("areas", results.getAree());
				req.setAttribute("languages", results.getLingue());
				req.setAttribute("evaluations", results.getListaValutazioni());
				req.setAttribute("evaluationsAvg", new ThesisEvaluationsAverage(results.getListaValutazioni()));
				req.setAttribute("languageDomain", languageDomain);
				req.setAttribute("areaDomain", areaDomain);
				
				if (lu.isStudent() && evalEnabled == true)
				{
					req.setAttribute("evalEnabled", "enabled");
				}
				else
				{
					req.setAttribute("evalEnabled", "notEnabled");
				}

				getServletContext().getRequestDispatcher("/jsp/show_thesis.jsp").forward(req, resp);
			}
			else { // Error page
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
		}
	}
	
	/**
	 * Handles an operation form
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
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Operation parameter
		String operation = null;
		
		if(req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest"))
			operation = "ajax";
		else
			operation = req.getParameter("operation");
		
		// Gets user from session
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		// Manager permissions
		boolean allowed = false;
		
		if (lu.isCoord() || lu.isFlowResp())
			allowed = true; // XXX
		
		/** 
		 * OPERATION DISPATCHER 
		 */
		if (operation != null && operation.equals(AJAX) && (lu != null))
		{		// Permissions required: LOGGED
			report(req, resp);
		}
		else if (operation != null && operation.equals(INSERT) && (lu != null)) 
		{		// Permissions required: LOGGED
			insert(req, resp, lu);
		} 
		else if (operation != null && operation.equals(UPDATE) && (allowed)) 
		{		// Permissions required: FlowManager, Coordinator
			edit(req, resp, lu);
		} 
		else if (operation != null && operation.equals(DELETE) && (allowed)) 
		{		// Permissions required: FlowManager, Coordinator
			delete(req, resp);
		}
		else 
		{
			req.setAttribute("message", 
					new Message("Not authorized or operation not allowed", "E200", ""));
			errorForward(req, resp);
		}
		
	}

	/**
	 * Handle the report for a thesis. 
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
	private void report(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// get JSON object
		response.setContentType("application/json");
		JsonReader reader = Json.createReader(request.getInputStream());
		JsonObject json = reader.readObject();
		reader.close();
		
		
		// modify the instance into the database
		Message m = null;
		Connection con = null;
		
		try {
			con = DS.getConnection();
			
			ArgomentoTesiDatabase.changeThesisStatus(con, "REPORTED", json.getInt("id"));
			
			DbUtils.close(con);
			
		} catch (SQLException e) {
			m = new Message("Error while reporting the thesis.", "XXX", "Please, contact the admin.");
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		} finally {
			DbUtils.closeQuietly(con);
		}
		
		// writing the JSON object to the page
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		builder.add("report", "success");
		JsonObject out = builder.build();
		
		JsonWriter writer = Json.createWriter(response.getOutputStream());
		writer.writeObject(out);
		writer.close();
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
	private void insert(HttpServletRequest request, HttpServletResponse response, LoggedUser lu) 
			throws ServletException, IOException  {
		
		// the connection to database
		Connection conn = null;
		
		// entity beans
		ArgomentoTesiBean argomentoTesiBean  =  new ArgomentoTesiBean();
		List<EstensioneBean> estensioneBeanList = new ArrayList<EstensioneBean>();
		List<LinguaTesiBean> linguaTesiBeanList = new ArrayList<LinguaTesiBean>();
		
		// models
		Message m = null;
		
		/**
		 * Beans population
		 */
		
		// Populating beans with the FORM submitted
		argomentoTesiBean.setNome(request.getParameter("nome"));
		argomentoTesiBean.setNomeUniversita(request.getParameter("nomeUniversita"));
		
		// set check-box values
		String triennale = request.getParameter("triennale");
		String magistrale = request.getParameter("magistrale");
		argomentoTesiBean.setTriennale(triennale == null ? false : true);
		argomentoTesiBean.setMagistrale(magistrale == null ? false : true);
		
		// Setting additional fields, according to roles
		if (lu.isCoord() || lu.isFlowResp())
			argomentoTesiBean.setStato("VERIFIED");
		else
			argomentoTesiBean.setStato("NOT VERIFIED");
		
		// Getting professors from the FORM submitted
		String[] profNames = request.getParameterValues("professorName");
		String[] profSurnames = request.getParameterValues("professorSurname");
		
		// Getting areas from the FORM submitted
		String[] aree = request.getParameterValues("area[]");
		if (aree != null) {
			for (int j=0; j<aree.length; j++) {
				EstensioneBean s = new EstensioneBean();
				s.setArea(aree[j]);
				estensioneBeanList.add(s);
			}
		}
		
		// Getting languages from the FORM submitted
		String[] siglaLingua = request.getParameterValues("language[]");
		if (siglaLingua != null) {
			for(int i=0;i<siglaLingua.length;i++)
			{
				LinguaTesiBean l = new LinguaTesiBean(); 
				l.setSiglaLingua(siglaLingua[i]);
				linguaTesiBeanList.add(l);
			}
		}

		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			

			if (lu.isStudent()) { // Checks whether the student has the permissions to insert
				boolean studentAllowed = UserDatabase.canStudentInsert(conn, lu.getUser(), 
						argomentoTesiBean.getNomeUniversita());
				if ( ! studentAllowed )
					throw new SQLException("Students can not insert thesis of universities they do not know!");
			}
			
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			/**
			 * -> ArgomentoTesi
			 */
			int idTesi= ArgomentoTesiDatabase.createArgomentoTesi(conn, argomentoTesiBean);
			
			/**
			 * -> Gestione
			 */
			if (profNames != null && profSurnames != null && profNames.length == profSurnames.length) {
				for (int j=0; j<profNames.length; j++) {
					String nome = profNames[j];
					String cognome = profSurnames[j];
					String universita = argomentoTesiBean.getNomeUniversita();
					
					if (nome != null && cognome != null && !nome.isEmpty() && !cognome.isEmpty() 
							&& universita != null) {
						nome = nome.trim();
						cognome = cognome.trim();
						
						// Select or insert professor
						int idProfessore = ProfessoreDatabase
								.selectOrInsertProfessore(conn, nome, cognome, universita);
						

						// Create the relation between professor and thesis
						GestioneBean g = new GestioneBean();
						g.setIdProfessore(idProfessore);
						g.setIdArgomentoTesi(idTesi);  // Setting foreign key 
						GestioneDatabase.createGestione(conn, g);
					}
				}
			}
			
			/**
			 * -> Estensione
			 */
			for (EstensioneBean e : estensioneBeanList) {
				e.setIdArgomentoTesi(idTesi); // Setting foreign key for IdArgomentoTesi
				EstensioneDatabase.createEstensione(conn, e); 
			}
			
			/**
			 * -> LinguaTesi
			 */
			for (LinguaTesiBean l : linguaTesiBeanList) {
				l.setIdArgomentoTesi(idTesi);  // Setting foreign key for IdArgomentoTesi
				LinguaTesiDatabase.createLinguaTesi(conn, l);
			}
			
			DbUtils.commitAndClose(conn); // COMMIT
			
			argomentoTesiBean.setId(idTesi);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK
			
			/** Primary key error */
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) { 
				
				m = new Message("Operation not allowed: Duplicate data", "E300", 
						"This thesis is already present in the database!");
			} /** Foreign key error */
			else if (e.getSQLState() != null && e.getSQLState().equals("23503")) { 
				
				m = new Message("University not found", "E300", 
						"The university you specified is not present in the database!");
			} 
			else { 
				m = new Message("Error while inserting a new Thesis.", "E200", "Please, contact the admin.");
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
			.append("/thesis?id=")
			.append(argomentoTesiBean.getId());
		response.sendRedirect(builder.toString());	
    }

 	/**
	 * Handles a delete request.
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
		
		String id = req.getParameter("id");
		String name = req.getParameter("name"); // name serve solo per la visualizzazione in entity_deleted
		
		if (id != null && !id.isEmpty() && name != null && !name.isEmpty()) 
		{
			int results;
			try {
				conn = DS.getConnection();
				results = ArgomentoTesiDatabase.deleteArgomentoTesi(conn, Integer.parseInt(id));				
				if (results > 0 )
				{
					String deletedEntity = name;
					req.setAttribute("deletedEntity", deletedEntity);
					getServletContext().getRequestDispatcher("/jsp/entity_deleted.jsp").forward(req, resp);
				}
				
			} 
			catch (SQLException e)
			{
				m = new Message("Error while deleting the thesis.", "", "Please, contact the admin.");
				req.setAttribute("message", m);
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			}
			finally {
				DbUtils.closeQuietly(conn); // always closes the connection 
			}
		} 
		else {
			m = new Message("Bad parameters.", "E100", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
		}
    }
    
    
    /**
     * Handle an edit post request.
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
    private void edit(HttpServletRequest req, HttpServletResponse resp, LoggedUser lu) 
    		throws IOException, ServletException {
		
    	// data models, connection
    	Message m = null;
    	Connection conn = null;
    	
		ArgomentoTesiBean argomento = new ArgomentoTesiBean();
		BeanUtilities.populateBean(argomento, req);

		// Sets status according to role
		if (lu.isCoord() || lu.isFlowResp())
			argomento.setStato("VERIFIED");
		else
			argomento.setStato("NOT VERIFIED");

		
		// set checkbox values
		String triennale = req.getParameter("triennale");
		String magistrale = req.getParameter("magistrale");
		if (triennale == null)
			argomento.setTriennale(false);
		else
			argomento.setTriennale(true);

		if (magistrale == null)
			argomento.setMagistrale(false);
		else
			argomento.setMagistrale(true);

		String[] languages = req.getParameterValues("language");
		String[] areas = req.getParameterValues("area");

		String[] professorName = req.getParameterValues("professorName");
		String[] professorSurname = req.getParameterValues("professorSurname");
		
		try {
			conn = DS.getConnection();

			// remove old thesis from gestione
			GestioneDatabase.deleteGestioneByThesisId(conn, argomento.getId());

			// remove old thesis languages
			LinguaTesiDatabase.deleteLinguaTesi(conn, argomento.getId());

			// remove old thesis areas
			EstensioneDatabase.deleteEstensione(conn, argomento.getId());

			// update the thesis
			ArgomentoTesiDatabase.updateArgomentoTesi(conn, argomento);

			// insert languages
			LinguaTesiBean linguaTesi = new LinguaTesiBean();
			
			for(int i = 0; i < languages.length; i++)
			{
				linguaTesi.setIdArgomentoTesi(argomento.getId());
				linguaTesi.setSiglaLingua(languages[i]);
				
				LinguaTesiDatabase.createLinguaTesi(conn, linguaTesi);
			}

			// insert  areas
			EstensioneBean estensione = new EstensioneBean();
			for(int i = 0; i < areas.length; i++)
			{
				estensione.setIdArgomentoTesi(argomento.getId());
				estensione.setArea(areas[i]);
				
				EstensioneDatabase.createEstensione(conn, estensione);	
			}

			// check if the professor still existing, otherwise insert the new
			// professor
			// and then insert the corresponding row into Gestione
			int idProfessore = 0;
			GestioneBean gestioneBean = new GestioneBean();

			for (int i = 0; i < professorName.length; i++) {
				if (!professorName[i].trim().equals("")
						&& !professorSurname[i].trim().equals("")) {
					idProfessore = ProfessoreDatabase.selectOrInsertProfessore(
							conn, professorName[i], professorSurname[i],
							argomento.getNomeUniversita());

					gestioneBean.setIdArgomentoTesi(argomento.getId());
					gestioneBean.setIdProfessore(idProfessore);

					GestioneDatabase.createGestione(conn, gestioneBean);
				}
			}

			// closing the connection
			DbUtils.close(conn);

			// Creating response path and redirect to the new page
			StringBuilder builder = new StringBuilder()
					.append(req.getContextPath())
					.append("/thesis?id=")
					.append(argomento.getId()).append("&edited=success");

			resp.sendRedirect(builder.toString());

		} catch (SQLException e) {
			// Error management
			m = new Message("Error while editing " + argomento.getNome()
					+ " instance.", "XXX", "Please, contact the admin.");
			req.setAttribute("message", m);

			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(
					req, resp); // ERROR PAGE
			return;
		} finally {
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
    		
    	getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
