package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Database operations about Svolgimento
 * @author Alessandro
 *
 */
public class SvolgimentoDatabase {

	/**
	 * Executes a statement to store a new Svolgimento into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param svolgimento The Svolgimento to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Svolgimento.
	 */
	public static void createSvolgimento(Connection con, SvolgimentoBean svolgimento)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Svolgimento (idInsegnamento, idProfessore) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setInt(1, svolgimento.getIdInsegnamento());
			pstmt.setInt(2, svolgimento.getIdProfessore());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	
	/**
	 * Delete Svolgimento's instances by idInsegnamento value.
	 * @param con database connection
	 * @param idInsegnamento Insegnamento's id 
	 * @return number of rows deleted
	 * @throws SQLException
	 */
	public static int deleteSvolgimentoByClassId(Connection con, int idInsegnamento) throws SQLException
	{
		// statement
		final String stmt = "DELETE FROM Svolgimento WHERE idInsegnamento = ?";
		
		QueryRunner run = new QueryRunner();
		
		return run.update(con, stmt, idInsegnamento);
	}

}
