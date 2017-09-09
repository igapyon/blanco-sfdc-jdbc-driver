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

				final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement("INSERT INTO GMETA_COLUMNS "
						+ " SET TABLE_CAT = ?, TABLE_SCHEM = ?, TABLE_NAME = ?, COLUMN_NAME = ?, DATA_TYPE = ?, TYPE_NAME = ?" //
						+ ", COLUMN_SIZE = ?, DECIMAL_DIGITS = ?, NUM_PREC_RADIX = ?, NULLABLE = ?, COLUMN_DEF= ?, REMARKS = ?" //
						+ ", SQL_DATA_TYPE = ?, SQL_DATETIME_SUB = ?, CHAR_OCTET_LENGTH = ?, ORDINAL_POSITION = ?" //
						+ " , IS_NULLABLE = ?, SCOPE_CATALOG = ?, SCOPE_SCHEMA = ?, SCOPE_TABLE = ?, SOURCE_DATA_TYPE = ?" //
						+ ", IS_AUTOINCREMENT = ?, IS_GENERATEDCOLUMN = ?");

				try {
					int rowNum = 1;

					// "TABLE_CAT"
					pstmt.setString(rowNum++, null);
					// "TABLE_SCHEM"
					pstmt.setString(rowNum++, null);
					// "TABLE_NAME"
					pstmt.setString(rowNum++, tableName);
					// "COLUMN_NAME"
					pstmt.setString(rowNum++, field.getName());
					// rsmdRs.getInt("DATA_TYPE")
					pstmt.setInt(rowNum++, dataType);
					// rsmdRs.getString("TYPE_NAME")
					pstmt.setString(rowNum++, field.getType().toString());
					// rsmdRs.getInt("COLUMN_SIZE")
					pstmt.setInt(rowNum++, field.getLength());
					// "DECIMAL_DIGITS"
					pstmt.setInt(rowNum++, field.getDigits());
					// "NUM_PREC_RADIX"
					pstmt.setInt(rowNum++, 10);
					/// rsmdRs.getInt("NULLABLE")
					pstmt.setInt(rowNum++,
							field.getNillable() ? ResultSetMetaData.columnNullable : ResultSetMetaData.columnNoNulls);
					// "COLUMN_DEF"
					pstmt.setString(rowNum++,
							(field.getDefaultValue() == null ? null : field.getDefaultValue().toString()));
					// "REMARKS"
					pstmt.setString(rowNum++, field.getLabel());
					// "SQL_DATA_TYPE" // reserved future
					pstmt.setInt(rowNum++, 0);
					// "SQL_DATETIME_SUB"
					pstmt.setInt(rowNum++, -1 /* FIXME */);
					// rsmdRs.getInt("CHAR_OCTET_LENGTH")
					pstmt.setInt(rowNum++, field.getLength());

					// ORDINAL_POSITION should not populate
					pstmt.setInt(rowNum++, ordinalIndex);

					// "IS_NULLABLE"
					pstmt.setString(rowNum++, String.valueOf(field.getNillable()));
					// "SCOPE_CATALOG"
					pstmt.setString(rowNum++, null);
					// "SCOPE_SCHEMA"
					pstmt.setString(rowNum++, null);
					// "SCOPE_TABLE"
					pstmt.setString(rowNum++, null);
					// "SOURCE_DATA_TYPE"
					pstmt.setString(rowNum++, null);
					// "IS_AUTOINCREMENT"
					pstmt.setString(rowNum++, null);
					// "IS_GENERATEDCOLUMN"
					pstmt.setString(rowNum++, null);

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
