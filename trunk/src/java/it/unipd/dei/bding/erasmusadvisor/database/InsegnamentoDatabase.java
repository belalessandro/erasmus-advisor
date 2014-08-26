package it.unipd.dei.bding.erasmusadvisor.database;


import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Teaching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Insegnamento 
 * @author Luca
 *
 */
public class InsegnamentoDatabase 
{
	
	/**
	 * Executes a statement to store a new Insegnamento into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param insegnamento The Insegnamento to be stored
	 * @return the generated id
	 * @throws SQLException
	 *             if any error occurs while storing the Insegnamento.
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
	
	public static Teaching getInsegnamento(Connection conn, int ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM Insegnamento WHERE ID = ?";
		final String statement2 = "SELECT * FROM Lingua WHERE Sigla = ?";
		final String statement3 = "SELECT * FROM ValutazioneInsegnamento WHERE IdInsegnamento = ?";
		final String statement4 = "SELECT P.Nome, P.Cognome FROM Professore AS P "
									+ "INNER JOIN Svolgimento AS S ON P.ID = S.IdProfessore "
									+ "WHERE S.IdInsegnamento = ?";
		
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

}
