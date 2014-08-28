package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Represents an University evaluation in the Database
 * 
 * @author mauro, Luca
 *
 */
public class ValutazioneUniversitaDatabase 
{

	/**
	 * Inserts a new university evalutation into the database.
	 *  
	 * @param con connection to the database
	 * @param val university evalutation
	 * @throws SQLException
	 */
	public static void createValutazioneUniversita(Connection con, ValutazioneUniversitaBean val) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("INSERT INTO ValutazioneUniversita (NomeUtenteStudente, NomeUniversita, CollocazioneUrbana, IniziativeErasmus, QtaInsegnamenti, QtaAule, Commento) ")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<ValutazioneUniversitaBean> rsh = new BeanHandler<ValutazioneUniversitaBean>(ValutazioneUniversitaBean.class);
		
		run.insert(con, sql.toString(), rsh, 
				val.getNomeUtenteStudente(),
				val.getNomeUniversita(),
				val.getCollocazioneUrbana(),
				val.getIniziativeErasmus(),
				val.getQtaInsegnamenti(),
				val.getQtaAule(),
				val.getCommento());
	}
	/**
	 * Get all the evaluation to universities inserted by a specific student
	 * @param conn A connection to the database
	 * @param user The User
	 * @return A list of evaluations
	 * @throws SQLException If something goes wrong
	 */
	public static List<ValutazioneUniversitaBean> getEvalByUser(Connection conn, String user) throws SQLException
	{
		final String statement = "SELECT * FROM ValutazioneUniversita WHERE nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ValutazioneUniversitaBean>> h = new BeanListHandler<ValutazioneUniversitaBean>(ValutazioneUniversitaBean.class);
		
		return run.query(conn, statement, h, user);
	}
	
	/**
	 * Delete an evaluation
	 * @param conn A connection to the database
	 * @param user The student that inserted the evaluation
	 * @param name The university evaluated
	 * @return The number of rows affected
	 * @throws SQLException If something goes wrong
	 */
	public static int deleteEvaluation(Connection conn, String user, String name) throws SQLException
	{
		final String statement = "DELETE FROM ValutazioneUniversita WHERE nomeutentestudente = ? AND nomeuniversita = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, user, name);
	}

}
