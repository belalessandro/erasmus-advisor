package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of CorsoDiLaurea
 * @author Ale
 *
 */

public class GetCorsoDiLaureaValues 
{
	/**
	 * Get the name domains of CorsoDiLaurea
	 * @param conn A connection to the database.
	 * @return a list of university names
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<CorsoDiLaureaBean> getDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT Nome FROM CorsoDiLaurea ORDER BY Nome"; 
		
		QueryRunner run = new QueryRunner();
		
		List<CorsoDiLaureaBean> corsoNames = null;
		
		ResultSetHandler<List<CorsoDiLaureaBean>> h1 = new BeanListHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		corsoNames = run.query(conn, statement, h1);
				
		return corsoNames;
	}
	
	/**
	 * Get the name domains of CorsoDiLaurea starting with some letters
	 * @param conn A connection to the database.
	 * @return a list of university names
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<CorsoDiLaureaBean> getDomainStartingWith(Connection conn, String with) throws SQLException
	{
		final String statement = "SELECT Nome FROM CorsoDiLaurea "
				+ "WHERE lower(Nome) LIKE lower(?)"; 
		
		QueryRunner run = new QueryRunner();
		
		List<CorsoDiLaureaBean> corsoNames = null;
		
		ResultSetHandler<List<CorsoDiLaureaBean>> h1 = new BeanListHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		corsoNames = run.query(conn, statement, h1, with + "%");
				
		return corsoNames;
	}
}
