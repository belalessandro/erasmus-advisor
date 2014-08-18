package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateIscrizioneDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO Iscrizione (IdCorso, NomeUtenteStudente, AnnoInizio, AnnoFine)"
			+ " VALUES(?, ?, ?, ?)";
			

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of Iscrizione to be stored into the database
	 */
	private final IscrizioneBean iscrizione;

	/**
	 * Creates a new object for storing a record of Iscrizione into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param iscrizione
	 *            the Iscrizione to be stored into the database.
	 */
	public CreateIscrizioneDatabase(final Connection con, final IscrizioneBean iscrizione) {
		this.con = con;
		this.iscrizione = iscrizione;
	}

	/**
	 * Stores a new Iscrizione into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Iscrizione.
	 */
	public void createIscrizione() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, iscrizione.getIdCorso());
			pstmt.setString(2, iscrizione.getNomeUtenteStudente());
			pstmt.setDate(3, iscrizione.getAnnoInizio());
			pstmt.setDate(4, iscrizione.getAnnoFine());
			pstmt.execute();
			
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
}
