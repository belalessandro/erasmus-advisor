package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Represents an University evaluation in the Database
 * 
 * @author mauro
 *
 */
public class ValutazioneUniversitaDatabase 
{

	/**
	 * Inserts a new university evalutation into the database.
	 *  
	 * @param con connection to the database
	 * @param val university evalutation
	 * @throws SQLException
	 */
	public static void createValutazioneUniversita(Connection con, ValutazioneUniversitaBean val) throws SQLException 
	{
		final StringBuilder sql = new StringBuilder()
		.append("INSERT INTO ValutazioneUniversita (NomeUtenteStudente, NomeUniversita, CollocazioneUrbana, IniziativeErasmus, QtaInsegnamenti, QtaAule, Commento) ")
		.append("VALUES (?, ?, ?, ?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<ValutazioneUniversitaBean> rsh = new BeanHandler<ValutazioneUniversitaBean>(ValutazioneUniversitaBean.class);
		
		run.insert(con, sql.toString(), rsh, 
				val.getNomeUtenteStudente(),
				val.getNomeUniversita(),
				val.getCollocazioneUrbana(),
				val.getIniziativeErasmus(),
				val.getQtaInsegnamenti(),
				val.getQtaAule(),
				val.getCommento());
	}

}
