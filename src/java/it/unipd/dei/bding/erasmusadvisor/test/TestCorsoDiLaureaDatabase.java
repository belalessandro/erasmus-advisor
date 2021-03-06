package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.beans.CorsoDiLaureaBean;
import it.unipd.dei.bding.erasmusadvisor.beans.ResponsabileFlussoBean;
import it.unipd.dei.bding.erasmusadvisor.database.CorsoDiLaureaDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

/**
 * Test class
 */
public class TestCorsoDiLaureaDatabase {


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


		CorsoDiLaureaBean corso = new CorsoDiLaureaBean();
		
		// populate..
		corso.setNome("Ing.dell'Informazione");
		corso.setNomeUniversita("University of Cambridge");
		corso.setLivello("GRADUATE");
		
		try {
			con = DriverManager.getConnection(DATABASE, USER, PASSWORD); // UNICA CONNESSIONE
			con.setAutoCommit(false);
			
			// insert test
//			DocumentazioneDatabase.createDocumentazione(con, documentazioneBean);
//			FlussoDatabase.createFlusso(con, flussoBean);
//			GestioneDatabase.createGestione(con, gestioneBean);
			//int ret = CorsoDiLaureaDatabase.createCorsoDiLaurea(con, corso);
			ResponsabileFlussoBean r = new ResponsabileFlussoBean();
			r.setNomeUtente("pilu");
			List<CorsoDiLaureaBean> list = CorsoDiLaureaDatabase.getPossibleCourses(con, r);
//			LinguaCittaDatabase.createLinguaCitta(con, linguaCittaBean);
//			LinguaTesiDatabase.createLinguaTesi(con, linguaTesiBean);
//			OrigineDatabase.createOrigine(con, origineBean);
//			SvolgimentoDatabase.createSvolgimento(con, svolgimentoBean);
			for (CorsoDiLaureaBean l : list)
				System.out.println(l.getId() + " " + l.getNome());
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
