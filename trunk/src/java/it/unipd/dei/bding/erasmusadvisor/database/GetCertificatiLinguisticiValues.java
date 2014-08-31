package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of "CertificatiLinguistici"
 * 
 * @author Luca
 *
 */

public class GetCertificatiLinguisticiValues 
{
	/**
	 * Returns the domain of "CertificatiLinguistici"
	 * 
	 * @param conn A connection to the database.
	 * @return A list of certificates.
	 * @throws SQLException If an error occurs running the SQL query.
	 */
	public static List<CertificatiLinguisticiBean> getCertificatiLinguisticiDomain(Connection conn) throws SQLException
	{
		final String statement = "SELECT * FROM CertificatiLinguistici ORDER BY nomelingua ASC"; 
		
		QueryRunner run = new QueryRunner();
		
		List<CertificatiLinguisticiBean> lingue = null;
		
		ResultSetHandler<List<CertificatiLinguisticiBean>> h1 = new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
		lingue = run.query(conn, statement, h1);
				
		return lingue;
	}
}
