/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about "Universita" .
 * @author Alessandro, Nicola
 *
 */
public class UniversitaDatabase {
	/**
	 * Executes a statement to store a new "Universita" into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param uni The "Universita" to be stored.
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the "Universita".
	 */
	public static void createUniversita(Connection con, UniversitaBean uni)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Universita (nome, link, posizioneClassifica, "
				+ "presenzaAlloggi, nomeCitta, statoCitta) VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, uni.getNome());
			pstmt.setString(2, uni.getLink());
			pstmt.setInt(3, uni.getPosizioneClassifica());
			pstmt.setBoolean(4, uni.isPresenzaAlloggi());
			pstmt.setString(5, uni.getNomeCitta());
			pstmt.setString(6, uni.getStatoCitta());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	
	/**
	 * Executes a statement to update a Universita into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param uni The "Universita" to be updated.
	 * 
	 * @return The number of rows affected.	
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the "Universita".
	 */
	public static int updateUniversita(Connection con, UniversitaBean uni, String old_name)
			throws SQLException {
		/**
		 * The SQL update statement
		 */
		String updateStmt = "UPDATE Universita SET nome = ?, link = ?, posizioneClassifica = ?, "
				+ "presenzaAlloggi = ?, nomeCitta = ?, statoCitta = ?"
				+ "WHERE nome = ?";
		
		QueryRunner run = new QueryRunner( );
		
	    return run.update(con, updateStmt, uni.getNome(), uni.getLink(), uni.getPosizioneClassifica(), uni.isPresenzaAlloggi(),
	    		uni.getNomeCitta(), uni.getStatoCitta(), old_name);
	}
	

	/**
	 * Delete a "Universita" (University) from the database.
	 * 
	 * @param conn A connection to the database.
	 * @param name The name of the university to delete.
	 * @return The number of rows affected: zero means an id that do not correspond to any university.
	 * @throws SQLException If an error occurs in SQL query execution.
	 */
	public static int deleteUniversita(Connection conn, String name) throws SQLException
	{
		String statement = "DELETE From Universita WHERE nome = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, name);
	}
	
	/**
	 * Search a University by name and fits into University model   
	 */
	public static University searchUniversityModelByName(Connection con, String byName) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		
		String statement1 = "SELECT U.Nome, U.Link, U.PosizioneClassifica, U.PresenzaAlloggi, "
				+ "U.NomeCitta, U.StatoCitta "
				+ "FROM Universita AS U "
				+ "WHERE U.Nome = ?";
		
		String statement2 = "SELECT V.NomeUtenteStudente, "
				+ "V.DataInserimento, V.Commento, V.CollocazioneUrbana, "
				+ "V.IniziativeErasmus, V.QtaInsegnamenti, V.QtaAule "
				+ "FROM Universita AS U "
				+ "INNER JOIN ValutazioneUniversita AS V ON U.Nome = V.NomeUniversita "
				+ "WHERE U.Nome = ? ORDER BY datainserimento DESC";


		// Entity Bean
		UniversitaBean uni = new UniversitaBean();
		List<ValutazioneUniversitaBean> valList = null;
		
		QueryRunner run = new QueryRunner();
		
		// Gets the university
		ResultSetHandler<UniversitaBean> h = new BeanHandler<UniversitaBean>(UniversitaBean.class);
		uni = run.query(con, statement1, h, byName); 
		
		if (uni == null)
			throw new SQLException("University not found");
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneUniversitaBean>> h2 = 
				new BeanListHandler<ValutazioneUniversitaBean>(ValutazioneUniversitaBean.class);
		valList = run.query(con, statement2, h2, byName);

		// Returns the results through the university model
		return new University(uni, valList);
	}
	
	/**
	 * Search a University by country or by city or both.
	 * 
	 * @param con A connection to the database.
	 * @param country (optional= null) The country in which the university is to search.
	 * @param city (optional= null) The city in which the university is to search.
	 * @return A list of universities.
	 * @throws SQLException If an error occurs in SQL query execution.
	 */
	public static List<UniversitaBean> searchUniversityByCity(Connection con, String country, String city) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		String statement = "SELECT DISTINCT * FROM Universita as U WHERE (? IS NULL OR U.statoCitta = ?) AND (? IS NULL OR U.nomeCitta = ?) ";

		// Entity Bean
		List<UniversitaBean> uniList = null;
		
		ResultSetHandler<List<UniversitaBean>> h = new BeanListHandler<UniversitaBean>(UniversitaBean.class);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// Gets the universities
		try {
			pstmt = con.prepareStatement(statement);
			int col = 1;
			pstmt.setString(col++, country);
			pstmt.setString(col++, country);
			pstmt.setString(col++, city);
			pstmt.setString(col++, city);
			rs = pstmt.executeQuery(); // execute query
			uniList = h.handle(rs); // load results to flussoList
			
		} finally {
			DbUtils.close(pstmt); // close the statement (*always*)
			DbUtils.close(rs); // close the result set (*always*)
		}
		if (uniList == null)
			throw new SQLException("University not found");

		// Returns the results through the university Bean List
		return uniList;
	}
		
}
