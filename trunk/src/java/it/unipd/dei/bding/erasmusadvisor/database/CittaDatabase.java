package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.City;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Citta 
 * @author Luca, Alessandro
 *
 */


public class CittaDatabase 
{
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

	public City searchCityByName(Connection conn, String name, String country) throws SQLException 
	{
		/**
		 * The SQL statements to be executed
		 */
		
		final String statement = "SELECT Nome, Stato FROM Citta WHERE Nome = ? AND Stato = ?";
		
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
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<CittaBean> h = new BeanHandler<CittaBean>(CittaBean.class);
		citta = run.query(conn, statement, h, name, country);
		
		if (citta == null)
			throw new SQLException("City not found.");
		
		// Gets the languages
		ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
		lingue = run.query(conn, statement1, h1, name, country);
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneCittaBean>> h2 = new BeanListHandler<ValutazioneCittaBean>(ValutazioneCittaBean.class);
		valList = run.query(conn, statement2, h2, name, country);
		
		// Returns the results
		return new City(citta, valList, lingue);
	}
	
	/**
	* Delete a city
	* @return the number of rows affected	
	*/
	public static int deleteCity(Connection conn, String name, String country) throws SQLException 
	{
		final String statement = "DELETE From Citta WHERE Nome = ? AND Stato = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, name, country);
		
	}
	
	/**
	 * Function to update city fields and relative languages.
	 * @param conn database connection
	 * @param new_name new name of the city
	 * @param new_country new city country
	 * @param old_name old name of the city
	 * @param old_country old city country
	 * @param linguaCittaBeanList list of bean with new languages
	 * @return the number of rows updated of relation Citta
	 * @throws SQLException
	 */
	public int editCity(Connection conn, String new_name, String new_country,
			String old_name, String old_country, List<LinguaCittaBean> linguaCittaBeanList) throws SQLException
	{
		// required variables
		QueryRunner run = new QueryRunner();
		ResultSetHandler<LinguaCittaBean> rsh = new BeanHandler<LinguaCittaBean>(LinguaCittaBean.class);
		
		// first of all delete all old "LinguaCitta" (referred to the old city)
		final String stmt_deleteLinguaCitta = "DELETE FROM LinguaCitta WHERE nomeCitta = ? AND statoCitta = ?";
		run.update(conn, stmt_deleteLinguaCitta, old_name, old_country);

		// second update the city
		final String stmt_updateCity = "UPDATE citta SET nome = ?, stato = ? WHERE nome = ? AND stato = ?";
		int ok = run.update(conn, stmt_updateCity, new_name, new_country, old_name, old_country);
		
		// finally inserting new LinguaCitta
		final String stmt_insertLinguaCitta = "INSERT INTO LinguaCitta (SiglaLingua, NomeCitta, StatoCitta) VALUES (?, ?, ?)";
		for(LinguaCittaBean linguaCittaBean : linguaCittaBeanList)
			run.insert(conn, stmt_insertLinguaCitta, rsh, 
					linguaCittaBean.getSiglaLingua(),
					linguaCittaBean.getNomeCitta(),
					linguaCittaBean.getStatoCitta());

		return ok;
	}
	
	public static List<CittaBean> getAllSortByCountry(Connection conn) throws SQLException 
	{
		final String statement = "SELECT * FROM Citta ORDER BY stato ASC";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		return run.query(conn, statement, h);
	}
	

	/**
	 * City search by language
	 * 
	 * @param conn The connection to the database, it will *not* be closed
	 * @param siglaLingua the language abbreviation to filter by 
	 * @return a list of CitySearchModel
	 * @throws SQLException in case of error
	 */
	public static List<CitySearchModel> filterCityBySiglaLingua(Connection conn, String siglaLingua) throws SQLException 
	{
		/**
		 * SQL statement for getting the cities with the language specified   
		 */
		final String statement1 = "SELECT NomeCitta AS nome, StatoCitta AS stato "
				+ "FROM LinguaCitta "
				+ "WHERE SiglaLingua = ? "
				+ "ORDER BY stato, nome ASC";
		
		/**
		 * SQL statement for getting, for each city, its languages 
		 */
		final String statement2 = "SELECT L.nome FROM Lingua AS L "
				+ "INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua "
				+ "WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		// query facility
		QueryRunner run = new QueryRunner();
		
		// result model
		List<CitySearchModel> results = new ArrayList<CitySearchModel>();
		
		// First query
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		List<CittaBean> cittaList = run.query(conn, statement1, h, siglaLingua);		
				
		// Queries for the languages of each city
		for (CittaBean c : cittaList) {
			// Gets the languages for the city
			ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
			List<LinguaBean> lingueList = run.query(conn, statement2, h1, c.getNome(), c.getStato());
			
			// adding one city-result to the results list 
			CitySearchModel resultRow = new CitySearchModel(c, lingueList);
			results.add(resultRow);
		}
		
		return results;
	}
	
	
	/**
	 * City search by country
	 * 
	 * @param conn The connection to the database, it will *not* be closed
	 * @param stato the country to filter by 
	 * @return a list of CitySearchModel
	 * @throws SQLException in case of error
	 */
	public static List<CitySearchModel> filterCityByStato(Connection conn, String stato) throws SQLException 
	{
		/**
		 * SQL statement for getting the cities of the specified country   
		 */
		final String statement1 = "SELECT nome, stato "
				+ "FROM Citta "
				+ "WHERE stato = ? "
				+ "ORDER BY nome ASC";
		
		/**
		 * SQL statement for getting, for each city, its languages 
		 */
		final String statement2 = "SELECT L.nome FROM Lingua AS L "
				+ "INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua "
				+ "WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		// query facility
		QueryRunner run = new QueryRunner();
		
		// result model
		List<CitySearchModel> results = new ArrayList<CitySearchModel>();
		
		// First query
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		List<CittaBean> cittaList = run.query(conn, statement1, h, stato);		
				
		// Queries for the languages of each city
		for (CittaBean c : cittaList) {
			// Gets the languages for the city
			ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
			List<LinguaBean> lingueList = run.query(conn, statement2, h1, c.getNome(), c.getStato());
			
			// adding one city-result to the results list 
			CitySearchModel resultRow = new CitySearchModel(c, lingueList);
			results.add(resultRow);
		}
		
		return results;
	}
}