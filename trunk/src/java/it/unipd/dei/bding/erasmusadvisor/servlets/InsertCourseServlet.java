
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SpecializzazioneBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.SpecializzazioneDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

/**
 * Form processing for the creation of a new Course
 * 
 * @author Nicola
 * @version 1.0
 * 
 * Nb: in insert_course.jsp va aggiunto un campo di inserimento area!!
 */
public class InsertCourseServlet extends AbstractDatabaseServlet {
	
	private static final long serialVersionUID = 12091245444464363L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO: DA SESSIONE
		LoggedUser lu = new LoggedUser(LoggedUser.AUTH_FLOWRESP, "erick.burn"); 

		if (!lu.isFlowResp()) { // Not authorized
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}
			
		// entity beans
		CorsoDiLaureaBean c  = null;
		SpecializzazioneBean[] s = null;
		
		// data models
		Message m = null;
		
		// database connection
		Connection conn = null;

		try{
			c = new CorsoDiLaureaBean();
			c.setNome(request.getParameter("name"));
			c.setLivello(request.getParameter("level"));
			c.setNomeUniversita(request.getParameter("university"));
			

			conn = DS.getConnection(); // Create *only* ONE connection
			conn.setAutoCommit(false); // BEGIN TRANSACTION
			
			int id = CorsoDiLaureaDatabase.createCorsoDiLaurea(conn, c);
			
			String[] aree = request.getParameterValues("aree[]");
			if (aree != null) {
				s = new SpecializzazioneBean[aree.length];
				for (int j=0; j<aree.length; j++) {
					s[j] = new SpecializzazioneBean();
							
					String area = aree[j];

					s[j].setIdCorso(id);
					s[j].setNomeArea(area);
					SpecializzazioneDatabase.createSpecializzazione(conn, s[j]); 
				}
			}
			
			DbUtils.commitAndClose(conn); // COMMIT TRANSACTION
			
			m = new Message("Course " + id + " inserted successfully.");
						
		} catch (NumberFormatException ex) {
			m = new Message("Cannot create the course. Invalid input parameters.", 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			DbUtils.rollbackAndCloseQuietly(conn); // ROLLBACK TRANSACTION
			if (ex.getSQLState().equals("23505")) {
				m = new Message("Cannot create the course: id " + c.getId() + " already exists.", 
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the course: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		} finally {
			DbUtils.closeQuietly(conn); // Close *always* the database connection
		}
		
		if (!m.isError()) {
			request.getSession().setAttribute("message", m);
			response.sendRedirect("jsp/insert_course.jsp?notify=success");
		} else { // ERROR
			// stores the message as a request attribute
			request.setAttribute("message", m);
			
			// come back to flow insertion JSP page
			request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		}
			
	}
}
