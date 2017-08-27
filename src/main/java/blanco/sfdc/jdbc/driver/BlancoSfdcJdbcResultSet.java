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

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.bind.XmlObject;

import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSet;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaData;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaDataColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;

public class BlancoSfdcJdbcResultSet extends BlancoJdbcSimpleResultSet {
	protected boolean isClosed = false;

	protected BlancoJdbcSimpleResultSetMetaData rsmd = null;

	protected String sObjectName = null;

	public BlancoSfdcJdbcResultSet(final Statement stmt, final List<SObject> resultSetValueList) {
		super(stmt);

		for (int indexRow = 0; indexRow < resultSetValueList.size(); indexRow++) {
			final BlancoJdbcSimpleResultSetRow record = new BlancoJdbcSimpleResultSetRow();
			getRowList().add(record);

			// 初回に ResultSetMetaDataをセットする必要あり。

			final XmlObject xmlSObject = (XmlObject) resultSetValueList.get(indexRow);

			String rowIdString = "";

			final Iterator<XmlObject> ite = xmlSObject.getChildren();
			int index = 0;
			for (; ite.hasNext(); index++) {
				final XmlObject obj = (XmlObject) ite.next();

				// System.out.println(obj);

				// First one should be : type, value=Account, children=[]}
				// Second one should be : Id, value=0012800000lbaM2AAI,
				// children=[]}

				if (index == 0) {
					sObjectName = obj.getValue().toString();
				} else if (index == 1) {
					rowIdString = obj.getValue().toString();
				} else {
					// 1 origin for getString

					final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = new BlancoJdbcSimpleResultSetMetaDataColumn();
					metaDataColumn.setColumnName(obj.getName().getLocalPart());
					// FIXME
					// TODO

					final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(metaDataColumn);
					record.getColumnList().add(column);

					// TODO 型情報が必要。がxmlからは得られない。object名称から逆引きか？？

					if (obj.getValue() == null) {
						column.setColumnValue("");
					} else {
						column.setColumnValue(obj.getValue().toString());

						// TODO date変換
					}

					// TODO tablename?
					// TODO set Object ID?
				}
			}
		}
	}

	public boolean wasNull() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getString(int columnIndex) throws SQLException {
		return getRowList().get(rowIndex).getColumnList().get(columnIndex - 1).getColumnValue();
	}

	public String getString(final String columnLabel) throws SQLException {
		for (int index = 0; index < getRowList().get(rowIndex).getColumnList().size(); index++) {
			final BlancoJdbcSimpleResultSetColumn item = getRowList().get(rowIndex).getColumnList().get(index);
			if (item.getColumnName().compareToIgnoreCase(columnLabel) == 0) {
				return item.getColumnValue();
			}
		}

		throw new SQLException("No such column [" + columnLabel + "]");
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

	public int getInt(int columnIndex) throws SQLException {
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

	protected static java.util.Date soqlDateToDate(final String soqlDateString) {
		if (soqlDateString == null || soqlDateString.length() == 0) {
			return null;
		}

		String workDateString = soqlDateString.replace("T", " ");
		workDateString = workDateString.replace("Z", "");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		// FIXME TODO DO NOT USE JST.
		final TimeZone jst = TimeZone.getTimeZone("JST");

		try {
			java.util.Date result = sdf.parse(workDateString);

			final Calendar calForGmt = Calendar.getInstance();
			calForGmt.setTime(result);
			calForGmt.add(Calendar.MILLISECOND, jst.getOffset(result.getTime()));
			result = calForGmt.getTime();

			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public Date getDate(final int columnIndex) throws SQLException {
		final String soqlDateString = getString(columnIndex);
		final java.util.Date date = soqlDateToDate(soqlDateString);
		return new Date(date.getTime());
	}

	public Date getDate(final String columnLabel) throws SQLException {
		final String soqlDateString = getString(columnLabel);
		final java.util.Date date = soqlDateToDate(soqlDateString);
		return new Date(date.getTime());
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		if (rsmd != null) {
			return rsmd;
		}
		rsmd = new BlancoJdbcSimpleResultSetMetaData();

		final BlancoSfdcJdbcConnection pconn = ((BlancoSfdcJdbcConnection) ((BlancoSfdcJdbcStatement) getStatement())
				.getConnection());
		try {
			final DescribeSObjectResult descSObjResult = pconn.getPartnerConnection().describeSObject(sObjectName);
			final Field[] fields = descSObjResult.getFields();

			for (Field field : fields) {
				final BlancoJdbcSimpleResultSetMetaDataColumn column = new BlancoJdbcSimpleResultSetMetaDataColumn();
				column.setColumnName(field.getName());
				// field.getAutoNumber()
				// field.getByteLength()
				// field.getCompoundFieldName()
				// field.getControllerName()
				// field.getCustom()
				// field.getDigits()
				// field.getPicklistValues()

				System.out.println(field.getType().toString());
				// FIXME
				column.setDataType(java.sql.Types.VARCHAR);
				column.setTypeName(field.getType().toString());

				column.setNullable(field.getNillable());

				column.setColumnLabel(field.getLabel());

				// ORDINAL_POSITION int => テーブル中の列のインデックス (1 から始まる)
				// protected int ordinalPosition = -1;

				// NUM_PREC_RADIX int 10;
				// SCOPE_CATLOG null
				// SCOPE_SCHEMA null
				// TABLE_CAT null
				// TABLE_SCHEM null
				// COLUMN_DEF null
				// SCOPE_TABLE String null
				// IS_AUTOINCREMENT String ""

				column.setColumnSize(field.getLength());
				// TODO : CHAR_OCTET_LENGTH int

				column.setTableName(sObjectName);
				column.setColumnLabel(field.getLabel());
				column.setPrecision(field.getPrecision());
				column.setScale(field.getScale());
				column.setRemarks(field.getLabel());

				rsmd.getColumnList().add(column);
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		return rsmd;
	}
}
