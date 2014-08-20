package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.SpecializzazioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new CorsoDiLaurea into the database.
 * 
 * @author Nicola
 */

public class CreateSpecializzazioneDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO Specializzazione (IdCorso, NomeArea)"
			+ " VALUES(?, ?)";
	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of Specializzazione to be stored into the database
	 */
	private final SpecializzazioneBean specializzazione;

	/**
	 * Creates a new object for storing a record of Specializzazione into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param flusso
	 *            the Specializzazione to be stored into the database.
	 */
	public CreateSpecializzazioneDatabase(final Connection con, final SpecializzazioneBean specializzazione) {
		this.con = con;
		this.specializzazione = specializzazione;
	}

	/**
	 * Stores a new Flusso into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Flusso.
	 */
	public void createSpecializzazione() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, specializzazione.getIdCorso());
			pstmt.setString(2, specializzazione.getNomeArea());
			pstmt.execute();
			
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
}
