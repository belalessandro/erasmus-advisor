package it.unipd.dei.bding.erasmusadvisor.servlets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 * 
 */
public abstract class AbstractDatabaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 5278163975935322460L;
	
	/**
	 * The connection pool to the database.
	 */
	protected static final DataSource DS;

	static {

		InitialContext cxt;
		DataSource ds = null;
		try {
			cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/erasmusadvisor");
		} catch (NamingException e) {
			ds = null;

			throw new IllegalStateException(
					"Impossible to access the connection pool to the database: "
							+ "Please, contact the admin.");
		} finally {
			DS = ds;
		}

	}

}
