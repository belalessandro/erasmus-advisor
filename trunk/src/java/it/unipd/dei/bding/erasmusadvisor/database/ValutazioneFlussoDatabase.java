package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneFlussoBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class ValutazioneFlussoDatabase 
{
	/**
	 * Insert a new flow evaluation into the database.
	 * @param con connection to the database
	 * @param val evaluation
	 * @throws SQLException 
	 */
	public static void creaValutazioneFlusso(Connection con, ValutazioneFlussoBean val) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
			.append("INSERT INTO ValutazioneFlusso (NomeUtenteStudente, IdFlusso, SoddEsperienza, SoddaccAdemica, Didattica, ValutazioneResponsabile, Commento) ")
			.append("VALUES (?, ?, ?, ?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ValutazioneFlussoBean> rsh = new BeanHandler(ValutazioneFlussoBean.class);
		
		run.insert(con, sql.toString(), rsh,
				val.getNomeUtenteStudente(),
				val.getIdFlusso(),
				val.getSoddEsperienza(),
				val.getSoddAccademica(),
				val.getDidattica(),
				val.getValutazioneResponsabile(),
				val.getCommento());
	}

}
