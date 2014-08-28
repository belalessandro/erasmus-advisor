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
	 * @param conn A connection to the database
	 * @param id The id of the thesis to delete
	 * @return The number of rows affected: zero means an id that do not correspond to any thesis.
	 * @throws SQLException If an error occurs.
	 */
	public static int deleteArgomentoTesi(Connection conn, int id) throws SQLException
	{
		final String statement = "DELETE FROM ArgomentoTesi WHERE Id = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, id);
	}
		
/*	*//**
	 * Return a list of Theses
	 * 
	 * @param con
	 * @param area				---------->>>			DEPRECATED
	 * @param nome
	 * @param livello
	 * @param lingua
	 * @return
	 * @throws SQLException
	 *//*
	public static List<SearchThesisBean> searchTheses(Connection con, String area, String nome, String livello, String lingua) throws SQLException	{
		List<SearchThesisBean> s = new ArrayList<SearchThesisBean>();
		List<Thesis > results = searchArgomentoTesi(con,area, nome, livello, lingua);
		for(int i =0; i<results.size();i++)
		{
			SearchThesisBean res = new SearchThesisBean();
			res.setNomeTesi(results.get(i).getArgomentoTesi().getNome());
			String ar="";
			for(int j = 0;j<results.get(i).getAree().size();j++)
			{
				ar+=" "+results.get(i).getAree().get(j).getNome()+'\n';
			}
			res.setAree(ar);
			String prof="";
			for(int j = 0;j<results.get(i).getProfessori().size();j++)
			{
				prof+=" "+results.get(i).getProfessori().get(j).getNome()+" "+results.get(i).getProfessori().get(j).getCognome()+'\n';
			}
			res.setProfessori(prof);
			String lev="";
			if (results.get(i).getArgomentoTesi().isTriennale()) lev+=" UNDERGRADUATE\n";
			if (results.get(i).getArgomentoTesi().isMagistrale()) lev+=" GRADUATE\n";
			res.setLivello(lev);
			
			String lang="";
			for(int j = 0;j<results.get(i).getLingue().size();j++)
			{
				lang+=" "+results.get(i).getLingue().get(j).getNome()+'\n';
			}
			res.setLingue(lang);
			s.add(res);
			
		}
		System.out.println(s.size());
		return s;
	}*/
	
	/**
	 * Return a list of Theses
	 * 
	 * @param con
	 * @param area
	 * @param nome
	 * @param livello
	 * @param lingua
	 * @return
	 * @throws SQLException
	 */
	public static List<ThesisSearchRow> searchArgomentoTesi(Connection con, String area, String nome, String livello, String lingua) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		//If level is selected --> AND statement
		String statement = "SELECT A.id, A.nomeUniversita, A.triennale, A.magistrale, A.stato FROM ArgomentoTesi AS A INNER JOIN Estensione AS E ON A.id=E.idArgomentoTesi " +
						"INNER JOIN LinguaTesi AS L ON L.idArgomentoTesi = A.id INNER JOIN Lingua AS LI on L.siglaLingua=LI.sigla WHERE" +
						"  A.NomeUniversita LIKE ?  AND E.Area LIKE ? AND (A.Triennale = CAST ( ? AS BOOLEAN) AND A.Magistrale = CAST ( ? AS BOOLEAN)) AND Li.Nome LIKE ?  ";
		
		String triennale, magistrale;
		if (livello.equalsIgnoreCase("Undergraduate")) {
			triennale = "true"; magistrale="false";
		} else if(livello.equalsIgnoreCase("Graduate"))
			
		{
			triennale = "false";magistrale="true";
		}
		else
		{
			triennale = magistrale = "true";
			statement = "SELECT A.id, A.nomeUniversita, A.triennale, A.magistrale, A.stato FROM ArgomentoTesi AS A INNER JOIN Estensione AS E ON A.id=E.idArgomentoTesi " +
					"INNER JOIN LinguaTesi AS L ON L.idArgomentoTesi = A.id INNER JOIN Lingua AS LI on L.siglaLingua=LI.sigla WHERE" +
					" A.NomeUniversita LIKE ?  AND E.Area LIKE ? AND (A.Triennale = CAST ( ? AS BOOLEAN) OR A.Magistrale = CAST ( ? AS BOOLEAN)) AND Li.Nome LIKE ?  ";
		}		
		if (area.equals("undefined")) area = "%"; else area+="%";
		if (nome.equals("undefined")) nome= "%"; else nome+="%";
		if (lingua.equals("undefined")) lingua= "%"; else lingua+="%";
		
		// Entity Bean
		List<ArgomentoTesiBean> idList = null;
		
		// Gets the theses
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<ArgomentoTesiBean>> h = 
				new BeanListHandler<ArgomentoTesiBean>(ArgomentoTesiBean.class);
		idList = run.query(con, statement, h, nome, area, triennale, magistrale, lingua); 
		
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
	 * Return a Thesis, for Search, search by ID
	 * 
	 * @param conn
	 * @param ID
	 * @return
	 * @throws SQLException
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
	 * Return a Thesis, for Evaluation, search by ID
	 * 
	 * @param conn
	 * @param ID
	 * @return
	 * @throws SQLException
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
	 * Change thesis status to REPORTED
	 * 
	 * @param con connection to the database
	 * @param id thesis id
	 * @throws SQLException
	 */
	public static void changeThesisStatusToReported(Connection con, int id) throws SQLException 
	{
		final String sql = "UPDATE ArgomentoTesi SET stato = 'REPORTED' WHERE id = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, id);
		
	}
}