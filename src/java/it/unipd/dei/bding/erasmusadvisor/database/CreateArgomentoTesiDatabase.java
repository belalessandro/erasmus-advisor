package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a new ArgomentoTesi into the database.
 * 
 * @author Alessandro
 */
public class CreateArgomentoTesiDatabase  {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO ArgomentoTesi (id, nome, nomeUniversita, triennale, magistrale, stato)"
			+ "VALUES (DEFAULT, ?, ?, ?, ?, ?)"
			+ "RETURNING id";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of ArgomentoTesi to be stored into the database
	 */
	private final ArgomentoTesiBean argomentoTesi;

	/**
	 * Creates a new object for storing a record of ArgomentoTesi into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param argomentoTesi
	 *            the ArgomentoTesi to be stored into the database.
	 */
	public CreateArgomentoTesiDatabase(final Connection con, final ArgomentoTesiBean argomentoTesi) {
		this.con = con;
		this.argomentoTesi = argomentoTesi;
	}

	/**
	 * Stores a new ArgomentoTesi into the database, returning the generated Id
	 * 
	 * @return the auto-generated Id for the row inserted
	 * @throws SQLException
	 *             if any error occurs while storing the ArgomentoTesi.
	 */
	public long createArgomentoTesi() throws SQLException {

		PreparedStatement pstmt = null;
		long generatedId = -1;
		
		try {
		pstmt = con.prepareStatement(STATEMENT);
		pstmt.setString(1, argomentoTesi.getNome());
		pstmt.setString(2, argomentoTesi.getNomeUniversita());
		pstmt.setBoolean(3, argomentoTesi.isTriennale());
		pstmt.setBoolean(4, argomentoTesi.isMagistrale());
		pstmt.setString(5,argomentoTesi.getStato());
		pstmt.execute();
			
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			 generatedId = rs.getLong(1);
		}
		} finally {
			if (pstmt != null) {
					pstmt.close();
			}

			con.close();
		}
		
		return generatedId;
	}
}

