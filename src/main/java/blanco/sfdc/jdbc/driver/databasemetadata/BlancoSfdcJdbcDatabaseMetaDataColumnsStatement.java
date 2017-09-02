package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcStatement;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSet;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetColumn;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetMetaDataColumn;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetRow;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaDataColumnsStatement extends AbstractBlancoGenericJdbcStatement {
	protected BlancoGenericJdbcResultSet rs = null;

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
		rs = new BlancoGenericJdbcResultSet(this);

		// NOTE ignored sql

		// 一覧は tables で取得

		final List<String> tableNameList = new ArrayList<String>();
		{
			final Connection connH2 = ((BlancoSfdcJdbcConnection) conn).getInternalH2Connection();
			final ResultSet rs = connH2.getMetaData().getTables(null, null, null, null);

			for (; rs.next();) {
				tableNameList.add(rs.getString("TABLE_NAME"));
				System.out.println("TabName: " + rs.getString("TABLE_NAME"));
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
					BlancoGenericJdbcResultSetRow record = new BlancoGenericJdbcResultSetRow();

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TABLE_CAT");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TABLE_SCHEM");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TABLE_NAME");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(tableName);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("COLUMN_NAME");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(field.getName());
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("DATA_TYPE");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
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
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("TYPE_NAME");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						// java.sql.Types
						column.setColumnValue(field.getType().toString());
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("COLUMN_SIZE");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getLength());
						column.setColumnValueByInteger(Integer.valueOf(field.getLength()));
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("DECIMAL_DIGITS");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getDigits());
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("NUM_PREC_RADIX");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("10");
						column.setColumnValueByInteger(Integer.valueOf(10));
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("NULLABLE");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getNillable());
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("REMARKS");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(field.getLabel());
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("COLUMN_DEF");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("CHAR_OCTET_LENGTH");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + field.getLength());
						column.setColumnValueByInteger(field.getLength());
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("ORDINAL_POSITION");
						metaDataColumn.setDataType(java.sql.Types.INTEGER);
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("" + ordinalIndex);
						column.setColumnValueByInteger(ordinalIndex);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("IS_NULLABLE");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SCOPE_CATALOG");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SCOPE_SCHEMA");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SCOPE_TABLE");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("SOURCE_DATA_TYPE");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue(null);
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("IS_AUTOINCREMENT");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
								metaDataColumn);
						column.setColumnValue("");
						record.getColumnList().add(column);
					}

					{
						final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
						metaDataColumn.setColumnName("IS_GENERATEDCOLUMN");
						final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(
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
