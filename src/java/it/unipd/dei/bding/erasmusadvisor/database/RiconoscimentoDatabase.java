package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class RiconoscimentoDatabase {

	public static List<InsegnamentoBean> getInsegnamentiRiconosciuti(Connection conn, String ID)
			throws SQLException 
	{
		final String statement = "SELECT * FROM Riconoscimento AS R INNER JOIN Insegnamento AS I ON I.id = R.idinsegnamento WHERE idflusso = ?";
		
		QueryRunner run = new QueryRunner();
		
		// Gets the profs
		ResultSetHandler<List<InsegnamentoBean>> h = new BeanListHandler<InsegnamentoBean>(InsegnamentoBean.class);
		return run.query(conn, statement, h, ID);
	}

}