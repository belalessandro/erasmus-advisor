package it.unipd.dei.bding.erasmusadvisor.database;


import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.resources.Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Database operations about Insegnamento 
 * @author Luca
 *
 */
public class InsegnamentoDatabase 
{
	public static Class getInsegnamento(DataSource ds, String ID)
			throws SQLException 
	{
		final String statement1 = "SELECT * FROM Insegnamento WHERE ID = CAST (? AS INTEGER)";
		final String statement2 = "SELECT * FROM Lingua WHERE Sigla = ?";
		final String statement3 = "SELECT * FROM ValutazioneInsegnamento WHERE IdInsegnamento = CAST (? AS INTEGER)";
		final String statement4 = "SELECT P.Nome, P.Cognome FROM Professore AS P INNER JOIN Svolgimento AS S ON P.ID = S.IdProfessore WHERE S.IdInsegnamento = CAST (? AS INTEGER)";
		
		InsegnamentoBean insegnamento = new InsegnamentoBean();
		LinguaBean lingua = new LinguaBean();
		List<ValutazioneInsegnamentoBean> listaValutazioni = null;
		List<ProfessoreBean> professori = null;

		System.out.println("inizia query");
		QueryRunner run = new QueryRunner(ds);
		
		ResultSetHandler<InsegnamentoBean> h1 = new BeanHandler<InsegnamentoBean>(InsegnamentoBean.class);
		insegnamento = run.query(statement1, h1, ID);
		
		if (insegnamento == null)
		{
			System.out.println("null");
			return null;
		}
		
		// Gets the language
		ResultSetHandler<LinguaBean> h2 = new BeanHandler<LinguaBean>(LinguaBean.class);
		lingua = run.query(statement2, h2, insegnamento.getNomeLingua());
		
		// Gets the evaluations
		ResultSetHandler<List<ValutazioneInsegnamentoBean>> h3 = new BeanListHandler<ValutazioneInsegnamentoBean>(ValutazioneInsegnamentoBean.class);
		listaValutazioni = run.query(statement3, h3, ID);
		
		// Gets the profs
		ResultSetHandler<List<ProfessoreBean>> h4 = new BeanListHandler<ProfessoreBean>(ProfessoreBean.class);
		professori = run.query(statement4, h4, ID);
		
		// Returns the results
		return new Class(insegnamento, listaValutazioni, professori, lingua);
	}

}
