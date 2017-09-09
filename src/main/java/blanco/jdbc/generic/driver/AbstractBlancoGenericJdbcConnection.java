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

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilDatabaseMetaData;

/**
 * Generic Read Only JDBC Connection.
 * 
 * @author Toshiki Iga
 */
public abstract class AbstractBlancoGenericJdbcConnection implements Connection {
	protected boolean isClosed = false;

	private Connection connCache = null;

	public AbstractBlancoGenericJdbcConnection() throws SQLException {
		try {
			Class.forName("org.h2.Driver");
			connCache = DriverManager.getConnection("jdbc:h2:mem:sfdcjdbc");

			// Create system tables for SFDC JDBC Driver.
			// databasemetadata.getTables();
			BlancoGenericJdbcCacheUtilDatabaseMetaData.createCacheTables(connCache);
		} catch (Exception ex) {
			throw new SQLException(ex);
		}
	}

	public Connection getCacheConnection() {
		return connCache;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented: unwrap(Class<T> iface)");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented: isWrapperFor(Class<?> iface)");
	}

	public abstract Statement createStatement() throws SQLException;

	public abstract PreparedStatement prepareStatement(final String sql) throws SQLException;

	public CallableStatement prepareCall(final String sql) throws SQLException {
		throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_NOT_SUPPORTED);
	}

	public String nativeSQL(String sql) throws SQLException {
		throw new SQLException("Not Implemented: nativeSQL(String sql)");
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// NOTE do nothing.
	}

	public boolean getAutoCommit() throws SQLException {
		// NOTE do nothing.
		return false;
	}

	public void commit() throws SQLException {
		// NOTE do nothing.
	}

	public void rollback() throws SQLException {
		// NOTE do nothing.
	}

	public void close() throws SQLException {
		isClosed = true;
		connCache.close();
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public abstract DatabaseMetaData getMetaData() throws SQLException;

	public void setReadOnly(boolean readOnly) throws SQLException {
		if (readOnly) {
			// OK
		} else {
			throw new SQLException("Not Supported: setReadOnly(boolean readOnly)");
		}
	}

	public boolean isReadOnly() throws SQLException {
		// Read Only !
		return true;
	}

	public void setCatalog(String catalog) throws SQLException {
		// ignore
	}

	public String getCatalog() throws SQLException {
		// TODO カタログ名に対応したDBの場合、これをうわがきしてください。
		return null;
	}

	public void setTransactionIsolation(int level) throws SQLException {
		throw new SQLException("Not Implemented: setTransactionIsolation(int level)");
	}

	public int getTransactionIsolation() throws SQLException {
		throw new SQLException("Not Implemented: getTransactionIsolation()");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not Implemented: getWarnings()");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not Implemented: clearWarnings()");
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLException("Not Implemented: createStatement(int resultSetType, int resultSetConcurrency)");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: prepareStatement(String sql, int resultSetType, int resultSetConcurrency)");
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// そもサポート外???
		throw new SQLException("Not Implemented: prepareCall(String sql, int resultSetType, int resultSetConcurrency)");
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new SQLException("Not Implemented: getTypeMap()");
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		throw new SQLException("Not Implemented: setTypeMap(Map<String, Class<?>> map)");
	}

	public void setHoldability(int holdability) throws SQLException {
		throw new SQLException("Not Implemented: setHoldability(int holdability)");
	}

	public int getHoldability() throws SQLException {
		throw new SQLException("Not Implemented: getHoldability()");
	}

	public Savepoint setSavepoint() throws SQLException {
		throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_NOT_SUPPORTED);
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_NOT_SUPPORTED);
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_NOT_SUPPORTED);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_NOT_SUPPORTED);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new SQLException(
				"Not Implemented: prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability");
	}

	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {
		throw new SQLException(BlancoGenericJdbcConstants.MESSAGE_NOT_SUPPORTED);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not Implemented: prepareStatement(String sql, int autoGeneratedKeys)");
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not Implemented: prepareStatement(String sql, int[] columnIndexes)");
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not Implemented: prepareStatement(String sql, String[] columnNames)");
	}

	public Clob createClob() throws SQLException {
		throw new SQLException("Not Implemented: createClob()");
	}

	public Blob createBlob() throws SQLException {
		throw new SQLException("Not Implemented: createBlob()");
	}

	public NClob createNClob() throws SQLException {
		throw new SQLException("Not Implemented: createNClob()");
	}

	public SQLXML createSQLXML() throws SQLException {
		throw new SQLException("Not Implemented: createSQLXML()");
	}

	public boolean isValid(int timeout) throws SQLException {
		throw new SQLException("Not Implemented: isValid(int timeout)");
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		throw new SQLClientInfoException("NotImplemented: setClientInfo(String name, String value)", null);
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		throw new SQLClientInfoException("NotImplemented: setClientInfo(Properties properties)", null);
	}

	public String getClientInfo(String name) throws SQLException {
		throw new SQLException("Not Implemented: getClientInfo(String name)");
	}

	public Properties getClientInfo() throws SQLException {
		throw new SQLException("Not Implemented: getClientInfo()");
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new SQLException("Not Implemented: createArrayOf(String typeName, Object[] elements)");
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new SQLException("Not Implemented: createStruct(String typeName, Object[] attributes)");
	}

	public void setSchema(String schema) throws SQLException {
		// TODO Scheme名に対応したDBの場合、これをうわがきしてください。
		// ignore
	}

	public String getSchema() throws SQLException {
		// TODO Scheme名に対応したDBの場合、これをうわがきしてください。
		return null;
	}

	public void abort(Executor executor) throws SQLException {
		throw new SQLException("Not Implemented: abort(Executor executor)");
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw new SQLException("Not Implemented: setNetworkTimeout(Executor executor, int milliseconds)");
	}

	public int getNetworkTimeout() throws SQLException {
		throw new SQLException("Not Implemented: getNetworkTimeout()");
	}
}
