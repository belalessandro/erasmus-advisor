package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListOfStudents {

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
	
	private static final String SQL = "SELECT * FROM Studente;"; 
	
	public static void main(String[] args) {
		
		Connection con = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		long start;
		long end;
		
		StudenteBean studente = new StudenteBean();
		
		try {
			Class.forName(DRIVER);
			
			System.out.printf("Driver %s successfully registered.%n", DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.printf("Driver %s not found. %s.%n", DRIVER, e.getMessage());
			System.exit(-1);
		}
		
		
		try {
			start = System.currentTimeMillis();
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD);
			end = System.currentTimeMillis();
			
			System.out.printf("Connection to database %s successfully estabilished in %,d milliseconds.%n",
					DATABASE, end - start);
			
			start = System.currentTimeMillis();
			stmt = con.createStatement();
			end = System.currentTimeMillis();
			
			System.out.printf("Statement successfully created in %,d milliseconds.%n", end - start);
			
			start = System.currentTimeMillis();
			rs = stmt.executeQuery(SQL);
			end = System.currentTimeMillis();
			
			System.out.printf("Query %s successfully executed in %,d milliseconds.%n",
					SQL, end - start);
			
			System.out.println("Query results:");
			
			// cycle on the query results
			while(rs.next())
			{
				studente.setNomeUtente(rs.getString("NomeUtente"));
				studente.setEmail(rs.getString("Email"));
				studente.setPassword(rs.getString("Password"));
				studente.setSalt(rs.getString("Salt"));
				studente.setDataRegistrazione(rs.getDate("DataRegistrazione"));
				studente.setUltimoAccesso(rs.getDate("UltimoAccesso"));
				studente.setAttivo(rs.getBoolean("Attivo"));
				
				
				System.out.printf("-The student %s has been created the %s%n", 
						studente.getNomeUtente(), studente.getDataRegistrazione().toString());
			}
			
		} catch (SQLException e) {
			System.out.println("Database System Error");
			
			while(e != null) {
				System.out.printf("- Message: %s%n", e.getMessage());
				System.out.printf("- SQL status code: %s%n", e.getSQLState());
				System.out.printf("- SQL error code: %s%n", e.getErrorCode());
				System.out.println();
				
				e = e.getNextException();
			}
		} finally {
			try {

				if(rs != null) {
					start = System.currentTimeMillis();
					rs.close();
					end = System.currentTimeMillis();
					
					System.out.printf("Result set successfully closed in %,d milliseconds.%n", end - start);
				}
				
				if(stmt != null) {
					start = System.currentTimeMillis();
					stmt.close();
					end = System.currentTimeMillis();
					
					System.out.printf("Statement successfully closed in %,d milliseconds.%n", end - start);
				}
				
				if(con != null) {
					start = System.currentTimeMillis();
					con.close();
					end = System.currentTimeMillis();
					
					System.out.printf("Connection successfully closed in %,d milliseconds.%n", end - start);
				}
				
			} catch (SQLException e) {
				while(e != null) {
					System.out.printf("- Message: %s%n", e.getMessage());
					System.out.printf("- SQL status code: %s%n", e.getSQLState());
					System.out.printf("- SQL error code: %s%n", e.getErrorCode());
					System.out.println();
					
					e = e.getNextException();	
				}
					
			} finally {
				rs = null;
				stmt = null;
				con = null;
			}
			
		}

	} 

}
