package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;
import it.unipd.dei.bding.erasmusadvisor.database.FlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowSearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.LoggedUser;
import it.unipd.dei.bding.erasmusadvisor.resources.UserType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

/**
 * Test class
 */
public class TestFlussoDatabase {


	private static final String DRIVER = "org.postgresql.Driver";

	private static final String DATABASE = "jdbc:postgresql://localhost/erasmusadvisor";

	/**
	 * The username for accessing the database
	 */
	private static final String USER = "EATeam";

	/**
	 * The password for accessing the database
	 */
	private static final String PASSWORD = "EATeam";

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
			LoggedUser lu = new LoggedUser(UserType.RESPONSABILE, "alessandro");
			
			//List<CitySearchRow> results = CittaDatabase.filterCityBySiglaLingua(con, "eng");
			List<FlowSearchRow> results = FlussoDatabase.filterFlowBy(con, lu, null, null, null, null, null, null);
			
			for (FlowSearchRow result : results) {
				System.out.print(result.getFlusso().getId() + " | langs:");
				for (CertificatiLinguisticiBean l : result.getListaCertificatiLinguistici()) {
					System.out.print(" " + l.getNomeLingua() + l.getLivello());
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
