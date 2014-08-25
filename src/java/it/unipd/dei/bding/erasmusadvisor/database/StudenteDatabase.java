package it.unipd.dei.bding.erasmusadvisor.database;

import java.sql.Connection;
import java.sql.SQLException;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.IscrizioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Student;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;


/**
 * Database operations about Studente 
 * @author Luca
 *
 */

public class StudenteDatabase 
{

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
}
