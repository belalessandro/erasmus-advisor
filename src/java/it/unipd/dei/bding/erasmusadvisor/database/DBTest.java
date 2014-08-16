package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

import java.sql.*;
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
		private static final String USER = "EATeam";

		/**
		 * The password for accessing the database
		 */
		private static final String PASSWORD = "EATeam";
		
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

			StudenteBean studente = new StudenteBean();
            studente.setNomeUtente("Prova Nome");
            studente.setEmail("prova@prova.it");
            studente.setPassword("afjso");
            studente.setSalt("fdasdfs");
			new CreateStudenteDatabase(con, studente).createStudente();
			
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