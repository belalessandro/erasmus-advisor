package it.unipd.dei.bding.erasmusadvisor.test;

import it.unipd.dei.bding.erasmusadvisor.database.ArgomentoTesiDatabase;
import it.unipd.dei.bding.erasmusadvisor.resources.ThesisSearchRow;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

/**
 * Test class
 */
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

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

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
			List<ThesisSearchRow> list = null;
			list = ArgomentoTesiDatabase.searchArgomentoTesi(con, "undefined", "undefined", "undefined", "undefined");
			//ArgomentoTesiDatabase.getArgomentoTesiSearch(con, "1");
			System.out.println(list.get(0).getArg().getNome());
			System.out.println(list.get(1).getListaAree().get(0).getNome());
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
