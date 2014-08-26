package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Database operations about ValutazioneCitta 
 * @author Maurp
 *
 */
public class ValutazioneInsegnamentoDatabase 
{
	public static void creaValutazioneInsegnamento(Connection con, ValutazioneInsegnamentoBean val)
		throws SQLException
	{
		StringBuilder builder = new StringBuilder()
			.append("INSERT INTO ValutazioneInsegnamento (nomeutentestudente,idinsegnamento,qtainsegnamanto,interesse,difficolta,rispettodelleore,datainserimento,commento)")
			.append("VALUES (?, ?, ?, ?, ?, ?, default, ?);");
		
		ResultSetHandler<ValutazioneInsegnamentoBean> rsh = new BeanHandler<ValutazioneInsegnamentoBean>(ValutazioneInsegnamentoBean.class);
		QueryRunner run = new QueryRunner();
		
		run.insert(con, builder.toString(), rsh, 
				val.getNomeUtenteStudente(),
				val.getIdInsegnamento(),
				val.getQtaInsegnamanto(),
				val.getInteresse(),
				val.getDifficolta(),
				val.getRispettoDelleOre(),
				val.getCommento());
	}
}
