package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

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
	 * @param username Flow manager username.
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

	
	

}
