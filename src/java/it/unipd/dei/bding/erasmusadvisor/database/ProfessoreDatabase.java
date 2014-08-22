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
	/**
	 * Executes a statement to store a new Teacher into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param uni The Teacher to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Teacher.
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
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				 generatedId = rs.getInt(0);
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