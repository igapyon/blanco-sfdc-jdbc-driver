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
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleStatement;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoSfdcJdbcDatabaseMetaDataTablesStatement extends BlancoJdbcSimpleStatement {
	protected final List<String> nameList = new ArrayList<String>();

	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		super(conn);
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		// NOTE ignored sql

		try {
			final DescribeGlobalResult descResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
					.describeGlobal();
			for (DescribeGlobalSObjectResult sobjectResult : descResult.getSobjects()) {
				System.out.println(sobjectResult.getName());
				nameList.add(sobjectResult.getName());
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		return true;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		final BlancoJdbcSimpleResultSet rs = new BlancoJdbcSimpleResultSet(this);

		for (String name : nameList) {
			BlancoJdbcSimpleResultSetRow record = new BlancoJdbcSimpleResultSetRow();
			{
				final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
				item.setColumnName("TABLE_CAT");
				item.setColumnValue("tableのCAT");
				record.getColumnList().add(item);
			}

			{
				final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
				item.setColumnName("TABLE_SCHEM");
				item.setColumnValue("");
				record.getColumnList().add(item);
			}

			{
				final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
				item.setColumnName("TABLE_NAME");
				item.setColumnValue(name);
				record.getColumnList().add(item);
			}

			{
				final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
				item.setColumnName("TABLE_TYPE");
				item.setColumnValue("tab type");
				record.getColumnList().add(item);
			}

			{
				final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
				item.setColumnName("REMARKS");
				item.setColumnValue("tab remarks");
				record.getColumnList().add(item);
			}

			rs.getRowList().add(record);
		}

		return rs;
	}

	// TYPE_CAT
	// TYPE_SCHEM
	// TYPE_NAME
	// SELF_REFERENCING_COL_NAME
	// REF_GENERATION
}
