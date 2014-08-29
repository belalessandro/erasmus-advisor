package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.GestioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.CittaDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.FlussoDatabase;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.CitySearchRow;
import it.unipd.dei.bding.erasmusadvisor.resources.FlowSearchRow;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

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
			
			//List<CitySearchRow> results = CittaDatabase.filterCityBySiglaLingua(con, "eng");
			List<FlowSearchRow> results = FlussoDatabase.filterFlowBy(con, null, null, null, null, null, null);
			
			for (FlowSearchRow result : results) {
				System.out.print(result.getFlusso().getId() + " | langs:");
//				for (LinguaBean l : result.getListaLingue()) {
//					System.out.print(" " + l.getNome());
//				}
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
