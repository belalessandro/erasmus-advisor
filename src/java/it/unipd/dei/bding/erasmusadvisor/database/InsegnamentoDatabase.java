package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Teaching;
import it.unipd.dei.bding.erasmusadvisor.resources.TeachingSearchRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about "Insegnamento".
 *  
 * @author Luca, Nicola
 *
 */

public class InsegnamentoDatabase 
{
	
	/**
	 * Executes a statement to store a new "Insegnamento" into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param insegnamento The "Insegnamento" to be stored.
	 * @return The generated id.
	 * @throws SQLException
	 *             If any error occurs while storing the "Insegnamento".
	 */
	public static int createInsegnamento(Connection con, InsegnamentoBean insegnamento)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = 
				"INSERT INTO Insegnamento (id, nome, crediti, nomeUniversita, "
					+ "periodoErogazione, stato, annoCorso, nomeArea, nomeLingua) "
				+ "VALUES (DEFAULT, ?, ?, ?, CAST(? AS semestre), CAST(? AS stato), "
					+ "CAST(? AS annoaccademico), ?, ?) "
				+ "RETURNING Id";
		
		PreparedStatement pstmt = null;
		int generatedId = -1;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, insegnamento.getNome());
			pstmt.setInt(2, insegnamento.getCrediti());
			pstmt.setString(3, insegnamento.getNomeUniversita());
			pstmt.setInt(4, insegnamento.getPeriodoErogazione());
			pstmt.setString(5, insegnamento.getStato());
			pstmt.setInt(6, insegnamento.getAnnoCorso());
			pstmt.setString(7, insegnamento.getNomeArea());
			pstmt.setString(8, insegnamento.getNomeLingua());
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
	 * Return a list of class (TeachingSearchRow), with the corresponding list
	 * of professors, used in Search.
	 * 
	 * @param conn A connection to the database.
	 * @param area (optional = null) The area of the class to search.
	 * @param nomeUni (optional = null) The class' university name.
	 * @param anno (optional = null) The year of the degree course in which the class is taught.
	 * @param periodo (optional = null) The period of the year in which the class is taught.
	 * @param lingua (optional = null) The language of the class.
	 * @return A list of class TeachingSearchRow.
	 * @throws SQLException if an error occurs while searching class.
	 */
	public static List<TeachingSearchRow> searchInsegnamento(Connection conn, String area, String nomeUni, 
			Integer anno, Integer periodo, String lingua) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		final String statement= "SELECT DISTINCT * FROM Insegnamento AS I INNER JOIN LINGUA AS L ON I.nomeLingua = L.sigla " +
						   		" WHERE (? IS NULL OR I.nomeArea = ?) AND (? IS NULL OR I.nomeUniversita = ?) AND " +
						   		"(? IS NULL OR I.annoCorso = ?) AND (? IS NULL OR I.periodoErogazione = ?) AND (? IS NULL OR L.nome = ?)" ;

		// query facility
		ResultSetHandler<List<InsegnamentoBean>> h = new BeanListHandler<InsegnamentoBean>(InsegnamentoBean.class);
		
		// result model
		List<TeachingSearchRow> results = new ArrayList<TeachingSearchRow>();
		
		//query
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InsegnamentoBean> insList = null;
		try {
			pstmt = conn.prepareStatement(statement);
			int col = 1;
			pstmt.setString(col++, area);
			pstmt.setString(col++, area);
			pstmt.setString(col++, nomeUni);
			pstmt.setString(col++, nomeUni);
			pstmt.setObject(col++, anno, Types.SMALLINT);
			pstmt.setObject(col++, anno, Types.SMALLINT);
			pstmt.setObject(col++, periodo, Types.SMALLINT);
			pstmt.setObject(col++, periodo, Types.SMALLINT);
			pstmt.setString(col++, lingua);
			pstmt.setString(col++, lingua);
			rs = pstmt.executeQuery(); // execute query
			insList = h.handle(rs); // load results to insList
			
		} finally {
			DbUtils.close(pstmt); // close the statement (*always*)
			DbUtils.close(rs); // close the result set (*always*)
		}

		for(int i=0;i<insList.size();i++)
		{
			results.add(getInsegnamentoID(conn, Integer.toString(insList.get(i).getId())));
		}
		
		return results ;
	}
	
	/**
	 * Return a class (TeachingSearchRow), for Search MODEL.
	 * 
	 * @param conn A connection to the database.
	 * @param ID The id of the class to search.
	 * @return A TeachingSearchRow (class) which have the searched id.
	 * @throws SQLException If an error occurs.
	 */
	public static TeachingSearchRow getInsegnamentoID(Connection conn, String ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM Insegnamento WHERE ID = CAST (? AS INTEGER) ";
		final String statement2 = "SELECT P.Nome, P.Cognome FROM Professore AS P "
									+ "INNER JOIN Svolgimento AS S ON P.ID = S.IdProfessore "
									+ "WHERE S.IdInsegnamento = CAST (? AS INTEGER) ";
		
		InsegnamentoBean insegnamento = new InsegnamentoBean();
		List<ProfessoreBean> professori = null;

		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<InsegnamentoBean> h1 = new BeanHandler<InsegnamentoBean>(InsegnamentoBean.class);
		insegnamento = run.query(conn, statement1, h1, ID);
		
		if (insegnamento == null)
			throw new SQLException("Class id not found.");
		
		// Gets the profs
		ResultSetHandler<List<ProfessoreBean>> h2 = 
				new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		professori = run.query(conn, statement2, h2, ID);
		
		// Returns the results
		return new TeachingSearchRow(insegnamento, professori);
	}	

		
	
	/**
	 * Search method, return a class (Teaching), with corresponding list
	 * of professors, languages, evaluations, used for Evaluation MODEL.
	 * 
	 * @param conn A connection to the database.
	 * @param ID The Id of the class.
	 * @return The Teaching searched for.
	 * @throws SQLException If an error occurs.
	 */
	public static Teaching getInsegnamento(Connection conn, int ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM Insegnamento WHERE ID = CAST (? AS INTEGER)";
		final String statement2 = "SELECT * FROM Lingua WHERE Sigla = ?";
		final String statement3 = "SELECT * FROM ValutazioneInsegnamento WHERE IdInsegnamento = CAST (? AS INTEGER) ORDER BY datainserimento DESC";
		final String statement4 = "SELECT P.Nome, P.Cognome FROM Professore AS P "
									+ "INNER JOIN Svolgimento AS S ON P.ID = S.IdProfessore "
									+ "WHERE S.IdInsegnamento = CAST (? AS INTEGER)";
		
		InsegnamentoBean insegnamento = new InsegnamentoBean();
		LinguaBean lingua = new LinguaBean();
		List<ValutazioneInsegnamentoBean> listaValutazioni = null;
		List<ProfessoreBean> professori = null;

		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<InsegnamentoBean> h1 = new BeanHandler<InsegnamentoBean>(InsegnamentoBean.class);
		insegnamento = run.query(conn, statement1, h1, ID);
		
		if (insegnamento == null)
			throw new SQLException("Class id not found.");
		
		// Gets the language
		ResultSetHandler<LinguaBean> h2 = new BeanHandler<LinguaBean>(LinguaBean.class);
		lingua = run.query(conn, statement2, h2, insegnamento.getNomeLingua());
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneInsegnamentoBean>> h3 = 
				new BeanListHandler<ValutazioneInsegnamentoBean>(ValutazioneInsegnamentoBean.class);
		listaValutazioni = run.query(conn, statement3, h3, ID);
		
		// Gets the profs
		ResultSetHandler<List<ProfessoreBean>> h4 = 
				new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		professori = run.query(conn, statement4, h4, ID);
		
		// Returns the results
		return new Teaching(insegnamento, listaValutazioni, professori, lingua);
	}
	
	/**
	 * Update the "Insegnamento" fields.
	 * 
	 * @param con A connection to the database.
	 * @param insegnamentoBean A bean of "Insegnamento".
	 * @return The numbers of classes updated.
	 * @throws SQLException
	 */
	public static int updateInsegnamento(Connection con, InsegnamentoBean insegnamentoBean) throws SQLException
	{
//		UPDATE Insegnamento SET nome='Analisi 2', crediti=12, NomeUniversita='Imperial College London', periodoerogazione=2, stato='NOT VERIFIED',annocorso=2,nomearea='Mathematics',nomelingua='eng' where id=1;
		StringBuilder sql = new StringBuilder()
			.append("UPDATE Insegnamento SET Nome = ?, Crediti = ?, NomeUniversita= ?,")
			.append("PeriodoErogazione = ?, Stato = 'NOT VERIFIED', AnnoCorso = ?, NomeArea = ?, NomeLingua = ? WHERE Id = ?;");
		
		QueryRunner run = new QueryRunner();
//		ResultSetHandler<InsegnamentoBean> rsh = new BeanHandler(InsegnamentoBean.class);
		
		return run.update(con, sql.toString(), 
				insegnamentoBean.getNome(),
				insegnamentoBean.getCrediti(), 
				insegnamentoBean.getNomeUniversita(),
				insegnamentoBean.getPeriodoErogazione(),
				insegnamentoBean.getAnnoCorso(),
				insegnamentoBean.getNomeArea(),
				insegnamentoBean.getNomeLingua(),
				insegnamentoBean.getId());		
	}
	
	/**
	 * Delete an "Insegnamento" (Teaching) from the database.
	 * @param conn A connection to the database
	 * @param id The id of the teaching to delete.
	 * @return The number of rows affected: zero means an id that do not correspond to any teaching.
	 * @throws SQLException If an error occurs.
	 */
	public static int deleteInsegnamento(Connection conn, int id) throws SQLException
	{
		final String statement = "DELETE FROM Insegnamento WHERE Id = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, id);
	}
	
	/**
	 * Change class status.
	 * 
	 * @param con A connection to the database
	 * @param id The id of class to update.
	 * @throws SQLException if an error occurs. 
	 */
	public static void changeClassStatus(Connection con, String status, int id) throws SQLException 
	{
		final String sql = "UPDATE Insegnamento SET stato = CAST(? AS STATO) WHERE id = ?;";
		PreparedStatement pstmt = null;
		
		pstmt = con.prepareStatement(sql);
		
		try
		{
			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			
			pstmt.execute();
			
		} 
		finally {
			DbUtils.close(pstmt); // close the statement (*always*)
		}
	}
}
