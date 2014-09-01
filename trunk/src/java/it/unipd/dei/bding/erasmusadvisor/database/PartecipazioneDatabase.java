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
	 * @throws SQLException if an error occurs while adding "Partecipazione".
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
}
