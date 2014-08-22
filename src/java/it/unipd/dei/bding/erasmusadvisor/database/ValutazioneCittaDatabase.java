package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.servlets.AbstractDatabaseServlet;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.City;

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
 * Database operations about ValutazioneCitta 
 * @author Maurp
 *
 */
public class ValutazioneCittaDatabase {
	
	/**
	 * Insert a new student's city evaluation into the Database
	 * @param con database connection
	 * @param val student's city evaluation
	 * @throws SQLException
	 */
	public static void createValutazioneCitta(Connection con, ValutazioneCittaBean val) 
		throws SQLException {
		final String statement = "INSERT INTO ValutazioneCitta (nomeutentestudente, nomecitta, statocitta, costodellavita, disponibilitaalloggi, vivibilitaurbana, vitasociale, datainserimento, commento)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, DEFAULT, ?);";

		
		ResultSetHandler<ValutazioneCittaBean> rsh = new BeanHandler<ValutazioneCittaBean>(ValutazioneCittaBean.class);
		QueryRunner run = new QueryRunner();
		
		run.insert(con, statement, rsh, 
				val.getNomeUtenteStudente(),
				val.getNomeCitta(),
				val.getStatoCitta(),
				val.getCostoDellaVita(),
				val.getDisponibilitaAlloggi(),
				val.getVivibilitaUrbana(),
				val.getVitaSociale(),
				val.getCommento()
				);
	}
	
	

}
