package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.databasemetadata.AbstractBlancoGenericJdbcDatabaseMetaData;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaData extends AbstractBlancoGenericJdbcDatabaseMetaData {
	public BlancoSfdcJdbcDatabaseMetaData(final BlancoSfdcJdbcConnection conn) {
		super(conn);
	}

	@Override
	protected void fillCacheTableOfGetTables(String catalog, String schemaPattern, String tableNamePattern,
			String[] types) throws SQLException {
		try {
			// build cache of table info
			final DescribeGlobalResult descResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
					.describeGlobal();
			for (DescribeGlobalSObjectResult sobjectResult : descResult.getSobjects()) {
				if (sobjectResult.isQueryable() == false) {
					// skip because non queryable.");
					continue;
				}

				final PreparedStatement pstmt = conn.getCacheConnection()
						.prepareStatement("INSERT INTO GMETA_TABLES SET TABLE_NAME = ?, REMARKS = ?");
				try {
					pstmt.setString(1, sobjectResult.getName());
					pstmt.setString(2, sobjectResult.getLabel());
					pstmt.execute();
				} finally {
					pstmt.close();
				}
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}
	}

	public void fillCacheTableOfGetColumns(String catalog, String schema, String tableName, String columnNamePattern)
			throws SQLException {
		try {
			// build cache of columns
			final DescribeSObjectResult sobjResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
					.describeSObject(tableName);

			int ordinalIndex = 0;
			for (Field field : sobjResult.getFields()) {
				ordinalIndex++;

				// TODO 型マッピングの一本化。
				int dataType = (Integer.valueOf(java.sql.Types.VARCHAR));
				if ("int".equals(field.getType().toString())) {
					dataType = (Integer.valueOf(java.sql.Types.INTEGER));
				} else if ("date".equals(field.getType().toString())) {
					dataType = (Integer.valueOf(java.sql.Types.DATE));
				} else if ("datetime".equals(field.getType().toString())) {
					dataType = (Integer.valueOf(java.sql.Types.TIMESTAMP));
				}

				final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement(
						"INSERT INTO GMETA_COLUMNS SET TABLE_NAME = ?, COLUMN_NAME = ?, DATA_TYPE = ?, TYPE_NAME = ?, COLUMN_SIZE = ?, DECIMAL_DIGITS = ?, NULLABLE = ?, REMARKS = ?, CHAR_OCTET_LENGTH = ?, ORDINAL_POSITION = ?");
				try {
					pstmt.setString(1, tableName);
					pstmt.setString(2, field.getName());
					pstmt.setInt(3, dataType);
					pstmt.setString(4, field.getType().toString());
					pstmt.setInt(5, field.getLength());
					pstmt.setInt(6, field.getDigits());
					pstmt.setInt(7,
							(field.getNillable() ? ResultSetMetaData.columnNullable : ResultSetMetaData.columnNoNulls));
					pstmt.setString(8, field.getLabel());
					pstmt.setInt(9, field.getLength());
					pstmt.setInt(10, ordinalIndex);
					pstmt.execute();
				} finally {
					pstmt.close();
				}

				// metaDataColumn.setColumnName("IS_NULLABLE");

			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}
	}

	@Override
	public String getDatabaseProductName() throws SQLException {
		return "Salesforce (FIXME)";
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		return "40.0.0 (FIXME)";
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		return 40;
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		return 0;
	}

	@Override
	public String getDriverName() throws SQLException {
		return "Blanco SFDC SOQL JDBC Driver";
	}

	@Override
	public String getDriverVersion() throws SQLException {
		return "1.0.0";
	}
}
