package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new Studente into the database.
 * 
 * @author Fede
 */
public class CreateResponsabileDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO ResponsabileFlusso (NomeUtente, Nome, Cognome, Email, DataRegistrazione, Password, Salt, UltimoAccesso, Attivo, Abilitato)"
			+ "VALUES(?, ?, ?, ?, DEFAULT, ?, ?, CURRENT_DATE , TRUE, FALSE)";
	
	private static final String UNISTMT =
			"UPDATE ResponsabileFlusso (SET NomeUniversita=?) WHERE NomeUtente=?";


	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of Manager to be stored into the database
	 */
	private final ResponsabileFlussoBean responsabile;

	/**
	 * Creates a new object for storing a record of Manager into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param responsabile
	 *            the Manager to be stored into the database.
	 */
	public CreateResponsabileDatabase(final Connection con, final ResponsabileFlussoBean responsabile) {
		this.con = con;
		this.responsabile = responsabile;
	}

	/**
	 * Stores a new Studente into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Studente.
	 */
	public void createManager() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, responsabile.getNomeUtente());
			pstmt.setString(2, responsabile.getNome());
			pstmt.setString(3, responsabile.getCognome());

			pstmt.setString(4, responsabile.getEmail());
			pstmt.setString(5, responsabile.getPassword());
			pstmt.setString(6, responsabile.getSalt());

			pstmt.execute();
			
			if(responsabile.getNomeUniversita()!=null && pstmt != null){
				pstmt = con.prepareStatement(UNISTMT);
				pstmt.setString(1, responsabile.getNomeUniversita());
				pstmt.setString(2, responsabile.getNomeUtente());
				
				pstmt.execute();
			}

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
}
