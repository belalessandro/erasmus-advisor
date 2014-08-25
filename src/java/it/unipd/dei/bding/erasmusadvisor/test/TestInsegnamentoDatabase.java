package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.UniversitaDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.University;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

public class TestInsegnamentoDatabase {


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


		CittaBean beanC = new CittaBean();
		beanC.setNome("Bagdad");
		beanC.setStato("Iraq");

		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD); // UNICA CONNESSIONE

			con.setAutoCommit(false); // UNICA TRANSAZIONE
			
			
			//CittaDatabase.createCitta(con, beanC); // Inserisco qualcosa
			
			University u = UniversitaDatabase.searchUniversityModelByName(con, "Universitat de Barcelona- Main Site");
			// VEDI http://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html

			UniversitaBean updated = u.getUniversita();
			updated.setLink("CIAOAOOOOO");
			UniversitaDatabase.updateUniversita(con, updated);
			
			DbUtils.commitAndClose(con); // COMMITTA 

			if (u.getUniversita() != null) {
				try { 
					String s = BeanUtils.describe(u.getUniversita()).toString();
					for ( ValutazioneUniversitaBean v : u.getListaValutazioni())
						s += "\n" + BeanUtils.describe(v).toString();
					System.out.println(s);
				} catch (Exception e) {}
			}
		} catch (SQLException e) {
//			// If there is any error.
//			if (con != null)
//				try {
//					con.rollback();
//				} catch (SQLException e2) {
//				}
			DbUtils.rollbackAndCloseQuietly(con); // ROLLBACK
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(con);
//			/*if (rs != null) {
//			try {
//				rs.close();
//			} catch (SQLException e) {
//			}
//		}*/
//			/*if (pstmt != null) {
//			try {
//				pstmt.close();
//			} catch (SQLException e) {
//			}
//		}*/
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//				}
//			}
		}
	}
}
