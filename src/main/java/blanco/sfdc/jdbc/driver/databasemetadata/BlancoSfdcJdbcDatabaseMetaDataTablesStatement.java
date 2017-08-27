package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSet;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaDataColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleStatement;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaDataTablesStatement extends BlancoJdbcSimpleStatement {
	protected BlancoJdbcSimpleResultSet rs = null;
	protected final List<String> nameList = new ArrayList<String>();

	protected String tableNamePattern = null;

	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, final String tableNamePattern, String[] types) throws SQLException {
		super(conn);
		this.tableNamePattern = tableNamePattern;
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		rs = new BlancoJdbcSimpleResultSet(this);

		// NOTE ignored sql

		try {
			final DescribeGlobalResult descResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
					.describeGlobal();
			for (DescribeGlobalSObjectResult sobjectResult : descResult.getSobjects()) {
				if (tableNamePattern != null) {
					if (tableNamePattern.compareToIgnoreCase(sobjectResult.getName()) != 0) {
						continue;
					}
				}

				if (sobjectResult.isQueryable() == false) {
					System.err.println("Skip [" + sobjectResult.getName() + "] it is not queryable.");
					continue;
				}

				final BlancoJdbcSimpleResultSetRow record = new BlancoJdbcSimpleResultSetRow();
				{
					final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_CAT");
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(metaDataColumn);
					column.setColumnValue(null);
					record.getColumnList().add(column);
				}

				{
					final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_SCHEM");
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(metaDataColumn);
					column.setColumnValue(null);
					record.getColumnList().add(column);
				}

				{
					final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_NAME");
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(metaDataColumn);
					column.setColumnValue(sobjectResult.getName());
					record.getColumnList().add(column);
				}

				{
					final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_TYPE");
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(metaDataColumn);
					// TODO 型は正しいか確認
					column.setColumnValue("TABLE");
					record.getColumnList().add(column);
				}

				{
					final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
					metaDataColumn.setColumnName("REMARKS");
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(metaDataColumn);
					column.setColumnValue(sobjectResult.getLabel());
					record.getColumnList().add(column);
				}

				rs.getRowList().add(record);
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

	// TYPE_CAT
	// TYPE_SCHEM
	// TYPE_NAME
	// SELF_REFERENCING_COL_NAME
	// REF_GENERATION
}
