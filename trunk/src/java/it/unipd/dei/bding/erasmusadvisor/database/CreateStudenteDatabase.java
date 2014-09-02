package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new "Studente" into the database and closes the connection.
 * 
 * @author Alessandro
 */
public class CreateStudenteDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO Studente (NomeUtente, Email, DataRegistrazione, Password, Salt, UltimoAccesso, Attivo)"
			+ "VALUES(?, ?, DEFAULT, ?, ?, CURRENT_DATE , TRUE)";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of "Studente" to be stored into the database
	 */
	private final StudenteBean studente;

	/**
	 * Creates a new object for storing a record of "Studente" into the database.
	 * 
	 * @param con
	 *            The connection to the database.
	 * @param studente
	 *           The "Studente" to be stored into the database.
	 */
	public CreateStudenteDatabase(final Connection con, final StudenteBean studente) {
		this.con = con;
		this.studente = studente;
	}

	/**
	 * Stores a new "Studente" into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the "Studente".
	 */
	public void createStudente() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, studente.getNomeUtente());
			pstmt.setString(2, studente.getEmail());
			pstmt.setString(3, studente.getPassword());
			pstmt.setString(4, studente.getSalt());

			pstmt.execute();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
}
