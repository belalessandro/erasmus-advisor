package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

/**
 * Database operations about LinguaCitta
 * @author Alessandro
 *
 */
public class LinguaCittaDatabase {

	/**
	 * Executes a statement to store a new LinguaCitta into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param linguaCitta The LinguaCitta to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the LinguaCitta.
	 */
	public static void createLinguaCitta(Connection con, LinguaCittaBean linguaCitta)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO LinguaCitta (siglaLingua, nomeCitta, statoCitta) VALUES (?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, linguaCitta.getSiglaLingua());
			pstmt.setString(2, linguaCitta.getNomeCitta());
			pstmt.setString(3, linguaCitta.getStatoCitta());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}

}
