
package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;

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

public class GetCorsoDiLaureaValues 
{
	public static List<CorsoDiLaureaBean> getPossibleCourses(DataSource ds, ResponsabileFlussoBean manager) 
			throws SQLException
	{
		final String statement = "SELECT * FROM CorsoDiLaurea WHERE NomeUniversita = ?"; 
		
		QueryRunner run = new QueryRunner(ds);
		
		List<CorsoDiLaureaBean> lingue = null;
		
		ResultSetHandler<List<CorsoDiLaureaBean>> h1 = new BeanListHandler<CorsoDiLaureaBean>(CorsoDiLaureaBean.class);
		lingue = run.query(statement, h1, manager.getNomeUniversita());
				
		return lingue;
	}
}
