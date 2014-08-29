package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about CorsoDiLaurea
 * 
 * @author Nicola, Luca
 */
public class CorsoDiLaureaDatabase  {

	/**
	 * Creates a new CorsoDiLaurea into the database.
	 * @param con A connection to the Database
	 * @param corsoDiLaurea The course to be inserted in the Database
	 * @return The course's ID
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static int createCorsoDiLaurea(final Connection con, 
			final CorsoDiLaureaBean corsoDiLaurea) throws SQLException {
		/**
		 * The SQL insert statement
		 */
		String statement = "INSERT INTO CorsoDiLaurea (id, nome, livello, nomeUniversita)"
							+ " VALUES (DEFAULT, ?, CAST(? AS tipolaurea), ?)"
							+ " RETURNING id";
		
		PreparedStatement pstmt = null;
		int generatedId = -1;
		
		try {
			pstmt = con.prepareStatement(statement);
			pstmt.setString(1, corsoDiLaurea.getNome());
			pstmt.setString(2, corsoDiLaurea.getLivello());
			pstmt.setString(3, corsoDiLaurea.getNomeUniversita());
			pstmt.execute();

			ResultSet rs = pstmt.getResultSet();
			if (rs.next()) {
				 generatedId = rs.getInt(1);
			}
		} finally {
			DbUtils.close(pstmt);
		}
		
		return generatedId;
	}

	/**
	 * Get all the degree courses in a Flow Manager University
	 * 
	 * @param conn
	 *            A connection to the database.
	 * @param manager
	 *            A ResponsabileFlussoBean with a specified NomeUtente
	 * @return a list of degree courses
	 * @throws SQLException
	 *             If an error occurs running the SQL query.
	 */
	public static List<CorsoDiLaureaBean> getPossibleCourses(Connection conn,
			ResponsabileFlussoBean manager) throws SQLException {
		final String statement = "SELECT * FROM CorsoDiLaurea "
				+ "WHERE NomeUniversita in "
				+ "(SELECT NomeUniversita FROM ResponsabileFlusso WHERE NomeUtente = ?)";

		QueryRunner run = new QueryRunner();

		List<CorsoDiLaureaBean> list = null;

		ResultSetHandler<List<CorsoDiLaureaBean>> h1 = new BeanListHandler<CorsoDiLaureaBean>(
				CorsoDiLaureaBean.class);
		list = run.query(conn, statement, h1, manager.getNomeUtente());

		return list;
	}
}