package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.resources.City;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchRow;

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
 * Contains static methods for the insertion, modification, deletion or city research.
 * Database operations about "Citta".
 * @author Luca, Alessandro
 *
 */


public class CittaDatabase 
{
	/**
	 * Creates a new city.
	 * 
	 * @param con A connection to the database.
	 * @param citta The city to be stored.
	 * @throws SQLException If an error occurs.
	 */
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
		}
	}

	/**
	 * Search a city, by name and return the city searched for with a list
	 * of the city's evaluations.
	 * 
	 * @param conn A connection to the database.
	 * @param name The language spoken in the city to search.
	 * @param country The country in which search the city,
	 * @return The city searched with a list of the city's evaluations.
	 * @throws SQLException If an error occurs.
	 */
	public City searchCityByName(Connection conn, String name, String country) throws SQLException 
	{
		/**
		 * The SQL statements to be executed
		 */
		
		final String statement = "SELECT Nome, Stato FROM Citta WHERE Nome = ? AND Stato = ?";
		
		final String statement1 = "SELECT L.nome FROM Lingua AS L INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		final String statement2 = "SELECT * FROM ValutazioneCitta WHERE NomeCitta = ? AND StatoCitta = ? ORDER BY datainserimento DESC";

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
	 * Delete a city in the database.
	 * 
	 * @param conn A connection to the database.
	 * @param name The name of the city to delete.
	 * @param country The city's country.
	 * @return The deletion of the city.
	 * @throws SQLException If an error occurs.
	 */
	public static int deleteCity(Connection conn, String name, String country) throws SQLException 
	{
		final String statement = "DELETE From Citta WHERE Nome = ? AND Stato = ?";
		
		QueryRunner run = new QueryRunner();
		return run.update(conn, statement, name, country);
		
	}
	
	/**
	 * Function to update city fields and relative languages.
	 * @param conn A database connection.
	 * @param new_name New name of the city.
	 * @param new_country New city country.
	 * @param old_name Old name of the city.
	 * @param old_country Old city country.
	 * @param linguaCittaBeanList List of bean with new languages.
	 * @return The number of rows updated of relation "Citta".
	 * @throws SQLException If an error occurs.
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
	
	/**
	 * @param conn A connection to the database.
	 * @return A list of all city.
	 * @throws SQLException If an error occurs.
	 */
	public static List<CittaBean> getAllSortByCountry(Connection conn) throws SQLException 
	{
		final String statement = "SELECT * FROM Citta ORDER BY stato ASC";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		return run.query(conn, statement, h);
	}
	

	/**
	 * City searched by language.
	 * 
	 * @param conn The connection to the database, it will *not* be closed.
	 * @param siglaLingua The language abbreviation to filter by.
	 * @return A list of CitySearchRow.
	 * @throws SQLException in case of error.
	 */
	public static List<CitySearchRow> filterCityBySiglaLingua(Connection conn, String siglaLingua) throws SQLException 
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
		final String statement2 = "SELECT L.nome, L.Sigla FROM Lingua AS L "
				+ "INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua "
				+ "WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		// query facility
		QueryRunner run = new QueryRunner();
		
		// result model
		List<CitySearchRow> results = new ArrayList<CitySearchRow>();
		
		// First query
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		List<CittaBean> cittaList = run.query(conn, statement1, h, siglaLingua);		
				
		// Queries for the languages of each city
		for (CittaBean c : cittaList) {
			// Gets the languages for the city
			ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
			List<LinguaBean> lingueList = run.query(conn, statement2, h1, c.getNome(), c.getStato());
			
			// adding one city-result to the results list 
			CitySearchRow resultRow = new CitySearchRow(c, lingueList);
			results.add(resultRow);
		}
		
		return results;
	}
	
	
	/**
	 * City searched by country.
	 * 
	 * @param conn The connection to the database, it will *not* be closed.
	 * @param stato The country to filter by.
	 * @return A list of CitySearchRow.
	 * @throws SQLException In case of error.
	 */
	public static List<CitySearchRow> filterCityByStato(Connection conn, String stato) throws SQLException 
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
		final String statement2 = "SELECT L.nome, L.Sigla FROM Lingua AS L "
				+ "INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua "
				+ "WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		
		// query facility
		QueryRunner run = new QueryRunner();
		
		// result model
		List<CitySearchRow> results = new ArrayList<CitySearchRow>();
		
		// First query
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		List<CittaBean> cittaList = run.query(conn, statement1, h, stato);		
				
		// Queries for the languages of each city
		for (CittaBean c : cittaList) {
			// Gets the languages for the city
			ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
			List<LinguaBean> lingueList = run.query(conn, statement2, h1, c.getNome(), c.getStato());
			
			// adding one city-result to the results list 
			CitySearchRow resultRow = new CitySearchRow(c, lingueList);
			results.add(resultRow);
		}
		
		return results;
	}
	
	/**
	 * City search by country and/or language.
	 * 
	 * @param conn The connection to the database, it will *not* be closed.
	 * @param stato (null = optional) The country to filter by.
	 * @param language (null = optional) The language.
	 * @return A list of CitySearchRow.
	 * @throws SQLException If an error occurs.
	 */
	public static List<CitySearchRow> filterCityByStatoLingua(Connection conn, String stato, String language) throws SQLException 
	{
		
		/**
		 * SQL statement for getting the cities
		 */
		final String statement1 = "SELECT NomeCitta AS Nome, StatoCitta AS stato FROM LinguaCitta "
				+ "WHERE siglalingua = ? AND StatoCitta = ? "
				+ "ORDER BY Nome ASC";

		/**
		 * SQL statement for getting, for each city, its languages 
		 */
		final String statement2 = "SELECT L.nome, L.Sigla FROM Lingua AS L "
				+ "INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua "
				+ "WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		// query facility
		QueryRunner run = new QueryRunner();
		
		// result model
		List<CitySearchRow> results = new ArrayList<CitySearchRow>();
		
		// First query
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		List<CittaBean> cittaList = run.query(conn, statement1, h, language, stato);		
				
		// Queries for the languages of each city
		for (CittaBean c : cittaList) {
			// Gets the languages for the city
			ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
			List<LinguaBean> lingueList = run.query(conn, statement2, h1, c.getNome(), c.getStato());
			
			// adding one city-result to the results list 
			CitySearchRow resultRow = new CitySearchRow(c, lingueList);
			results.add(resultRow);
		}
		
		return results;
	}
	
	/**
	 * City search - with the filters off.
	 * 
	 * @param conn The connection to the database, it will *not* be closed.
	 * @return A list of CitySearchRow.
	 * @throws SQLException In case of error.
	 */
	public static List<CitySearchRow> filterCity(Connection conn) throws SQLException 
	{
		/**
		 * SQL statement for getting the cities of the specified country   
		 */
		final String statement1 = "SELECT nome, stato "
				+ "FROM Citta "
				+ "ORDER BY nome ASC";

		/**
		 * SQL statement for getting, for each city, its languages 
		 */
		final String statement2 = "SELECT L.nome, L.Sigla FROM Lingua AS L "
				+ "INNER JOIN LinguaCitta AS C ON L.Sigla = C.SiglaLingua "
				+ "WHERE C.NomeCitta = ? AND C.StatoCitta = ?";
		
		
		// query facility
		QueryRunner run = new QueryRunner();
		
		// result model
		List<CitySearchRow> results = new ArrayList<CitySearchRow>();
		
		// First query
		ResultSetHandler<List<CittaBean>> h = new BeanListHandler<CittaBean>(CittaBean.class);
		List<CittaBean> cittaList = run.query(conn, statement1, h);		
				
		// Queries for the languages of each city
		for (CittaBean c : cittaList) {
			// Gets the languages for the city
			ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
			List<LinguaBean> lingueList = run.query(conn, statement2, h1, c.getNome(), c.getStato());
			
			// adding one city-result to the results list 
			CitySearchRow resultRow = new CitySearchRow(c, lingueList);
			results.add(resultRow);
		}
		
		return results;
	}
}