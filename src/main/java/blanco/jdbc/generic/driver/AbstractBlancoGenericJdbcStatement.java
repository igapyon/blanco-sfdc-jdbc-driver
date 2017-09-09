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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilResultSetMetaData;

public abstract class AbstractBlancoGenericJdbcStatement implements BlancoGenericJdbcBlockReadStatement {
	protected AbstractBlancoGenericJdbcConnection conn = null;

	protected boolean isClosed = false;

	protected int maxRows = 0;

	/**
	 * グローバルにユニークな文字列の必要性。
	 */
	private final String globalUniqueKey = String.valueOf(System.currentTimeMillis()) + "_"
			+ String.valueOf(Math.random()).replace('.', '_');

	public String getGlobalUniqueKey() {
		return globalUniqueKey;
	}

	public AbstractBlancoGenericJdbcStatement(final AbstractBlancoGenericJdbcConnection conn) {
		this.conn = conn;
	}

	public void close() throws SQLException {
		isClosed = true;
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public final ResultSet executeQuery(final String sql) throws SQLException {
		final boolean isSuccess = execute(sql);
		if (isSuccess == false) {
			throw new SQLException("fail to run SQL [" + sql + "]");
		}

		return getResultSet();
	}

	public boolean execute(String sql) throws SQLException {
		BlancoGenericJdbcCacheUtilResultSetMetaData.createCacheTableOfResultSetMetaData(conn.getCacheConnection(),
				getGlobalUniqueKey());
		return firstBlock(sql);
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not Implemented: execute(String sql, int autoGeneratedKeys)");
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not Implemented: execute(String sql, int[] columnIndexes)");
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not Implemented: execute(String sql, String[] columnNames)");
	}

	public ResultSet getResultSet() throws SQLException {
		return new BlancoGenericJdbcResultSet(this, getGlobalUniqueKey());
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented: unwrap(Class<T> iface)");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented: isWrapperFor(Class<?> iface)");
	}

	public int executeUpdate(String sql) throws SQLException {
		throw new SQLException("Not Implemented: executeUpdate(String sql)");
	}

	public int getMaxFieldSize() throws SQLException {
		throw new SQLException("Not Implemented: getMaxFieldSize()");
	}

	public void setMaxFieldSize(int max) throws SQLException {
		throw new SQLException("Not Implemented: setMaxFieldSize(int max)");
	}

	public int getMaxRows() throws SQLException {
		return maxRows;
	}

	public void setMaxRows(int max) throws SQLException {
		maxRows = max;
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new SQLException("Not Implemented: setEscapeProcessing(boolean enable)");
	}

	public int getQueryTimeout() throws SQLException {
		throw new SQLException("Not Implemented: getQueryTimeout()");
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		throw new SQLException("Not Implemented: setQueryTimeout(int seconds)");
	}

	public void cancel() throws SQLException {
		throw new SQLException("Not Implemented: cancel()");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not Implemented: getWarnings()");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not Implemented: clearWarnings()");
	}

	public void setCursorName(String name) throws SQLException {
		throw new SQLException("Not Implemented: setCursorName(String name)");
	}

	public boolean getMoreResults() throws SQLException {
		throw new SQLException("Not Implemented: getMoreResults()");
	}

	public void setFetchDirection(int direction) throws SQLException {
		if (conn.isClosed()) {
			throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_DATABASE_CLOSED);
		}

		// Ignore what value was provided.
	}

	public int getFetchDirection() throws SQLException {
		if (conn.isClosed()) {
			throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_DATABASE_CLOSED);
		}

		// Always FETCH FORWARD.
		return ResultSet.FETCH_FORWARD;
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException("Not Implemented: setFetchSize(int rows)");
	}

	public int getFetchSize() throws SQLException {
		throw new SQLException("Not Implemented: getFetchSize()");
	}

	public int getResultSetConcurrency() throws SQLException {
		throw new SQLException("Not Implemented: getResultSetConcurrency()");
	}

	public int getResultSetType() throws SQLException {
		throw new SQLException("Not Implemented: getResultSetType()");
	}

	public Connection getConnection() throws SQLException {
		return conn;
	}

	public boolean getMoreResults(int current) throws SQLException {
		throw new SQLException("Not Implemented: getMoreResults(int current)");
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		throw new SQLException("Not Implemented: getMoreResults(int current)");
	}

	public int getResultSetHoldability() throws SQLException {
		throw new SQLException("Not Implemented: getResultSetHoldability()");
	}

	public void setPoolable(boolean poolable) throws SQLException {
		throw new SQLException("Not Implemented: setPoolable(boolean poolable)");
	}

	public boolean isPoolable() throws SQLException {
		return false;
	}

	public void closeOnCompletion() throws SQLException {
		throw new SQLException("Not Implemented: closeOnCompletion()");
	}

	public boolean isCloseOnCompletion() throws SQLException {
		throw new SQLException("Not Implemented: isCloseOnCompletion()");
	}

	///////////////////////////
	// No update supported

	public int getUpdateCount() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	///////////////////////
	public void addBatch(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void clearBatch() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public int[] executeBatch() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	////////////////////////////

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	// No update supported
	///////////////////////////
}
