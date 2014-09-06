package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Notifications;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Represents the entity "ReponsabileFlusso".
 * 
 * @author mauro
 *
 */
public class ResponsabileFlussoDatabase 
{
	
	
	/**
	 * Set the field "abilitato" of a flow manager to true.
	 * 
	 * @param con A connection to the database.
	 * @param username Flow manager username.
	 * @throws SQLException If an error occurs in SQL query.
	 */
	public static void enableResponsabileFlusso(Connection con, String username) throws SQLException 
	{
		final String sql = "UPDATE ResponsabileFlusso SET abilitato = 't' WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, username);
	}

	/**
	 * Set the field "attivo" of a flow manager to false.
	 * 
	 * @param con A connection to the database.
	 * @param username Flow manager username.
	 * @throws SQLException If an error occurs in SQL query.
	 */
	public static void disableResponsabileFlusso(Connection con, String username) throws SQLException 
	{
		final String sql = "UPDATE ResponsabileFlusso SET attivo = 'f' WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, username);
	}

	
	/**
	 * Return the flow manager with the user name given.
	 * 
	 * @param con A connection to the database.
	 * @param user Flow manager username.
	 * @return A flow manager.
	 * @throws SQLException If an error occurs in SQL query.
	 */
	public static ResponsabileFlussoBean getReponsabileFlusso(Connection con, String user) throws SQLException {
		
		final StringBuilder sql = new StringBuilder()
			.append("SELECT NomeUtente, Nome, Cognome, Email, DataRegistrazione, Password, ultimoaccesso, Attivo, Abilitato, NomeUniversita ")
			.append("FROM ResponsabileFlusso ")
			.append("WHERE NomeUtente = ?;");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ResponsabileFlussoBean> rsh = new BeanHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		
		return run.query(con, sql.toString(), rsh, user);
	}

	
	/**
	 * Method for udpating a Responsabile di Flusso with the user name given.
	 * 
	 * @param con database connection
	 * @param manager ResponsabileFlussoBean object 
	 * @return number of instances changed [1]
	 * @throws SQLException
	 */
	public static int  updateResponsabileFlusso(Connection con, ResponsabileFlussoBean manager) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
			.append("UPDATE ResponsabileFlusso SET Nome = ?, Cognome = ?, Email = ?, Password = ?, Salt = ?, Attivo = ?, Abilitato = ?, NomeUniversita = ? ")
			.append("WHERE NomeUtente = ?;");
		
		QueryRunner run = new QueryRunner();
		
		int v  = run.update(con, sql.toString(), manager.getNome(), manager.getCognome(), manager.getEmail(), 
				manager.getPassword(), manager.getSalt(), manager.isAttivo(), manager.isAbilitato(), manager.getNomeUniversita(), manager.getNomeUtente());
		
		return v;
	}

	public static void createResponsabileFlusso(Connection con, ResponsabileFlussoBean manager) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
			.append("INSERT INTO ResponsabileFlusso (NomeUtente, Nome, Cognome, Email, DataRegistrazione, Password, Salt, NomeUniversita, UltimoAccesso, Attivo, Abilitato) ")
			.append("VALUES(?, ?, ?, ?, DEFAULT, ?, ?, ?, CURRENT_DATE , TRUE, FALSE)");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ResponsabileFlussoBean> rsh = new BeanHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		
		run.insert(con, sql.toString(), rsh, manager.getNomeUtente(), manager.getNome(), manager.getCognome(), manager.getEmail(),
				manager.getPassword(), manager.getSalt(), manager.getNomeUniversita());
		
	}

	
	/**
	 * Method for udpating a Responsabile di Flusso with the user name given
	 * without setting the password.
	 * 
	 * @param con database connection
	 * @param manager ResponsabileFlussoBean object 
	 * @return number of instances changed [1]
	 * @throws SQLException
	 */
	public static int updateResponsabileFlussoWithoutPassword(Connection con, ResponsabileFlussoBean manager) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("UPDATE ResponsabileFlusso SET Nome = ?, Cognome = ?, Email = ?, Attivo = ?, Abilitato = ?, NomeUniversita = ? ")
		.append("WHERE NomeUtente = ?;");
	
		QueryRunner run = new QueryRunner();
		
		int v  = run.update(con, sql.toString(), manager.getNome(), manager.getCognome(), manager.getEmail(), 
				 		manager.isAttivo(), manager.isAbilitato(), manager.getNomeUniversita(), manager.getNomeUtente());
		
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
		final String sql = "UPDATE ResponsabileFlusso SET UltimoAccesso = CURRENT_DATE WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, nomeUtente);
	}

	/**
	 * Returns all notifications related to a flow manager.
	 * 
	 * @param con A connection to the database.
	 * @param username Flow manager's username.
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
		
		StringBuilder getInsegnamenti = new StringBuilder()
			.append("SELECT I.Id, I.Nome, I.Crediti, I.NomeUniversita, I.PeriodoErogazione, I.Stato, I.AnnoCorso, I.NomeArea, I.NomeLingua ")
			.append("FROM Insegnamento AS I JOIN ResponsabileFlusso AS R ON I.NomeUniversita = R.NomeUniversita ")
			.append("WHERE R.NomeUtente = ? AND I.Stato = 'REPORTED' ")
			.append("UNION ")
			.append("SELECT I.Id, I.Nome, I.Crediti, I.NomeUniversita, I.PeriodoErogazione, I.Stato, I.AnnoCorso, I.NomeArea, I.NomeLingua ")
			.append("FROM Insegnamento AS I JOIN Flusso AS F ON I.NomeUniversita = F.Destinazione ")
			.append("JOIN ResponsabileFlusso AS R ON R.NomeUtente = F.respFlusso ")
			.append("WHERE R.NomeUtente = ? AND I.Stato = 'REPORTED'");
		
		StringBuilder getArgomenti = new StringBuilder()
		 .append("SELECT A.Id, A.Nome, A.NomeUniversita, A.triennale, A.magistrale, A.stato ")
		 .append("FROM ArgomentoTesi AS A JOIN ResponsabileFlusso AS R ON A.NomeUniversita = R.NomeUniversita ")
		 .append("WHERE R.NomeUtente = ? AND A.Stato = 'REPORTED' ")
		 .append("UNION ")
		 .append("SELECT A.Id, A.Nome, A.NomeUniversita, A.triennale, A.magistrale, A.stato ")
		 .append("FROM ArgomentoTesi AS A JOIN Flusso AS F ON A.NomeUniversita = F.Destinazione ")
		 .append("JOIN ResponsabileFlusso AS R ON F.respFlusso = R.NomeUtente ")
		 .append("WHERE R.NomeUtente = ? AND A.stato = 'REPORTED'");
		
		StringBuilder getProfessoriInsegnamento = new StringBuilder()
		.append("SELECT P.id, P.Nome, P.Cognome ")
		.append("FROM Professore AS P JOIN Svolgimento AS S ON P.id = S.IdProfessore ")
		.append("WHERE S.IdInsegnamento = ?;");
		
		
		final StringBuilder getProfessoriArgomenti = new StringBuilder()
		.append("SELECT P.id, P.Nome, P.Cognome ")
		.append("FROM Professore AS P JOIN Gestione AS G ON P.id = G.idProfessore ")
		.append("WHERE G.IdArgomentoTesi = ?;");
		
		// setting handlers
		//ResultSetHandler<List<ResponsabileFlussoBean>> rshResponsabiliFlusso = new BeanListHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		ResultSetHandler<List<InsegnamentoBean>> rshClasses = new BeanListHandler<InsegnamentoBean>(InsegnamentoBean.class);
		ResultSetHandler<List<ProfessoreBean>> rshClassProfessors = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		ResultSetHandler<List<ArgomentoTesiBean>> rshThesis = new BeanListHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		ResultSetHandler<List<ProfessoreBean>> rshThesisProfessors = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		
		// get classes and theses
		classes = run.query(con, getInsegnamenti.toString(), rshClasses, username, username);
		thesis = run.query(con, getArgomenti.toString(), rshThesis, username, username);
		
		
		// get relative professors
		for(InsegnamentoBean c : classes)
			classProfessors.add(run.query(con, getProfessoriInsegnamento.toString(), 
					rshClassProfessors, c.getId()));
		
		for(ArgomentoTesiBean a : thesis)
			thesisProfessors.add(run.query(con, getProfessoriArgomenti.toString(), 
					rshThesisProfessors, a.getId()));
		
		// fill the model to return
		Notifications notifications = new Notifications(flowManagers, classes, classProfessors, thesis, thesisProfessors);
		
		return notifications;
	}


	
	

}
