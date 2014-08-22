package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.SpecializzazioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

/**
 * Creates a new CorsoDiLaurea into the database.
 * 
 * @author Nicola
 */

public class SpecializzazioneDatabase {

	/**
	 * Stores a new Specializzazione into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Specializzazione.
	 */
	public static void createSpecializzazione(final Connection conn, final SpecializzazioneBean specializzazione) throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String sql = "INSERT INTO Specializzazione (IdCorso, NomeArea)"
					+ " VALUES(?, ?)";
		
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, specializzazione.getIdCorso());
			pstmt.setString(2, specializzazione.getNomeArea());
			pstmt.execute();
			
		} finally {
			DbUtils.close(pstmt);
		}

	}
}
