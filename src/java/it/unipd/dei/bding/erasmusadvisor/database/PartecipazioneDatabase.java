package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.PartecipazioneBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Database operations about "Partecipazione".
 * 
 * @author Mauro
 *
 */
public class PartecipazioneDatabase
{
	/**
	 * Returns all the flow a student has joined.
	 * 
	 * @param conn A connection to the database.
	 * @param stud A student.
	 * @return A list of partecipations bean.
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<PartecipazioneBean> getFlows(Connection conn, String stud) 
			throws SQLException
	{
		final String statement = "SELECT * FROM Partecipazione WHERE nomeutentestudente = ?"; 
		
		QueryRunner run = new QueryRunner();
		
		List<PartecipazioneBean> list = null;
		
		ResultSetHandler<List<PartecipazioneBean>> h1 = new BeanListHandler<PartecipazioneBean>(PartecipazioneBean.class);
		list = run.query(conn, statement, h1, stud);
				
		return list;
	}
	
	/**
	 * Add a student's partecipation to a flow.
	 * 
	 * @param conn A connection to the database.
	 * @param flow Flow's id.
	 * @param user A username.
	 * @param inizio The start date of Erasmus.
	 * @param fine The end date of Erasmus.
	 * @throws SQLException If an error occurs while adding "Partecipazione".
	 */
	public static void addParticipation(Connection conn, String flow, String user, Date inizio, Date fine) throws SQLException
	{
		String insertStmt = "INSERT INTO Partecipazione (idflusso, nomeutentestudente, inizio, fine) VALUES (?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(insertStmt);
			pstmt.setString(1, flow);
			pstmt.setString(2, user);
			pstmt.setDate(3, inizio);
			pstmt.setDate(4, fine);
			pstmt.execute();
		} 
		finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	
	/**
	 * Remove a participation.
	 * 
	 * @param conn A connection to the database.
	 * @param flow The flow's ID
	 * @param user The user name.
	 * @return The number of rows affected.
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static int removeParticipation(Connection conn, String flow, String user) throws SQLException
	{
		final String statement = "DELETE From Partecipazione WHERE idflusso = ? AND nomeutentestudente = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, flow, user);
	}
	
	/**
	 * Returns if a student have participated to a flow towards a given university.
	 * @param conn A connection to the database.
	 * @param university The flow destination university.
	 * @param user The student user name.
	 * @return {@code true} if the student has participated to at least a flow, {@code false} otherwise.
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static boolean checkParticipation(Connection conn, String university, String user) throws SQLException
	{
		final String statement = "SELECT COUNT (*) FROM Partecipazione AS P INNER JOIN Flusso AS F ON P.idflusso = F.id "
				+ "WHERE P.nomeutentestudente = ? AND F.destinazione = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		long result = run.query(conn, statement, h1, user, university);
		
		if (result > 0)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Returns if a student have participated to a flow towards a given city.
	 * @param conn A connection to the database.
	 * @param city The flow destination city.
	 * @param country The flow destination country.
	 * @param user The student user name.
	 * @return {@code true} if the student has participated to at least a flow, {@code false} otherwise.
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static boolean checkParticipation(Connection conn, String city, String country, String user) throws SQLException 
	{

		final String statement = "SELECT COUNT (*) FROM Partecipazione AS P INNER JOIN Flusso AS F ON P.idflusso = F.id "
				+ "INNER JOIN Universita AS U ON F.destinazione = U.nome "
				+ "WHERE P.nomeutentestudente = ? AND U.nomecitta = ? AND U.statocitta = ?";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
		long result = run.query(conn, statement, h1, user, city, country);
		
		if (result > 0)
		{
			return true;
		}
		
		return false;
	}
}
