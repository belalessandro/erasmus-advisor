package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Database operations about "ValutazioneFlusso".
 * @author Mauro, Luca
 *
 */
public class ValutazioneFlussoDatabase 
{
	/**
	 * Insert a new flow evaluation into the database.
	 * 
	 * @param con A connection to the database.
	 * @param val A flow evaluation.
	 * @throws SQLException if an error occurs.
	 */
	public static void creaValutazioneFlusso(Connection con, ValutazioneFlussoBean val) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
			.append("INSERT INTO ValutazioneFlusso (NomeUtenteStudente, IdFlusso, SoddEsperienza, SoddaccAdemica, Didattica, ValutazioneResponsabile, Commento) ")
			.append("VALUES (?, ?, ?, ?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ValutazioneFlussoBean> rsh = new BeanHandler<ValutazioneFlussoBean>(ValutazioneFlussoBean.class);
		
		run.insert(con, sql.toString(), rsh,
				val.getNomeUtenteStudente(),
				val.getIdFlusso(),
				val.getSoddEsperienza(),
				val.getSoddAccademica(),
				val.getDidattica(),
				val.getValutazioneResponsabile(),
				val.getCommento());
	}

	/**
	 * Returns all the evaluation to flows inserted by a specific student.
	 * 
	 * @param conn A connection to the database.
	 * @param user The User.
	 * @return A list of evaluations.
	 * @throws SQLException If something goes wrong.
	 */
	public static List<ValutazioneFlussoBean> getEvalByUser(Connection conn, String user) throws SQLException
	{
		final String statement = "SELECT * FROM ValutazioneFlusso WHERE nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ValutazioneFlussoBean>> h = new BeanListHandler<ValutazioneFlussoBean>(ValutazioneFlussoBean.class);
		
		return run.query(conn, statement, h, user);
	}
	
	/**
	 * Delete an evaluation.
	 * 
	 * @param conn A connection to the database.
	 * @param user The student that inserted the evaluation.
	 * @param id The flow evaluated.
	 * @return The number of rows affected.
	 * @throws SQLException If something goes wrong.
	 */
	public static int deleteEvaluation(Connection conn, String user, String id) throws SQLException
	{
		final String statement = "DELETE FROM ValutazioneFlusso WHERE nomeutentestudente = ? AND idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, user, id);
	}

	/**
	 * Returns if a user has already inserted an evaluation to a flow.
	 * @param conn A connection to the database.
	 * @param user The student that inserted the evaluation. 
	 * @param id The flow.
	 * @return {@code true} if the student has already inserted an evaluation for this flow, {@code false} otherwise.
	 * @throws SQLException If something goes wrong.
	 */
	public static boolean checkEvaluation(Connection conn, String user, String id) throws SQLException
	{
		final String statement = "SELECT COUNT (*) FROM ValutazioneFlusso WHERE nomeutentestudente = ? AND idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		long result = run.query(conn, statement, h1, user, id);
		
		if (result > 0)
			return true;
		
		return false;
	}
}
