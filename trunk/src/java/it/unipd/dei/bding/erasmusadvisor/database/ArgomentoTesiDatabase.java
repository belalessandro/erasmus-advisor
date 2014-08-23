package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
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
	 * @param arg The Thesis to be stored
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
				+ "VALUES (DEFAULT, ?, ?, ?, ?, CAST(? AS stato))"
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
			
			ResultSet rs = pstmt.getResultSet();
			if (rs.next()) {
				 generatedId = rs.getInt(1);
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
	

	/**
	 * Search Theses by University and by Area and fits into Thesis model   
	 */
	public static List<ArgomentoTesiBean> searchArgomentoTesiBy(Connection con, String nomeUni, String area) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		
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
		return argList;
	}
	
	public static Thesis getArgomentoTesiByID(Connection conn, String ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM ArgomentoTesi WHERE ID = CAST (? AS INTEGER)";
		final String statement2 = "SELECT L.Sigla, L.Nome FROM Lingua AS L INNER JOIN LinguaTesi AS T ON L.Sigla = T.SiglaLingua WHERE idargomentotesi = CAST (? AS INTEGER)";
		final String statement3 = "SELECT * FROM ValutazioneTesi WHERE idargomentotesi = CAST (? AS INTEGER)";
		final String statement4 = "SELECT P.Nome, P.Cognome FROM Professore AS P INNER JOIN Gestione AS G ON P.ID = G.IdProfessore WHERE G.idargomentotesi = CAST (? AS INTEGER)";
		final String statement5 = "SELECT area FROM Estensione WHERE idargomentotesi = CAST (? AS INTEGER)";
		
		ArgomentoTesiBean tesi = new ArgomentoTesiBean();
		List<LinguaBean> lingue = null;
		List<ValutazioneTesiBean> listaValutazioni = null;
		List<ProfessoreBean> professori = null;
		List<AreaBean> aree = null;

		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<ArgomentoTesiBean> h1 = new BeanHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		tesi = run.query(conn, statement1, h1, ID);
		
		if (tesi == null)
			throw new SQLException("Thesis id not found.");
		
		// Gets the languages
		ResultSetHandler<List<LinguaBean>> h2 = new BeanListHandler<LinguaBean>(LinguaBean.class);
		lingue = run.query(conn, statement2, h2, ID);
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneTesiBean>> h3 = new BeanListHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
		listaValutazioni = run.query(conn, statement3, h3, ID);
		
		// Gets the profs
		ResultSetHandler<List<ProfessoreBean>> h4 = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		professori = run.query(conn, statement4, h4, ID);
		
		// Gets the areas
		ResultSetHandler<List<AreaBean>> h5 = new BeanListHandler<AreaBean>(AreaBean.class);
		aree = run.query(conn, statement5, h5, ID);
		
		// Returns the results
		return new Thesis(tesi, listaValutazioni, professori, lingue, aree);
	}
}