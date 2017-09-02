package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSet;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetColumn;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetMetaDataColumn;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetRow;
import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcStatement;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaDataTablesStatement extends AbstractBlancoGenericJdbcStatement {
	protected BlancoGenericJdbcResultSet rs = null;
	protected final List<String> nameList = new ArrayList<String>();

	protected String tableNamePattern = null;

	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, final String tableNamePattern, String[] types) throws SQLException {
		super(conn);
		this.tableNamePattern = tableNamePattern;
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		rs = new BlancoGenericJdbcResultSet(this);

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

				final BlancoGenericJdbcResultSetRow record = new BlancoGenericJdbcResultSetRow();
				{
					final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_CAT");
					final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(metaDataColumn);
					column.setColumnValue(null);
					record.getColumnList().add(column);
				}

				{
					final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_SCHEM");
					final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(metaDataColumn);
					column.setColumnValue(null);
					record.getColumnList().add(column);
				}

				{
					final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_NAME");
					final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(metaDataColumn);
					column.setColumnValue(sobjectResult.getName());
					record.getColumnList().add(column);
				}

				{
					final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
					metaDataColumn.setColumnName("TABLE_TYPE");
					final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(metaDataColumn);
					// TODO 型は正しいか確認
					column.setColumnValue("TABLE");
					record.getColumnList().add(column);
				}

				{
					final BlancoGenericJdbcResultSetMetaDataColumn metaDataColumn = new BlancoGenericJdbcResultSetMetaDataColumn();
					metaDataColumn.setColumnName("REMARKS");
					final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(metaDataColumn);
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
