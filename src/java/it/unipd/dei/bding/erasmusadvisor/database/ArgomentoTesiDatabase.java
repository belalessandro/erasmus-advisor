package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;

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
public class ArgomentoTesiDatabase {
	/**
	 * Executes a statement to store a new Thesis into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param uni The Thesis to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Thesis.
	 */
	public static int createArgomentoTesi(Connection con, ArgomentoTesiBean arg)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO ArgomentoTesi (id, nome, nomeUniversita, triennale, magistrale, stato) "
				+ "VALUES (DEFAULT, ?, ?, ?, ?, ?)"
				+ "RETURNING id";
		
		PreparedStatement pstmt = null;
		int generatedId = -1;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, arg.getNome());
			pstmt.setString(2, arg.getNomeUniversita());
			pstmt.setBoolean(3, arg.isTriennale());
			pstmt.setBoolean(4, arg.isMagistrale());
			pstmt.setString(5, arg.getStato());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				 generatedId = rs.getInt(0);
			}
		} finally {
			DbUtils.closeQuietly(pstmt);
			//System.out.println("PreparedStatement closed");
		}
		return generatedId;
	}
	
	/**
	 * Executes a statement to update a Thesis into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param uni The Thesis to be updated
	 * 
	 * @return the number of rows affected	
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Thesis.
	 */
	public static int updateArgomentoTesi(Connection con, ArgomentoTesiBean arg)
			throws SQLException {
		/**
		 * The SQL update statement
		 */
		String updateStmt = "UPDATE ArgomentoTesi SET triennale = ?, magistrale = ?, stato = ? "
				+ " WHERE nome = ? AND nomeUniversita = ?";
		
		QueryRunner run = new QueryRunner( );
		
	    return run.update(con, updateStmt, arg.isTriennale(), arg.isMagistrale(),
	    		arg.getStato(), arg.getNome(), arg.getNomeUniversita());
	}
	
	/**
	* Delete a Thesis
	* 
	* @return the number of rows affected	
	* @throws SQLException if any error occurs 
	*/
	public static int deleteArgomentoTesi(Connection con, String nome, String nomeUni) throws SQLException 
	{
		/**
		 * The SQL delete statement
		 */
		String statement = "DELETE From ArgomentoTesi WHERE nome = ? AND nomeUniversita = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(con, statement, nome, nomeUni);
		
	}
	
	
	/**
	 * Search a Thesis by name and fits into Thesis model   
	 */
	public static Thesis searchThesisModelByName(Connection con, String byName) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		
		String statement1 = "SELECT A.Nome, A.NomeUniversita, A.Triennale, A.Magistrale "
				+ "FROM ArgomentoTesi AS A "
				+ "WHERE A.Nome = ?";
		
		String statement2 = "SELECT V.NomeUtenteStudente, "
				+ "V.DataInserimento, V.Commento, V.ImpegnoNecessario, "
				+ "V.InteresseArgomento, V.DiponibilitaRelatore, V.Soddisfazione "
				+ "FROM ArgomentoTesi AS A "
				+ "INNER JOIN ValutazioneTesi AS V ON A.id = V.idArgomentoTesi "
				+ "WHERE A.Nome = ?";

		// Entity Bean
		ArgomentoTesiBean arg = new ArgomentoTesiBean();
		List<ValutazioneTesiBean> valList = null;
		
		QueryRunner run = new QueryRunner();
		
		// Gets the Thesis
		ResultSetHandler<ArgomentoTesiBean> h = new BeanHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		arg = run.query(con, statement1, h, byName); 
		
		if (arg == null)
			throw new SQLException("Thesis not found");
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneTesiBean>> h2 = 
				new BeanListHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
		valList = run.query(con, statement2, h2, byName);

		// Returns the results through the university model
		return new Thesis(arg, valList);
	}
	
	/*//DA RIVEDERE:
	*//**
	 * Search Theses by University and by Area and fits into Thesis model   
	 *//*
	public static List<Thesis> searchArgomentoTesiBy(Connection con, String nomeUni, String area) throws SQLException {
		*//**
		 * The SQL statements to be executed
		 *//*
		
		String statement = "SELECT A.Nome, A.NomeUniversita, A.Triennale, A.Magistrale, A.Stato "
				+ "FROM ArgomentoTesi AS A INNER JOIN Estensione AS E ON A.id=E.idArgomentoTesi"
				+ "WHERE A.NomeUniversita = ? AND E.Area = ?";

		// Entity Bean
		List<ArgomentoTesiBean> argList = null;
		
		// Gets the theses
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ArgomentoTesiBean>> h = 
				new BeanListHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		argList = run.query(con, statement, h, nomeUni, area); 
		if (argList == null)
			throw new SQLException("Theses not found");

		// Returns the results through the university model
		return new Thesis(argList);
	}*/
}