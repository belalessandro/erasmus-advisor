package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Message;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Handle all the manipulations to the Iscrizione relation
 * 
 * @author mauro
 *
 */
public class IscrizioneDatabase {

	/**
	 * Insert a new instance into the table Iscrizione
	 * 
	 * @param con connection to the database
	 * @param iscrizione bean representing the instance
//	 * @return true if all is ok, false if there is a subscription overlap
	 * @return 
	 * @throws SQLException
	 */
	public static void createIscrizione(Connection con, IscrizioneBean iscrizione) throws SQLException 
	{
		StringBuilder sql = new StringBuilder()
		.append("INSERT INTO Iscrizione (idcorso, nomeutentestudente, annoinizio, annofine ) ")
		.append("VALUES (?, ?, ?, ?);");
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<IscrizioneBean> rsh = new BeanHandler<IscrizioneBean>(IscrizioneBean.class);
		
//		try {
			run.insert(con, sql.toString(), rsh, iscrizione.getIdCorso(), iscrizione.getNomeUtenteStudente(), 
					iscrizione.getAnnoInizio(), iscrizione.getAnnoFine());
			
//			return true;
//		} catch(SQLException e) {
			// unique violation -> not insert
//			if(e.getSQLState().equals("23505"))
//				return true;
//			else
//				throw new SQLException(e);
			
//		}
		
		
		
	}

}
