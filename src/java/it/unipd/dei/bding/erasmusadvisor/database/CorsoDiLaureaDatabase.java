package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

/**
 * Creates a new CorsoDiLaurea into the database.
 * 
 * @author Nicola
 */
public class CorsoDiLaureaDatabase  {

	public static int createCorsoDiLaurea(final Connection con, 
			final CorsoDiLaureaBean corsoDiLaurea) throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String statement = "INSERT INTO CorsoDiLaurea (id, nome, livello, nomeUniversita)"
							+ " VALUES (DEFAULT, ?, ?, ?)"
							+ " RETURNING id";
		
		PreparedStatement pstmt = null;
		int generatedId = -1;
		
		try {
			pstmt = con.prepareStatement(statement);
			pstmt.setString(1, corsoDiLaurea.getNome());
			pstmt.setString(2, corsoDiLaurea.getLivello());
			pstmt.setString(3, corsoDiLaurea.getNomeUniversita());
			pstmt.execute();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				 generatedId = rs.getInt(1);
			}
		} finally {
			DbUtils.close(pstmt);
		}
		
		return generatedId;
	}
}