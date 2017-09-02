package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.databasemetadata.BlancoGenericJdbcDatabaseMetaData;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaData extends BlancoGenericJdbcDatabaseMetaData {
	protected BlancoSfdcJdbcConnection conn = null;

	public BlancoSfdcJdbcDatabaseMetaData(final BlancoSfdcJdbcConnection conn) {
		this.conn = conn;
	}

	/**
	 * TABLES
	 */
	@Override
	public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
			throws SQLException {

		try {
			final DescribeGlobalResult descResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
					.describeGlobal();

			{
				final PreparedStatement pstmt = conn.getInternalH2Connection()
						.prepareStatement("SELECT COUNT(*) FROM SF_TABLES");
				pstmt.executeQuery();
				ResultSet rs = pstmt.getResultSet();
				rs.next();
				int count = rs.getInt(1);
				System.out.println("SF_TABLES:" + count);
				rs.close();
				pstmt.close();
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		@SuppressWarnings("resource")
		final BlancoSfdcJdbcDatabaseMetaDataTablesStatement stmt = new BlancoSfdcJdbcDatabaseMetaDataTablesStatement(
				conn, catalog, schemaPattern, tableNamePattern, types);
		stmt.execute("dummy");
		return stmt.getResultSet();
	}

	/**
	 * COLUMNS
	 */
	@Override
	public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
			throws SQLException {

		@SuppressWarnings("resource")
		final BlancoSfdcJdbcDatabaseMetaDataColumnsStatement stmt = new BlancoSfdcJdbcDatabaseMetaDataColumnsStatement(
				conn, catalog, schemaPattern, tableNamePattern, columnNamePattern);
		stmt.execute("dummy");
		return stmt.getResultSet();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return conn;
	}
}
