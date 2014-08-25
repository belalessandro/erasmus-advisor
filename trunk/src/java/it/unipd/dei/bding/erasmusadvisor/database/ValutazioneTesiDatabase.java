package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * 
 * @author mauro
 *
 */
public class ValutazioneTesiDatabase 
{
	/**
	 * Insert a new ValutazioneTesi instance into the database. 
	 * @param con connection to the database
	 * @param val student's evaluation
	 * @throws SQLException 
	 */
	public static void createValutazioneTesi(Connection con, ValutazioneTesiBean val) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("INSERT INTO ValutazioneTesi (NomeUtenteStudente, idargomentotesi, impegnonecessario, interesseargomento, diponibilitarelatore, soddisfazione, Commento) ")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<ValutazioneTesiBean> rsh = new BeanHandler<ValutazioneTesiBean>(ValutazioneTesiBean.class);
		run.insert(con, sql.toString(), rsh,
				val.getNomeUtenteStudente(),
				val.getIdArgomentoTesi(),
				val.getImpegnoNecessario(),
				val.getInteresseArgomento(),
				val.getDisponibilitaRelatore(),
				val.getSoddisfazione(),
				val.getCommento());

	}
	

}
