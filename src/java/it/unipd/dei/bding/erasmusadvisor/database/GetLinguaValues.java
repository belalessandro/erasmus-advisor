package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of Lingua
 * @author Luca
 *
 */

public class GetLinguaValues 
{
	public static List<LinguaBean> getLinguaDomain(DataSource ds) throws SQLException
	{
		final String statement = "SELECT * FROM Lingua ORDER BY nome ASC"; 
		
		QueryRunner run = new QueryRunner(ds);
		
		List<LinguaBean> lingue = null;
		
		ResultSetHandler<List<LinguaBean>> h1 = new BeanListHandler<LinguaBean>(LinguaBean.class);
		lingue = run.query(statement, h1);
				
		return lingue;
	}
}
