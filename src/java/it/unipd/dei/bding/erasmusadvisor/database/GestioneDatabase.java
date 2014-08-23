package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.servlets.AbstractDatabaseServlet;
import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.GestioneBean;
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

}
