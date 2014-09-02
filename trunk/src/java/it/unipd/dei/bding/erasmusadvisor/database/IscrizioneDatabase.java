package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Handle all the manipulations to the "Iscrizione" relation.
 * 
 * @author mauro
 *
 */
public class IscrizioneDatabase {

	/**
	 * Insert a new instance into the table "Iscrizione".
	 * 
	 * @param con A connection to the database.
	 * @param subscription A bean representing the instance of "Iscrizione".
	 * @return True if all is ok, false if there is a subscription overlap.
	 * @throws SQLException If an error occurs while generating a new "Iscrizione".
	 */
	public static void createIscrizione(Connection con, IscrizioneBean subscription) throws SQLException 
	{
		StringBuilder sql = new StringBuilder()
		.append("INSERT INTO Iscrizione (idcorso, nomeutentestudente, annoinizio, annofine ) ")
		.append("VALUES (?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<IscrizioneBean> rsh = new BeanHandler<IscrizioneBean>(IscrizioneBean.class);
		
		run.insert(con, sql.toString(), rsh, subscription.getIdCorso(), subscription.getNomeUtenteStudente(), 
				subscription.getAnnoInizio(), subscription.getAnnoFine());
	}

	/**
	 * Method used for updating a subscription.
	 * 
	 * @param con connection to the database 
	 * @param subscription IscrizioneBean object representing the subscription
	 * @throws SQLException
	 */
	public static void updateIscrizione(Connection con, IscrizioneBean subscription) throws SQLException {
		final String delete = "DELETE FROM Iscrizione WHERE NomeUtenteStudente = ? AND idCorso = ?;";
		
		StringBuilder insert = new StringBuilder()
		.append("INSERT INTO Iscrizione (idcorso, nomeutentestudente, annoinizio, annofine ) ")
		.append("VALUES (?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<IscrizioneBean> rsh = new BeanHandler<IscrizioneBean>(IscrizioneBean.class);
		
		// delete the old subscription
		run.update(con, delete, subscription.getNomeUtenteStudente(), subscription.getIdCorso());
		
		// insert the new subscription
		run.insert(con, insert.toString(), rsh, subscription.getIdCorso(), subscription.getNomeUtenteStudente(), 
				subscription.getAnnoInizio(), subscription.getAnnoFine());
	}
	
	
	/**
	 * Method used for updating a subscription.
	 * It removes the previous subscription and creates a new one
	 * 
	 * 
	 * @param con connection to the database 
	 * @param subscription IscrizioneBean object representing the subscription
	 * @param old_id previous course id
	 * @throws SQLException
	 */
	public static void updateIscrizione(Connection con, IscrizioneBean subscription, int old_id) throws SQLException {
		final String delete = "DELETE FROM Iscrizione WHERE NomeUtenteStudente = ? AND idCorso = ?;";
		
		StringBuilder insert = new StringBuilder()
		.append("INSERT INTO Iscrizione (idcorso, nomeutentestudente, annoinizio, annofine ) ")
		.append("VALUES (?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<IscrizioneBean> rsh = new BeanHandler<IscrizioneBean>(IscrizioneBean.class);
		
		// delete the old subscription
		run.update(con, delete, subscription.getNomeUtenteStudente(), old_id);
		
		// insert the new subscription
		run.insert(con, insert.toString(), rsh, subscription.getIdCorso(), subscription.getNomeUtenteStudente(), 
				subscription.getAnnoInizio(), subscription.getAnnoFine());
	}

}
