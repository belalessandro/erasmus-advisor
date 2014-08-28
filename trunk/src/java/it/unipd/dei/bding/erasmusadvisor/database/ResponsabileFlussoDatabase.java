package it.unipd.dei.bding.erasmusadvisor.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

/**
 * Represents the entity ReponsabileFlusso
 * 
 * @author mauro
 *
 */
public class ResponsabileFlussoDatabase 
{
	/**
	 * Set the field abilitato of a flow manager to false.
	 * 
	 * @param con connection to the database
	 * @param username flow manager username
	 * @throws SQLException 
	 */
	public static void enableResponsabileFlusso(Connection con, String username) throws SQLException 
	{
		final String sql = "UPDATE ResponsabileFlusso SET abilitato = 't' WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, username);
	}

	/**
	 * Set the field attivo of a flow manager to false.
	 * 
	 * @param con connection to the database
	 * @param username flow manager username
	 * @throws SQLException 
	 */
	public static void disableResponsabileFlusso(Connection con, String username) throws SQLException 
	{
		final String sql = "UPDATE ResponsabileFlusso SET attivo = 'f' WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, username);
	}
	

}
