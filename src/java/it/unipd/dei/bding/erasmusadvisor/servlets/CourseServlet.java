package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SpecializzazioneBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.SpecializzazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages a specific Course.
 * 
 * <p> Base URL: /course
 * 
 * <p> Accepts: POST
 * 
 * <p> Operations: INSERT, EDIT, DELETE
 * 
 * @see UniversityServlet
 * @author Nicola, Ale
 */
public class CourseServlet extends AbstractDatabaseServlet {
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
	
	private static final long serialVersionUID = 12091245444464363L;

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// get the operation
		String operation = req.getParameter("operation");

		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn"); 

		if (operation == null || operation.isEmpty() || !lu.isFlowResp()) {

			// Error
			Message m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;

		} else if (operation.equals(INSERT)) {
			
			/**
			 * INSERT OPERATION
			 */

			insert(req, resp);

		} else if (operation.equals(EDIT)) {
			
			/**
			 * EDIT OPERATION
			 */

			edit(req, resp);

		} else if (operation.equals(DELETE)) {
			
			/**
			 * DELETE OPERATION
			 */

			delete(req, resp);

		} else {
			// operation not supported..
		}
	}
	


	/**
	 * Handle logic for insert operation.
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
		
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn"); 

		
		// entity beans
		CorsoDiLaureaBean corsoDiLaureaBean  = null;
		List<SpecializzazioneBean> specializzazioneBeanList = new ArrayList<SpecializzazioneBean>();
				
		// data models
		Message m = null;
				
		// database connection
		Connection conn = null;
		
		// Populating beans from FORM parameters
		corsoDiLaureaBean = new CorsoDiLaureaBean();
		corsoDiLaureaBean.setNome(request.getParameter("name"));
		corsoDiLaureaBean.setLivello(request.getParameter("level"));
		corsoDiLaureaBean.setNomeUniversita(request.getParameter("university"));
				
		String[] aree = request.getParameterValues("aree[]");
		if (aree != null) {
			for (int j=0; j<aree.length; j++) {
				SpecializzazioneBean s = new SpecializzazioneBean();
				s.setNomeArea(aree[j]);
				specializzazioneBeanList.add(s);
			}
		}

		/**
		 * Insert to database
		 */
		try {
			conn = DS.getConnection();
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			int id = CorsoDiLaureaDatabase.createCorsoDiLaurea(conn, corsoDiLaureaBean);
			for (SpecializzazioneBean s : specializzazioneBeanList) {
				s.setIdCorso(id); // Setting foreign key for IdCorso
				SpecializzazioneDatabase.createSpecializzazione(conn, s); 
			}
			
			DbUtils.commitAndClose(conn); // COMMIT
			
			corsoDiLaureaBean.setId(id);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK
			
			/** Primary key error */
			if (e.getSQLState() != null && e.getSQLState().equals("23505")) { 
				
				m = new Message("Operation not allowed: Duplicate data", "E300", 
						"This course is already present in the database!");
			} /** Foreign key error */
			else if (e.getSQLState() != null && e.getSQLState().equals("23503")) { 
				
				m = new Message("University not found", "E300", 
						"The university you specified is not present in the database!");
			} 
			else { 
				m = new Message("Error while inserting a new course.", "E200", e.getMessage());
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
		String addedEntity = request.getParameter("name");
		request.setAttribute("addedEntity", addedEntity);
		getServletContext().getRequestDispatcher("/jsp/entity_added.jsp").forward(request, response);
		/*StringBuilder builder = new StringBuilder()
				.append(request.getContextPath())
				.append("/jsp/include/showMessage.jsp");
		response.sendRedirect(builder.toString());*/	
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
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        //handle logic for delete operation...
    }
    

	/**
     * Handles an edit post request.
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
    private void edit(HttpServletRequest request, HttpServletResponse response) {
        //handle logic for edit operation...
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
