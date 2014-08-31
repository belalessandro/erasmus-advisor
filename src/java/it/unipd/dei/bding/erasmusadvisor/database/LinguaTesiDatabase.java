package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaTesiBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Database operations about "LinguaTesi".
 * @author Alessandro
 *
 */
public class LinguaTesiDatabase {

	/**
	 * Executes a statement to store a new LinguaTesi into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param linguaTesi The LinguaTesi to be stored.
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the LinguaTesi.
	 */
	public static void createLinguaTesi(Connection con, LinguaTesiBean linguaTesi)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO LinguaTesi (siglaLingua, idArgomentoTesi) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, linguaTesi.getSiglaLingua());
			pstmt.setInt(2, linguaTesi.getIdArgomentoTesi());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}

	/**
	 * Delete "LinguaTesi" instances by the thesis id given.
	 * 
	 * @param con A connection to the database.
	 * @param idArgomentoTesi Id of the thesis.
	 * @return Number of row deleted.
	 * @throws SQLException if an error occurs while removing "LinguaTesi".
	 */
	public static int  deleteLinguaTesi(Connection con, int idArgomentoTesi) throws SQLException 
	{
		final String sql = "DELETE FROM LinguaTesi WHERE idargomentotesi = ?;";
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql, idArgomentoTesi);
	}

}
