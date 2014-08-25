package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.EstensioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneTesiBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Thesis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * 
 * @author Nicola
 *
 */
public class EstensioneDatabase {
	/**
	 * Executes a statement to store a new Estensione into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param est The Estensione to be stored
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Estensione.
	 */
	public static void createEstensione(Connection con, EstensioneBean est)
			throws SQLException {
		/**
		 * The SQL insert statement
		 */
		final String sql = "INSERT INTO Estensione (idArgomentoTesi, Area) VALUES (?, ?);";
		
		QueryRunner run = new QueryRunner();
		
		ResultSetHandler<EstensioneBean> rsh = new BeanHandler<EstensioneBean>(EstensioneBean.class);
		
		run.insert(con, sql, rsh, est.getIdArgomentoTesi(), est.getArea());
	}
	
	/**
	 * Executes a statement to update a Thesis into the database,
	 * without closing the connection.
	 * 
	 * @param con The connection to the database
	 * @param uni The Estensione to be updated
	 * 
	 * @return the number of rows affected	
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the Estensione.
	 */
	public static int updateEstensione(Connection con, EstensioneBean est)
			throws SQLException {
		/**
		 * The SQL update statement
		 */
		String updateStmt = "UPDATE Estensione SET area = ? "
				+ " WHERE idArgomentoTesi = ?";
		
		QueryRunner run = new QueryRunner( );
		
	    return run.update(con, updateStmt, est.getArea(), est.getIdArgomentoTesi());
	}
	
	/**
	* Delete a Estensione
	* 
	* @return the number of rows affected	
	* @throws SQLException if any error occurs 
	*/
	public static int deleteEstensione(Connection con, int idArgomentoTesi) throws SQLException 
	{
		/**
		 * The SQL delete statement
		 */
		String statement = "DELETE FROM Estensione WHERE idArgomentoTesi = ?;";
		
		QueryRunner run = new QueryRunner();
		return run.update(con, statement, idArgomentoTesi);	
	}
	
	
	/**
	 * Search a List of "Estensione" by the id of the Thesis 
	 */
	public static List<EstensioneBean> getEstensione(Connection con, String idArgomentoTesi) throws SQLException {
		/**
		 * The SQL statements to be executed
		 */
		
		String statement = "SELECT E.idArgomentoTesi, E.area"
				+ "FROM Estensione AS E "
				+ "WHERE E.idArgomentoTesi = ?";
		
		List<EstensioneBean> estList = null;
		
		QueryRunner run = new QueryRunner();
		
		// Gets the evaluations
		ResultSetHandler<List<EstensioneBean>> h = 
				new BeanListHandler<EstensioneBean>(EstensioneBean.class);
		estList = run.query(con, statement, h, idArgomentoTesi);

		// Returns the results through the university model
		return estList;
	}
}