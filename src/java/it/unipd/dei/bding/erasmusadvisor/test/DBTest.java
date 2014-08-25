package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.database.CreateStudenteDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
/**
 * Esempio di blocco "finally" corretto per disconnessione db 
 * @author Alessandro
 *
 */

public class DBTest {
		

		private static final String DRIVER = "org.postgresql.Driver";
		
		private static final String DATABASE = "jdbc:postgresql://localhost/erasmusadvisor";

		/**
		 * The username for accessing the database
		 */
		private static final String USER = "mauro";

		/**
		 * The password for accessing the database
		 */
		private static final String PASSWORD = "";
		
		public static void main(String[] args) {
			
			Connection con = null;
			
			//Statement stmt = null;
			
			ResultSet rs = null;
			
			try {
				Class.forName(DRIVER);
				
				System.out.printf("Driver %s successfully registered.%n", DRIVER);
			} catch (ClassNotFoundException e) {
				System.out.printf("Driver %s not found. %s.%n", DRIVER, e.getMessage());
				System.exit(-1);
			}
			
		
		
		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD);
//
//			StudenteBean studente = new StudenteBean();
//            studente.setNomeUtente("Prova Nome");
//            studente.setEmail("prova@prova.it");
//            studente.setPassword("afjso");
//            studente.setSalt("fdasdfs");
//			new CreateStudenteDatabase(con, studente).createStudente();
			
			List<AreaBean> aree = null;
			final String statement5 = "SELECT area as nome FROM Estensione WHERE idargomentotesi=? ;";
			QueryRunner run = new QueryRunner();
			// Gets the areas
			ResultSetHandler<List<AreaBean>> h5 = new BeanListHandler<AreaBean>(AreaBean.class);
			aree = run.query(con, statement5, h5, 4);
			
			System.out.println(aree.get(0).getNome());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			/*if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}*/
		}
	}
}