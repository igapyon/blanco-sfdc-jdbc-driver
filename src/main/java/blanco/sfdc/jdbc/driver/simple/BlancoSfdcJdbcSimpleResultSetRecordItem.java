package blanco.sfdc.jdbc.driver.simple;

public class BlancoSfdcJdbcSimpleResultSetRecordItem {
	protected String columnName = null;

	protected String columnValue = null;

	// columnType ???

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
}
