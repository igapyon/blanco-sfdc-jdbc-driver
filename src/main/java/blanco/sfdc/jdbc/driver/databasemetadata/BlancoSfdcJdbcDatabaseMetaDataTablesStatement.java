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
	protected BlancoJdbcSimpleResultSet rs = null;
	protected final List<String> nameList = new ArrayList<String>();

	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		super(conn);
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		rs = new BlancoJdbcSimpleResultSet(this);

		// NOTE ignored sql

		try {
			final DescribeGlobalResult descResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
					.describeGlobal();
			for (DescribeGlobalSObjectResult sobjectResult : descResult.getSobjects()) {

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
					item.setColumnValue(sobjectResult.getName());
					record.getColumnList().add(item);
				}

				{
					final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
					item.setColumnName("TABLE_TYPE");
					// TODO 型は正しいか確認
					item.setColumnValue("TABLE");
					record.getColumnList().add(item);
				}

				{
					final BlancoJdbcSimpleResultSetColumn item = new BlancoJdbcSimpleResultSetColumn();
					item.setColumnName("REMARKS");
					item.setColumnValue("");
					record.getColumnList().add(item);
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
