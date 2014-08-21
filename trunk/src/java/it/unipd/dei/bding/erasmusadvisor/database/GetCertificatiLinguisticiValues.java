package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CertificatiLinguisticiBean;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of Certificati Linguistici
 * @author Luca
 *
 */

public class GetCertificatiLinguisticiValues 
{
	public static List<CertificatiLinguisticiBean> getCertificatiLinguisticiDomain(DataSource ds) throws SQLException
	{
		final String statement = "SELECT * FROM CertificatiLinguistici ORDER BY nomelingua ASC"; 
		
		QueryRunner run = new QueryRunner(ds);
		
		List<CertificatiLinguisticiBean> lingue = null;
		
		ResultSetHandler<List<CertificatiLinguisticiBean>> h1 = new BeanListHandler<CertificatiLinguisticiBean>(CertificatiLinguisticiBean.class);
		lingue = run.query(statement, h1);
				
		return lingue;
	}
}
