package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.ArgomentoTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.CittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.DocumentazioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.FlussoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.GestioneBean;
import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaCittaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.LinguaTesiBean;
import it.unipd.dei.bding.erasmusadvisor.beans.OrigineBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ProfessoreBean;
import it.unipd.dei.bding.erasmusadvisor.beans.SvolgimentoBean;
import it.unipd.dei.bding.erasmusadvisor.beans.UniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneUniversitaBean;
import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

public class TestArgomentoTesiDatabase {


	private static final String DRIVER = "org.postgresql.Driver";

	private static final String DATABASE = "jdbc:postgresql://localhost/erasmusadvisor";

	/**
	 * The username for accessing the database
	 */
	private static final String USER = "postgres";

	/**
	 * The password for accessing the database
	 */
	private static final String PASSWORD = "postgres";

	public static void main(String[] args) {

		Connection con = null;

		try {
			Class.forName(DRIVER);

			System.out.printf("Driver %s successfully registered.%n", DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.printf("Driver %s not found. %s.%n", DRIVER, e.getMessage());
			System.exit(-1);
		}


		/*ArgomentoTesiBean argom = new ArgomentoTesiBean();
		
		// populate..
		argom.setNome("UnArgomentoDiTEsi");
		argom.setMagistrale(true);
		argom.setNomeUniversita("University of Cambridge");
		argom.setStato("NOT VERIFIED");*/
		
		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD); // UNICA CONNESSIONE
			con.setAutoCommit(false);
			ArgomentoTesiDatabase.searchArgomentoTesiBy(con, "Universitat de Barcelona- Main Site", "Mathematics");
			
			// insert test
//			DocumentazioneDatabase.createDocumentazione(con, documentazioneBean);
//			FlussoDatabase.createFlusso(con, flussoBean);
//			GestioneDatabase.createGestione(con, gestioneBean);
//			int ret = ArgomentoTesiDatabase.createArgomentoTesi(con, argom);
//			LinguaCittaDatabase.createLinguaCitta(con, linguaCittaBean);
//			LinguaTesiDatabase.createLinguaTesi(con, linguaTesiBean);
//			OrigineDatabase.createOrigine(con, origineBean);
//			SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
			
//			System.out.println(ret);
//			if (c.getInsegnamento() != null) {
//				try { 
//					String s = BeanUtils.describe(c.getInsegnamento()).toString();
//					for ( ProfessoreBean v : c.getProfessori())
//						s += "\n" + BeanUtils.describe(v).toString();
//					System.out.println(s);
//				} catch (Exception e) {}
//			}
		} catch (SQLException e) {
//			// If there is any error.
//			if (con != null)
//				try {
//					con.rollback();
//				} catch (SQLException e2) {
//				}
			DbUtils.rollbackAndCloseQuietly(con); // ROLLBACK
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(con);
//			/*if (rs != null) {
//			try {
//				rs.close();
//			} catch (SQLException e) {
//			}
//		}*/
//			/*if (pstmt != null) {
//			try {
//				pstmt.close();
//			} catch (SQLException e) {
//			}
//		}*/
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//				}
//			}
		}
	}
}
