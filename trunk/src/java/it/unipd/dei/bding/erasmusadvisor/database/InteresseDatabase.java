package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.resources.InterestBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


/**
 * Database operations about "Interesse". 
 * 
 * @author Luca
 *
 */
public class InteresseDatabase 
{
	
	/**
	 * Returns the number of "Like" for a flow.
	 * 
	 * @param conn A connection to the database.
	 * @param ID The id of the flow.
	 * @return Number of interest of the flow. 
	 * @throws SQLException if an error occurs while searching.
	 */
	public static long getCountInteresseByFlusso(Connection conn, String ID) 
			throws SQLException
	{
		final String statement1 = "SELECT COUNT (idflusso) FROM Interesse WHERE idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		return run.query(conn, statement1, h1, ID);
		
	}
	
	/**
	 * Returns a list of Interest for a user.
	 * 
	 * @param conn A connection to the database.
	 * @param username The username.	
	 * @return A list of InterestBean for a user.
	 * @throws SQLException if an error occurs.
	 */
	public static List<InterestBean> getInterestInformationsFromUser(Connection conn, String username) throws SQLException
	{

		final String statement1 = "SELECT U.nomecitta AS cityName, U.statocitta AS countryName, U.nome AS universityName, F.ID AS flowID, I.nomeutentestudente AS userName"
				+ " FROM Flusso AS F INNER JOIN Universita AS U ON F.Destinazione = U.Nome"
				+ " INNER JOIN Interesse AS I ON I.idflusso = F.id WHERE I.nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();

		ResultSetHandler<List<InterestBean>> h = new BeanListHandler<InterestBean>(InterestBean.class);
		return run.query(conn, statement1, h, username);
	}
	
	/**
	 * Remove an user's Interest for a Flow.
	 * 
	 * @param conn A connection to the database.
	 * @param flow The Id of the flow to which the user want to remove.
	 * @param user The user that want to remove the Interest.	
	 * @return 1 if the interest have been removed.
	 * @throws SQLException If an error occurs while deleting an Interest.
	 */
	public static int removeInterest(Connection conn, String flow, String user) throws SQLException
	{
		final String statement = "DELETE FROM Interesse WHERE idflusso = ? AND nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, flow, user);
	}
	
	/**
	 * Add an user's Interest to a Flow.
	 * 
	 * @param conn A connection to the database.
	 * @param flow The Flow's ID to which add the Interest.
	 * @param user The user that want to add an Interest.
	 * @throws SQLException If an error occurs while adding a Interest.
	 */
	public static void addInterest(Connection conn, String flow, String user) throws SQLException
	{
		String insertStmt = "INSERT INTO Interesse (idflusso, nomeutentestudente) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(insertStmt);
			pstmt.setString(1, flow);
			pstmt.setString(2, user);
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	

	/**
	 * Returns if a student have expressed interest towards the flow.
	 * @param conn A connection to the database.
	 * @param flow The flow id.
	 * @param user The student user name.
	 * @return {@code true} if the student has expressed interest towards the the flow, {@code false} otherwise.
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static boolean checkInterestById(Connection conn, String id, String user) throws SQLException 
	{
		final String statement = "SELECT COUNT (*) FROM Interesse WHERE nomeutentestudente = ? AND idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		long result = run.query(conn, statement, h1, user, id);
		
		if (result > 0)
		{
			return true;
		}
		
		return false;		
	}

}
