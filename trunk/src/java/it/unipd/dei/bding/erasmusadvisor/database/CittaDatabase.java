package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
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
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Citta 
 * @author Luca
 *
 */

public class CittaDatabase {
	public static void createCitta(Connection con, CittaBean citta)
			throws SQLException {
		
		final String statement = "INSERT INTO Citta (nome, stato) VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(statement);
			pstmt.setString(1, citta.getNome());
			pstmt.setString(2, citta.getStato());
			pstmt.execute();
		} finally {
			DbUtils.closeQuietly(pstmt);
			//System.out.println("PreparedStatement closed");
		}
	}
	
	
	public City searchCityByName(DataSource ds, String name, String country) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		
		final String statement1 = "SELECT L.nome FROM Lingua AS L INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
/*		final String statement2 = "SELECT V.NomeUtenteStudente,"
				+ "V.DataInserimento, V.Commento, V.CostoDellaVita, "
				+ "V.DisponibilitaAlloggi, V.VivibilitaUrbana, V.VitaSociale "
				+ "FROM Citta AS C "
				+ "INNER JOIN ValutazioneCitta AS V ON C.Nome = V.NomeCitta AND C.Stato = V.StatoCitta "
				+ "WHERE C.Nome = ? AND C.Stato = ?";*/
		
		final String statement2 = "SELECT * FROM ValutazioneCitta WHERE NomeCitta = ? AND StatoCitta = ?";


		// Entity Bean
		CittaBean citta = new CittaBean();
		List<ValutazioneCittaBean> valList = null;
		List<LinguaBean> lingue = null;
		
		citta.setNome(name);
		citta.setStato(country);
		
		QueryRunner run = new QueryRunner(ds);
		
		// Gets the languages
		ResultSetHandler<List<LinguaBean>> h1 = 
				new BeanListHandler<LinguaBean>(LinguaBean.class);
		lingue = run.query(statement1, h1, name, country);
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneCittaBean>> h2 = 
				new BeanListHandler<ValutazioneCittaBean>(ValutazioneCittaBean.class);
		valList = run.query(statement2, h2, name, country);
		
		// Returns the results
		return new City(citta, valList, lingue);
	}
}
