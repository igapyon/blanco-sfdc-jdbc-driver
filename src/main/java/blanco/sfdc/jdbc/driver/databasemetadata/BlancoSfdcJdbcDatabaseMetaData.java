package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.databasemetadata.BlancoGenericJdbcDatabaseMetaData;
import blanco.jdbc.generic.driver.databasemetadata.BlancoGenericJdbcDatabaseMetaDataUtil;
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

			if (BlancoGenericJdbcDatabaseMetaDataUtil.isGmetaTablesCached(conn.getInternalH2Connection()) == false) {
				System.out.println("NEED TO READ CACHE");
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
