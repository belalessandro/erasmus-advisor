package it.unipd.dei.bding.erasmusadvisor.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


/**
 * Database operations about Interesse 
 * @author Luca
 *
 */
public class InteresseDatabase 
{
	
	public static long getCountInteresseByFlusso(Connection conn, String ID) 
			throws SQLException
	{
		final String statement1 = "SELECT COUNT (idflusso) FROM Interesse WHERE idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		return run.query(conn, statement1, h1, ID);
		
	}
	

}
