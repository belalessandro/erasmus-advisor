package it.unipd.dei.bding.erasmusadvisor.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CoordinatoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Notifications;

/**
 * Database operations about "Coordinatore".
 * 
 * @author Mauro
 *
 */
public class CoordinatoreDatabase 
{
	/**
	 * Returns the bean of the coordinator with the username given.
	 * 
	 * @param con A connection to the database.
	 * @param username User name.
	 * @return The bean of the coordinator.
	 * @throws SQLException If an error occurs.
	 */
	public static CoordinatoreBean getCoordinatore(Connection con, String username) throws SQLException
	{
		StringBuilder sql = new StringBuilder()
			.append("SELECT NomeUtente, Email, Password, DataRegistrazione, ultimoaccesso, attivo, nomeuniversita ")
			.append("FROM Coordinatore ")
			.append("WHERE NomeUtente = ?;");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<CoordinatoreBean> rsh = new BeanHandler<CoordinatoreBean>(CoordinatoreBean.class);
		
		return  run.query(con, sql.toString(), rsh, username);
	}
	
	public static int removeCoordinatore(Connection con, String username) throws SQLException
	{
		StringBuilder sql = new StringBuilder() 
			.append("UPDATE Studente SET attivo = 'false' ")
			.append("WHERE nomeUtente = ? ; ");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<CoordinatoreBean> rsh = new BeanHandler<CoordinatoreBean>(CoordinatoreBean.class);
		
		return  run.update(con, sql.toString(), rsh, username);
	}
	
	/**
	 * Returns the bean of the coordinator's university with the username given.
	 * 
	 * @param con A connection to the database.
	 * @param username The user's name.
	 * @return The name of university.
	 * @throws SQLException If an error occurs.
	 */
	public static String getUniversity(Connection con, String username) throws SQLException
	{
		StringBuilder sql = new StringBuilder()
			.append("SELECT NomeUniversita")
			.append("FROM ResponsabileFlusso")
			.append("WHERE NomeUtente = ?;");
		
		QueryRunner run = new QueryRunner();
		
		return  run.query(con, sql.toString(), null, username);
	}
	
	/**
	 * Returns all notifications related to a coordinator.
	 * 
	 * @param con A connection to the database.
	 * @param username Coordinator's username.
	 * @return notifications Notifications model with all fields initialized.
	 * @throws SQLException If an error occurs.
	 */
	public static Notifications getNotifications(Connection con, String username) throws SQLException
	{
		// required variables 
		QueryRunner run = new QueryRunner();
		List<ResponsabileFlussoBean> flowManagers = null;
		List<InsegnamentoBean> classes = null;
		ArrayList<List<ProfessoreBean>> classProfessors = new ArrayList<List<ProfessoreBean>>();
		List<ArgomentoTesiBean> thesis = null;
		ArrayList<List<ProfessoreBean>> thesisProfessors = new ArrayList<List<ProfessoreBean>>();
		
		// statements
		final StringBuilder getResponsabiliFlusso = new StringBuilder()
			.append("SELECT R.NomeUtente, R.Nome, R.Cognome, R.Email, R.DataRegistrazione, R.Attivo, R.Abilitato, R.NomeUniversita ")
			.append("FROM ResponsabileFlusso AS R JOIN Coordinatore AS C ON R.NomeUniversita = C.NomeUniversita ")
			.append("WHERE C.NomeUtente = ? AND R.Attivo = TRUE AND R.Abilitato = FALSE;");
		
		final StringBuilder getInsegnamenti = new StringBuilder()
			.append("SELECT I.Id, I.Nome, I.Crediti, I.NomeUniversita, I.periodoErogazione, I.stato, I.AnnoCorso, I.NomeArea, I.NomeLingua ")
			.append("FROM Insegnamento AS I JOIN Coordinatore AS C ON I.NomeUniversita = C.NomeUniversita ")
			.append("WHERE C.NomeUtente = ? AND I.Stato = 'REPORTED';");
		
		final StringBuilder getProfessoriInsegnamento = new StringBuilder()
			.append("SELECT P.id, P.Nome, P.Cognome ")
			.append("FROM Professore AS P JOIN Svolgimento AS S ON P.id = S.IdProfessore ")
			.append("WHERE S.IdInsegnamento = ?;");
		
		
		final StringBuilder getArgomenti = new StringBuilder()
			.append("SELECT A.Id, A.Nome, A.NomeUniversita, A.Triennale, A.Magistrale, A.Stato ")
			.append("FROM ArgomentoTesi AS A JOIN Coordinatore AS C ON A.NomeUniversita = C.NomeUniversita ")
			.append("WHERE C.NomeUtente = ? AND A.Stato = 'REPORTED';");
		
		final StringBuilder getProfessoriArgomenti = new StringBuilder()
			.append("SELECT P.id, P.Nome, P.Cognome ")
			.append("FROM Professore AS P JOIN Gestione AS G ON P.id = G.idProfessore ")
			.append("WHERE G.IdArgomentoTesi = ?;");
		
		// setting handlers
		ResultSetHandler<List<ResponsabileFlussoBean>> rshResponsabiliFlusso = new BeanListHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		ResultSetHandler<List<InsegnamentoBean>> rshClasses = new BeanListHandler<InsegnamentoBean>(InsegnamentoBean.class);
		ResultSetHandler<List<ProfessoreBean>> rshClassProfessors = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		ResultSetHandler<List<ArgomentoTesiBean>> rshThesis = new BeanListHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		ResultSetHandler<List<ProfessoreBean>> rshThesisProfessors = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		
		// executing the query
		flowManagers = run.query(con, getResponsabiliFlusso.toString(), rshResponsabiliFlusso, username);
		classes = run.query(con, getInsegnamenti.toString(), rshClasses, username);
		
		for(InsegnamentoBean c : classes)
			classProfessors.add(run.query(con, getProfessoriInsegnamento.toString(), rshClassProfessors, c.getId()));
		
		thesis = run.query(con, getArgomenti.toString(), rshThesis, username);
		
		for(ArgomentoTesiBean a : thesis)
			thesisProfessors.add(run.query(con, getProfessoriArgomenti.toString(), rshThesisProfessors, a.getId()));
		
		// fill the model to return
		Notifications notifications = new Notifications(flowManagers, classes, classProfessors, thesis, thesisProfessors);
		
		return notifications;
	}

	/**
	 * Method for udpating an Erasmus Coordinator with the user name given.
	 * 
	 * @param con database connection
	 * @param coordinator CoordinatoreBean object 
	 * @return the number of instances updated [1]
	 * @throws SQLException
	 */
	public static int updateCoordinatore(Connection con, CoordinatoreBean coordinator) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("UPDATE Coordinatore SET Email = ?, Password = ?, Salt = ?, Attivo = ?, NomeUniversita = ? ")
		.append("WHERE NomeUtente = ?;");
	
		QueryRunner run = new QueryRunner();
		
		int v  = run.update(con, sql.toString(), coordinator.getEmail(), coordinator.getPassword(), 
				coordinator.getSalt(), coordinator.isAttivo(), coordinator.getNomeUniversita(), coordinator.getNomeUtente());
		
		return v;
	}

	
	/**
	 * Method for updating an Erasmus Coordinator with the user name given
	 * without setting the password.
	 * 
	 * @param con database connection
	 * @param coordinator CoordinatoreBean object 
	 * @return the number of instances updated [1]
	 * @throws SQLException
	 */
	public static int updateCoordinatoreWithoutPassword(Connection con, CoordinatoreBean coordinator)  throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("UPDATE Coordinatore SET Email = ?, Attivo = ?, NomeUniversita = ? ")
		.append("WHERE NomeUtente = ?;");
	
		QueryRunner run = new QueryRunner();
		
		int v  = run.update(con, sql.toString(), coordinator.getEmail(), coordinator.isAttivo(), coordinator.getNomeUniversita(), coordinator.getNomeUtente());
		
		return v;
	}

	/**
	 * Method used for updating user access' date.
	 * 
	 * @param con  database connection
	 * @param nomeUtente user name
	 * @throws SQLException
	 */
	public static void updateLastLogin(Connection con, String nomeUtente) throws SQLException 
	{
		final String sql = "UPDATE Coordinatore SET UltimoAccesso = CURRENT_DATE WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, nomeUtente);
	}
	
	
}
