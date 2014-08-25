package it.unipd.dei.bding.erasmusadvisor.database;


import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Flow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Flusso 
 * @author Luca
 *
 */
public class FlussoDatabase 
{

	/**
	 * Executes a statement to store a new Flusso into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param flusso The Flusso to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Flusso.
	 */
	public static void createFlusso(Connection con, FlussoBean flusso)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String insertStmt = "INSERT INTO Flusso (id, destinazione, respFlusso, "
				+ "postiDisponibili, attivo, dataUltimaModifica, durata, dettagli) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertStmt);
			pstmt.setString(1, flusso.getId());
			pstmt.setString(2, flusso.getDestinazione());
			pstmt.setString(3, flusso.getRespFlusso());
			pstmt.setInt(4, flusso.getPostiDisponibili());
			pstmt.setBoolean(5, flusso.isAttivo());
			pstmt.setDate(6, flusso.getDataUltimaModifica());
			pstmt.setInt(7, flusso.getDurata());
			pstmt.setString(8, flusso.getDettagli());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
		}
	}
	
	public static Flow getFlusso(DataSource ds, String ID)
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

		QueryRunner run = new QueryRunner(ds);
		
		ResultSetHandler<FlussoBean> h1 = new BeanHandler<FlussoBean>(FlussoBean.class);
		flow = run.query(statement1, h1, ID);
		
		if (flow == null)
		{
			return null;
		}

		// Gets the evaluations
		ResultSetHandler<List<ValutazioneFlussoBean>> h2 = new BeanListHandler<ValutazioneFlussoBean>(ValutazioneFlussoBean.class);
		listaValutazioni = run.query(statement2, h2, ID);
		
		// Gets the manager
		ResultSetHandler<ResponsabileFlussoBean> h3 = new BeanHandler<ResponsabileFlussoBean>(ResponsabileFlussoBean.class);
		responsabile = run.query(statement3, h3, flow.getRespFlusso());
		
		// Gets the courses
		ResultSetHandler<List<CorsoDiLaureaBean>> h4 = new BeanListHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		corsiOrigine = run.query(statement4, h4, ID);
		
		// Gets the certificates
		ResultSetHandler<List<CertificatiLinguisticiBean>> h5 = new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
		certificati = run.query(statement5, h5, ID);
		
		// Returns the results
		return new Flow(flow, responsabile, corsiOrigine, certificati, listaValutazioni);
	}
	
	/**
	 * Update the flow with the id given.
	 * 
	 * @param con connection to the database
	 * @param flusso flow bean
	 * @param old_id id of the flow to modify
	 * @throws SQLException 
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

}
