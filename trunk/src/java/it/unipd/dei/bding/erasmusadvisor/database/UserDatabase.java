package it.unipd.dei.bding.erasmusadvisor.database;

import it.unipd.dei.bding.erasmusadvisor.beans.UserBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDatabase {

	public UserBean login(Connection conn, String email) throws SQLException {

		final String statement1 = "SELECT Email, Password, Salt FROM Studente WHERE Email = ?";
		final String statement2 = "SELECT Email, Password, Salt FROM ResponsabileFlusso WHERE Email = ?";
		final String statement3 = "SELECT Email, Password, Salt FROM Coordinatore WHERE Email = ?";

		UserBean user = new UserBean();

		QueryRunner run = new QueryRunner();

		ResultSetHandler<UserBean> h = new BeanHandler<UserBean>(UserBean.class);
		user = run.query(conn, statement1, h, email);

		int fails=0;
		if (user == null) {
			user = run.query(conn, statement2, h, email);
			fails++;
			if (user == null) {
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
