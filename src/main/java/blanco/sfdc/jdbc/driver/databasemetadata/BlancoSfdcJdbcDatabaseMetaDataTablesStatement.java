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
					column.setColumnValue(sobjectResult.getName());
					record.getColumnList().add(column);
				}

				{
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
					column.setColumnName("TABLE_TYPE");
					// TODO 型は正しいか確認
					column.setColumnValue("TABLE");
					record.getColumnList().add(column);
				}

				{
					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn();
					column.setColumnName("REMARKS");
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
