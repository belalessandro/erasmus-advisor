package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.ValutazioneInsegnamentoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Database operations about "ValutazioneInsegnamento".
 * @author Mauro, Luca
 *
 */
public class ValutazioneInsegnamentoDatabase 
{
        /**
         * Creates a new ValutazioneInsegnamento.
         * 
         * @param con A connection to the database.
         * @param val An evaluation.
         * @throws SQLException If something goes wrong.
         */
        public static void creaValutazioneInsegnamento(Connection con, ValutazioneInsegnamentoBean val)
                throws SQLException
        {
                StringBuilder builder = new StringBuilder()
                        .append("INSERT INTO ValutazioneInsegnamento (nomeutentestudente, idinsegnamento, qtainsegnamanto, ")
                        .append("interesse, difficolta, rispettodelleore, datainserimento, commento) ")
                        .append("VALUES (?, ?, ?, ?, ?, ?, default, ?);");
                
                ResultSetHandler<ValutazioneInsegnamentoBean> rsh = 
                                new BeanHandler<ValutazioneInsegnamentoBean>(ValutazioneInsegnamentoBean.class);
                QueryRunner run = new QueryRunner();
                
                run.insert(con, builder.toString(), rsh, 
                                val.getNomeUtenteStudente(),
                                val.getIdInsegnamento(),
                                val.getQtaInsegnamanto(),
                                val.getInteresse(),
                                val.getDifficolta(),
                                val.getRispettoDelleOre(),
                                val.getCommento());
        }
        
        /**
         * Returns all the evaluation to classes inserted by a specific student.
         * 
         * @param conn A connection to the database.
         * @param user The User.
         * @return A list of evaluations.
         * @throws SQLException If something goes wrong.
         */
        public static List<ValutazioneInsegnamentoBean> getEvalByUser(Connection conn, String user) throws SQLException
        {	
        		StringBuilder statement = new StringBuilder()
        			.append("SELECT NomeUtenteStudente, IdInsegnamento, QtaInsegnamanto, Interesse, Difficolta, RispettoDelleOre, DataInserimento, Commento, Nome AS nomeInsegnamento ")
        			.append("FROM ValutazioneInsegnamento JOIN Insegnamento ON IdInsegnamento = Id ")
        			.append("WHERE NomeUtenteStudente = ?");
                
                QueryRunner run = new QueryRunner();
                ResultSetHandler<List<ValutazioneInsegnamentoBean>> h = new BeanListHandler<ValutazioneInsegnamentoBean>(ValutazioneInsegnamentoBean.class);
                
                return run.query(conn, statement.toString(), h, user);
        }
//        public static List<ValutazioneInsegnamentoBean> getEvalByUser(Connection conn, String user) throws SQLException
//        {
//                final String statement = "SELECT * FROM ValutazioneInsegnamento WHERE nomeutentestudente = ?";
//                
//                QueryRunner run = new QueryRunner();
//                ResultSetHandler<List<ValutazioneInsegnamentoBean>> h = new BeanListHandler<ValutazioneInsegnamentoBean>(ValutazioneInsegnamentoBean.class);
//                
//                return run.query(conn, statement, h, user);
//        }
        
        /**
         * Delete an evaluation.
         * 
         * @param conn A connection to the database.
         * @param user The student that inserted the evaluation.
         * @param id The class evaluated.
         * @return The number of rows affected.
         * @throws SQLException If something goes wrong.
         */
        public static int deleteEvaluation(Connection conn, String user, int id) throws SQLException
        {
        	final String statement = "DELETE FROM ValutazioneInsegnamento WHERE nomeutentestudente = ? AND idinsegnamento = ?";
                
        	QueryRunner run = new QueryRunner();
        	return run.update(conn, statement, user, id);
        }

    	/**
    	 * Returns if a user has already inserted an evaluation to a class.
    	 * @param conn A connection to the database.
    	 * @param user The student that inserted the evaluation. 
    	 * @param id The teaching.
    	 * @return {@code true} if the student has already inserted an evaluation for this class, {@code false} otherwise.
    	 * @throws SQLException If something goes wrong.
    	 */
		public static boolean checkEvaluation(Connection conn, String user, int id) throws SQLException
		{
			final String statement = "SELECT COUNT (*) FROM ValutazioneInsegnamento WHERE nomeutentestudente = ? AND idinsegnamento = ?";
			
			QueryRunner run = new QueryRunner();
			
			ResultSetHandler<Long> h1 = new ScalarHandler<Long>();
			long result = run.query(conn, statement, h1, user, id);
			
			if (result > 0)
				return true;
			
			return false;
		}
}