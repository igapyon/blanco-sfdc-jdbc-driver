package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.ws.ConnectionException;

import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;
import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleResultSetRecord;
import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleResultSetRecordItem;
import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleStatement;

public class BlancoSfdcJdbcDatabaseMetaDataTablesStatement extends BlancoSfdcJdbcSimpleStatement {
	protected final List<String> nameList = new ArrayList<String>();

	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		super(conn);
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		// NOTE ignored sql

		try {
			final DescribeGlobalResult descResult = conn.getPartnerConnection().describeGlobal();
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
		BlancoSfdcJdbcDatabaseMetaDataTablesResultSet rs = new BlancoSfdcJdbcDatabaseMetaDataTablesResultSet(this);

		for (String name : nameList) {
			BlancoSfdcJdbcSimpleResultSetRecord record = new BlancoSfdcJdbcSimpleResultSetRecord();
			{
				final BlancoSfdcJdbcSimpleResultSetRecordItem item = new BlancoSfdcJdbcSimpleResultSetRecordItem();
				item.setColumnName("TABLE_CAT");
				item.setColumnValue("tableのCAT");
				record.getItemList().add(item);
			}

			{
				final BlancoSfdcJdbcSimpleResultSetRecordItem item = new BlancoSfdcJdbcSimpleResultSetRecordItem();
				item.setColumnName("TABLE_SCHEM");
				item.setColumnValue("");
				record.getItemList().add(item);
			}

			{
				final BlancoSfdcJdbcSimpleResultSetRecordItem item = new BlancoSfdcJdbcSimpleResultSetRecordItem();
				item.setColumnName("TABLE_NAME");
				item.setColumnValue(name);
				record.getItemList().add(item);
			}

			{
				final BlancoSfdcJdbcSimpleResultSetRecordItem item = new BlancoSfdcJdbcSimpleResultSetRecordItem();
				item.setColumnName("TABLE_TYPE");
				item.setColumnValue("tab type");
				record.getItemList().add(item);
			}

			{
				final BlancoSfdcJdbcSimpleResultSetRecordItem item = new BlancoSfdcJdbcSimpleResultSetRecordItem();
				item.setColumnName("REMARKS");
				item.setColumnValue("tab remarks");
				record.getItemList().add(item);
			}

			rs.getRecordList().add(record);
		}

		return rs;
	}

	// TYPE_CAT
	// TYPE_SCHEM
	// TYPE_NAME
	// SELF_REFERENCING_COL_NAME
	// REF_GENERATION
}
