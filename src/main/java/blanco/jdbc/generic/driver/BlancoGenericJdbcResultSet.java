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
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoGenericJdbcResultSet implements ResultSet {
	private AbstractBlancoGenericJdbcStatement stmt = null;

	protected String globalUniqueKey;

	private boolean isClosed = false;

	protected ResultSet cacheResultSet = null;

	/**
	 * 1 origin. 0 means not selected. -1 means closed.
	 */
	protected int currentRow = 0;

	public BlancoGenericJdbcResultSet(final AbstractBlancoGenericJdbcStatement stmt, final String globalUniqueKey)
			throws SQLException {
		this.stmt = stmt;
		this.globalUniqueKey = globalUniqueKey;

		trialReadResultSetFromCache();
	}

	public void close() throws SQLException {
		isClosed = true;
		currentRow = -1;

		cacheResultSet.close();
	}

	/**
	 * TODO 名前を変更しよう。
	 * 
	 * @throws SQLException
	 */
	protected void trialReadResultSetFromCache() throws SQLException {
		// FIXME ENUM ALL COLUMN NAME INSTEAD OF *
		// FIXME ORDER BY
		cacheResultSet = ((BlancoSfdcJdbcConnection) stmt.getConnection()).getCacheConnection().createStatement()
				.executeQuery("SELECT * FROM GMETA_RS_" + globalUniqueKey);
	}

	public String getString(final int columnIndex) throws SQLException {
		return cacheResultSet.getString(columnIndex);
	}

	public String getString(final String columnLabel) throws SQLException {
		return cacheResultSet.getString(columnLabel);
	}

	public Date getDate(int columnIndex) throws SQLException {
		return cacheResultSet.getDate(columnIndex);
	}

	public Date getDate(String columnLabel) throws SQLException {
		return cacheResultSet.getDate(columnLabel);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return cacheResultSet.getTimestamp(columnIndex);
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return cacheResultSet.getTimestamp(columnLabel);
	}

	public int getInt(int columnIndex) throws SQLException {
		return cacheResultSet.getInt(columnIndex);
	}

	public int getInt(String columnLabel) throws SQLException {
		return cacheResultSet.getInt(columnLabel);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return new BlancoGenericJdbcResultSetMetaData(stmt, globalUniqueKey);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented: unwrap(Class<T> iface)");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented: isWrapperFor(Class<?> iface)");
	}

	public boolean wasNull() throws SQLException {
		return cacheResultSet.wasNull();
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return cacheResultSet.getBoolean(columnIndex);
	}

	public byte getByte(int columnIndex) throws SQLException {
		return cacheResultSet.getByte(columnIndex);
	}

	public short getShort(int columnIndex) throws SQLException {
		return cacheResultSet.getShort(columnIndex);
	}

	public long getLong(int columnIndex) throws SQLException {
		return cacheResultSet.getLong(columnIndex);
	}

	public float getFloat(int columnIndex) throws SQLException {
		return cacheResultSet.getFloat(columnIndex);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return cacheResultSet.getDouble(columnIndex);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		return cacheResultSet.getBigDecimal(columnIndex, scale);
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		return cacheResultSet.getBytes(columnIndex);
	}

	public Time getTime(int columnIndex) throws SQLException {
		return cacheResultSet.getTime(columnIndex);
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return cacheResultSet.getAsciiStream(columnIndex);
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return cacheResultSet.getUnicodeStream(columnIndex);
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return cacheResultSet.getBinaryStream(columnIndex);
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		return cacheResultSet.getBoolean(columnLabel);
	}

	public byte getByte(String columnLabel) throws SQLException {
		return cacheResultSet.getByte(columnLabel);
	}

	public short getShort(String columnLabel) throws SQLException {
		return cacheResultSet.getShort(columnLabel);
	}

	public long getLong(String columnLabel) throws SQLException {
		return cacheResultSet.getLong(columnLabel);
	}

	public float getFloat(String columnLabel) throws SQLException {
		return cacheResultSet.getFloat(columnLabel);
	}

	public double getDouble(String columnLabel) throws SQLException {
		return cacheResultSet.getDouble(columnLabel);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		return cacheResultSet.getBigDecimal(columnLabel, scale);
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		return cacheResultSet.getBytes(columnLabel);
	}

	public Time getTime(String columnLabel) throws SQLException {
		return cacheResultSet.getTime(columnLabel);
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return cacheResultSet.getAsciiStream(columnLabel);
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return cacheResultSet.getUnicodeStream(columnLabel);
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return cacheResultSet.getBinaryStream(columnLabel);
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO こちらラッパー側のエラーと合成する必要あり。
		return cacheResultSet.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		// TODO こちらラッパー側のエラーと合成する必要あり。
		cacheResultSet.clearWarnings();
	}

	public String getCursorName() throws SQLException {
		// FIXME
		return globalUniqueKey;
	}

	public Object getObject(int columnIndex) throws SQLException {
		return cacheResultSet.getObject(columnIndex);
	}

	public Object getObject(String columnLabel) throws SQLException {
		return cacheResultSet.getObject(columnLabel);
	}

	public int findColumn(String columnLabel) throws SQLException {
		// TODO チェック必要。直接内部JDBCではなく自力での解決が必要かも。
		return cacheResultSet.findColumn(columnLabel);
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return cacheResultSet.getCharacterStream(columnIndex);
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return cacheResultSet.getCharacterStream(columnLabel);
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return cacheResultSet.getBigDecimal(columnIndex);
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return cacheResultSet.getBigDecimal(columnLabel);
	}

	public void setFetchDirection(int direction) throws SQLException {
		// Direct map to Statement. Because Stmt should have the data.
		stmt.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		// Direct map to Statement. Because Stmt should have the data.
		return stmt.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException("Not Implemented: setFetchSize(int rows)");
	}

	public int getFetchSize() throws SQLException {
		return 20;
	}

	public int getType() throws SQLException {
		// TODO これなに？
		throw new SQLException("Not Implemented: getType()");
	}

	public int getConcurrency() throws SQLException {
		// must be read-only
		return ResultSet.CONCUR_READ_ONLY;
	}

	public Statement getStatement() throws SQLException {
		return stmt;
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		return cacheResultSet.getObject(columnIndex, map);
	}

	public Ref getRef(int columnIndex) throws SQLException {
		return cacheResultSet.getRef(columnIndex);
	}

	public Blob getBlob(int columnIndex) throws SQLException {
		return cacheResultSet.getBlob(columnIndex);
	}

	public Clob getClob(int columnIndex) throws SQLException {
		return cacheResultSet.getClob(columnIndex);
	}

	public Array getArray(int columnIndex) throws SQLException {
		return cacheResultSet.getArray(columnIndex);
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		return cacheResultSet.getObject(columnLabel, map);
	}

	public Ref getRef(String columnLabel) throws SQLException {
		return cacheResultSet.getRef(columnLabel);
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		return cacheResultSet.getBlob(columnLabel);
	}

	public Clob getClob(String columnLabel) throws SQLException {
		return cacheResultSet.getClob(columnLabel);
	}

	public Array getArray(String columnLabel) throws SQLException {
		return cacheResultSet.getArray(columnLabel);
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return cacheResultSet.getDate(columnIndex, cal);
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return cacheResultSet.getDate(columnLabel, cal);
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return cacheResultSet.getTime(columnIndex, cal);
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return cacheResultSet.getTime(columnLabel, cal);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		return cacheResultSet.getTimestamp(columnIndex, cal);
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		return cacheResultSet.getTimestamp(columnLabel, cal);
	}

	public URL getURL(int columnIndex) throws SQLException {
		return cacheResultSet.getURL(columnIndex);
	}

	public URL getURL(String columnLabel) throws SQLException {
		return cacheResultSet.getURL(columnLabel);
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		return cacheResultSet.getRowId(columnIndex);
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		return cacheResultSet.getRowId(columnLabel);
	}

	public int getHoldability() throws SQLException {
		// FIXME!!!
		throw new SQLException("Not Implemented.");
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		return cacheResultSet.getNClob(columnIndex);
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		return cacheResultSet.getNClob(columnLabel);
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return cacheResultSet.getSQLXML(columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return cacheResultSet.getSQLXML(columnLabel);
	}

	public String getNString(int columnIndex) throws SQLException {
		return cacheResultSet.getNString(columnIndex);
	}

	public String getNString(String columnLabel) throws SQLException {
		return cacheResultSet.getNString(columnLabel);
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return cacheResultSet.getNCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return cacheResultSet.getNCharacterStream(columnLabel);
	}

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		return cacheResultSet.getObject(columnIndex, type);
	}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		return cacheResultSet.getObject(columnLabel, type);
	}

	public boolean next() throws SQLException {
		if (isClosed()) {
			return false;
		}

		// check next() in cache.
		if (cacheResultSet.next()) {
			currentRow++;
			return true;
		}

		// cache ended.
		// check next block

		if (stmt.hasNextBlock() == false) {
			return false;
		}

		// TODO DO TRUNCATE BLOCK
		// FIXME

		// truncate table
		// do next block
		stmt.nextBlock();

		// open cache table;
		trialReadResultSetFromCache();

		currentRow++;

		return cacheResultSet.next();
	}

	public int getRow() throws SQLException {
		return currentRow;
	}

	/////////////////////////////////////////////////////////
	// No Cursor related JDBC API supported.

	public boolean previous() throws SQLException {
		if (isClosed()) {
			return false;
		}

		// check previous() in cache.
		if (cacheResultSet.previous()) {
			currentRow--;
			return true;
		}

		// traverse from begining.
		final int targetCurrentRow = currentRow;

		// statement should remember sql
		stmt.firstBlock(null);

		// open cache table;
		trialReadResultSetFromCache();

		int index = 0;

		for (;;) {
			if (this.next() == false) {
				return false;
			}
			index++;
			if (index >= targetCurrentRow) {
				break;
			}
		}

		return true;
	}

	public boolean isBeforeFirst() throws SQLException {
		if (currentRow < 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAfterLast() throws SQLException {
		if (stmt.hasNextBlock() == false) {
			return cacheResultSet.isAfterLast();
		}

		return false;
	}

	public boolean isFirst() throws SQLException {
		if (currentRow == 0) {
			return true;
		}
		return false;
	}

	public boolean isLast() throws SQLException {
		if (stmt.hasNextBlock() == true) {
			return true;
		}

		return cacheResultSet.isLast();
	}

	public void beforeFirst() throws SQLException {
		if (this.first() == false) {
			// TODO これはこまる。
			return;
		}
		this.previous();
	}

	public void afterLast() throws SQLException {
		if (this.last() == false) {
			// TODO これはこまる。
			return;
		}
		this.next();
	}

	public boolean first() throws SQLException {
		// statement should remember sql
		stmt.firstBlock(null);

		// open cache table;
		trialReadResultSetFromCache();

		return this.next();
	}

	public boolean last() throws SQLException {
		for (;;) {
			if (this.next() == false) {
				break;
			}
		}
		return this.previous();
	}

	public boolean absolute(int row) throws SQLException {
		for (;;) {
			if (row == currentRow) {
				// do nothing.
				return true;
			}

			if (row > currentRow) {
				this.next();
			} else {
				this.previous();
			}
		}
	}

	public boolean relative(int rows) throws SQLException {
		for (;;) {
			if (rows == 0) {
				// do nothing.
				return true;
			}

			if (rows > 0) {
				this.next();
				rows--;
			} else {
				this.previous();
				rows++;
			}
		}
	}

	// No Cursor related JDBC API supported.
	/////////////////////////////////////////////////////////

	////////////////////////////////////////////
	// No update supported

	public boolean rowUpdated() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public boolean rowInserted() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public boolean rowDeleted() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNull(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNull(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void insertRow() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateRow() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void deleteRow() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void refreshRow() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void cancelRowUpdates() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void moveToInsertRow() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void moveToCurrentRow() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	/////////

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	//////////

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	/////////////////////

	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	///////////

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	/////////////////////

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_NO_UPDATE_SUPPORTED);
	}

	// No update supported
	////////////////////////////////////////////
}
