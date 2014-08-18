package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateFlussoDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO Flusso (Id, Destinazione, RespFlusso, PostiDisponibili, Attivo, DataUltimaModifica, Durata, Dettagli)"
			+ " VALUES(?, ?, ?, ?, DEFAULT, CURRENT_DATE, ?, ?)";
			

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of Flusso to be stored into the database
	 */
	private final FlussoBean flusso;

	/**
	 * Creates a new object for storing a record of Flusso into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param flusso
	 *            the Flusso to be stored into the database.
	 */
	public CreateFlussoDatabase(final Connection con, final FlussoBean flusso) {
		this.con = con;
		this.flusso = flusso;
	}

	/**
	 * Stores a new Flusso into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Flusso.
	 */
	public void createFlusso() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, flusso.getId());
			pstmt.setString(2, flusso.getDestinazione());
//			if (flusso.getRespFlusso() != null) {
			pstmt.setString(3, flusso.getRespFlusso()); 
//			} else {
//				pstmt.setNull(3, java.sql.Types.VARCHAR);
//			}
			pstmt.setInt(4, flusso.getPostiDisponibili());
			pstmt.setInt(5, flusso.getDurata());
			pstmt.setString(6, flusso.getDettagli());
			pstmt.execute();
			
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
}
