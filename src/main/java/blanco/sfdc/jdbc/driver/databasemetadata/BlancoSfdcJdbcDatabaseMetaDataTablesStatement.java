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
}
