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

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilResultSetMetaData;

public abstract class AbstractBlancoGenericJdbcPreparedStatement extends AbstractBlancoGenericJdbcStatement
		implements PreparedStatement {
	public AbstractBlancoGenericJdbcPreparedStatement(final AbstractBlancoGenericJdbcConnection conn) {
		super(conn);
	}

	public boolean execute(String sql) throws SQLException {
		BlancoGenericJdbcCacheUtilResultSetMetaData.createCacheTableOfResultSetMetaData(conn.getCacheConnection(),
				getGlobalUniqueKey());
		return firstBlock(sql);
	}

	public ResultSet getResultSet() throws SQLException {
		return new BlancoGenericJdbcResultSet(this, getGlobalUniqueKey());
	}

	public abstract ResultSet executeQuery() throws SQLException;

	public int executeUpdate() throws SQLException {
		throw new SQLException("Not Implemented: executeUpdate()");
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		throw new SQLException("Not Implemented: setNull(int parameterIndex, int sqlType)");
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		throw new SQLException("Not Implemented: setBoolean(int parameterIndex, boolean x)");
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		throw new SQLException("Not Implemented: setByte(int parameterIndex, byte x)");
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		throw new SQLException("Not Implemented: setShort(int parameterIndex, short x)");
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		throw new SQLException("Not Implemented: setInt(int parameterIndex, int x)");
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		throw new SQLException("Not Implemented: setLong(int parameterIndex, long x)");
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		throw new SQLException("Not Implemented: setFloat(int parameterIndex, float x)");
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		throw new SQLException("Not Implemented: setDouble(int parameterIndex, double x)");
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		throw new SQLException("Not Implemented: setBigDecimal(int parameterIndex, BigDecimal x)");
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		throw new SQLException("Not Implemented: setString(int parameterIndex, String x)");
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		throw new SQLException("Not Implemented: setBytes(int parameterIndex, byte[] x)");
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		throw new SQLException("Not Implemented: setDate(int parameterIndex, Date x)");
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		throw new SQLException("Not Implemented: setTime(int parameterIndex, Time x)");
	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		throw new SQLException("Not Implemented: setTimestamp(int parameterIndex, Timestamp x)");
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented: setAsciiStream(int parameterIndex, InputStream x, int length)");
	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented: setUnicodeStream(int parameterIndex, InputStream x, int length)");
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented: setBinaryStream(int parameterIndex, InputStream x, int length)");
	}

	public void clearParameters() throws SQLException {
		throw new SQLException("Not Implemented: clearParameters()");
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		throw new SQLException("Not Implemented: setObject(int parameterIndex, Object x, int targetSqlType)");
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		throw new SQLException("Not Implemented: setObject(int parameterIndex, Object x)");
	}

	public boolean execute() throws SQLException {
		throw new SQLException("Not Implemented: execute()");
	}

	public void addBatch() throws SQLException {
		throw new SQLException("Not Implemented: addBatch()");
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		throw new SQLException("Not Implemented: setCharacterStream(int parameterIndex, Reader reader, int length)");
	}

	public void setRef(int parameterIndex, Ref x) throws SQLException {
		throw new SQLException("Not Implemented: setRef(int parameterIndex, Ref x)");
	}

	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		throw new SQLException("Not Implemented: setBlob(int parameterIndex, Blob x)");
	}

	public void setClob(int parameterIndex, Clob x) throws SQLException {
		throw new SQLException("Not Implemented: setClob(int parameterIndex, Clob x)");
	}

	public void setArray(int parameterIndex, Array x) throws SQLException {
		throw new SQLException("Not Implemented: setArray(int parameterIndex, Array x)");
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		throw new SQLException("Not Implemented: ResultSetMetaData getMetaData()");
	}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented: setDate(int parameterIndex, Date x, Calendar cal)");
	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented: setTime(int parameterIndex, Time x, Calendar cal)");
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented: setTimestamp(int parameterIndex, Timestamp x, Calendar cal)");
	}

	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		throw new SQLException("Not Implemented: setNull(int parameterIndex, int sqlType, String typeName)");
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		throw new SQLException("Not Implemented: setURL(int parameterIndex, URL x)");
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		throw new SQLException("Not Implemented: getParameterMetaData()");
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		throw new SQLException("Not Implemented: setRowId(int parameterIndex, RowId x)");
	}

	public void setNString(int parameterIndex, String value) throws SQLException {
		throw new SQLException("Not Implemented: setNString(int parameterIndex, String value)");
	}

	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		throw new SQLException("Not Implemented: setNCharacterStream(int parameterIndex, Reader value, long length)");
	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		throw new SQLException("Not Implemented: setNClob(int parameterIndex, NClob value)");
	}

	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented: setClob(int parameterIndex, Reader reader, long length)");
	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		throw new SQLException("Not Implemented: setBlob(int parameterIndex, InputStream inputStream, long length)");
	}

	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented: setNClob(int parameterIndex, Reader reader, long length)");
	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		throw new SQLException("Not Implemented: setSQLXML(int parameterIndex, SQLXML xmlObject)");
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		throw new SQLException("Not Implemented: setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)");
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented: setAsciiStream(int parameterIndex, InputStream x, long length)");
	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented: setBinaryStream(int parameterIndex, InputStream x, long length)");
	}

	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented: setCharacterStream(int parameterIndex, Reader reader, long length)");
	}

	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented: setAsciiStream(int parameterIndex, InputStream x)");
	}

	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented: setBinaryStream(int parameterIndex, InputStream x)");
	}

	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented: setCharacterStream(int parameterIndex, Reader reader)");
	}

	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		throw new SQLException("Not Implemented: setNCharacterStream(int parameterIndex, Reader value)");
	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented: setClob(int parameterIndex, Reader reader)");
	}

	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		throw new SQLException("Not Implemented: setBlob(int parameterIndex, InputStream inputStream)");
	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented: setNClob(int parameterIndex, Reader reader)");
	}
}
