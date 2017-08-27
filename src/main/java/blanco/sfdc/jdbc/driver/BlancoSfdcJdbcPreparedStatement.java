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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSet;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaData;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleStatement;

public class BlancoSfdcJdbcPreparedStatement extends BlancoJdbcSimpleStatement implements PreparedStatement {
	protected String sql = null;

	protected BlancoJdbcSimpleResultSet rs = null;

	public BlancoSfdcJdbcPreparedStatement(final BlancoSfdcJdbcConnection conn, final String sql) {
		super(conn);
		this.sql = sql;
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		rs = new BlancoJdbcSimpleResultSet(this);

		try {
			// TODO そもそもこの処理はResultSet側のフェッチ境界の考慮が必要だが、難易度が高いので一旦保留。
			// TODO ただし、これを解決しないと、巨大な検索結果の際に全件を持ってきてしまうのでまずい実装だと思う。
			QueryResult qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection().query(sql);
			for (;;) {
				final SObject[] sObjs = qryResult.getRecords();
				if (sObjs.length == 0) {
					break;
				}

				final BlancoJdbcSimpleResultSetMetaData rsmd = BlancoSfdcJdbcStatement
						.getResultSetMetaData((BlancoSfdcJdbcConnection) conn, sObjs[0]);

				for (int indexRow = 0; indexRow < sObjs.length; indexRow++) {
					final BlancoJdbcSimpleResultSetRow row = BlancoSfdcJdbcStatement.getRowObj(sObjs[indexRow], rsmd);
					rs.getRowList().add(row);
				}
				if (qryResult.isDone()) {
					break;
				}
				// TODO This should be more better.
				qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
						.queryMore(qryResult.getQueryLocator());
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		return true;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return rs;
	}

	public ResultSet executeQuery() throws SQLException {
		return executeQuery(sql);
	}

	public int executeUpdate() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void clearParameters() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean execute() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void addBatch() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setRef(int parameterIndex, Ref x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setClob(int parameterIndex, Clob x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setArray(int parameterIndex, Array x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNString(int parameterIndex, String value) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}
}
