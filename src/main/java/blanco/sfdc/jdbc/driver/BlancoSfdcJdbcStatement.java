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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.bind.XmlObject;

import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaData;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetMetaDataColumn;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleResultSetRow;
import blanco.jdbc.driver.simple.BlancoJdbcSimpleStatement;

public class BlancoSfdcJdbcStatement extends BlancoJdbcSimpleStatement {
	protected final List<SObject> resultSetValueList = new ArrayList<SObject>();

	/**
	 * NOTE: static field!!!
	 */
	protected static BlancoJdbcSimpleResultSetMetaData rsmd = null;

	public BlancoSfdcJdbcStatement(final BlancoSfdcJdbcConnection conn) {
		super(conn);
	}

	public static java.util.Date soqlDateToDate(final String soqlDateString) {
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

	public static BlancoJdbcSimpleResultSetMetaData getResultSetMetaData(final BlancoSfdcJdbcConnection conn,
			final SObject resultSetValue) throws SQLException {
		final BlancoJdbcSimpleResultSetMetaData rsmd = new BlancoJdbcSimpleResultSetMetaData();

		final XmlObject xmlSObject = (XmlObject) resultSetValue;
		final Iterator<XmlObject> ite = xmlSObject.getChildren();
		final XmlObject obj = (XmlObject) ite.next();

		// First one should be : type, value=Account, children=[]}

		final String sObjectName = obj.getValue().toString();

		{
			final ResultSet rsmdRs = conn.getMetaData().getColumns(null, null, sObjectName, null);
			for (; rsmdRs.next();) {
				final BlancoJdbcSimpleResultSetMetaDataColumn column = new BlancoJdbcSimpleResultSetMetaDataColumn();
				column.setColumnName(rsmdRs.getString("COLUMN_NAME"));
				// column.setdatatype
				System.err.println("  " + rsmdRs.getString("DATA_TYPE"));

				column.setTypeName(rsmdRs.getString("TYPE_NAME"));
				column.setTableName(rsmdRs.getString("TABLE_NAME"));
				column.setColumnSize(Integer.parseInt(rsmdRs.getString("COLUMN_SIZE")));
				column.setRemarks(rsmdRs.getString("REMARKS"));
				// column.settableCat(rsmdRs.getString("TABLE_CAT"));
				// rsmdRs.getString("TABLE_SCHEM"));
				column.setNullable("true".equals(rsmdRs.getString("NULLABLE")));
				// rsmdRs.getString("IS_NULLABLE");// discard
				// rsmdRs.getString("ORDINAL_POSITION");// discard
				// rsmdRs.getString("SCOPE_TABLE"); // discard

				rsmd.getColumnList().add(column);
			}
			rsmdRs.close();
		}

		return rsmd;
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		try {
			// TODO そもそもこの処理はResultSet側のフェッチ境界の考慮が必要だが、難易度が高いので一旦保留。
			// TODO ただし、これを解決しないと、巨大な検索結果の際に全件を持ってきてしまうのでまずい実装だと思う。
			QueryResult qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection().query(sql);
			for (;;) {
				final SObject[] sObjs = qryResult.getRecords();
				if (sObjs.length == 0) {
					break;
				}

				if (rsmd == null) {
					rsmd = getResultSetMetaData((BlancoSfdcJdbcConnection) conn, sObjs[0]);
				}

				for (int indexRow = 0; indexRow < sObjs.length; indexRow++) {
					final BlancoJdbcSimpleResultSetRow record = new BlancoJdbcSimpleResultSetRow();
					// getRowList().add(record);

					final XmlObject xmlSObject = (XmlObject) sObjs[indexRow];

					// rsmd.getColumnByColumnName(columnName)

					final Iterator<XmlObject> ite = xmlSObject.getChildren();
					int indexColumn = 0;
					for (; ite.hasNext(); indexColumn++) {
						final XmlObject obj = (XmlObject) ite.next();

						// First one should be : type, value=Account,
						// children=[]}
						// Second one should be : Id, value=0012800000lbaM2AAI,
						// children=[]}

						String sObjectName = null;
						String rowIdString = null;

						if (indexColumn == 0) {
							sObjectName = obj.getValue().toString();
						} else if (indexColumn == 1) {
							rowIdString = obj.getValue().toString();
						} else {
							// 1 origin for getString
							final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = rsmd
									.getColumnByColumnName(obj.getName().getLocalPart());

							final BlancoJdbcSimpleResultSetColumn column = new BlancoJdbcSimpleResultSetColumn(
									metaDataColumn);
							record.getColumnList().add(column);

							if (obj.getValue() == null) {
								column.setColumnValue("");
							} else {
								column.setColumnValue(obj.getValue().toString());

								// Date変換
								switch (column.getMetaDataColumn().getDataType()) {
								case java.sql.Types.DATE:
								case java.sql.Types.TIME:
								case java.sql.Types.TIME_WITH_TIMEZONE:
								case java.sql.Types.TIMESTAMP:
								case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
									column.setColumnValueByDate(soqlDateToDate(column.getColumnValue()));
									break;
								}
							}

							// TODO tablename?
							// TODO set Object ID?
						}
					}

					resultSetValueList.add(sObjs[indexColumn]);
				}
				if (qryResult.isDone()) {
					break;
				}
				// TODO This should be more better.
				qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
						.queryMore(qryResult.getQueryLocator());
			}

		} catch (

		ConnectionException ex) {
			throw new SQLException(ex);
		}

		return true;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// 標準共通の方のしょりで動くように変更したい。
		return new BlancoSfdcJdbcResultSet(this, resultSetValueList);
	}
}
