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
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaDataColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleStatement;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaDataColumnsStatement extends BlancoJdbcSimpleStatement {
	protected BlancoJdbcSimpleResultSet rs = null;

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
			@SuppressWarnings("resource")
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
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TABLE_CAT");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TABLE_SCHEM");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TABLE_NAME");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(tableName);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("COLUMN_NAME");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(field.getName());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("DATA_TYPE");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);

						// default is VARCHAR
						column.setColumnValueByInteger(Integer.valueOf(java.sql.Types.VARCHAR));
						if ("int".equals(field.getType().toString())) {
							column.setColumnValueByInteger(Integer.valueOf(java.sql.Types.INTEGER));
						} else if ("date".equals(field.getType().toString())) {
							column.setColumnValueByInteger(Integer.valueOf(java.sql.Types.DATE));
						} else if ("datetime".equals(field.getType().toString())) {
							column.setColumnValueByInteger(Integer.valueOf(java.sql.Types.TIMESTAMP));
						}
						column.setColumnValue("" + column.getColumnValueByInteger());

						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TYPE_NAME");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						// java.sql.Types
						column.setColumnValue(field.getType().toString());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("COLUMN_SIZE");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getLength());
						column.setColumnValueByInteger(Integer.valueOf(field.getLength()));
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("DECIMAL_DIGITS");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getDigits());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("NUM_PREC_RADIX");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("10");
						column.setColumnValueByInteger(Integer.valueOf(10));
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("NULLABLE");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getNillable());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("REMARKS");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(field.getLabel());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("COLUMN_DEF");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("CHAR_OCTET_LENGTH");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getLength());
						column.setColumnValueByInteger(field.getLength());
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("ORDINAL_POSITION");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + ordinalIndex);
						column.setColumnValueByInteger(ordinalIndex);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("IS_NULLABLE");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SCOPE_CATALOG");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SCOPE_SCHEMA");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SCOPE_TABLE");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SOURCE_DATA_TYPE");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("IS_AUTOINCREMENT");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					{
						final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
						metaDataColumn.setColumnName("IS_GENERATEDCOLUMN");
						final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
								metaDataColumn);
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
