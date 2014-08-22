package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.UserBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDatabase {

	/**
	 * 
	 * @param conn A connection to the database
	 * @param email The email of the user
	 * @return The user corresponding to the email
	 * @throws SQLException If the user does not exist
	 */
	public UserBean login(Connection conn, String email) throws SQLException {

		final String statement1 = "SELECT Email, NomeUtente, Password, Salt FROM Studente WHERE Email = ?";
		final String statement2 = "SELECT Email, NomeUtente, Password, Salt FROM ResponsabileFlusso WHERE Email = ?";
		final String statement3 = "SELECT Email, NomeUtente, Password, Salt FROM Coordinatore WHERE Email = ?";

		UserBean user = new UserBean();

		QueryRunner run = new QueryRunner();

		ResultSetHandler<UserBean> h = new BeanHandler<UserBean>(UserBean.class);
		
		//Search for the email in the student table
		user = run.query(conn, statement1, h, email);
		int fails=0;
		if (user == null) {
			//If it is not a student, try Responsabile
			user = run.query(conn, statement2, h, email);
			fails++;
			if (user == null) {
				//If it is not a responsible, try Coordinatore
				user = run.query(conn, statement3, h, email);
				fails++;
				if (user == null) {
					throw new SQLException("City not found.");
				}
			}
		}
		return new UserBean(user, fails);
	}
}
