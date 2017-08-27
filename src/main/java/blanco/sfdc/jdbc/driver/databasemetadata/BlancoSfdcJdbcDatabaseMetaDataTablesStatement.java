package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.ws.ConnectionException;

import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;
import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleStatement;

public class BlancoSfdcJdbcDatabaseMetaDataTablesStatement extends BlancoSfdcJdbcSimpleStatement {
	protected final List<String> nameList = new ArrayList<String>();

	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		super(conn);
	}


	@Override
	public boolean execute(String sql) throws SQLException {
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
		return new BlancoSfdcJdbcDatabaseMetaDataTablesResultSet(this, nameList);
	}



	TABLE_CAT	String	The name of the database in which the specified table resides.
	TABLE_SCHEM	String	The table schema name.
	TABLE_NAME	String	The table name.
	TABLE_TYPE	String	"".

	REMARKS	String	The description of the table.
	TYPE_CAT	String	Not supported by the JDBC driver.
	TYPE_SCHEM	String	Not supported by the JDBC driver.
	TYPE_NAME	String	Not supported by the JDBC driver.
	SELF_REFERENCING_COL_NAME	String	Not supported by the JDBC driver.
	REF_GENERATION	String	Not supported by the JDBC driver.

}
