/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Universita 
 * @author Alessandro
 *
 */
public class UniversitaDatabase {
	/**
	 * Executes a statement to store a new Universita into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param uni The Universita to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Universita.
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
			//System.out.println("PreparedStatement closed");
		}
	}
	
	/**
	 * Executes a statement to update a Universita into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param uni The Universita to be updated
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Universita.
	 */
	public static void updateUniversita(Connection con, UniversitaBean uni)
			throws SQLException {
		/**
		 * The SQL update statement
		 */
		String updateStmt = "UPDATE Universita SET link = ?, posizioneClassifica = ?, "
				+ "presenzaAlloggi = ?, nomeCitta = ?, statoCitta = ?"
				+ "WHERE nome = ?";
		
		QueryRunner run = new QueryRunner( );
		
	    run.update(con, updateStmt, uni.getLink(), uni.getPosizioneClassifica(), uni.isPresenzaAlloggi(),
	    		uni.getNomeCitta(), uni.getStatoCitta(), uni.getNome());
	    
	    return;
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
				+ "WHERE U.Nome = ?";


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
}
