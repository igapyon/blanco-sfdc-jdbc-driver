package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSet;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleStatement;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaDataColumnsStatement extends BlancoJdbcSimpleStatement {
	protected BlancoJdbcSimpleResultSet rs = null;
	protected final List<String> nameList = new ArrayList<String>();

	protected String tableNamePattern = null;

	public BlancoSfdcJdbcDatabaseMetaDataColumnsStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
		super(conn);
		this.tableNamePattern = tableNamePattern;

		if (tableNamePattern == null) {
			throw new SQLException("Param tableNamePatter required.");
		}
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		rs = new BlancoJdbcSimpleResultSet(this);

		// NOTE ignored sql

		// 一覧は tables で取得

		final List<String> tableNameList = new ArrayList<String>();
		{
			// use tables
			final BlancoSfdcJdbcDatabaseMetaDataTablesStatement stmt = new BlancoSfdcJdbcDatabaseMetaDataTablesStatement(
					(BlancoSfdcJdbcConnection) conn, null, null, null, null);
			final ResultSet rs = stmt.executeQuery("dummy sql");
			for (; rs.next();) {
				tableNameList.add(rs.getString("TABLE_NAME"));
			}
		}

		try {
			for (String tableName : tableNameList) {
				if (tableName.compareToIgnoreCase(tableNamePattern) != 0) {
					// skip this table
					continue;
				}

				final DescribeSObjectResult sobjResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
						.describeSObject(tableName);

				int ordinalIndex = 0;
				for (Field field : sobjResult.getFields()) {
					ordinalIndex++;
					BlancoJdbcSimpleResultSetRow record = new BlancoJdbcSimpleResultSetRow();

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("TABLE_CAT");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("TABLE_SCHEM");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("TABLE_NAME");
						column.setColumnValue(tableName);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("COLUMN_NAME");
						column.setColumnValue(field.getName());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("DATA_TYPE");
						// java.sql.Types
						column.setColumnValue(field.getType().toString());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("TYPE_NAME");
						// java.sql.Types
						column.setColumnValue(field.getType().toString());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("COLUMN_SIZE");
						column.setColumnValue("" + field.getLength());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("DECIMAL_DIGITS");
						column.setColumnValue("" + field.getDigits());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("NUM_PREC_RADIX");
						column.setColumnValue("10");
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("NULLABLE");
						column.setColumnValue("" + field.getNillable());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("REMARKS");
						column.setColumnValue(field.getLabel());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("COLUMN_DEF");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("CHAR_OCTET_LENGTH");
						column.setColumnValue("" + field.getLength());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("ORDINAL_POSITION");
						column.setColumnValue("" + ordinalIndex);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("IS_NULLABLE");
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("SCOPE_CATALOG");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("SCOPE_SCHEMA");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("SCOPE_TABLE");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("SOURCE_DATA_TYPE");
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("IS_AUTOINCREMENT");
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
						column.setColumnName("IS_GENERATEDCOLUMN");
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					rs.getRowList().add(record);
				}
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		return true;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return rs;
	}
}
