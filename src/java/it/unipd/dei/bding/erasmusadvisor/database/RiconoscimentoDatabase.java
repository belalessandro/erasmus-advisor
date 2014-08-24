package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class RiconoscimentoDatabase 
{
	/**
	 * Returns all classes validated by the flow manager.
	 * @param conn connection to the database
	 * @param ID flow id
	 * @return all classes validated as a list of InsegnamentoBean
	 * @throws SQLException
	 */
	public static List<InsegnamentoBean> getInsegnamentiRiconosciuti(Connection conn, String ID)
			throws SQLException 
	{
		final String statement = "SELECT * FROM Riconoscimento AS R INNER JOIN Insegnamento AS I ON I.id = R.idinsegnamento WHERE idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		// Gets the profs
		ResultSetHandler<List<InsegnamentoBean>> h = new BeanListHandler<InsegnamentoBean>(InsegnamentoBean.class);
		return run.query(conn, statement, h, ID);
	}

	/**
	 * Delete Riconoscimento's instances with the flow id given.
	 * @param con connection to the database
	 * @param flowId the flow id given
	 * @return number of instances deleted
	 * @throws SQLException
	 */
	public static int deleteRiconoscimentoByFlowId(Connection con, String flowId) throws SQLException 
	{
		final String sql = "DELETE FROM Riconoscimento WHERE IdFlusso = ?;";
		
		QueryRunner run = new QueryRunner();
		
		return run.update(con, sql);
	}

}