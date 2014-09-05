package it.unipd.dei.bding.erasmusadvisor.database;


import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Flow;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowSearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;

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
 * Database operations about "Flusso". 
 * @author Luca, Alessandro
 *
 */
/**
 * @author Nicola
 *
 */
public class FlussoDatabase 
{

	/**
	 * Executes a statement to store a new "Flusso" into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database.
	 * @param flusso The "Flusso" to be stored.
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the "Flusso".
	 */
	public static void createFlusso(Connection con, FlussoBean flusso)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Flusso (id, destinazione, respFlusso, "
				+ "postiDisponibili, attivo, dataUltimaModifica, durata, dettagli) "
				+ "VALUES (?, ?, ?, ?, TRUE, CURRENT_DATE, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, flusso.getId());
			pstmt.setString(2, flusso.getDestinazione());
			pstmt.setString(3, flusso.getRespFlusso());
			pstmt.setInt(4, flusso.getPostiDisponibili());
			//pstmt.setBoolean(x, flusso.isAttivo());
			//pstmt.setDate(x, flusso.getDataUltimaModifica());
			pstmt.setInt(5, flusso.getDurata());
			pstmt.setString(6, flusso.getDettagli());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	
	/**
	 * Gets a flow, searched by ID, with the corresponding 
	 * list of professors, courses, certificates, evaluations.
	 * 
	 * @param conn the connection.
	 * @param ID The Flow ID.
	 * @return A Flow with the corresponding list of professors, courses, certificates, evaluations.
	 * @throws SQLException If an error occurs.
	 */
	public static Flow getFlusso(Connection conn, String ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM Flusso WHERE ID = ?";
		final String statement2 = "SELECT * FROM ValutazioneFlusso WHERE IdFlusso = ?";
		final String statement3 = "SELECT * FROM ResponsabileFlusso WHERE NomeUtente = ?";
		final String statement4 = "SELECT C.Id, C.Nome, C.Livello, C.NomeUniversita FROM CorsoDiLaurea AS C "
				+ "INNER JOIN Origine AS O ON C.Id = o.idcorso WHERE O.idflusso = ?";
		final String statement5 = "SELECT C.nomelingua, C.livello FROM Certificatilinguistici AS C "
				+ "INNER JOIN documentazione AS D ON C.nomelingua = d.nomecertificato AND c.livello = d.livellocertificato "
				+ "WHERE d.idflusso = ?";
		
		FlussoBean flow = new FlussoBean();
		ResponsabileFlussoBean responsabile = new ResponsabileFlussoBean();
		List<CorsoDiLaureaBean> corsiOrigine = null;
		List<CertificatiLinguisticiBean> certificati = null;
		List<ValutazioneFlussoBean> listaValutazioni = null;

		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<FlussoBean> h1 = new BeanHandler<FlussoBean>(FlussoBean.class);
		flow = run.query(conn, statement1, h1, ID);
		
		if (flow == null)
		{
			return null;
		}

		// Gets the evaluations
		ResultSetHandler<List<ValutazioneFlussoBean>> h2 = new BeanListHandler<ValutazioneFlussoBean>(ValutazioneFlussoBean.class);
		listaValutazioni = run.query(conn, statement2, h2, ID);
		
		// Gets the manager
		ResultSetHandler<ResponsabileFlussoBean> h3 = new BeanHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		responsabile = run.query(conn, statement3, h3, flow.getRespFlusso());
		
		// Gets the courses
		ResultSetHandler<List<CorsoDiLaureaBean>> h4 = new BeanListHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		corsiOrigine = run.query(conn, statement4, h4, ID);
		
		// Gets the certificates
		ResultSetHandler<List<CertificatiLinguisticiBean>> h5 = new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
		certificati = run.query(conn, statement5, h5, ID);
		
		// Returns the results
		return new Flow(flow, responsabile, corsiOrigine, certificati, listaValutazioni);
	}
	
	
	/**
	 * Gets a flow, searched by ID and manager name, with the corresponding 
	 * list of professors, courses, certificates, evaluations.
	 * 
	 * @param conn the connection.
	 * @param ID The Flow ID.
	 * @param managerName manager username.
	 * @return A Flow with the corresponding list of professors, courses, certificates, evaluations.
	 * @throws SQLException If an error occurs.
	 */
	public static Flow getFlusso(Connection conn, String ID, String managerName) throws SQLException 
	{
		final String statement1 = "SELECT * FROM Flusso WHERE ID = ? AND respflusso = ?";
		final String statement2 = "SELECT * FROM ValutazioneFlusso WHERE IdFlusso = ?";
		final String statement3 = "SELECT * FROM ResponsabileFlusso WHERE NomeUtente = ?";
		final String statement4 = "SELECT C.Id, C.Nome, C.Livello, C.NomeUniversita FROM CorsoDiLaurea AS C "
				+ "INNER JOIN Origine AS O ON C.Id = o.idcorso WHERE O.idflusso = ?";
		final String statement5 = "SELECT C.nomelingua, C.livello FROM Certificatilinguistici AS C "
				+ "INNER JOIN documentazione AS D ON C.nomelingua = d.nomecertificato AND c.livello = d.livellocertificato "
				+ "WHERE d.idflusso = ?";
		
		FlussoBean flow = new FlussoBean();
		ResponsabileFlussoBean responsabile = new ResponsabileFlussoBean();
		List<CorsoDiLaureaBean> corsiOrigine = null;
		List<CertificatiLinguisticiBean> certificati = null;
		List<ValutazioneFlussoBean> listaValutazioni = null;

		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<FlussoBean> h1 = new BeanHandler<FlussoBean>(FlussoBean.class);
		flow = run.query(conn, statement1, h1, ID, managerName);
		
		if (flow == null)
		{
			return null;
		}

		// Gets the evaluations
		ResultSetHandler<List<ValutazioneFlussoBean>> h2 = new BeanListHandler<ValutazioneFlussoBean>(ValutazioneFlussoBean.class);
		listaValutazioni = run.query(conn, statement2, h2, ID);
		
		// Gets the manager
		ResultSetHandler<ResponsabileFlussoBean> h3 = new BeanHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		responsabile = run.query(conn, statement3, h3, flow.getRespFlusso());
		
		// Gets the courses
		ResultSetHandler<List<CorsoDiLaureaBean>> h4 = new BeanListHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		corsiOrigine = run.query(conn, statement4, h4, ID);
		
		// Gets the certificates
		ResultSetHandler<List<CertificatiLinguisticiBean>> h5 = new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
		certificati = run.query(conn, statement5, h5, ID);
		
		// Returns the results
		return new Flow(flow, responsabile, corsiOrigine, certificati, listaValutazioni);
	}
	
	/**
	 * Update the flow with the id given.
	 * 
	 * @param con A connection to the database.
	 * @param flusso Flow's bean.
	 * @param old_id Id of the flow to modify.
	 * @throws SQLException If an error occurs.
	 */
	public static void updateFlusso(Connection con, FlussoBean flusso, String old_id) throws SQLException
	{
		final StringBuilder sql = new StringBuilder()
			.append("UPDATE Flusso SET id = ?, destinazione = ?, respflusso = ?, postidisponibili = ?,")
			.append("attivo = ?, durata = ?, dettagli = ? WHERE id = ?;");
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql.toString(),
				flusso.getId(),
				flusso.getDestinazione(),
				flusso.getRespFlusso(),
				flusso.getPostiDisponibili(),
				flusso.isAttivo(),
				flusso.getDurata(),
				flusso.getDettagli(),
				old_id);
	}
	

	/**
	 * Delete a "Flusso" from the database.
	 * 
	 * @param conn A connection to the databas.
	 * @param id The id of the flow to delete.
	 * @return The number of rows affected: zero means an id that do not correspond to any flow.
	 * @throws SQLException If an error occurs.
	 */
	public static int deleteFlusso(Connection conn, String id) throws SQLException
	{
		final String statement = "DELETE FROM Flusso WHERE Id = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, id);
	}

	
/**
 * Flow Search with optional filter fields, 
 * limiting the results to the student's allowed destinations.
 * 
 * (null = optional)
 * 
 * @param conn The connection to the database, it will *not* be closed
 * @param utente (required) the name of the student
 * @param stato (optional) The country toward which the flow.
 * @param citta  (optional) The city toward which the flow.
 * @param durata  (optional) The durations of the Flow.
 * @param minPosti (optional) The minimum number of available seats.
 * @param nomeCertificato nomeCertificato (optional) The language's certificate required for the flow.
 * @param livelloCertificato Certificate's level (required only if nome Certificato is not null).
 * @return A list of FlowSearchRow
 * @throws SQLException In case of error.
 */
	public static List<FlowSearchRow> filterFlowBy(Connection conn, LoggedUser utente, 
			String stato, String citta, Integer durata, Integer minPosti, String nomeCertificato, 
			String livelloCertificato) throws SQLException 
	{
		if (utente.isFlowResp())
		{
			return filterByFlowResp(conn, utente, stato, citta, durata, minPosti, nomeCertificato, livelloCertificato);
		}
		
		
		/**
		 * SQL statement for getting the flow ID's with the specified conditions
		 */
		final String statement1 = "SELECT DISTINCT F.Id, "
				+ "F.PostiDisponibili, F.Durata, F.Destinazione FROM Flusso AS F "
				+ "JOIN Universita AS U ON F.Destinazione = U.Nome "
				+ "JOIN Documentazione AS D ON F.Id = D.IdFlusso "
				+ "JOIN Origine AS O ON F.Id = O.IdFlusso "
				+ "JOIN Iscrizione AS I ON O.IdCorso = I.IdCorso "
				+ "WHERE I.NomeUtenteStudente = ? " // Where student is allowed
				+ "AND (? IS NULL OR U.StatoCitta = ?) " // by StatoCitta (optional)
				+ "AND (? IS NULL OR U.NomeCitta = ?) " // by NomeCitta (optional)
				+ "AND (? IS NULL OR F.Durata = ?) " // by Durata (optional)
				+ "AND (? IS NULL OR F.PostiDisponibili >= ?) " // by min. PostiDisponibili (optional)
				+ "AND (? IS NULL OR (D.NomeCertificato = ? "
					+ "AND D.LivelloCertificato = ?)) " // by Certificato (optional)
				+ "AND F.Attivo = TRUE " // Only *active* flows
				+ "ORDER BY F.Id ASC";
		
		/**
		 * SQL statement for getting, for each flow, the other row fields
		 */
		final String statement2 = "SELECT nome, nomeCitta, statoCitta FROM Universita "
				+ "WHERE nome = ? ";

		final String statement3 = "SELECT NomeCertificato AS NomeLingua, LivelloCertificato AS Livello "
				+ "FROM Documentazione "
				+ "WHERE IdFlusso = ? "
				+ "ORDER BY NomeCertificato, LivelloCertificato ASC";
		
		// query facility
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<FlussoBean>> h = new BeanListHandler<FlussoBean>(FlussoBean.class);
		
		// result model
		List<FlowSearchRow> results = new ArrayList<FlowSearchRow>();
		
		// First query
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FlussoBean> flussoList = null;
		try {
			pstmt = conn.prepareStatement(statement1);
			int col = 1;
			pstmt.setString(col++, utente.getUser()); // required
			pstmt.setString(col++, stato);
			pstmt.setString(col++, stato);
			pstmt.setString(col++, citta);
			pstmt.setString(col++, citta);
			pstmt.setObject(col++, durata, Types.SMALLINT);
			pstmt.setObject(col++, durata, Types.SMALLINT);
			pstmt.setObject(col++, minPosti, Types.SMALLINT);
			pstmt.setObject(col++, minPosti, Types.SMALLINT);
			pstmt.setString(col++, nomeCertificato);
			pstmt.setString(col++, nomeCertificato);
			pstmt.setString(col++, livelloCertificato);
			rs = pstmt.executeQuery(); // execute query
			flussoList = h.handle(rs); // load results to flussoList
			
		} finally {
			DbUtils.close(pstmt); // close the statement (*always*)
			DbUtils.close(rs); // close the result set (*always*)
		}
		
		// Queries for other row fields
		for (FlussoBean f : flussoList) {
			// Gets the destination university of the flow
			ResultSetHandler<UniversitaBean> h1 = 
				new BeanHandler<UniversitaBean>(UniversitaBean.class);
			UniversitaBean universita = run.query(conn, statement2, h1, f.getDestinazione());
			
			// Gets the certifications for the flow
			ResultSetHandler<List<CertificatiLinguisticiBean>> h2 = 
					new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
			List<CertificatiLinguisticiBean> docList = run.query(conn, statement3, h2, f.getId());
			
			// adding one flow-result to the results list 
			FlowSearchRow resultRow = new FlowSearchRow(f, universita, docList);
			results.add(resultRow);
		}
		
		return results;
	}

	/**
	 * Flow Search with optional filter fields, 
	 * limiting the results to the flow's manager flows.
	 * 
	 * (null = optional)
	 * 
	 * @param conn The connection to the database, it will *not* be closed
	 * @param utente (required) The name of the user.
	 * @param stato (optional) The country toward which the flow.
	 * @param citta  (optional) The city toward which the flow.
	 * @param durata  (optional) The durations of the Flow.
	 * @param minPosti (optional) The minimum number of available seats.
	 * @param nomeCertificato nomeCertificato (optional) The language's certificate required for the flow.
	 * @param livelloCertificato Certificate's level (required only if nome Certificato is not null).
	 * @return A list of FlowSearchRow.
	 * @throws SQLException In case of error.
	 */
	private static List<FlowSearchRow> filterByFlowResp(Connection conn,
			LoggedUser utente, String stato, String citta, Integer durata,
			Integer minPosti, String nomeCertificato, String livelloCertificato) throws SQLException {
		
		
		/**
		 * SQL statement for getting the flow ID's with the specified conditions
		 */
		final String statement1 = "SELECT DISTINCT F.Id, "
				+ "F.PostiDisponibili, F.Durata, F.Destinazione FROM Flusso AS F "
				+ "JOIN Universita AS U ON F.Destinazione = U.Nome "
				+ "JOIN Documentazione AS D ON F.Id = D.IdFlusso "
				+ "JOIN Origine AS O ON F.Id = O.IdFlusso "
				+ "JOIN Iscrizione AS I ON O.IdCorso = I.IdCorso "
				+ "WHERE (? IS NULL OR U.StatoCitta = ?) " // by StatoCitta (optional)
				+ "AND (? IS NULL OR U.NomeCitta = ?) " // by NomeCitta (optional)
				+ "AND (? IS NULL OR F.Durata = ?) " // by Durata (optional)
				+ "AND (? IS NULL OR F.PostiDisponibili >= ?) " // by min. PostiDisponibili (optional)
				+ "AND (? IS NULL OR (D.NomeCertificato = ? "
					+ "AND D.LivelloCertificato = ?)) " // by Certificato (optional)
				+ "AND F.respFlusso = ? " 
				+ "ORDER BY F.Id ASC";
		
		/**
		 * SQL statement for getting, for each flow, the other row fields
		 */
		final String statement2 = "SELECT nome, nomeCitta, statoCitta FROM Universita "
				+ "WHERE nome = ? ";
	
		final String statement3 = "SELECT NomeCertificato AS NomeLingua, LivelloCertificato AS Livello "
				+ "FROM Documentazione "
				+ "WHERE IdFlusso = ? "
				+ "ORDER BY NomeCertificato, LivelloCertificato ASC";
		
		// query facility
		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<FlussoBean>> h = new BeanListHandler<FlussoBean>(FlussoBean.class);
		
		// result model
		List<FlowSearchRow> results = new ArrayList<FlowSearchRow>();
		
		// First query
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FlussoBean> flussoList = null;
		try {
			pstmt = conn.prepareStatement(statement1);
			int col = 1;
			pstmt.setString(col++, stato);
			pstmt.setString(col++, stato);
			pstmt.setString(col++, citta);
			pstmt.setString(col++, citta);
			pstmt.setObject(col++, durata, Types.SMALLINT);
			pstmt.setObject(col++, durata, Types.SMALLINT);
			pstmt.setObject(col++, minPosti, Types.SMALLINT);
			pstmt.setObject(col++, minPosti, Types.SMALLINT);
			pstmt.setString(col++, nomeCertificato);
			pstmt.setString(col++, nomeCertificato);
			pstmt.setString(col++, livelloCertificato);
			pstmt.setString(col++, utente.getUser());
			rs = pstmt.executeQuery(); // execute query
			flussoList = h.handle(rs); // load results to flussoList
			
		} finally {
			DbUtils.close(pstmt); // close the statement (*always*)
			DbUtils.close(rs); // close the result set (*always*)
		}
		
		// Queries for other row fields
		for (FlussoBean f : flussoList) {
			// Gets the destination university of the flow
			ResultSetHandler<UniversitaBean> h1 = 
				new BeanHandler<UniversitaBean>(UniversitaBean.class);
			UniversitaBean universita = run.query(conn, statement2, h1, f.getDestinazione());
			
			// Gets the certifications for the flow
			ResultSetHandler<List<CertificatiLinguisticiBean>> h2 = 
					new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
			List<CertificatiLinguisticiBean> docList = run.query(conn, statement3, h2, f.getId());
			
			// adding one flow-result to the results list 
			FlowSearchRow resultRow = new FlowSearchRow(f, universita, docList);
			results.add(resultRow);
		}
		
		return results;
	}
}
