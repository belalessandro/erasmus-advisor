package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about "Origine".
 * @author Alessandro
 *
 */
public class OrigineDatabase {

	/**
	 * Executes a statement to store a new "Origine" into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param origine The "Origine" to be stored.
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the "Origine".
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
	 * @param con A connection to the database.
	 * @param flowId Flow's id.
	 * @return Number of rows deleted.
	 * @throws SQLException If an error occurs while deleting an "Origine".
	 */
	public static int deleteOrigineByFlowId(Connection con, String flowId) throws SQLException
	{
		final String sql = "DELETE FROM Origine WHERE idFlusso = ?;";
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql, flowId);
		
	}

	/**
	 * Return a list of Flow's "Origine".
	 * @param conn A connection to the database.
	 * @param id Id of the flow.
	 * @return A list of OrigineBean objects.
	 * @throws SQLException If an error occurs while getting an "Origine".
	 */
	public static List<OrigineBean> getOriginsByFlowId(Connection conn, String id) throws SQLException {
		final String sql = "SELECT * FROM Origine WHERE idFlusso = ?;";
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<List<OrigineBean>> rsh = new BeanListHandler<OrigineBean>(OrigineBean.class);
		
		return run.query(conn, sql, rsh,id);
	}

}
