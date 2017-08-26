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

import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;

import blanco.sfdc.jdbc.driver.simple.BlancoSfdcJdbcSimpleSizedResultSet;

public class BlancoSfdcJdbcResultSet extends BlancoSfdcJdbcSimpleSizedResultSet {
	protected List<SObject> resultSetValueList;

	protected boolean isClosed = false;

	public BlancoSfdcJdbcResultSet(final Statement stmt, final List<SObject> resultsetValues) {
		super(stmt, resultsetValues.size());
		this.resultSetValueList = resultsetValues;
	}

	@Override
	public void close() throws SQLException {
		super.close();
		resultSetValueList = null;
	}

	public boolean wasNull() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getString(int columnIndex) throws SQLException {
		final XmlObject xmlObj = (XmlObject) resultSetValueList.get(resultSetIndex);

		final Iterator<XmlObject> ite = xmlObj.getChildren();
		int index = 0;
		for (; ite.hasNext(); index++) {
			final XmlObject obj = (XmlObject) ite.next();

			// First one should be : type, value=Account, children=[]}
			// Second one should be : Id, value=0012800000lbaM2AAI, children=[]}

			// 1 origin for getString
			if ((index + 1 - 2) == columnIndex) {
				if (obj.getValue() == null) {
					return "";
				}
				return obj.getValue().toString();
			}
		}

		throw new SQLException("No such column index [" + columnIndex + "]");
	}

	public String getString(final String columnLabel) throws SQLException {
		final XmlObject xmlObj = (XmlObject) resultSetValueList.get(resultSetIndex);

		final Iterator<XmlObject> ite = xmlObj.getChildren();
		for (; ite.hasNext();) {
			final XmlObject obj = (XmlObject) ite.next();
			if (obj.getName().getLocalPart().compareToIgnoreCase(columnLabel) == 0) {
				if (obj.getValue() == null) {
					return "";
				}
				return obj.getValue().toString();
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
		// TODO Auto-generated method stub
		return null;
	}
}
