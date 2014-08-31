package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Database operations about "Documentazione".
 * @author Alessandro
 *
 */
public class DocumentazioneDatabase {
	
	/**
	 * Executes a statement to store a new "Documentazione" into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param doc The "Documentazione" to be stored.
	 * 
	 * @throws SQLException if any error occurs while storing the Documentazione.
	 */
	public static void createDocumentazione(Connection con, DocumentazioneBean doc)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Documentazione (idFlusso, nomeCertificato, livelloCertificato) VALUES (?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, doc.getIdFlusso());
			pstmt.setString(2, doc.getNomeCertificato());
			pstmt.setString(3, doc.getLivelloCertificato());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}

	/**
	 * Deletes all Documentazione's instaces with the flow id given.
	 * 
	 * @param con A connection to the database.
	 * @param flowId Flow id.
	 * @return Number of instances deleted.
	 * @throws SQLException If an error occurs while deleting the "Documentazione".
	 */
	public static int  deleteDocumentazioneByFlowId(Connection con, String flowId) throws SQLException {
		final String sql = "DELETE FROM Documentazione WHERE idFlusso = ?;";
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql, flowId);
		
	}

}
