package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
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
 * Database operations about Citta 
 * @author Luca
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
}