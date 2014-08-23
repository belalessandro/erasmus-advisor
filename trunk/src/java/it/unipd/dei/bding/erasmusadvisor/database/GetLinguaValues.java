package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of Lingua
 * @author Luca
 *
 */

public class GetLinguaValues 
{
	/**
	 * Get the domain of Lingua (so the languages in the databases)
	 * @param conn A connection to the database.
	 * @return A list of languages
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<LinguaBean> getLinguaDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT * FROM Lingua ORDER BY nome ASC"; 
		
		QueryRunner run = new QueryRunner();
		
		List<LinguaBean> lingue = null;
		
		ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
		lingue = run.query(conn, statement, h1);
		
		return lingue;
	}
}
