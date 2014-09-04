package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.database.UserDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

/**
 * Test class
 */
public class TestUserDatabase {


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
			
			System.out.print(UserDatabase.canStudentInsert(con, "alessandroo", "Università degli Studi di Padova"));
			
		} catch (SQLException e) {
			//DbUtils.rollbackAndCloseQuietly(con); // ROLLBACK
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
}
