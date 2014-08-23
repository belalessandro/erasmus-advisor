package it.unipd.dei.bding.erasmusadvisor.database;



import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;

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
 * 
 * @author Nicola
 *
 */
public class ProfessoreDatabase {
	public static int selectOrInsertProfessore (Connection con, String nome, 
			String cognome, String universita) throws SQLException
	
	{
		int ID = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//Search if The Teaches has already Classes or Theses and returning the id
		String statement =  " SELECT P.id FROM Professore AS P " +
							" INNER JOIN Svolgimento AS S ON P.id = S.idProfessore " +
							" INNER JOIN Insegnamento AS I on I.id = S.idInsegnamento " +
							" WHERE P.nome = ? AND P.cognome = ? AND I.nomeUniversita = ? " +
							" UNION " +
							" SELECT P.id FROM Professore AS P " +
							" INNER JOIN Gestione AS G ON P.id = G.idProfessore " + 
							" INNER JOIN ArgomentoTesi AS A on A.id = G.idArgomentoTesi " +
							" WHERE P.nome = ? AND P.cognome = ? AND A.nomeUniversita = ? ";
		try {
			pstmt = con.prepareStatement(statement);
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, universita);
			pstmt.setString(4, nome);
			pstmt.setString(5, cognome);
			pstmt.setString(6, universita);
			rs = pstmt.executeQuery();
			//If the query has results then update ID
			if (rs.next()) 
			{
				ID = rs.getInt(1);
			}
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
		
		//If NOT, insert the Teacher into database
		if (ID==-1) 
		{
			ProfessoreBean prof = new ProfessoreBean();
			prof.setNome(nome);
			prof.setCognome(cognome);
			ID = createProfessore(con, prof);
		}
		
		return ID;
	}
	
	
	/**
	 * Create a new Teacher and return is Id
	 * @param con
	 * @param prof
	 * @return idProf
	 * @throws SQLException
	 */
	public static int createProfessore(Connection con, ProfessoreBean prof)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Professore (id, nome, cognome) "
				+ "VALUES (DEFAULT, ?, ?)"
				+ "RETURNING id";
		
		PreparedStatement pstmt = null;
		int generatedId = -1;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, prof.getNome());
			pstmt.setString(2, prof.getCognome());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				 generatedId = rs.getInt(1);
			}
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
		return generatedId;
	}
	
	/**
	* Delete a Teacher
	* 
	* @return the number of rows affected	
	* @throws SQLException if any error occurs 
	*/
	public static int deleteProfessore (Connection con, String nome, String cognome) throws SQLException 
	{
		/**
		 * The SQL delete statement
		 */
		String statement = "DELETE From Professore WHERE nome = ? AND cognome = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(con, statement, nome, cognome);
		
	}
	
	/**
	 * Search a Teacher by name and surname and fits into Teacher model   
	 */
	/*public static Teacher searchTeacher(Connection con, String nome, String cognome) throws SQLException {
		*//**
		 * The SQL statements to be executed
		 *//*
		String statement = "SELECT * "
				+ "FROM Professore AS P "
				+ "WHERE P.Nome = ? AND P.Cognome = ?";
		
		// Entity Bean
		ProfessoreBean arg = new ProfessoreBean();
		QueryRunner run = new QueryRunner();
		
		// Gets the Teacher
		ResultSetHandler<ProfessoreBean> h = new BeanHandler<ProfessoreBean>(ProfessoreBean.class);
		arg = run.query(con, statement, h, nome, cognome); 
		
		if (arg == null)
			throw new SQLException("Teacher not found");
	}
	*/
}