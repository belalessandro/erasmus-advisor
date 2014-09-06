package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Student;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Database operations about "Studente".
 * 
 * @author Mauro
 * 
 */

public class StudenteDatabase {

	/**
	 * Gets Studente by the database, given student's username.
	 * 
	 * @param conn
	 *            A connection to the database.
	 * @param username
	 *            Student's username.
	 * @return A Studente.
	 * @throws SQLException
	 *             If an error occurs.
	 */
	public static Student getStudent(Connection conn, String username)
			throws SQLException {
		final String statement1 = "SELECT * FROM Studente WHERE nomeutente = ?";
		final String statement2 = "SELECT * FROM Iscrizione WHERE nomeutentestudente = ?";
		final String statement3 = "SELECT * FROM CorsoDiLaurea WHERE id = ?";

		StudenteBean studente = new StudenteBean();
		IscrizioneBean iscrizione = new IscrizioneBean();
		CorsoDiLaureaBean corso = new CorsoDiLaureaBean();

		QueryRunner run = new QueryRunner();

		ResultSetHandler<StudenteBean> h1 = new BeanHandler<StudenteBean>(
				StudenteBean.class);
		studente = run.query(conn, statement1, h1, username);

		ResultSetHandler<IscrizioneBean> h2 = new BeanHandler<IscrizioneBean>(
				IscrizioneBean.class);
		iscrizione = run.query(conn, statement2, h2, username);

		ResultSetHandler<CorsoDiLaureaBean> h3 = new BeanHandler<CorsoDiLaureaBean>(
				CorsoDiLaureaBean.class);
		corso = run.query(conn, statement3, h3, iscrizione.getIdCorso());

		return new Student(studente, iscrizione, corso);
	}


	/**
	 * Update a student instance with the username given.
	 * 
	 * @param con
	 *            A connection to the database.
	 * @param studente
	 *            A student bean model.
	 * @throws SQLException
	 *             if an error occurs.
	 */
	public static void updateStudent(Connection con, StudenteBean studente)
			throws SQLException {
		StringBuilder sql = new StringBuilder()
				.append("UPDATE Studente SET email = ?, dataregistrazione = ?, password = ?, salt = ?, attivo = 't' ")
				.append("WHERE nomeutente = ?;");

		QueryRunner run = new QueryRunner();

		run.update(con, sql.toString(), studente.getEmail(),
				studente.getDataRegistrazione(), studente.getPassword(),
				studente.getSalt(), studente.getNomeUtente());
	}

	/**
	 * Set the field "attivo" of a student to false.
	 * 
	 * @param con
	 *            A connection to the database.
	 * @param username
	 *            Student username.
	 * @throws SQLException
	 *             If an error occurs in SQL query.
	 */
	public static void disableStudente(Connection con, String username)
			throws SQLException {
		final String sql = "UPDATE Studente SET attivo = 'f' WHERE NomeUtente = ?;";

		QueryRunner run = new QueryRunner();

		run.update(con, sql, username);
	}

	/**
	 * Method used for creating a new Studente instance into the database.
	 * 
	 * @param con
	 *            database connection
	 * @param student
	 *            StudenteBean object
	 * @throws SQLException
	 */
	public static void createStudente(Connection con, StudenteBean student)
			throws SQLException {
		final StringBuilder sql = new StringBuilder()
				.append("INSERT INTO Studente (NomeUtente, Email, DataRegistrazione, Password, Salt, UltimoAccesso, Attivo) ")
				.append("VALUES(?, ?, DEFAULT, ?, ?, CURRENT_DATE , TRUE);");

		QueryRunner run = new QueryRunner();

		ResultSetHandler<StudenteBean> rsh = new BeanHandler<StudenteBean>(
				StudenteBean.class);

		run.insert(con, sql.toString(), rsh, student.getNomeUtente(),
				student.getEmail(), student.getPassword(), student.getSalt());

	}

	/**
	 * Method used for creating a new Studente instance into the database and
	 * modify also the password.
	 * 
	 * @param con
	 *            database connection
	 * @param student
	 *            StudenteBean object
	 * @throws SQLException
	 */
	public static void updateStudentWithoutPassword(Connection con,
			StudenteBean student) throws SQLException {
		StringBuilder sql = new StringBuilder()
				.append("UPDATE Studente SET email = ?, dataregistrazione = ?, attivo = 't' ")
				.append("WHERE nomeutente = ?;");

		QueryRunner run = new QueryRunner();

		run.update(con, sql.toString(), student.getEmail(),
				student.getDataRegistrazione(), student.getNomeUtente());
	}

	/**
	 * Method used for updating user access' date.
	 * 
	 * @param con  database connection
	 * @param nomeUtente user name
	 * @throws SQLException
	 */
	public static void updateLastLogin(Connection con, String nomeUtente) throws SQLException 
	{
		final String sql = "UPDATE Studente SET UltimoAccesso = CURRENT_DATE WHERE NomeUtente = ?;";
		
		QueryRunner run = new QueryRunner();
		
		run.update(con, sql, nomeUtente);
	}

}
