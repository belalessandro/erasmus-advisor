package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.database.ProfessoreDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Test class
 */
public class TestProfessore {
		

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
			//Provo a inserire un professore: se tramite
			//il nome dell'uni di appartenenza trova tesi o altro fatto da lui torna l'id originale
			//altrimenti crea un nuovo professore e torna l'id appena creato
			int id = ProfessoreDatabase.selectOrInsertProfessore(con, "Luca", "Pizzul","Universitat de Barcelona- Main Site");
			System.out.println(id);
			id = ProfessoreDatabase.selectOrInsertProfessore(con, "asd", "asda", "Universitat de Barcelona- Main Site");
			System.out.println(id);
			id = ProfessoreDatabase.selectOrInsertProfessore(con, "asd", "asda", "Universitat de Barcelona- Main Site");
			System.out.println(id);
			
			
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