package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

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
	 * Get the name domains of Universita
	 * @param conn A connection to the database.
	 * @return a list of university names
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<UniversitaBean> getAreaDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT Nome FROM Universita"; 
		
		QueryRunner run = new QueryRunner();
		
		List<UniversitaBean> universitaNames = null;
		
		ResultSetHandler<List<UniversitaBean>> h1 = new BeanListHandler<UniversitaBean>(UniversitaBean.class);
		universitaNames = run.query(conn, statement, h1);
				
		return universitaNames;
	}
	
	/**
	 * Get the name domains of Universita starting with some letters
	 * @param conn A connection to the database.
	 * @return a list of university names
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<UniversitaBean> getAreaDomainStartingWith(Connection conn, String with) throws SQLException
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
