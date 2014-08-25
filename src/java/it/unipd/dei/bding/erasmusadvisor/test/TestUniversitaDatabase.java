package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

public class TestUniversitaDatabase {


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



		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD); // UNICA CONNESSIONE

			
			it.unipd.dei.bding.erasmusadvisor.resources.Teaching c = InsegnamentoDatabase.getInsegnamento(con, 1);
			
			DbUtils.close(con);

			if (c.getInsegnamento() != null) {
				try { 
					String s = BeanUtils.describe(c.getInsegnamento()).toString();
					for ( ProfessoreBean v : c.getProfessori())
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
