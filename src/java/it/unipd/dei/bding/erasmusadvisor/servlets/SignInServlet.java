package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.IscrizioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ResponsabileFlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.StudenteDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
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
 * Form processing for the Sign in.
 * 
 * <p> Base URL: /signin
 * 
 * <p> Accepts: GET, POST
 * 
 * <p> Operations: (none)
 * 
 * @author Alessandro
 */
public class SignInServlet extends AbstractDatabaseServlet {
	
	private static final long serialVersionUID = -7165499740814517742L;

	/**
	 * Gets the Sign-in FORM
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Gets the current session (if exists)
		HttpSession session = request.getSession(false);
		
		// Checks if User is already logged! 
		if (session != null && session.getAttribute("loggedUser") != null) {
			// He has to do the Logout first!
			
			StringBuilder builder = new StringBuilder()
				.append(request.getContextPath() + "/index");
			
			// Redirect to index
			response.sendRedirect(builder.toString());
			
		} else { // Ok.. proceeds with the signin FORM

			request.getRequestDispatcher("/jsp/sign_in.jsp").forward(request,
					response);
		}

	}

	/**
	 * Method used for hash a particular password with the salt given.
	 * 
	 * @param password 
	 * 				password to hash
	 * @param salt 
	 * 				salt
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
	 * Processes the new subscription.
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Gets the current session (if exists)
		HttpSession session = request.getSession(false);
		
		// Check: User must not be already logged! 
		if (session != null && session.getAttribute("loggedUser") != null) {
			request.setAttribute("message", new Message("Operation impossibile.",
					"E200", "A logged user can not post a new signin!"));
			errorForward(request, response); // Error forward!
			return;
		}

		// Parameters
		String username = (String) request.getParameter("user");
		Connection con = null;
		Message m = null;
		UserType auth = null;

		try {
			String type = (String) request.getParameter("userType");
			if (type.equals("Manager")) {
				
				// set authorization
				auth = UserType.RESPONSABILE;
				
				// populate 
				ResponsabileFlussoBean manager = new ResponsabileFlussoBean();
				BeanUtilities.populateBean(manager, request);
				
				// set the password
				SecureRandom random = new SecureRandom();
				manager.setSalt("" + random.nextLong());
				manager.setPassword(hashPassword(manager.getPassword(), manager.getSalt()));
				manager.setNomeUtente(username);
				manager.setAbilitato(false);
				manager.setAttivo(true);

				
				
				// insert into the database
				con = DS.getConnection();
				
				ResponsabileFlussoDatabase.createResponsabileFlusso(con, manager);
				
			} else if(type.equals("Student")) {
				// set authorization
				auth = UserType.STUDENTE;
				
				// populate 
				StudenteBean student = new StudenteBean();
				BeanUtilities.populateBean(student, request);
				
				// settin the password
				SecureRandom random = new SecureRandom();
				student.setSalt("" + random.nextLong());
				student.setPassword(hashPassword(student.getPassword(), student.getSalt()));
				student.setNomeUtente(username);

				// processing the course name and level
				String[] res = null;
				res = extractCourseNameAndLevel(request.getParameter("courseName"));
				String courseName = res[0].trim();
				String courseLevel = res[1].trim();
				String courseUniversity = request.getParameter("nomeUniversita").trim();
				
				// getting the connection
				con = DS.getConnection();
				
				// getting the course id
				int courseId = CorsoDiLaureaDatabase.getCourseId(con, courseName, courseLevel, courseUniversity);
				
				
				// initialize a new subscription
				IscrizioneBean subscription = new IscrizioneBean();
				
				subscription.setIdCorso(courseId);
				subscription.setNomeUtenteStudente(student.getNomeUtente());
				subscription.setAnnoInizio(Date.valueOf(request.getParameter("date_from")));
				subscription.setAnnoFine(Date.valueOf(request.getParameter("date_to")));
				
				// inserting into the database
				
				con.setAutoCommit(false);
				StudenteDatabase.createStudente(con, student);
				IscrizioneDatabase.createIscrizione(con, subscription);
			}

			LoggedUser logged = new LoggedUser(auth, username);
			session = request.getSession();
			session.setAttribute("loggedUser", logged);

		} catch (NumberFormatException ex) {
			m = new Message(
					"Cannot create the user. Invalid input parameters.",
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message("Cannot create the user account: name "
						+ username + " already exists.", "E300",
						ex.getMessage());
			} else {
				m = new Message(
						"Cannot create the user: unexpected error while accessing the database.",
						"E200", ex.getMessage());
			}
		} finally {
			try {
				con.setAutoCommit(true);
				DbUtils.close(con);
			} catch (SQLException ex) {
				m = new Message(
						"Cannot close the connection",
						"XXX", ex.getMessage());
			} finally {
				DbUtils.closeQuietly(con);
			}
		}

		if (m == null) {
			getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(
					request, response);
		} else {
			// Error Page
			request.setAttribute("message", m);
			errorForward(request, response);
		}
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
    		
    	request.getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
