package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
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

		if (BlancoGenericJdbcDatabaseMetaDataUtil.isGmetaTablesCached(conn.getInternalH2Connection()) == false) {
			try {
				final DescribeGlobalResult descResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
						.describeGlobal();
				for (DescribeGlobalSObjectResult sobjectResult : descResult.getSobjects()) {
					if (sobjectResult.isQueryable() == false) {
						System.err.println("Skip [" + sobjectResult.getName() + "] it is not queryable.");
						continue;
					}

					final PreparedStatement pstmt = conn.getInternalH2Connection()
							.prepareStatement("INSERT INTO GMETA_TABLES SET TABLE_NAME = ?, REMARKS = ?");
					try {
						pstmt.setString(1, sobjectResult.getName());
						pstmt.setString(2, sobjectResult.getLabel());
						pstmt.execute();
					} finally {
						pstmt.close();
					}
				}
				conn.commit();
			} catch (ConnectionException ex) {
				throw new SQLException(ex);
			}
		}

		if (tableNamePattern == null) {
			final Statement stmt = conn.getInternalH2Connection().createStatement();
			stmt.executeQuery("SELECT * FROM GMETA_TABLES");
			return stmt.getResultSet();
		} else {
			final PreparedStatement pstmt = conn.getInternalH2Connection()
					.prepareStatement("SELECT * FROM GMETA_TABLES WHERE TABLE_NAME LIKE ?");
			pstmt.setString(1, tableNamePattern);
			pstmt.executeQuery();
			return pstmt.getResultSet();
		}
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
