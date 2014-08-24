/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
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
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
	 * Default Serial version UID 
	 */
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
		
		// model
		Teaching results = null;
		Message m = null;
		List<LinguaBean> languageDomain = null;
		List<AreaBean> areaDomain = null;
		
		// database connection
		Connection conn = null;

					
		try {
			conn = DS.getConnection();
			results = InsegnamentoDatabase.getInsegnamento(conn, Integer.parseInt(ID));
			languageDomain = GetLinguaValues.getLinguaDomain(conn);
			areaDomain = GetAreaValues.getAreaDomain(conn);
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
		
		String operation = req.getParameter("operation");
		
		// database required fields
		Connection con = null;
		Message m = null;
		
		if(operation.equals("edit"))
		{
			// Populate beans
			InsegnamentoBean insegnamentoBean = new InsegnamentoBean();
			BeanUtilities.populateBean(insegnamentoBean, req);
						
			String[] professorName = req.getParameterValues("professorName");
			String[] professorSurname = req.getParameterValues("professorSurname");
			
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
				
				resp.sendRedirect(builder.toString());
				
			} catch (SQLException e) {
				// Error management
				m = new Message("Error while editing " + insegnamentoBean.getNome() + " instance.","XXX", e.getMessage());
				req.setAttribute("message", m);
				
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
				return;
			} finally {
				DbUtils.closeQuietly(con);
			}
		}
		
	}
	
	
}
