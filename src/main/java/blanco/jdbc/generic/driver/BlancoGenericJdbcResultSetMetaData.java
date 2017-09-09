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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilDatabaseMetaData;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoGenericJdbcResultSetMetaData implements ResultSetMetaData {
	protected Statement stmt = null;
	protected String globalUniqueKey = null;

	public BlancoGenericJdbcResultSetMetaData(final Statement stmt, final String globalUniqueKey) throws SQLException {
		this.stmt = stmt;
		this.globalUniqueKey = globalUniqueKey;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getColumnCount() throws SQLException {
		final ResultSet columnsRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(
				((BlancoSfdcJdbcConnection) stmt.getConnection()).getCacheConnection(),
				"GMETA_COLUMNS_" + globalUniqueKey, null, null, null, null);
		int count = 0;
		for (; columnsRs.next();) {
			count++;
		}
		columnsRs.close();
		return count;
	}

	protected ResultSet nextResultSetTo(int column) throws SQLException {
		final ResultSet columnsRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(
				((BlancoSfdcJdbcConnection) stmt.getConnection()).getCacheConnection(),
				"GMETA_COLUMNS_" + globalUniqueKey, null, null, null, null);
		for (; column > 0; column--) {
			columnsRs.next();
		}
		return columnsRs;
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getBoolean("IS_AUTOINCREMENT");
		} finally {
			columnRs.close();
		}
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isSearchable(int column) throws SQLException {
		// FIXME 正しいかどうか不明。
		return true;
	}

	public boolean isCurrency(int column) throws SQLException {
		// FIXME 正しいかどうか不明。
		return false;
	}

	public int isNullable(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getInt("NULLABLE");
		} finally {
			columnRs.close();
		}
	}

	public boolean isSigned(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		// FIXME ディスプレイサイズは 別途カラムに格納しよう。
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("COLUMN_NAME").length();
		} finally {
			columnRs.close();
		}
	}

	public String getColumnLabel(int column) throws SQLException {
		// FIXME カラムラベルは 別途カラムに格納しよう。
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("COLUMN_NAME");
		} finally {
			columnRs.close();
		}
	}

	public String getColumnName(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("COLUMN_NAME");
		} finally {
			columnRs.close();
		}
	}

	public String getSchemaName(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("TABLE_SCHEM");
		} finally {
			columnRs.close();
		}
	}

	public int getPrecision(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			// FIXME
			return columnRs.getInt("DECIMAL_DIGITS");
		} finally {
			columnRs.close();
		}
	}

	public int getScale(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			// FIXME
			return columnRs.getInt("DECIMAL_DIGITS");
		} finally {
			columnRs.close();
		}
	}

	public String getTableName(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("TABLE_NAME");
		} finally {
			columnRs.close();
		}
	}

	public String getCatalogName(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("TABLE_CAT");
		} finally {
			columnRs.close();
		}
	}

	public int getColumnType(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getInt("DATA_TYPE");
		} finally {
			columnRs.close();
		}
	}

	public String getColumnTypeName(int column) throws SQLException {
		final ResultSet columnRs = nextResultSetTo(column);
		try {
			return columnRs.getString("TYPE_NAME");
		} finally {
			columnRs.close();
		}
	}

	public boolean isReadOnly(int column) throws SQLException {
		// always true. readonly JDBC Driver
		return true;
	}

	public boolean isWritable(int column) throws SQLException {
		// always false. readonly JDBC Driver
		return false;
	}

	public boolean isDefinitelyWritable(int column) throws SQLException {
		// always false. readonly JDBC Driver
		return false;
	}

	public String getColumnClassName(int column) throws SQLException {
		throw new SQLException("Not Implemented.");
	}
}
