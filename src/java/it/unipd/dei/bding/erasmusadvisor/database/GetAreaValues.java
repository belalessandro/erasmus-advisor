package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of Area
 * @author Luca
 *
 */

public class GetAreaValues 
{
	/**
	 * Get the domain of Area
	 * @param conn A connection to the database.
	 * @return a list of areas
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<AreaBean> getAreaDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT nome FROM Area"; 
		
		QueryRunner run = new QueryRunner();
		
		List<AreaBean> lingue = null;
		
		ResultSetHandler<List<AreaBean>> h1 = new BeanListHandler<AreaBean>(AreaBean.class);
		lingue = run.query(conn, statement, h1);
				
		return lingue;
	}
}
