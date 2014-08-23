/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.servlets;

import it.unipd.dei.bding.erasmusadvisor.database.GetAreaValues;
import it.unipd.dei.bding.erasmusadvisor.database.GetLinguaValues;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.ProfessoreDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.SvolgimentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.TeachingEvaluationAverage;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;
import it.unipd.dei.bding.erasmusadvisor.resources.Teaching;
import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.BeanUtilities;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	protected void  doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String operation = req.getParameter("operation");
		
		// database required fields
		Connection con = null;
		Message m = null;
		
		if(operation.equals("edit"))
		{
			// Populate beans
			InsegnamentoBean insegnamentoBean = new InsegnamentoBean();
			BeanUtilities.populateBean(insegnamentoBean, req);
			
			ArrayList<ProfessoreBean> professoreBeanList = new ArrayList<ProfessoreBean>();			
			String[] professorName = req.getParameterValues("professorName");
			String[] professorSurname = req.getParameterValues("professorSurname");
			PrintWriter w = resp.getWriter();
			
			// remove old class from Svolgimento
			w.println("<html>");
			w.println("<body>");
			ProfessoreBean pb = new ProfessoreBean();
			for(int i = 0; i < professorName.length; i++)
			{
				pb.setNome(professorName[i]);
				pb.setCognome(professorSurname[i]);
				
				professoreBeanList.add(pb);
				w.println("<h2>Delete " + professorName[i]+ " rows</h2>");
			}
			
			try {
				con = DS.getConnection();
				
				
				int deleted = SvolgimentoDatabase.deleteSvolgimentoByClassId(con, insegnamentoBean.getId());
				w.println("<h2>Delete " + deleted + " rows</h2>");
				
				// update the class
				int value = InsegnamentoDatabase.updateInsegnamento(con, insegnamentoBean);
				if(value == 1)
					w.println("<h2>Insegnamento successfully updated.</h2>");
				
				
				// check if the professor still existing, otherwise insert the new professor 
				// and then insert the corresponding row in Svolgimento
				int id = 0;
				SvolgimentoBean svolgimentoBean = new SvolgimentoBean();
				
				for(int i = 0; i < professoreBeanList.size(); i++)
				{
					id = ProfessoreDatabase.selectOrInsertProfessore(con,
							professoreBeanList.get(i).getNome(),
							professoreBeanList.get(i).getCognome(),
							insegnamentoBean.getNomeUniversita());
					
					svolgimentoBean.setIdInsegnamento(insegnamentoBean.getId());
					svolgimentoBean.setIdProfessore(id);
					
					SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
				}
				
//				for(ProfessoreBean profBean : professoreBeanList)
//				{
//					id = ProfessoreDatabase.selectOrInsertProfessore(
//							con, 
//							profBean.getNome(), 
//							profBean.getCognome(), 
//							insegnamentoBean.getNomeUniversita());
//					
//					svolgimentoBean.setIdInsegnamento(insegnamentoBean.getId());
//					svolgimentoBean.setIdProfessore(id);
//					
//					SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
//				}
				w.println("<h2> " + professorName.length +"</h2>");
				w.println("<h2> " + professoreBeanList.get(0).getNome() +"</h2>");
				w.println("<h2> " + professoreBeanList.get(1).getNome() +"</h2>");
				
				w.println("</body>");
				w.println("</html>");
				w.flush();
				w.close();
				
				DbUtils.close(con);
				
				// Creating response path and redirect to the new page
				StringBuilder builder = new StringBuilder()
				.append("/erasmus-advisor/class?id=")
				.append(insegnamentoBean.getId());
				
				resp.sendRedirect(builder.toString());
				
			} catch (SQLException e) {
				// Error management
				m = new Message("Error while editing " + insegnamentoBean.getNome() + " instance.","XXX", e.getMessage());
				req.setAttribute("message", m);
				
				getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp); // ERROR PAGE
				return;
			} finally {
			} 
		}
		
	}
	
	
}
