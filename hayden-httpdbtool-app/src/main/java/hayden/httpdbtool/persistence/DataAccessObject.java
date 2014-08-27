package hayden.httpdbtool.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataAccessObject {

	private String jndiDataSource;

	public DataAccessObject(String dataSource) {
		super();
		this.jndiDataSource = "java:/" + dataSource;
	}

	public List<Map<String, Object>> read(String sql) throws SQLException {

		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DataSourceLookup.getPool(jndiDataSource).getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			List<String> columns = getColumns(rs);
			while (rs.next()) {
				Map<String, Object> row = new LinkedHashMap<String, Object>();
				for (String column : columns) {
					Object value = rs.getObject(column);
					row.put(column, value);
				}
				result.add(row);
			}
		} finally {
			DataSourceLookup.closeConnection(conn, stmt, rs);
		}
		return result;
	}

	private List<String> getColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		List<String> columns = new LinkedList<String>();
		for (int i = 1; i < columnCount; i++) {
			String columnName = metadata.getColumnName(i);
			columns.add(columnName);
		}
		return columns;
	}

	public int write(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DataSourceLookup.getPool(jndiDataSource).getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.addBatch();
			return pstmt.executeUpdate();
		} finally {
			DataSourceLookup.closeConnection(conn, pstmt, rs);
		}
	}

}
