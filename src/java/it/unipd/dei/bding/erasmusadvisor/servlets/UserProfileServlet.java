package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.CoordinatoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.database.CoordinatoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.IscrizioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ResponsabileFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.StudenteDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Student;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

/**
 * Manages the profile of a generic User.
 * 
 * <p> Base URL: /user/profile
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: EDIT
 * 
 * @author Mauro
 */
public class UserProfileServlet extends AbstractDatabaseServlet 
{
	/**
	 * (Autorizzazioni: solo STUDENTE)
	 * 
	 * mappato su /student/profile
	 * 
	 * quando riceve GET
	 * 			-> ritorna su user_profile.jsp tutti i campi collegati allo studente loggato
	 * 
	 * 
	 * quando riceve POST
	 *   		-> Se operazione è "update" modifica i campi relativi allo studente loggato
	 *   		-> Se l'operazione e' "remove" imposta a false il campo "attivo" dell'utente.
	 *   
	 *   @author: luca
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7349132696365020115L;
	/**
	 * Operation constants
	 */
	private static final String INSERT = "insert";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
	
    /**
	 * Gets the user profile page.
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
		Connection con = null;
		Message m = null;
		Student student = null;
		CoordinatoreBean coordinator = null;
		ResponsabileFlussoBean manager = null;
		
		
		/* TODO
		HttpSession session = req.getSession(false);
		if (session == null)
		{
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
		TODO LoggedUser lu = (LoggedUser)req.getSession().getAttribute("loggedUser");*/
		
//		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "mario.rossi");
//		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn");
//		LoggedUser lu = new LoggedUser(UserType.COORDINATORE, "ErasmusCoordinator");
		
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		if(lu == null)
			lu = new LoggedUser(UserType.STUDENTE, "mario.rossi");
		
		try {
			con = DS.getConnection();
			
			// checking the user
			if(lu.isCoord())
				coordinator = CoordinatoreDatabase.getCoordinatore(con, lu.getUser());
			else if(lu.isFlowResp())
				manager = ResponsabileFlussoDatabase.getReponsabileFlusso(con, lu.getUser());
			else if(lu.isStudent())
				student = StudenteDatabase.getStudent(con, lu.getUser());
			
			DbUtils.close(con);
		} 
		catch (SQLException ex) {
			m = new Message("Error while getting the user profile page.", "XXX", ex.getMessage());
		} 
		finally {
			DbUtils.closeQuietly(con); // always closes the connection 
		}

		if (m == null)
		{
			// checking the user
			if(lu.isCoord())
				req.setAttribute("coordinator", coordinator);
			else if(lu.isFlowResp())
				req.setAttribute("flowmanager", manager);
			else if(lu.isStudent())
			{
				req.setAttribute("student", student.getStudente());
				req.setAttribute("subscription", student.getIscrizione());
				req.setAttribute("course", student.getCorso());
			}
			
			getServletContext().getRequestDispatcher("/jsp/user_profile.jsp").forward(req, resp);
		}
		else
		{
			req.setAttribute("message", m);
			getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}
	}
	
	/**
	 * Handles an operation form.
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
			throws ServletException, IOException 
	{
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(UserType.STUDENTE, "mario.rossi");
//		LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "erick.burn");
//		LoggedUser lu = new LoggedUser(UserType.COORDINATORE, "ErasmusCoordinator"); 
		
		// Required  fields
		Message m = null;
		
		String operation = req.getParameter("operation");
		
		
//		PrintWriter w = resp.getWriter();
//		w.println("<html>");
//		w.println("<body>");
//		w.println("<p>" + operation + "</p>");
//		
//		w.println("</body>");
//		w.println("</html>");
//		w.flush();
//		w.close();
		if (operation == null || operation.isEmpty()) {
			
			// Error
			m = new Message("Not authorized or operation null", "", "");
			req.setAttribute("message", m);
			errorForward(req, resp);
			return;
			
		} 
		else if (operation.equals(EDIT)) 
		{
			edit(lu, req, resp);
		}
		else if (operation.equals("remove")) 
		{
			remove(lu, req, resp);
		}
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
	private void edit(LoggedUser lu, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// required variables
		Connection con = null;
		Message m = null;
		
		// populate the bean
		CoordinatoreBean coordinatore = null;
		StudenteBean studente = null;
		CorsoDiLaureaBean corso = null;
		IscrizioneBean iscrizione = null;
		ResponsabileFlussoBean responsabile = null;
		
		try {
			if(lu.isCoord())
			{
				// populate the bean
				coordinatore = new CoordinatoreBean();
				BeanUtilities.populateBean(coordinatore, request);
				
				// get the connection
				con = DS.getConnection();
				
				// update the instance
				// TODO
//				CoordinatoreDatabase.updateCoordinatore(con, coordinatore);
			}
			else if(lu.isFlowResp())
			{
				// populate the bean
				responsabile = new ResponsabileFlussoBean();
				BeanUtilities.populateBean(responsabile, request);
				
				PrintWriter w = response.getWriter();
				w.println("<html>");
				w.println("<body>");
				w.println("<p>" + responsabile.getNome() + "</p>");
				w.println("<p>" + request.getParameter("date_from") + "</p>");
				w.println("<p>" + request.getParameter("date_to") + "</p>");
				w.println("</body>");
				w.println("</html>");
				w.flush();
				w.close();
				
				// get the connection
				con = DS.getConnection();
				
				// TODO
//				ResponsabileFlussoDatabase.updateResponsabileFlusso(con, responsabile);
			}
			else if(lu.isStudent())
			{
				// populate the bean
				studente = new StudenteBean();
				BeanUtilities.populateBean(studente, request);
				
				
//				corso.setId(Integer.parseInt(request.getParameter("courseId")));
//				corso.setNome("courseName");
//				corso.setLivello(request.getParameter("courseLevel"));
//				corso.setNomeUniversita(request.getParameter("courseUniversity"));
				
//				int corsoId = Integer.parseInt(request.getParameter("courseId"));
				
				
//				iscrizione = new IscrizioneBean();
//				iscrizione.setIdCorso(Integer.parseInt(request.getParameter("courseId")));
//				iscrizione.setNomeUtenteStudente(studente.getNomeUtente());
//				iscrizione.setAnnoInizio(Date.valueOf(request.getParameter("date_from")));
//				iscrizione.setAnnoFine(Date.valueOf(request.getParameter("date_to")));
//				
				
				PrintWriter w = response.getWriter();
				w.println("<html>");
				w.println("<body>");
				w.println("<p>" + request.getParameter("old_courseId") + "</p>");
				w.println("<p>" + request.getParameter("courseName") + "</p>");
				w.println("<p>" + request.getParameter("courseUniversity") + "</p>");
				w.println("<p>" + request.getParameter("date_from") + "</p>");
				w.println("<p>" + request.getParameter("date_to") + "</p>");
				w.println("</body>");
				w.println("</html>");
				w.flush();
				w.close();

				
				// get the connection
				con = DS.getConnection();
				
				StudenteDatabase.updateStudent(con, studente);
				
				IscrizioneDatabase.createIscrizione(con, iscrizione);
				
				DbUtils.close(con);
				
				// Creating response path
				StringBuilder builder = new StringBuilder()
						.append(request.getContextPath())
						.append("/user/profile?edited=success");
				response.sendRedirect(builder.toString());
			}
			
			DbUtils.close(con);
		} catch (SQLException e) {
			
			// TODO: manage the case ERROR CODE = EA003
			
			// Error
			m = new Message("Error while editing user profile.", String.valueOf(e.getErrorCode()) + " " +  e.getSQLState() , e.getMessage());
			request.setAttribute("message", m);
			errorForward(request, response);
			return;
		} finally {
			DbUtils.closeQuietly(con);
			con = null;
		}
	}
	
	/**
	 * Disable an user.
	 * 
	 * @param lu A logged User.
	 * @param request An HttpServletRequest.
	 * @param response An HttpServletResponse.
	 * @throws ServletException
	 * @throws IOException If an error occurs.
	 */
	private void remove(LoggedUser lu, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
				// required variables
				Connection con = null;
				Message m = null;
				
				try {
					if(lu.isCoord())
					{
						// get the connection
						con = DS.getConnection();
						//disable an erasmus' manager.
						CoordinatoreDatabase.removeCoordinatore(con, lu.getUser());
					}
					else if(lu.isFlowResp())
					{
						// get the connection
						con = DS.getConnection();
						//disable a flow's manager.
						ResponsabileFlussoDatabase.disableResponsabileFlusso(con, lu.getUser());
					}
					else if(lu.isStudent())
					{
						// get the connection
						con = DS.getConnection();
						
						StudenteDatabase.disableStudente(con, lu.getUser());
						
						DbUtils.close(con);
						
						// Creating response path
						StringBuilder builder = new StringBuilder()
								.append(request.getContextPath())
								.append("/jsp/home.jsp");
						response.sendRedirect(builder.toString());
					}
					
					DbUtils.close(con);
				} catch (SQLException e) {
					
					// TODO: manage the case ERROR CODE = EA003
					
					// Error
					m = new Message("Error while editing user profile.", String.valueOf(e.getErrorCode()) + " " +  e.getSQLState() , e.getMessage());
					request.setAttribute("message", m);
					errorForward(request, response);
					return;
				} finally {
					DbUtils.closeQuietly(con);
					con = null;
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
