package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;
import it.unipd.dei.bding.erasmusadvisor.resources.ThesisSearchRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Contains static methods for the insertion, modification, deletion or thesis research.
 * Database operations about "ArgomentoTesi".
 * @author Nicola
 *
 */
public class ArgomentoTesiDatabase {
	/**
	 * Executes a statement to store a new Thesis into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param arg The Thesis to be stored.
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Thesis.
	 * @return generateId The ID of the the thesis newly generated: -1 means that the thesis already exist o there is a problem.
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
		}
		return generatedId;
	}
	
	/**
	 * Executes a statement to update a Thesis into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param arg The Thesis to be updated.
	 * 
	 * @return The number of rows affected.	
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Thesis.
	 */
	public static int updateArgomentoTesi(Connection con, ArgomentoTesiBean arg)
			throws SQLException {
		/**
		 * The SQL update statement
		 */
		String updateStmt = "UPDATE ArgomentoTesi SET nome = ?, nomeUniversita = ?, triennale = ?, magistrale = ?, stato = CAST(? AS stato) "
				+ " WHERE id = ?";
		
		QueryRunner run = new QueryRunner( );
		
	    return run.update(con, updateStmt, 
	    		arg.getNome(),
	    		arg.getNomeUniversita(),
	    		arg.isTriennale(),
	    		arg.isMagistrale(),
	    		arg.getStato(),
	    		arg.getId());
	}
	
	/**
	 * Delete an ArgomentoTesi (Thesis) from the database.
	 * @param conn A connection to the database.
	 * @param id The id of the thesis to delete.
	 * @return The number of rows affected: zero means an id that do not correspond to any thesis.
	 * @throws SQLException If an error occurs.
	 */
	public static int deleteArgomentoTesi(Connection conn, int id) throws SQLException
	{
		final String statement = "DELETE FROM ArgomentoTesi WHERE Id = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, id);
	}
		
	/**
	 * Return a list of Theses, used in Search servlet.
	 * 
	 * @param con A connection to the database.
	 * @param area (null = optional) The area of the thesis.
	 * @param nomeUni (null = optional) The name of the university in which you search for the thesis.
	 * @param livello (null = optional) The level of the thesis to search.
	 * @param lingua (null = optional) The language in which you search the thesis.
	 * @return listThesis A list of thesis with the features you are looking for.
	 * @throws SQLException SQLException If an error occurs.
	 */
	public static List<ThesisSearchRow> searchArgomentoTesi(Connection con, String area, String nomeUni, String livello, String lingua) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		//If level is selected --> AND statement
		String statement; 
		
		//Thesis is for Graduate or Ungraduate students?
		boolean triennale, magistrale;
		triennale = magistrale = true ;
		if (livello==null) {
			statement = "SELECT DISTINCT A.id, A.nomeUniversita, A.triennale, A.magistrale, A.stato FROM ArgomentoTesi AS A " +
						"INNER JOIN Estensione AS E ON A.id=E.idArgomentoTesi " +
						"INNER JOIN LinguaTesi AS L ON L.idArgomentoTesi = A.id INNER JOIN Lingua AS LI on L.siglaLingua=LI.sigla WHERE" +
						" (? IS NULL OR A.NomeUniversita = ?)  AND (? IS NULL OR E.Area = ?) AND " +
						"(A.Triennale = CAST ( ? AS BOOLEAN) OR A.Magistrale = CAST ( ? AS BOOLEAN)) AND (? IS NULL OR Li.Nome = ?)  ";		
		} 
		else if(livello.equalsIgnoreCase("Graduate"))	
		{
			statement = "SELECT DISTINCT A.id, A.nomeUniversita, A.triennale, A.magistrale, A.stato FROM ArgomentoTesi AS A " +
					"INNER JOIN Estensione AS E ON A.id=E.idArgomentoTesi " +
					"INNER JOIN LinguaTesi AS L ON L.idArgomentoTesi = A.id INNER JOIN Lingua AS LI on L.siglaLingua=LI.sigla WHERE" +
					" (? IS NULL OR A.NomeUniversita = ?)  AND (? IS NULL OR E.Area = ?) AND " +
					" A.Magistrale = CAST ( ? AS BOOLEAN) AND A.Magistrale = CAST ( ? AS BOOLEAN) AND ( ? IS NULL OR Li.Nome = ?)  ";
		}
		else
		{
			statement = "SELECT DISTINCT A.id, A.nomeUniversita, A.triennale, A.magistrale, A.stato FROM ArgomentoTesi AS A " +
					"INNER JOIN Estensione AS E ON A.id=E.idArgomentoTesi " +
					"INNER JOIN LinguaTesi AS L ON L.idArgomentoTesi = A.id INNER JOIN Lingua AS LI on L.siglaLingua=LI.sigla WHERE" +
					" ( ? IS NULL OR A.NomeUniversita = ?)  AND (? IS NULL OR E.Area = ?) AND " +
					" A.Triennale = CAST ( ? AS BOOLEAN) AND  A.Triennale = CAST ( ? AS BOOLEAN) AND ( ? IS NULL OR Li.Nome = ?)  ";
		}	
		
		// query facility
		ResultSetHandler<List<ArgomentoTesiBean>> h = new BeanListHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		
		//query
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ArgomentoTesiBean> idList = null;
		try {
			pstmt = con.prepareStatement(statement);
			int col = 1;
			pstmt.setString(col++, nomeUni);
			pstmt.setString(col++, nomeUni);
			pstmt.setString(col++, area);
			pstmt.setString(col++, area);
			pstmt.setBoolean(col++, triennale);
			pstmt.setBoolean(col++, magistrale);
			pstmt.setString(col++, lingua);
			pstmt.setString(col++, lingua);
			rs = pstmt.executeQuery(); // execute query
			idList = h.handle(rs); // load results to thesis List
			
		} finally {
			DbUtils.close(pstmt); // close the statement (*always*)
			DbUtils.close(rs); // close the result set (*always*)
		}
 
		
		if (idList == null)
			throw new SQLException("Theses not found");
		
		//For each thesis search it by ID and build a new ThesisSearchRow
		List<ThesisSearchRow> listThesis = new ArrayList<ThesisSearchRow>();
		for(int i=0;i<idList.size();i++)
		{
			listThesis.add(getArgomentoTesiID(con, Integer.toString(idList.get(i).getId())));
		}
		
		return listThesis ;
	}
	
	/**
	 * Return a Thesis, for Search's servlets, search by ID.
	 * 
	 * @param conn A connection to the database.
	 * @param ID The ID of the thesis to search.
	 * @return A thesis with its corresponding list of languages, professors, areas.
	 * @throws SQLException If an error occurs.
	 */
	
	public static ThesisSearchRow getArgomentoTesiID(Connection conn, String ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM ArgomentoTesi WHERE ID = CAST (? AS INTEGER)";
		final String statement2 = "SELECT L.Sigla, L.Nome FROM Lingua AS L INNER JOIN LinguaTesi AS T ON L.Sigla = T.SiglaLingua WHERE idargomentotesi = CAST (? AS INTEGER)";
		final String statement4 = "SELECT P.Nome, P.Cognome FROM Professore AS P INNER JOIN Gestione AS G ON P.ID = G.IdProfessore WHERE G.idargomentotesi = CAST (? AS INTEGER)";
		final String statement5 = "SELECT area AS nome FROM Estensione WHERE idargomentotesi = CAST (? AS INTEGER);";
		
		ArgomentoTesiBean tesi = new ArgomentoTesiBean();
		List<LinguaBean> lingue = null;
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
		
		// Gets the profs
		ResultSetHandler<List<ProfessoreBean>> h4 = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		professori = run.query(conn, statement4, h4, ID);
		
		// Gets the areas
		ResultSetHandler<List<AreaBean>> h5 = new BeanListHandler<AreaBean>(AreaBean.class);
		aree = run.query(conn, statement5, h5, ID);
		
		// Returns the results
		return new ThesisSearchRow(tesi, aree, professori, lingue);
	}	
	
	
	/**
	 * Return a Thesis, for Evaluation, search by ID.
	 * 
	 * @param conn A connection to the database.
	 * @param ID The ID of the thesis to search.
	 * @return The Thesis with the ID you have searched for. 
	 * @throws SQLException If an error occurs.
	 */
	public static Thesis getArgomentoTesi(Connection conn, String ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM ArgomentoTesi WHERE ID = CAST (? AS INTEGER)";
		final String statement2 = "SELECT L.Sigla, L.Nome FROM Lingua AS L INNER JOIN LinguaTesi AS T ON L.Sigla = T.SiglaLingua WHERE idargomentotesi = CAST (? AS INTEGER)";
		final String statement3 = "SELECT * FROM ValutazioneTesi WHERE idargomentotesi = CAST (? AS INTEGER)";
		final String statement4 = "SELECT P.Nome, P.Cognome FROM Professore AS P INNER JOIN Gestione AS G ON P.ID = G.IdProfessore WHERE G.idargomentotesi = CAST (? AS INTEGER)";
		final String statement5 = "SELECT area AS nome FROM Estensione WHERE idargomentotesi = CAST (? AS INTEGER);";
		
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
	
	

	/**
	 * Change thesis status.
	 * 
	 * @param con A connection to the database.
	 * @param id The thesis' ID.
	 * @throws SQLException If an error occurs.
	 */
	public static void changeThesisStatus(Connection con, String status, int id) throws SQLException 
	{
		final String sql = "UPDATE ArgomentoTesi SET stato = CAST(? AS STATO) WHERE id = ?;";
		
		PreparedStatement pstmt = null;
		
		pstmt = con.prepareStatement(sql);
		
		try{
			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			
			pstmt.execute();
			
		} 
		finally {
			DbUtils.close(pstmt); // close the statement (*always*)
		}
		
	}
}