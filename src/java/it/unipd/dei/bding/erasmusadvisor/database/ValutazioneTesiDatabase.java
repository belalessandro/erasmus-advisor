package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


/**
 * Database operations about "ValutazioneTesi" 
 * @author Mauro, Luca
 *
 */
public class ValutazioneTesiDatabase 
{
	/**
	 * Insert a new ValutazioneTesi instance into the database. 
	 * 
	 * @param con A connection to the database.
	 * @param val A student's evaluation.
	 * @throws SQLException If an error occurs in SQL query executing.
	 */
	public static void createValutazioneTesi(Connection con, ValutazioneTesiBean val) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("INSERT INTO ValutazioneTesi (NomeUtenteStudente, idargomentotesi, impegnonecessario, interesseargomento, disponibilitarelatore, soddisfazione, Commento) ")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ValutazioneTesiBean> rsh = new BeanHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
		run.insert(con, sql.toString(), rsh,
				val.getNomeUtenteStudente(),
				val.getIdArgomentoTesi(),
				val.getImpegnoNecessario(),
				val.getInteresseArgomento(),
				val.getDisponibilitaRelatore(),
				val.getSoddisfazione(),
				val.getCommento());

	}
	/**
	 * Returns all the evaluation to theses inserted by a specific student.
	 * 
	 * @param conn A connection to the database.
	 * @param user The User.
	 * @return A list of evaluations.
	 * @throws SQLException If something goes wrong.
	 */
	public static List<ValutazioneTesiBean> getEvalByUser(Connection conn, String user) throws SQLException
	{
		StringBuilder statement = new StringBuilder()
			.append("SELECT NomeUtentestudente, IdArgomentoTesi, ImpegnoNecessario, InteresseArgomento, DisponibilitaRelatore, Soddisfazione, DataInserimento, Commento, Nome As nomeTesi ")
			.append("FROM ValutazioneTesi JOIN ArgomentoTesi ON idArgomentoTesi = id ")
			.append("WHERE NomeUtenteStudente = ?");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ValutazioneTesiBean>> h = new BeanListHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
		
		return run.query(conn, statement.toString(), h, user);
	}
//	public static List<ValutazioneTesiBean> getEvalByUser(Connection conn, String user) throws SQLException
//	{
//		final String statement = "SELECT * FROM ValutazioneTesi WHERE nomeutentestudente = ?";
//		
//		QueryRunner run = new QueryRunner();
//		ResultSetHandler<List<ValutazioneTesiBean>> h = new BeanListHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
//		
//		return run.query(conn, statement, h, user);
//	}
	
	/**
	 * Delete an evaluation.
	 * 
	 * @param conn A connection to the database.
	 * @param user The student that inserted the evaluation.
	 * @param id The thesis evaluated.
	 * @return The number of rows affected.
	 * @throws SQLException If something goes wrong.
	 */
	public static int deleteEvaluation(Connection conn, String user, int id) throws SQLException
	{
		final String statement = "DELETE FROM ValutazioneTesi WHERE nomeutentestudente = ? AND idargomentotesi = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, user, id);
	}
	
	/**
	 * Returns if a user has already inserted an evaluation to a thesis.
	 * @param conn A connection to the database.
	 * @param user The student that inserted the evaluation. 
	 * @param thesis The thesis.
	 * @return {@code true} if the student has already inserted an evaluation for this thesis, {@code false} otherwise.
	 * @throws SQLException If something goes wrong.
	 */
	public static boolean checkEvaluation(Connection conn, String user, int thesis) throws SQLException
	{
		final String statement = "SELECT COUNT (*) FROM ValutazioneTesi WHERE nomeutentestudente = ? AND idargomentotesi = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		long result = run.query(conn, statement, h1, user, thesis);
		
		if (result > 0)
			return true;
		
		return false;
	}
	

}
