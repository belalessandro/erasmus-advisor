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
import it.unipd.dei.bding.erasmusadvisor.database.ProfessoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;
import it.unipd.dei.bding.erasmusadvisor.resources.ThesisEvaluationsAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Mapped to /thesis
 * @author Nicola, Luca
 *
 */
public class ThesisServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";

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
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		
		// database connection
		Connection conn = null;

		try {
			conn = DS.getConnection();
			results = ArgomentoTesiDatabase.getArgomentoTesiByID(conn, ID);
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
			DbUtils.close(conn);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the thesis.","XXX", ex.getMessage());
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
				req.setAttribute("areas", results.getAree());
				req.setAttribute("languages", results.getLingue());
				req.setAttribute("evaluations", results.getListaValutazioni());
				req.setAttribute("evaluationsAvg", new ThesisEvaluationsAverage(results.getListaValutazioni()));
				req.setAttribute("languageDomain", languageDomain);
				req.setAttribute("areaDomain", areaDomain);

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

		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn");
		String operation = req.getParameter("operation");
		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {
			/* Error or not authorized. */
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
			return;
		} 
		else if (operation.equals(INSERT)) 
		{		
			insert(req, resp);
		} 
		else if (operation.equals(UPDATE)) 
		{
			edit(req, resp);
		} 
		else if (operation.equals(DELETE)) 
		{
			delete(req, resp);
		} 
		else {
			// operation not supported..
		}
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
		
		// Setting additional fields 
		argomentoTesiBean.setStato("NOT VERIFIED"); // Setting status 
		//TODO: in base all'authorization
		
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
			
			m = new Message("Error while inserting a new thesis.", "XXX", e.getMessage());
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
				m = new Message("Error while deleting the thesis.", "", e.getMessage());
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
    
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// handle logic for edit operation...

    	// data models, connection
    	Message m = null;
    	Connection conn = null;
    	
		ArgomentoTesiBean argomento = new ArgomentoTesiBean();
		BeanUtilities.populateBean(argomento, req);

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

		String language = req.getParameter("language");
		String area = req.getParameter("area");

		String[] professorName = req.getParameterValues("professorName");
		String[] professorSurname = req.getParameterValues("professorSurname");

		try {
			conn = DS.getConnection();

			// remove old thesis from gestione
			GestioneDatabase.deleteGestioneByThesisId(conn, argomento.getId());

			// remove old thesis language
			LinguaTesiDatabase.deleteLinguaTesi(conn, argomento.getId());

			// remove old thesis area
			EstensioneDatabase.deleteEstensione(conn, argomento.getId());

			// update the thesis
			ArgomentoTesiDatabase.updateArgomentoTesi(conn, argomento);

			// insert the language
			LinguaTesiBean linguaTesi = new LinguaTesiBean();
			linguaTesi.setIdArgomentoTesi(argomento.getId());
			linguaTesi.setSiglaLingua(language);

			LinguaTesiDatabase.createLinguaTesi(conn, linguaTesi);

			// insert the area
			EstensioneBean estensione = new EstensioneBean();
			estensione.setIdArgomentoTesi(argomento.getId());
			estensione.setArea(area);

			EstensioneDatabase.createEstensione(conn, estensione);

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
					.append("/erasmus-advisor/thesis?id=")
					.append(argomento.getId()).append("&edited=success");

			resp.sendRedirect(builder.toString());

		} catch (SQLException e) {
			// Error management
			m = new Message("Error while editing " + argomento.getNome()
					+ " instance.", "XXX", e.getMessage());
			req.setAttribute("message", m);

			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(
					req, resp); // ERROR PAGE
			return;
		} finally {
			DbUtils.closeQuietly(conn);
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
