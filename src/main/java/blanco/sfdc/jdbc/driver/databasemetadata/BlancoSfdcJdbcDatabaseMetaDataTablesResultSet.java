package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleResultSet;

public class BlancoSfdcJdbcDatabaseMetaDataTablesResultSet extends BlancoSfdcJdbcSimpleResultSet {
	public BlancoSfdcJdbcDatabaseMetaDataTablesResultSet(final Statement stmt) {
		super(stmt);
	}

	public String getString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
