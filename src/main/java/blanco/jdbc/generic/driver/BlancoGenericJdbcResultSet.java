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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public class BlancoGenericJdbcResultSet implements ResultSet {
	private Statement stmt = null;

	protected long timeMillis = -1;

	private boolean isClosed = false;

	protected ResultSet trialReadResultSet = null;

	protected List<BlancoGenericJdbcResultSetRow> rowList = new ArrayList<BlancoGenericJdbcResultSetRow>();
	protected int rowIndex = -1;

	public BlancoGenericJdbcResultSet(final Statement stmt, final long timeMillis) {
		this.stmt = stmt;
		this.timeMillis = timeMillis;
	}

	public void close() throws SQLException {
		isClosed = true;
		rowIndex = -1;

		trialReadResultSet.close();
	}

	public void trialReadResultSetFromCache() throws SQLException {
		// FIXME ORDER BY
		trialReadResultSet = ((BlancoSfdcJdbcConnection) stmt.getConnection()).getCacheConnection().createStatement()
				.executeQuery("SELECT * FROM GEMA_RS_" + timeMillis);

	}

	public List<BlancoGenericJdbcResultSetRow> getRowList() {
		return rowList;
	}

	public String getString(final int columnIndex) throws SQLException {
		return trialReadResultSet.getString(columnIndex);
	}

	public String getString(final String columnLabel) throws SQLException {
		return trialReadResultSet.getString(columnLabel);
	}

	public Date getDate(int columnIndex) throws SQLException {
		return trialReadResultSet.getDate(columnIndex);
	}

	public Date getDate(String columnLabel) throws SQLException {
		return trialReadResultSet.getDate(columnLabel);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return trialReadResultSet.getTimestamp(columnIndex);
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return trialReadResultSet.getTimestamp(columnLabel);
	}

	public int getInt(int columnIndex) throws SQLException {
		return trialReadResultSet.getInt(columnIndex);
	}

	public int getInt(String columnLabel) throws SQLException {
		return trialReadResultSet.getInt(columnLabel);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO 内部で持っている metadata を返却可能なはず。
		throw new SQLException("Not Implemented.");
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean wasNull() throws SQLException {
		return trialReadResultSet.wasNull();
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return trialReadResultSet.getBoolean(columnIndex);
	}

	public byte getByte(int columnIndex) throws SQLException {
		return trialReadResultSet.getByte(columnIndex);
	}

	public short getShort(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public long getLong(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public float getFloat(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public double getDouble(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Time getTime(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public byte getByte(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public short getShort(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public long getLong(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public float getFloat(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public double getDouble(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Time getTime(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(int columnIndex) throws SQLException {
		// for ease
		return getString(columnIndex);
	}

	public Object getObject(String columnLabel) throws SQLException {
		// for ease
		return getString(columnLabel);
	}

	public int findColumn(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void setFetchDirection(int direction) throws SQLException {
		// Direct map to Statement.
		stmt.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		// Direct map to Statement.
		return stmt.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getFetchSize() throws SQLException {
		return 10;
	}

	public int getType() throws SQLException {
		// TODO
		throw new SQLException("Not Implemented.");
	}

	public int getConcurrency() throws SQLException {
		// must be read-only
		return ResultSet.CONCUR_READ_ONLY;
	}

	public Statement getStatement() throws SQLException {
		return stmt;
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Ref getRef(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Blob getBlob(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Clob getClob(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Array getArray(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Ref getRef(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Clob getClob(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Array getArray(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public URL getURL(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public URL getURL(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getHoldability() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getNString(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getNString(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	protected int getCurrentRecordCount() {
		return rowList.size();
	}

	public boolean next() throws SQLException {
		if (isClosed()) {
			return false;
		}

		// TODO 次のブロック読み込みの考慮
		trialReadResultSet.next();

		if (getCurrentRecordCount() <= 0) {
			return false;
		}

		rowIndex++;

		if (rowIndex < getCurrentRecordCount()) {
			return true;
		} else {
			return false;
		}
	}

	public int getRow() throws SQLException {
		return rowIndex;
	}

	/////////////////////////////////////////////////////////
	// No Cursor related JDBC API supported.

	public boolean previous() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean isBeforeFirst() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean isAfterLast() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean isFirst() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean isLast() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public void beforeFirst() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public void afterLast() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean first() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean last() throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean absolute(int row) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
	}

	public boolean relative(int rows) throws SQLException {
		throw new SQLFeatureNotSupportedException(BlancoGenericJdbcConstants.MESSAGE_FORWARD_ONLY_SUPPORTED);
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
