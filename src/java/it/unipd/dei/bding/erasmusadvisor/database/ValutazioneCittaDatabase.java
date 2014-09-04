package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Database operations about "ValutazioneCitta". 
 * @author Mauro, Luca
 *
 */
public class ValutazioneCittaDatabase {
	
	/**
	 * Insert a new student's city evaluation into the Database.
	 * @param con A database connection.
	 * @param val Student's city evaluation.
	 * @throws SQLException If an error occurs.
	 */
	public static void createValutazioneCitta(Connection con, ValutazioneCittaBean val) 
		throws SQLException {
		final String statement = "INSERT INTO ValutazioneCitta (nomeutentestudente, nomecitta, "
				+ "statocitta, costodellavita, disponibilitaalloggi, "
				+ "vivibilitaurbana, vitasociale, datainserimento, commento) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, DEFAULT, ?);";

		
		ResultSetHandler<ValutazioneCittaBean> rsh = new BeanHandler<ValutazioneCittaBean>(ValutazioneCittaBean.class);
		QueryRunner run = new QueryRunner();
		
		run.insert(con, statement, rsh, 
				val.getNomeUtenteStudente(),
				val.getNomeCitta(),
				val.getStatoCitta(),
				val.getCostoDellaVita(),
				val.getDisponibilitaAlloggi(),
				val.getVivibilitaUrbana(),
				val.getVitaSociale(),
				val.getCommento()
				);
	}
	
	/**
	 * Returns all the evaluation to cities inserted by a specific student.
	 * 
	 * @param conn A connection to the database.
	 * @param user The username.
	 * @return A list of evaluations.
	 * @throws SQLException If an error occurs.
	 */
	public static List<ValutazioneCittaBean> getEvalByUser(Connection conn, String user) throws SQLException
	{
		final String statement = "SELECT * FROM ValutazioneCitta WHERE nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ValutazioneCittaBean>> h = new BeanListHandler<ValutazioneCittaBean>(ValutazioneCittaBean.class);
		
		return run.query(conn, statement, h, user);
	}
	
	/**
	 * Delete an evaluation.
	 * 
	 * @param conn A connection to the database.
	 * @param user The student that inserted the evaluation.
	 * @param city The city evaluated.
	 * @param country The country of the city.
	 * @return The number of rows affected.
	 * @throws SQLException If an error occurs while removing the evaluation.
	 */
	public static int deleteEvaluation(Connection conn, String user, String city, String country) throws SQLException
	{
		final String statement = "DELETE FROM ValutazioneCitta WHERE nomeutentestudente = ? AND nomecitta = ? AND statocitta = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, user, city, country);
	}

	/**
	 * Returns if a user has already inserted an evaluation to a city.
	 * @param conn A connection to the database.
	 * @param user The student that inserted the evaluation. 
	 * @param city The city evaluated.
	 * @param country The country of the city.
	 * @return {@code true} if the student has already inserted an evaluation for this city, {@code false} otherwise.
	 * @throws SQLException If something goes wrong.
	 */
	public static boolean checkEvaluation(Connection conn, String user, String city, String country) throws SQLException
	{
		final String statement = "SELECT COUNT (*) FROM ValutazioneCitta WHERE nomeutentestudente = ? AND nomecitta = ? AND statocitta = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		long result = run.query(conn, statement, h1, user, city, country);
		
		if (result > 0)
			return true;
		
		return false;
	}

}
