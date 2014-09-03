package it.unipd.dei.bding.erasmusadvisor.servlets;


import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.CoordinatoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.database.CoordinatoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.IscrizioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ResponsabileFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.StudenteDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Student;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
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
	 * mappato su /user/profile
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
    private static final String DELETE = "remove";
    
	
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
		
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		if(lu == null)
		{	// TODO cancellare appena possibile
			lu = new LoggedUser(UserType.COORDINATORE, "ErasmusCoordinator");
		}

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
		HttpSession session = req.getSession();
		LoggedUser lu = (LoggedUser) session.getAttribute("loggedUser");
		
		if(lu == null)
		{
			lu = new LoggedUser(UserType.COORDINATORE, "ErasmusCoordinator");
		}
		
		// Required  fields
		Message m = null;
		
		String operation = req.getParameter("operation");
		
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
		else if (operation.equals(DELETE)) 
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
	 * @throws ParseException 
	 */
	private void edit(LoggedUser lu, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// required variables
		Connection con = null;
		Message m = null;
		
		// populate the bean
		CoordinatoreBean coordinator = null;
		StudenteBean student = null;
		IscrizioneBean subscription = null;
		ResponsabileFlussoBean manager = null;
		
		try {
			if(lu.isCoord())
			{
				// populate the bean
				coordinator = new CoordinatoreBean();
				BeanUtilities.populateBean(coordinator, request);
				
				// set the password
				SecureRandom random = new SecureRandom();
				coordinator.setSalt("" + random.nextLong());
				coordinator.setPassword(hashPassword(coordinator.getPassword(), coordinator.getSalt()));
				
				// get the connection
				con = DS.getConnection();
				
				CoordinatoreDatabase.updateCoordinatore(con, coordinator);
			}
			else if(lu.isFlowResp())
			{
				// populate the bean
				manager = new ResponsabileFlussoBean();
				BeanUtilities.populateBean(manager, request);
				
				// set the password
				SecureRandom random = new SecureRandom();
				manager.setSalt("" + random.nextLong());
				manager.setPassword(hashPassword(manager.getPassword(), manager.getSalt()));
				
				// get the connection
				con = DS.getConnection();
				
				ResponsabileFlussoDatabase.updateResponsabileFlusso(con, manager);
			}
			else if(lu.isStudent())
			{
				// populate the bean
				student = new StudenteBean();
				BeanUtilities.populateBean(student, request);
				
				// set the password
				SecureRandom random = new SecureRandom();
				student.setSalt("" + random.nextLong());
				student.setPassword(hashPassword(student.getPassword(), student.getSalt()));				
				
				// processing the course name and level
				String[] res = null;
				res = extractCourseNameAndLevel(request.getParameter("courseName"));
				String courseName = res[0].trim();
				String courseLevel = res[1].trim();
				String courseUniversity = request.getParameter("courseUniversity").trim();
				
				// getting old course id
				int old_courseId = Integer.parseInt(request.getParameter("old_courseId"));
				
				// get the connection
				con = DS.getConnection();
				
				// initialize a new subscription
				subscription = new IscrizioneBean();
				
				subscription.setNomeUtenteStudente(student.getNomeUtente());
				subscription.setAnnoInizio(Date.valueOf(request.getParameter("date_from")));
				subscription.setAnnoFine(Date.valueOf(request.getParameter("date_to")));
				
				// getting the course id
				int new_courseId = CorsoDiLaureaDatabase.getCourseId(con, courseName, courseLevel, courseUniversity);

				// Starting the transaction
				con.setAutoCommit(false);
				StudenteDatabase.updateStudent(con, student);
				subscription.setIdCorso(new_courseId);
				IscrizioneDatabase.updateIscrizione(con, subscription, old_courseId);
				
				con.commit();
			}
			
			// Creating response path
			StringBuilder builder = new StringBuilder()
					.append(request.getContextPath())
					.append("/user/profile?edited=success");
			
			response.sendRedirect(builder.toString());
			
		} catch (SQLException e) {
			
			// managing overlapping courses
			if(e.getSQLState().equals("EA003"))
			{
				sendErrorOverlappingDates(request, response);
			}
			else
			{
				// Error
				m = new Message("Error while editing user profile.", String.valueOf(e.getErrorCode()) + " " +  e.getSQLState() , e.getMessage());
				request.setAttribute("message", m);
				
				errorForward(request, response);
				return;
			}
			
		} finally {
			// closing the connection
			try {
				if(con != null)
					con.setAutoCommit(true);
				DbUtils.close(con);
			} catch (SQLException e) {
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
	}
	
	/**
	 * Method used for sending a warn to the user.
	 * @param request request object
	 * @param response response object
	 * @throws IOException
	 */
	private void sendErrorOverlappingDates(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// let the page show an error
		StringBuilder builder = new StringBuilder()
				.append(request.getContextPath())
				.append("/user/profile?error=overlapping");
		response.sendRedirect(builder.toString());
		
	}

	/**
	 * Extract a name and the level from a complete course 
	 * name.
	 * 
	 * Example: Information Engineering (UNDERGRADUATE) 
	 * 			returns s[0] = "Information Engineering" 
	 * 					s[1] = "UNDERGRADUATE"
	 * 
	 * @param parameter string to parse
	 */
	private String[] extractCourseNameAndLevel(String parameter) {
		
		String[] res = new String[2];
		
		int init = 0;
		int end = parameter.length();
		boolean open = false;
		for(int i = 0; i < parameter.length(); i++)
		{
			if(parameter.charAt(i) == '(' && !open)
			{
				open = true;
				init = i + 1;
			}
			else if(parameter.charAt(i) == ')' && open)
				end = i;
		}
		
		res[0] = parameter.substring(0, init - 1);
		res[1] = parameter.substring(init, end);
		
		return res;
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
	 * Method used for hash a particular password with the salt given.
	 * 
	 * @param password password to hash
	 * @param salt salt
	 * @return the hashed password
	 */
	private String hashPassword(String password, String salt) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			String salted = password + salt;
			try {
				byte[] hash = digest.digest(salted.getBytes("UTF-8"));
				return new String(hash, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				//e.printStackTrace();
				throw new IllegalStateException();
			}
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
			throw new IllegalStateException();
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
