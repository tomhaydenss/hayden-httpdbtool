package hayden.httpdbtool.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceLookup {

	private static DataSourceLookup service = null;
	private DataSource dataSource = null;

	public static DataSource getPool(String jndiDataSource) {
		if (service == null) {
			synchronized (DataSourceLookup.class) {
				if (service == null) {
					service = new DataSourceLookup(jndiDataSource);
				}
			}
		}
		return service.dataSource;
	}

	private DataSourceLookup(String jndiDataSource) {
		dataSource = getDataSource(jndiDataSource);
	}

	private DataSource getDataSource(String jndiDataSource) {
		try {

			return (DataSource) new InitialContext().lookup(jndiDataSource);

		} catch (Exception e) {

			throw new RuntimeException("Falha no lookup do DataSource " + jndiDataSource, e);

		}
	}

	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException("Falha no fechamento do ResultSet.");
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException("Falha no fechamento do Statement.");
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException("Falha no fechamento da Conexao.");
			}
		}
	}

}
