package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Database operations about Origine
 * @author Alessandro
 *
 */
public class OrigineDatabase {

	/**
	 * Executes a statement to store a new Origine into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param origine The Origine to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Origine.
	 */
	public static void createOrigine(Connection con, OrigineBean origine)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Origine (idFlusso, idCorso) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, origine.getIdFlusso());
			pstmt.setInt(2, origine.getIdCorso());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	
	/**
	 * Delete origine instances by flow id.
	 * @param con connection to the database
	 * @param flowId flow id
	 * @return number of rows deleted
	 * @throws SQLException
	 */
	public static int deleteOrigineByFlowId(Connection con, String flowId) throws SQLException
	{
		final String sql = "DELETE FROM Origine WHERE idFlusso = ?;";
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql, flowId);
		
	}

}
