/**
 * 
 */
package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Universita 
 * @author Alessandro
 *
 */
public class UniversitaDatabase {
//	/**
//	 * The Data Source to the database
//	 */
//	private final DataSource ds = null;

	/**
	 * 
	 */
	public University searchUniversityModelByName(DataSource ds, String byName) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
//		String statement = "SELECT U.Link, U.PosizioneClassifica, U.PresenzaAlloggi, "
//				+ "U.NomeCitta, U.StatoCitta, V.NomeUtenteStudente,"
//				+ "V.DataInserimento, V.Commento, V.CollocazioneUrbana, "
//				+ "V.IniziativeErasmus, V.QtaInsegnamenti, V.QtaAule"
//				+ "FROM Universita AS U "
//				+ "INNER JOIN ValutazioneUniversita AS V ON U.Nome = V.NomeUniversita"
//				+ "WHERE U.Nome = ?";
		
		String statement1 = "SELECT U.Link, U.PosizioneClassifica, U.PresenzaAlloggi, "
				+ "U.NomeCitta, U.StatoCitta"
				+ "FROM Universita AS U "
				+ "WHERE U.Nome = ?";
		
		String statement2 = "SELECT V.NomeUtenteStudente,"
				+ "V.DataInserimento, V.Commento, V.CollocazioneUrbana, "
				+ "V.IniziativeErasmus, V.QtaInsegnamenti, V.QtaAule"
				+ "FROM Universita AS U "
				+ "INNER JOIN ValutazioneUniversita AS V ON U.Nome = V.NomeUniversita"
				+ "WHERE U.Nome = ?";


		// Entity Bean
		UniversitaBean uni = new UniversitaBean();
		List<ValutazioneUniversitaBean> valList = null;
		
		QueryRunner run = new QueryRunner(ds);
		
		// Gets the university
		ResultSetHandler<UniversitaBean> h = new BeanHandler<UniversitaBean>(UniversitaBean.class);
		uni = run.query(statement1, h, byName); 
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneUniversitaBean>> h2 = 
				new BeanListHandler<ValutazioneUniversitaBean>(ValutazioneUniversitaBean.class);
		valList = run.query(statement2, h2, byName);

		// Returns the results through the university model
		return new University(uni, valList);
	}
}
