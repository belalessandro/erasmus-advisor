package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about ValutazioneCitta 
 * @author Mauro, Luca
 *
 */
public class ValutazioneCittaDatabase {
	
	/**
	 * Insert a new student's city evaluation into the Database
	 * @param con database connection
	 * @param val student's city evaluation
	 * @throws SQLException
	 */
	public static void createValutazioneCitta(Connection con, ValutazioneCittaBean val) 
		throws SQLException {
		final String statement = "INSERT INTO ValutazioneCitta (nomeutentestudente, nomecitta, statocitta, costodellavita, disponibilitaalloggi, vivibilitaurbana, vitasociale, datainserimento, commento)"
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
	 * Get all the evaluation to cities inserted by a specific student
	 * @param conn A connection to the database
	 * @param user The User
	 * @return A list of evaluations
	 * @throws SQLException If something goes wrong
	 */
	public static List<ValutazioneCittaBean> getEvalByUser(Connection conn, String user) throws SQLException
	{
		final String statement = "SELECT * FROM ValutazioneCitta WHERE nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ValutazioneCittaBean>> h = new BeanListHandler<ValutazioneCittaBean>(ValutazioneCittaBean.class);
		
		return run.query(conn, statement, h, user);
	}

}
