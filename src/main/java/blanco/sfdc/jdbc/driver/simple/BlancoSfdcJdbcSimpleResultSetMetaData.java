package blanco.sfdc.jdbc.driver.simple;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlancoSfdcJdbcSimpleResultSetMetaData implements ResultSetMetaData {
	protected List<BlancoSfdcJdbcSimpleResultSetMetaDataItem> itemList = new ArrayList<BlancoSfdcJdbcSimpleResultSetMetaDataItem>();

	private String tableName = null;

	public List<BlancoSfdcJdbcSimpleResultSetMetaDataItem> getItemList() {
		return itemList;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getColumnCount() throws SQLException {
		return getItemList().size();
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isSearchable(int column) throws SQLException {
		// TODO validate it.
		return true;
	}

	public boolean isCurrency(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int isNullable(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isSigned(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getColumnLabel(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getColumnName(int column) throws SQLException {
		return getItemList().get(column).getColumnName();
	}

	public String getSchemaName(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getPrecision(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getScale(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getTableName(int column) throws SQLException {
		// FIXME 多くのテーブルからの検索結果への考慮が必要。
		return tableName;
	}

	public String getCatalogName(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getColumnType(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getColumnTypeName(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isReadOnly(int column) throws SQLException {
		// Always true
		return true;
	}

	public boolean isWritable(int column) throws SQLException {
		// Always false
		return false;
	}

	public boolean isDefinitelyWritable(int column) throws SQLException {
		// Always false
		return false;
	}

	public String getColumnClassName(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}
}
