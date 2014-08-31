package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of Universita
 * @author Ale
 *
 */

public class GetUniversitaValues 
{
	/**
	 * Returns the name domains of Universita
	 * @param conn A connection to the database.
	 * @return a list of university names
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<UniversitaBean> getDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT Nome FROM Universita ORDER BY Nome"; 
		
		QueryRunner run = new QueryRunner();
		
		List<UniversitaBean> universitaNames = null;
		
		ResultSetHandler<List<UniversitaBean>> h1 = new BeanListHandler<UniversitaBean>(UniversitaBean.class);
		universitaNames = run.query(conn, statement, h1);
				
		return universitaNames;
	}
	
	/**
	 * Returns the name domains of Universita starting with some letters
	 * @param conn A connection to the database.
	 * @return a list of university names
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<UniversitaBean> getDomainStartingWith(Connection conn, String with) throws SQLException
	{
		final String statement = "SELECT Nome FROM Universita "
				+ "WHERE lower(Nome) LIKE lower(?)"; 
		
		QueryRunner run = new QueryRunner();
		
		List<UniversitaBean> universitaNames = null;
		
		ResultSetHandler<List<UniversitaBean>> h1 = new BeanListHandler<UniversitaBean>(UniversitaBean.class);
		universitaNames = run.query(conn, statement, h1, with + "%");
				
		return universitaNames;
	}
}
