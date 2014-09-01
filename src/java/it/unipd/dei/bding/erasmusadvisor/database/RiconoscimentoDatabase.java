package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;





import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;



/**
 * @author Nicola
 *
 *Database operations about "Riconoscimento".
 *
 */

public class RiconoscimentoDatabase 
{
	/**
	 * Returns all classes validated by the flow manager.
	 * 
	 * @param conn A connection to the database.
	 * @param ID Flow's id.
	 * @return All classes validated as a list of InsegnamentoBean.
	 * @throws SQLException If an error occurs.
	 */
	public static List<InsegnamentoBean> getInsegnamentiRiconosciuti(Connection conn, String ID)
			throws SQLException 
	{
		final String statement = "SELECT * FROM Riconoscimento AS R INNER JOIN Insegnamento AS I ON I.id = R.idinsegnamento WHERE idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		// Gets the profs
		ResultSetHandler<List<InsegnamentoBean>> h = new BeanListHandler<InsegnamentoBean>(InsegnamentoBean.class);
		return run.query(conn, statement, h, ID);
	}

	/**
	 * Delete "Riconoscimento"'s instances with the flow id given.
	 * 
	 * @param con A connection to the database.
	 * @param flowId The flow's id given.
	 * @return Number of instances of "Riconoscimento" deleted.
	 * @throws SQLException If an error occurs.
	 */
	public static int deleteRiconoscimentoByFlowId(Connection con, String flowId) throws SQLException 
	{
		final String sql = "DELETE FROM Riconoscimento WHERE IdFlusso = ?;";
		
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql);
	}
	
	/**
	 * Add an acknowledge class to a flow.
	 * 
	 * @param conn A connection to the database.
	 * @param flowId Flow's id.
	 * @param classId Class' id.
	 * @throws SQLException If an error occurs.
	 */
	public static void addRiconoscimento(Connection conn, String flowId, int classId) throws SQLException
	{
		String insertStmt = "INSERT INTO Riconoscimento (idflusso, idinsegnamento) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(insertStmt);
			pstmt.setString(1, flowId);
			pstmt.setInt(2, classId);
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}

}