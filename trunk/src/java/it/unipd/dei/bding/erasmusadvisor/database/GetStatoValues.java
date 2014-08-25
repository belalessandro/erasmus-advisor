package it.unipd.dei.bding.erasmusadvisor.database;




import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;


/**
 * Get all the values for the attribute Citta.Stato
 * @author Luca, Nicola
 *
 */
public class GetStatoValues 
{
	/**
	 * Get the values for the attribute Citta.Stato
	 * @param conn A connection to the database.
	 * @return A list of countries
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<String> getValues(Connection conn) throws SQLException
	{
		final String statement = "SELECT DISTINCT Stato FROM Citta ORDER BY Stato ASC"; 
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<List<String>> h1 = new ColumnListHandler<String>("stato");
		return run.query(conn, statement, h1);
				
	}
	public static List<String> getDomainStartingWith(Connection conn, String with) throws SQLException
	{
		final String statement = "SELECT DISTINCT Stato FROM Citta "
				+ "WHERE lower(Stato) LIKE lower(?)"; 
		
		QueryRunner run = new QueryRunner();
		
		List<String> statoNames = null;
		
		ResultSetHandler<List<String>> h1 = new ColumnListHandler<String>("stato");
		statoNames = run.query(conn, statement, h1, with + "%");
				
		return statoNames;
	}
}
