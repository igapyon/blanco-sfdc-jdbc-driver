/*
 *  blanco-jdbc-driver-simple
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
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class BlancoGenericJdbcResultSet implements ResultSet {
	private Statement stmt = null;

	private boolean isClosed = false;

	protected List<BlancoGenericJdbcResultSetRow> rowList = new ArrayList<BlancoGenericJdbcResultSetRow>();
	protected int rowIndex = -1;

	public BlancoGenericJdbcResultSet(final Statement stmt) {
		this.stmt = stmt;
	}

	public void close() throws SQLException {
		isClosed = true;
		rowIndex = -1;
	}

	public List<BlancoGenericJdbcResultSetRow> getRowList() {
		return rowList;
	}

	public String getString(final int columnIndex) throws SQLException {
		final BlancoGenericJdbcResultSetColumn column = getRowList().get(rowIndex).getColumnList().get(columnIndex - 1);
		return column.getColumnValue();
	}

	public String getString(final String columnLabel) throws SQLException {
		for (int index = 0; index < getRowList().get(rowIndex).getColumnList().size(); index++) {
			final BlancoGenericJdbcResultSetColumn item = getRowList().get(rowIndex).getColumnList().get(index);
			if (item.getColumnName().compareToIgnoreCase(columnLabel) == 0) {
				return item.getColumnValue();
			}
		}

		throw new SQLException("No such column [" + columnLabel + "]");
	}

	public Date getDate(int columnIndex) throws SQLException {
		final BlancoGenericJdbcResultSetColumn column = getRowList().get(rowIndex).getColumnList().get(columnIndex - 1);
		if (column.getColumnValueByDate() == null) {
			return null;
		} else {
			return new java.sql.Date(column.getColumnValueByDate().getTime());
		}
	}

	public Date getDate(String columnLabel) throws SQLException {
		for (int index = 0; index < getRowList().get(rowIndex).getColumnList().size(); index++) {
			final BlancoGenericJdbcResultSetColumn item = getRowList().get(rowIndex).getColumnList().get(index);
			if (item.getColumnName().compareToIgnoreCase(columnLabel) == 0) {
				if (item.getColumnValueByDate() == null) {
					return null;
				} else {
					return new java.sql.Date(item.getColumnValueByDate().getTime());
				}
			}
		}

		throw new SQLException("No such column [" + columnLabel + "]");
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		final BlancoGenericJdbcResultSetColumn column = getRowList().get(rowIndex).getColumnList().get(columnIndex - 1);
		if (column.getColumnValueByDate() == null) {
			return null;
		} else {
			return new java.sql.Timestamp(column.getColumnValueByDate().getTime());
		}
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		for (int index = 0; index < getRowList().get(rowIndex).getColumnList().size(); index++) {
			final BlancoGenericJdbcResultSetColumn item = getRowList().get(rowIndex).getColumnList().get(index);
			if (item.getColumnName().compareToIgnoreCase(columnLabel) == 0) {
				if (item.getColumnValueByDate() == null) {
					return null;
				} else {
					return new java.sql.Timestamp(item.getColumnValueByDate().getTime());
				}
			}
		}

		throw new SQLException("No such column [" + columnLabel + "]");
	}

	public int getInt(int columnIndex) throws SQLException {
		final BlancoGenericJdbcResultSetColumn column = getRowList().get(rowIndex).getColumnList().get(columnIndex - 1);
		return column.getColumnValueByInteger();
	}

	public int getInt(String columnLabel) throws SQLException {
		for (int index = 0; index < getRowList().get(rowIndex).getColumnList().size(); index++) {
			final BlancoGenericJdbcResultSetColumn item = getRowList().get(rowIndex).getColumnList().get(index);
			if (item.getColumnName().compareToIgnoreCase(columnLabel) == 0) {
				return item.getColumnValueByInteger();
			}
		}

		throw new SQLException("No such column [" + columnLabel + "]");
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean wasNull() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public byte getByte(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
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
		throw new SQLException("Not Implemented.");
	}

	public int getFetchDirection() throws SQLException {
		throw new SQLException("Not Implemented.");
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
		return CONCUR_READ_ONLY;
	}

	public boolean rowUpdated() throws SQLException {
		return false;
	}

	public boolean rowInserted() throws SQLException {
		return false;
	}

	public boolean rowDeleted() throws SQLException {
		return false;
	}

	public void updateNull(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNull(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void insertRow() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateRow() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void deleteRow() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void refreshRow() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void cancelRowUpdates() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void moveToInsertRow() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void moveToCurrentRow() throws SQLException {
		throw new SQLException("Not Implemented.");
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

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getHoldability() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new SQLException("Not Implemented.");
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

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
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

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
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

	public boolean previous() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (getCurrentRecordCount() <= 0) {
			return false;
		}

		rowIndex--;

		if (rowIndex >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBeforeFirst() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (rowIndex < 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAfterLast() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (rowIndex >= getCurrentRecordCount()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFirst() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (rowIndex == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLast() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if ((rowIndex + 1) == getCurrentRecordCount()) {
			return true;
		} else {
			return false;
		}
	}

	public void beforeFirst() throws SQLException {
		rowIndex = (-1);
	}

	public void afterLast() throws SQLException {

		rowIndex = getCurrentRecordCount();
	}

	public boolean first() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (getCurrentRecordCount() <= 0) {
			return false;
		}

		rowIndex = 0;
		return true;
	}

	public boolean last() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (getCurrentRecordCount() <= 0) {
			return false;
		}

		rowIndex = getCurrentRecordCount() - 1;
		return true;
	}

	public int getRow() throws SQLException {
		return rowIndex;
	}

	public boolean absolute(int row) throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (getCurrentRecordCount() <= 0) {
			return false;
		}

		if (row < 0) {
			return false;
		}

		if (row >= getCurrentRecordCount()) {
			return false;
		}

		rowIndex = row;

		return true;
	}

	public boolean relative(int rows) throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (getCurrentRecordCount() <= 0) {
			return false;
		}

		if ((rowIndex + rows) < 0) {
			return false;
		}

		if ((rowIndex + rows) >= getCurrentRecordCount()) {
			return false;
		}

		rowIndex += rows;

		return true;
	}
}