package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.database.CreateStudenteDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Form processing for the Sign in
 * 
 * @author Alessandro
 * 
 */
public class SignInServlet extends AbstractDatabaseServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		// model
		StudenteBean s  = null;
		Message m = null;

		try{
			
			s = new StudenteBean();
			// Sporcooooo.. da valutare se utilizzare gli stessi nomi per la conversione automatica o farlo a mano
			BeanUtilities.populateBean(s, request); // popola automaticam. i campi i cui nomi coincidono con quelli dei beans
			s.setSalt("afdfsfafdsg");
			s.setNomeUtente(request.getParameter("user"));
			
			new CreateStudenteDatabase(DS.getConnection(), s).createStudente();
			
		} catch (NumberFormatException ex) {
			m = new Message("Cannot create the student. Invalid input parameters: identifier, age, and salary must be integer.", 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message("Cannot create the student account: name " + s.getNomeUtente() + " already exists.", 
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the employee: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		
		// stores the employee and the message as a request attribute
		request.setAttribute("student", s);
		request.setAttribute("message", m);
		
		if (m == null) {
			// forwards the control to the user_profile JSP
			request.getRequestDispatcher("/jsp/user_profile.jsp").forward(request, response);
		} else {
			// come back to sign_in JSP
			request.getRequestDispatcher("/jsp/sign_in.jsp").forward(request, response);
		}
			
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}