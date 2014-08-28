package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


/**
 * Database operations about ValutazioneTesi 
 * @author Mauro, Luca
 *
 */
public class ValutazioneTesiDatabase 
{
	/**
	 * Insert a new ValutazioneTesi instance into the database. 
	 * @param con connection to the database
	 * @param val student's evaluation
	 * @throws SQLException 
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
	 * Get all the evaluation to theses inserted by a specific student
	 * @param conn A connection to the database
	 * @param user The User
	 * @return A list of evaluations
	 * @throws SQLException If something goes wrong
	 */
	public static List<ValutazioneTesiBean> getEvalByUser(Connection conn, String user) throws SQLException
	{
		final String statement = "SELECT * FROM ValutazioneTesi WHERE nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ValutazioneTesiBean>> h = new BeanListHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
		
		return run.query(conn, statement, h, user);
	}
	

}