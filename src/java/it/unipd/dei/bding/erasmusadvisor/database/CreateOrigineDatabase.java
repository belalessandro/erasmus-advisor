package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new Origine into the database.
 * 
 * @author Alessandro
 */
public class CreateOrigineDatabase  {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO Origine (idFlusso, idCorso)" 
										  + " VALUES (?, ?)";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The instance of Origine to be stored into the database
	 */
	private final OrigineBean origine;

	/**
	 * Creates a new object for storing a record of Origine into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param origine
	 *            the Origine to be stored into the database.
	 */
	public CreateOrigineDatabase(final Connection con, final OrigineBean origine) {
		this.con = con;
		this.origine = origine;
	}

	/**
	 * Stores a new Origine into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Origine.
	 */
	public void createOrigine() throws SQLException {

		PreparedStatement pstmt = null;

		try {
		pstmt = con.prepareStatement(STATEMENT);
		pstmt.setString(1, origine.getIdFlusso());
		pstmt.setInt(2, origine.getIdCorso());
		pstmt.execute();
			
		} finally {
			if (pstmt != null) {
					pstmt.close();
			}

			con.close();
		}

	}
}

