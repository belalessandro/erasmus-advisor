package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.AreaBean;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Get the values of the domain of Area
 * @author Luca
 *
 */

public class GetAreaValues 
{
	public static List<AreaBean> getAreaDomain(DataSource ds) throws SQLException
	{
		final String statement = "SELECT nome FROM Area"; 
		
		QueryRunner run = new QueryRunner(ds);
		
		List<AreaBean> lingue = null;
		
		ResultSetHandler<List<AreaBean>> h1 = new BeanListHandler<AreaBean>(AreaBean.class);
		lingue = run.query(statement, h1);
				
		return lingue;
	}
}
