package it.unipd.dei.bding.erasmusadvisor.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Student;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;


/**
 * Database operations about "Studente".
 * @author Luca
 *
 */

public class StudenteDatabase 
{

	/**
	 * Gets Studente by the database, given student's username.
	 * 
	 * @param conn A connection to the database.
	 * @param username Student's username.
	 * @return A Studente.
	 * @throws SQLException If an error occurs.
	 */
	public static Student getStudent(Connection conn, String username) throws SQLException
	{
		final String statement1 = "SELECT * FROM Studente WHERE nomeutente = ?";
		final String statement2 = "SELECT * FROM Iscrizione WHERE nomeutentestudente = ?";
		final String statement3 = "SELECT * FROM CorsoDiLaurea WHERE id = ?";
		
		StudenteBean studente = new StudenteBean();
		IscrizioneBean iscrizione = new IscrizioneBean();
		CorsoDiLaureaBean corso = new CorsoDiLaureaBean();

		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<StudenteBean> h1 = new BeanHandler<StudenteBean>(StudenteBean.class);
		studente = run.query(conn, statement1, h1, username);
		
		ResultSetHandler<IscrizioneBean> h2 = new BeanHandler<IscrizioneBean>(IscrizioneBean.class);
		iscrizione = run.query(conn, statement2, h2, username);
		
		ResultSetHandler<CorsoDiLaureaBean> h3 = new BeanHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		corso = run.query(conn, statement3, h3, iscrizione.getIdCorso());
		
		return new Student(studente, iscrizione, corso);
	}

	/**
	 * Update a student's instance with the username given.
	 * 
	 * @param con A connection to the database.
	 * @param stud Model of a student ("Studente").
	 * @throws SQLException If an error occurs.
	 */
	public static void updateStudent(Connection con, Student stud) throws SQLException 
	{
		StudenteBean studente = stud.getStudente();
		IscrizioneBean iscrizione = stud.getIscrizione();
		
		StringBuilder stmt1 = new StringBuilder()
			.append("UPDATE Studente SET email = ?, dataregistrazione = ?, password = ?, attivo = 't' ")
			.append("WHERE nomeutente = ?;");
		
		StringBuilder stmt2 = new StringBuilder()
			.append("INSERT INTO Iscrizione (idcorso, nomeutentestudente, annoinizio, annofine ) ")
			.append("VALUES (?, ?, ?, ?);");
		
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		// start the transaction
		try {
			
			
			con.setAutoCommit(false);
			
			pstmt1 = con.prepareStatement(stmt1.toString());
			pstmt1.setString(1, studente.getEmail());
			pstmt1.setDate(2, studente.getDataRegistrazione());
			pstmt1.setString(3, studente.getPassword());
			pstmt1.setString(4, studente.getNomeUtente());
			
			
			pstmt3 = con.prepareStatement(stmt2.toString());
			pstmt3.setInt(1, iscrizione.getIdCorso());
			pstmt3.setString(2, iscrizione.getNomeUtenteStudente());
			pstmt3.setDate(3, iscrizione.getAnnoInizio());
			pstmt3.setDate(4, iscrizione.getAnnoFine());
			
			pstmt1.executeUpdate();
			pstmt2.execute();
			
			con.commit();
			
		} catch(SQLException e)
		{
			if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                con.rollback();
	            } catch(SQLException excep) {
	                e.printStackTrace();
	            }
	        }
		} finally {
			try {
				if(pstmt1 != null)
					pstmt1.close();
				if(pstmt2 != null)
					pstmt2.close();
				if(pstmt3 != null)
					pstmt3.close();
				if(con != null)
					con.close();
			} catch(SQLException ex) {
				
			} finally {
				con.setAutoCommit(true);
				pstmt1 = null;
				pstmt2 = null;
				pstmt3 = null;
				con = null;
			}
		}
	}

	/**
	 * Update a student instance with the username given.
	 * 
	 * @param con A connection to the database.
	 * @param studente A student bean model.
	 * @throws SQLException if an error occurs.
	 */
	public static void updateStudent(Connection con, StudenteBean studente) throws SQLException 
	{
		StringBuilder sql = new StringBuilder()
		.append("UPDATE Studente SET email = ?, dataregistrazione = ?, password = ?, attivo = 't' ")
		.append("WHERE nomeutente = ?;");
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql.toString(), studente.getEmail(), studente.getDataRegistrazione(), 
				studente.getPassword(),studente.getNomeUtente());
	}
	
	
}
