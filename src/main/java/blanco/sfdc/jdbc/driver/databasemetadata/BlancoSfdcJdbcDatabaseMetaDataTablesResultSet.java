package blanco.sfdc.jdbc.driver.databasemetadata;

import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleSizedResultSet;

public class BlancoSfdcJdbcDatabaseMetaDataTablesResultSet extends BlancoSfdcJdbcSimpleSizedResultSet {
	protected List<String> nameList = new ArrayList<String>();

	public BlancoSfdcJdbcDatabaseMetaDataTablesResultSet(final Statement stmt, final List<String> nameList) {
		super(stmt, nameList.size());
		this.nameList = nameList;
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
