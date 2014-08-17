/*
 * Copyright 2014 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.unipd.dei.bding.erasmusadvisor.servlets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 * 
 * @author Nicola Ferro
 * @version 1.00
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
							+ e.getMessage());
		} finally {
			DS = ds;
		}

	}

}
