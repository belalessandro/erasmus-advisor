package it.unipd.dei.bding.erasmusadvisor.servlets;

import java.sql.*;


/**
 * CLASSE DI ESEMPIO: quasi nulla di quello che c'è qui funziona, 
 * @author fede
 *
 */

public class Validate {
	
	
	public static int checkUser(String email, String pass) throws SQLException {
		boolean st = false;
		// loading driver
		try {
			Class.forName("com.postgresql.jdbc.Driver");
			// creating connection with the database
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "abhijit");
			PreparedStatement ps0 = con
					.prepareStatement("select * from Studente where Email=?");
			PreparedStatement ps1 = con
					.prepareStatement("select * from ResponsabileFlusso where Email=?");
			PreparedStatement ps2 = con
					.prepareStatement("select * from Coordinatore where Email=?");
			
			ps0.setString(1, email);
			ps1.setString(1, email);
			ps1.setString(1, email);
			
			ResultSet rs = ps0.executeQuery();
			st = rs.next();
			if (st) {
				return 0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 2;
	}
	
	private boolean checkPass(String email, String pass){
		return false;
	}
}