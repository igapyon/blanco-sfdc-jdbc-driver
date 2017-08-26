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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class BlancoSfdcJdbcStatement implements Statement {
	protected BlancoSfdcJdbcConnection conn = null;

	protected boolean isClosed = false;

	public BlancoSfdcJdbcStatement(final BlancoSfdcJdbcConnection conn) {
		this.conn = conn;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet executeQuery(final String sql) throws SQLException {
		final List<SObject> resultSetValueList = new ArrayList<SObject>();
		try {
			// TODO そもそもこの処理はResultSet側にあるべきのようだが、難易度が高いので一旦保留。
			// TODO ただし、これを解決しないと、巨大な検索結果の際に全件を持ってきてしまうのでまずい実装だと思う。
			QueryResult qryResult = conn.getPartnerConnection().query(sql);
			for (;;) {
				final SObject[] sObjs = qryResult.getRecords();
				for (int index = 0; index < sObjs.length; index++) {
					resultSetValueList.add(sObjs[index]);
				}
				if (qryResult.isDone()) {
					break;
				}
				// TODO This should be more better.
				qryResult = conn.getPartnerConnection().queryMore(qryResult.getQueryLocator());
			}

			return new BlancoSfdcJdbcResultSet(this, resultSetValueList);
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}
	}

	public int executeUpdate(String sql) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void close() throws SQLException {
		isClosed = true;
	}

	public int getMaxFieldSize() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setMaxFieldSize(int max) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxRows() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setMaxRows(int max) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getQueryTimeout() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void cancel() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setCursorName(String name) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean execute(String sql) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getResultSet() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getUpdateCount() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean getMoreResults() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getFetchDirection() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getFetchSize() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getResultSetConcurrency() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getResultSetType() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void addBatch(String sql) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void clearBatch() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int[] executeBatch() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Connection getConnection() throws SQLException {
		return conn;
	}

	public boolean getMoreResults(int current) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getResultSetHoldability() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public void setPoolable(boolean poolable) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isPoolable() throws SQLException {
		return false;
	}

	public void closeOnCompletion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isCloseOnCompletion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}
}
