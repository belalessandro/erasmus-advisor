package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new Documentazione into the database.
 * 
 * @author Alessandro
 */
public class CreateDocumentazioneDatabase  {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO Documentazione (idFlusso, nomeCertificato, livelloCertificato)"
					+ " VALUES (?, ?, ?)";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of Documentazione to be stored into the database
	 */
	private final DocumentazioneBean documentazione;

	/**
	 * Creates a new object for storing a record of Documentazione into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param documentazione
	 *            the Documentazione to be stored into the database.
	 */
	public CreateDocumentazioneDatabase(final Connection con, final DocumentazioneBean documentazione) {
		this.con = con;
		this.documentazione = documentazione;
	}

	/**
	 * Stores a new Documentazione into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Documentazione.
	 */
	public void createDocumentazione() throws SQLException {

		PreparedStatement pstmt = null;

		try {
		pstmt = con.prepareStatement(STATEMENT);
		pstmt.setString(1, documentazione.getIdFlusso());
		pstmt.setString(2, documentazione.getNomeCertificato());
		pstmt.setString(3, documentazione.getLivelloCertificato());
		pstmt.execute();
			
		} finally {
			if (pstmt != null) {
					pstmt.close();
			}

			con.close();
		}

	}
}

