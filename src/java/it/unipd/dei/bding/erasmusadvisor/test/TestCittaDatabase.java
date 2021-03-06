package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

/**
 * Test class
 */
public class TestCittaDatabase {


	private static final String DRIVER = "org.postgresql.Driver";

	private static final String DATABASE = "jdbc:postgresql://localhost/erasmusadvisor";

	/**
	 * The username for accessing the database
	 */
	private static final String USER = "postgres";

	/**
	 * The password for accessing the database
	 */
	private static final String PASSWORD = "postgres";

	public static void main(String[] args) {

		Connection con = null;

		try {
			Class.forName(DRIVER);

			System.out.printf("Driver %s successfully registered.%n", DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.printf("Driver %s not found. %s.%n", DRIVER, e.getMessage());
			System.exit(-1);
		}


		//CittaBean cittaBean = new CittaBean();
		
		
		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD); // UNICA CONNESSIONE
			//con.setAutoCommit(false);
			
			//List<CitySearchRow> results = CittaDatabase.filterCityBySiglaLingua(con, "eng");
			List<CitySearchRow> results = CittaDatabase.filterCityByStato(con, "Italy");
			
			for (CitySearchRow result : results) {
				System.out.print(result.getCitta().getNome() + " | langs:");
				for (LinguaBean l : result.getListaLingue()) {
					System.out.print(" " + l.getNome());
				}
				System.out.print("\n");
			}
		} catch (SQLException e) {
			//DbUtils.rollbackAndCloseQuietly(con); // ROLLBACK
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
}
