package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.servlets.AbstractDatabaseServlet;
import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Documentazione
 * @author Alessandro
 *
 */
public class DocumentazioneDatabase {
	
	/**
	 * Executes a statement to store a new Documentazione into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param doc The Documentazione to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Documentazione.
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
	 * @param con connection to the database
	 * @param flowId flow id
	 * @return number of instances deleted
	 * @throws SQLException
	 */
	public static int  deleteDocumentazioneByFlowId(Connection con, String flowId) throws SQLException {
		final String sql = "DELETE FROM Documentazione WHERE idFlusso = ?;";
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql, flowId);
		
	}

}
