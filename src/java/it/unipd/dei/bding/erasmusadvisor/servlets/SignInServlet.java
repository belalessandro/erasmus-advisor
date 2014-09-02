package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.database.CreateResponsabileDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.CreateStudenteDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		Message m = null;
		UserType auth = UserType.STUDENTE;

		try {
			String type = (String) request.getParameter("userType");
			if (type.equals("Manager")) {
				auth = UserType.RESPONSABILE;
				ResponsabileFlussoBean s = new ResponsabileFlussoBean();
				// Sporcooooo.. da valutare se utilizzare gli stessi nomi per la
				// conversione automatica o farlo a mano
				BeanUtilities.populateBean(s, request); // popola automaticam. i
														// campi i cui nomi
														// coincidono con quelli
														// dei
														// beans
				SecureRandom random = new SecureRandom();
				s.setSalt("" + random.nextLong());
				s.setPassword(hashPassword(s.getPassword(), s.getSalt()));
				s.setNomeUtente(username);
				s.setAbilitato(false);
				s.setAttivo(true);

				new CreateResponsabileDatabase(DS.getConnection(), s)
						.createManager();
			} else {
				StudenteBean s = new StudenteBean();
				// Sporcooooo.. da valutare se utilizzare gli stessi nomi per la
				// conversione automatica o farlo a mano
				BeanUtilities.populateBean(s, request); // popola automaticam. i
														// campi i cui nomi
														// coincidono con quelli
														// dei
														// beans
				SecureRandom random = new SecureRandom();
				s.setSalt("" + random.nextLong());
				s.setPassword(hashPassword(s.getPassword(), s.getSalt()));
				s.setNomeUtente(username);

				new CreateStudenteDatabase(DS.getConnection(), s)
						.createStudente();
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
		}

		// stores the employee and the message as a request attribute
		// request.setAttribute("student", s);

		if (m == null) {
			// forwards the control to the index JSP
			// luca: cambiato da user_profile a index.jsp
			getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(
					request, response);
		} else {
			// Error Page
			request.setAttribute("message", m);
			errorForward(request, response);
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
    		
    	request.getServletContext().getRequestDispatcher("/jsp/error.jsp")
    		.forward(request, response); // ERROR PAGE
    }
}
