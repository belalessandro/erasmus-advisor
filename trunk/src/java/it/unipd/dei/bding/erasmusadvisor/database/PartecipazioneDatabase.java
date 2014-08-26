package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.PartecipazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.StudenteBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class PartecipazioneDatabase
{
	/**
	 * Get all the flow a student has joined
	 * @param conn A connection to the database.
	 * @param stud A student
	 * @return a list of partecipations bean
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<PartecipazioneBean> getFlows(Connection conn, String stud) 
			throws SQLException
	{
		final String statement = "SELECT * FROM Partecipazione WHERE nomeutentestudente = ?"; 
		
		QueryRunner run = new QueryRunner();
		
		List<PartecipazioneBean> list = null;
		
		ResultSetHandler<List<PartecipazioneBean>> h1 = new BeanListHandler<PartecipazioneBean>(PartecipazioneBean.class);
		list = run.query(conn, statement, h1, stud);
				
		return list;
	}
}
