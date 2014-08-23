package it.unipd.dei.bding.erasmusadvisor.database;


import it.unipd.dei.bding.erasmusadvisor.resources.TipoLaureaBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


/**
 * Get the values of the enum TipoLaurea
 * @author Luca
 *
 */

public class GetTipoLaureaValues 
{
	/**
	 * Get the domain of TipoLaurea (so the types of the degree courses)
	 * @param conn A connection to the database.
	 * @return A list of types of courses
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<TipoLaureaBean> getDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT unnest(enum_range(NULL::tipolaurea)) AS TipoLaurea"; 
		
		QueryRunner run = new QueryRunner();
		
		List<TipoLaureaBean> tipi = null;
		
		ResultSetHandler<List<TipoLaureaBean>> h1 = new BeanListHandler<TipoLaureaBean>(TipoLaureaBean.class);
		tipi = run.query(conn, statement, h1);
				
		return tipi;
	}
}