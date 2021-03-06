package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.InsegnamentoBean;
import it.unipd.dei.bding.erasmusadvisor.database.InsegnamentoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

/**
 * Test class
 */
public class TestGestioneDatabase {


	private static final String DRIVER = "org.postgresql.Driver";

	private static final String DATABASE = "jdbc:postgresql://localhost/erasmusadvisor";

	/**
	 * The username for accessing the database
	 */
	private static final String USER = "EATeam";

	/**
	 * The password for accessing the database
	 */
	private static final String PASSWORD = "EATeam";

	public static void main(String[] args) {

		Connection con = null;

		try {
			Class.forName(DRIVER);

			System.out.printf("Driver %s successfully registered.%n", DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.printf("Driver %s not found. %s.%n", DRIVER, e.getMessage());
			System.exit(-1);
		}


//		DocumentazioneBean documentazioneBean = new DocumentazioneBean();
//		FlussoBean flussoBean = new FlussoBean();
//		GestioneBean gestioneBean = new GestioneBean();
		InsegnamentoBean insegnamentoBean = new InsegnamentoBean();
//		LinguaCittaBean linguaCittaBean = new LinguaCittaBean();
//		LinguaTesiBean linguaTesiBean = new LinguaTesiBean();
//		OrigineBean origineBean = new OrigineBean();
//		SvolgimentoBean svolgimentoBean = new SvolgimentoBean();
		
		// populate..
		insegnamentoBean.setNome("Analisi3");
		insegnamentoBean.setCrediti(9);
		insegnamentoBean.setNomeUniversita("University of Cambridge");
		insegnamentoBean.setPeriodoErogazione(1);
		insegnamentoBean.setStato("NOT VERIFIED");
		insegnamentoBean.setAnnoCorso(4);
		insegnamentoBean.setNomeArea("Mathematics");
		insegnamentoBean.setNomeLingua("eng");
		
		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD); // UNICA CONNESSIONE
			con.setAutoCommit(false);
			
			// insert test
//			DocumentazioneDatabase.createDocumentazione(con, documentazioneBean);
//			FlussoDatabase.createFlusso(con, flussoBean);
//			GestioneDatabase.createGestione(con, gestioneBean);
			int ret = InsegnamentoDatabase.createInsegnamento(con, insegnamentoBean);
//			LinguaCittaDatabase.createLinguaCitta(con, linguaCittaBean);
//			LinguaTesiDatabase.createLinguaTesi(con, linguaTesiBean);
//			OrigineDatabase.createOrigine(con, origineBean);
//			SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
			
			System.out.println(ret);
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
