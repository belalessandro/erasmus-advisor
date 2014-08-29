package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

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

	
	/**
	 * Return the flow manager with the user name given.
	 * 
	 * @param con connection to the database 
	 * @param user user name
	 * @return a flow manage
	 * @throws SQLException
	 */
	public static ResponsabileFlussoBean getReponsabileFlusso(Connection con, String user) throws SQLException {
		
		final StringBuilder sql = new StringBuilder()
			.append("SELECT NomeUtente, Email, DataRegistrazione, Password, ultimoaccesso, attivo, abilitato, nomeuniversita ")
			.append("FROM ResponsabileFlusso ")
			.append("WHERE NomeUtente = ?;");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ResponsabileFlussoBean> rsh = new BeanHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		
		return run.query(con, sql.toString(), rsh, user);
	}
	

}
