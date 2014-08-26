package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.GestioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Database operations about Gestione
 * @author Alessandro
 *
 */
public class GestioneDatabase {
	
	/**
	 * Executes a statement to store a new Gestione into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param gestione The Gestione to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Gestione.
	 */
	public static void createGestione(Connection con, GestioneBean gestione)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Gestione (idArgomentoTesi, idProfessore) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setInt(1, gestione.getIdArgomentoTesi());
			pstmt.setInt(2, gestione.getIdProfessore());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}

	/**
	 * Delete Gestione instances by the thesis given.
	 * 
	 * @param con connection to the database
	 * @param idThesis id of the thesis
	 * @return number of instances deleted
	 * @throws SQLException
	 */
	public static int  deleteGestioneByThesisId(Connection con, int idThesis) throws SQLException 
	{
		final String  sql = "DELETE FROM Gestione WHERE idargomentotesi = ?;";
		
		QueryRunner run = new QueryRunner();
		
		
		return run.update(con, sql, idThesis);
		
	}

}
