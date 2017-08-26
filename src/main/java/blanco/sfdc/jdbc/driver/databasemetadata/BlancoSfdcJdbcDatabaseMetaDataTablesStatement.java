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
	public BlancoSfdcJdbcDatabaseMetaDataTablesStatement(final BlancoSfdcJdbcConnection conn, String catalog,
			String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		super(conn);
	}

	public ResultSet executeQuery(final String sql) throws SQLException {
		// FIXME Ignored sql

		final List<String> nameList = new ArrayList<String>();
		try {
			final DescribeGlobalResult descResult = conn.getPartnerConnection().describeGlobal();
			for (DescribeGlobalSObjectResult sobjectResult : descResult.getSobjects()) {
				System.out.println(sobjectResult.getName());
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		return new BlancoSfdcJdbcDatabaseMetaDataTablesResultSet(this, nameList);
	}
}
