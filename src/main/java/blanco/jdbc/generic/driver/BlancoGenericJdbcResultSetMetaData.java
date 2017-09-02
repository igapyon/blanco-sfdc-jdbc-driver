/*
 *  blanco-generic-jdbc-driver
 *  Copyright (C) 2017  Toshiki Iga
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 *  Copyright 2017 Toshiki Iga
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package blanco.jdbc.generic.driver;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlancoGenericJdbcResultSetMetaData implements ResultSetMetaData {
	protected List<BlancoGenericJdbcResultSetMetaDataColumn> columnList = new ArrayList<BlancoGenericJdbcResultSetMetaDataColumn>();

	private String tableName = null;

	public List<BlancoGenericJdbcResultSetMetaDataColumn> getColumnList() {
		return columnList;
	}

	public BlancoGenericJdbcResultSetMetaDataColumn getColumnByColumnName(final String columnName) {
		for (BlancoGenericJdbcResultSetMetaDataColumn column : columnList) {
			if (column.getColumnName().compareToIgnoreCase(columnName) == 0) {
				return column;
			}
		}

		return null;
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
		return getColumnList().size();
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
		return getColumnList().get(column).getColumnName();
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
