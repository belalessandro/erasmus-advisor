package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a new CorsoDiLaurea into the database.
 * 
 * @author Nicola
 */
public class CorsoDiLaureaDatabase  {

	/**
	 * The first SQL statement to be executed
	 */
	private static final String STATEMENT = 
			"INSERT INTO CorsoDiLaurea (id, nome, livello, nomeUniversita)"
			+ "VALUES (DEFAULT, ?, ?, ?)"
			+ "RETURNING id";
	
	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of CorsoDiLaurea to be stored into the database
	 */
	private final CorsoDiLaureaBean corsoDiLaurea;
	
	
	/**
	 * Creates a new object for storing a record of CorsoDiLaurea into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param corsoDiLaurea
	 *            the CorsoDiLaurea to be stored into the database.
	 */
	public CorsoDiLaureaDatabase(final Connection con, final CorsoDiLaureaBean corsoDiLaurea) {
		this.con = con;
		this.corsoDiLaurea = corsoDiLaurea;
	}

	/**
	 * Stores a new CorsoDiLaurea into the database, returning the generated Id
	 * 
	 * @return the auto-generated Id for the row inserted
	 * @throws SQLException
	 *             if any error occurs while storing the CorsoDiLaurea.
	 */
	public int createCorsoDiLaurea() throws SQLException {

		PreparedStatement pstmt = null;
		int generatedId = -1;
		
		try {
		pstmt = con.prepareStatement(STATEMENT);
		pstmt.setString(1, corsoDiLaurea.getNome());
		pstmt.setString(2, corsoDiLaurea.getLivello());
		pstmt.setString(3, corsoDiLaurea.getNomeUniversita());
		pstmt.execute();
			
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			 generatedId = rs.getInt(1);
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