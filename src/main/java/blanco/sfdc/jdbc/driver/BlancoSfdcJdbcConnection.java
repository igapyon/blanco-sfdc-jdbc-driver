/*
 *  blanco-sfdc-jdbc-driver
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

package blanco.sfdc.jdbc.driver;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.fault.LoginFault;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class BlancoSfdcJdbcConnection implements Connection {
	protected PartnerConnection partnerConn = null;

	protected boolean isClosed = false;

	public PartnerConnection getPartnerConnection() {
		return partnerConn;
	}

	public BlancoSfdcJdbcConnection(final String url, final String user, final String pass) throws SQLException {
		try {
			final ConnectorConfig connectorCfg = new ConnectorConfig();

			connectorCfg.setAuthEndpoint(url.substring(BlancoSfdcJdbcConstants.JDBC_DRIVER_URL_PREFIX.length()));
			connectorCfg.setUsername(user);
			connectorCfg.setPassword(pass);

			partnerConn = new PartnerConnection(connectorCfg);
		} catch (LoginFault ex) {
			System.err.println("[" + ex.getExceptionCode() + "] " + ex.getExceptionMessage());
			throw new SQLException("SFDC connection failed: " + ex.getExceptionCode() + ": " + ex.getExceptionMessage(),
					ex);
		} catch (ConnectionException ex) {
			throw new SQLException("SFDC connection failed: " + ex.toString(), ex);
		}
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Statement createStatement() throws SQLException {
		return new BlancoSfdcJdbcStatement(this);
	}

	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		return new BlancoSfdcJdbcPreparedStatement(this, sql);
	}

	public CallableStatement prepareCall(final String sql) throws SQLException {
		throw new SQLException(BlancoSfdcJdbcConstants.NOT_SUPPORTED_MESSAGE);
	}

	public String nativeSQL(String sql) throws SQLException {
		throw new SQLException("Not Implemented.");
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
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		if (readOnly) {
			// OK
		} else {
			throw new SQLException("Not Supported.");
		}
	}

	public boolean isReadOnly() throws SQLException {
		// Read Only !
		return true;
	}

	public void setCatalog(String catalog) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getCatalog() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setTransactionIsolation(int level) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getTransactionIsolation() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setHoldability(int holdability) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getHoldability() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Savepoint setSavepoint() throws SQLException {
		throw new SQLException(BlancoSfdcJdbcConstants.NOT_SUPPORTED_MESSAGE);
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		throw new SQLException(BlancoSfdcJdbcConstants.NOT_SUPPORTED_MESSAGE);
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		throw new SQLException(BlancoSfdcJdbcConstants.NOT_SUPPORTED_MESSAGE);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new SQLException(BlancoSfdcJdbcConstants.NOT_SUPPORTED_MESSAGE);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {
		throw new SQLException(BlancoSfdcJdbcConstants.NOT_SUPPORTED_MESSAGE);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Clob createClob() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Blob createBlob() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public NClob createNClob() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public SQLXML createSQLXML() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isValid(int timeout) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		throw new SQLClientInfoException();
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		throw new SQLClientInfoException();
	}

	public String getClientInfo(String name) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Properties getClientInfo() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setSchema(String schema) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getSchema() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void abort(Executor executor) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getNetworkTimeout() throws SQLException {
		throw new SQLException("Not Implemented.");
	}
}
