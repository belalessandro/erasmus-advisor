package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.resources.InterestBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
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
	
	public static List<InterestBean> getInterestInformationsFromUser(Connection conn, String username) throws SQLException
	{

		final String statement1 = "SELECT U.nomecitta AS cityName, U.statocitta AS countryName, U.nome AS universityName, F.ID AS flowID, I.nomeutentestudente AS userName"
				+ " FROM Flusso AS F INNER JOIN Universita AS U ON F.Destinazione = U.Nome"
				+ " INNER JOIN Interesse AS I ON I.idflusso = F.id WHERE I.nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();

		ResultSetHandler<List<InterestBean>> h = new BeanListHandler<InterestBean>(InterestBean.class);
		return run.query(conn, statement1, h, username);
	}
	
	public static int removeInterest(Connection conn, String flow, String user) throws SQLException
	{
		final String statement = "DELETE FROM Interesse WHERE idflusso = ? AND nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, flow, user);
	}
	

}
