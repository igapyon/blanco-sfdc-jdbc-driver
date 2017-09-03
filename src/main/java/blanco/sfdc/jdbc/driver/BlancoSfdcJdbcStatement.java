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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.bind.XmlObject;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcStatement;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSet;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetColumn;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetRow;
import blanco.jdbc.generic.driver.databasemetadata.BlancoGenericJdbcDatabaseMetaDataCacheUtil;

public class BlancoSfdcJdbcStatement extends AbstractBlancoGenericJdbcStatement {
	/**
	 * FIXME ほんとうは、ユニークな何か文字列。
	 */
	final long timeMillis = System.currentTimeMillis();

	protected BlancoGenericJdbcResultSet rs = null;

	public BlancoSfdcJdbcStatement(final BlancoSfdcJdbcConnection conn) {
		super(conn);
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		final BlancoSfdcJdbcConnection pconn = ((BlancoSfdcJdbcConnection) conn);
		pconn.getPartnerConnection().setQueryOptions(rows);
	}

	@Override
	public int getFetchSize() throws SQLException {
		final BlancoSfdcJdbcConnection pconn = ((BlancoSfdcJdbcConnection) conn);
		return pconn.getPartnerConnection().getQueryOptions().getBatchSize();
	}

	protected static void createTableTrial(final Connection connCache, final long timemillisecs) throws SQLException {

		String ddl = "CREATE TABLE GEMA_RS_" + timemillisecs + " (";
		// 一時テーブル名に
		// create temporary table XXXX( 名前はプログラムで決めないとダメみたい。
		// closeのときには、テーブルドロップかしら???
		// for (int index = 0; index < rsmd.getColumnCount(); index++) {
		// if (index != 0) {
		// ddl += ",";
		// }
		// TODO
		// TODO ddl += rsmd.getColumnName(index + 1);
		// ddl += " ";
		// TODO ddl += rsmd.getColumnType(index + 1);
		// }
		ddl += ")";
		System.err.println("ddl=" + ddl);
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		rs = new BlancoGenericJdbcResultSet(this);

		try {
			// TODO そもそもこの処理はResultSet側のフェッチ境界の考慮が必要だが、難易度が高いので一旦保留。
			// TODO ただし、これを解決しないと、巨大な検索結果の際に全件を持ってきてしまうのでまずい実装だと思う。
			QueryResult qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection().query(sql);
			for (;;) {
				final SObject[] sObjs = qryResult.getRecords();
				if (sObjs.length == 0) {
					break;
				}

				buildResultSetMetaData((BlancoSfdcJdbcConnection) conn, timeMillis, sObjs[0]);

				// create table
				createTableTrial(((BlancoSfdcJdbcConnection) conn).getCacheConnection(), timeMillis);

				for (int indexRow = 0; indexRow < sObjs.length; indexRow++) {
					final BlancoGenericJdbcResultSetRow row = getRowObj((BlancoSfdcJdbcConnection) conn,
							sObjs[indexRow], timeMillis);
					rs.getRowList().add(row);
				}
				if (qryResult.isDone()) {
					break;
				}
				// TODO This should be more better.
				qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
						.queryMore(qryResult.getQueryLocator());
				System.out.println("QueryMore!!!");
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

	///////////////////////////
	// common func

	public static BlancoGenericJdbcResultSetRow getRowObj(final BlancoSfdcJdbcConnection conn, final SObject sObj,
			final long timemillisecs) throws SQLException {
		final BlancoGenericJdbcResultSetRow record = new BlancoGenericJdbcResultSetRow();
		// getRowList().add(record);

		final XmlObject xmlSObject = (XmlObject) sObj;

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
				final ResultSet metadataRs = BlancoGenericJdbcDatabaseMetaDataCacheUtil.getColumnsFromCache(
						conn.getCacheConnection(), "GMETA_COLUMNS_" + timemillisecs, null, null, sObjectName,
						obj.getName().getLocalPart());
				metadataRs.next();

				final BlancoGenericJdbcResultSetColumn column = new BlancoGenericJdbcResultSetColumn(metadataRs);
				record.getColumnList().add(column);

				if (obj.getValue() == null) {
					column.setColumnValue("");
				} else {
					column.setColumnValue(obj.getValue().toString());

					// Date変換
					switch (column.getMetaDataColumn().getInt("DATA_TYPE")) {
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
		return record;
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

	public static void buildResultSetMetaData(final BlancoSfdcJdbcConnection conn, final long timemillisecs,
			final SObject resultSetValue) throws SQLException {

		{
			String ddl = BlancoGenericJdbcDatabaseMetaDataCacheUtil.DATABASEMETADATA_COLUMNS_DDL_H2
					.replace("GMETA_COLUMNS", "GMETA_COLUMNS_" + timemillisecs);
			conn.getCacheConnection().createStatement().execute(ddl);
		}

		final XmlObject xmlSObject = (XmlObject) resultSetValue;
		final Iterator<XmlObject> ite = xmlSObject.getChildren();
		final XmlObject objObjectName = (XmlObject) ite.next();

		// First one should be : type, value=Account, children=[]}

		if (objObjectName.getName().getLocalPart().equals("type") == false) {
			System.err.println("FATAL: if(objObjectName.getName().getLocalPart().equals(type)==false){");
		}
		final String sObjectName = objObjectName.getValue().toString();

		// TODO これを、行のユニークに利用できるはず。
		final XmlObject sObjectId = (XmlObject) ite.next();

		int ordinalIndex = 1;
		for (; ite.hasNext(); ordinalIndex++) {
			final XmlObject objChild = (XmlObject) ite.next();
			final ResultSet rsmdRs = conn.getMetaData().getColumns(null, null, sObjectName,
					objChild.getName().getLocalPart());
			try {
				System.out.println("child, Name:" + objChild.getName().getLocalPart());
				System.out.println("child, Value:" + objChild.getValue());
				rsmdRs.next();

				final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement("INSERT INTO GMETA_COLUMNS_"
						+ timemillisecs
						+ " SET TABLE_NAME = ?, COLUMN_NAME = ?, DATA_TYPE = ?, TYPE_NAME = ?, COLUMN_SIZE = ?, DECIMAL_DIGITS = ?, NULLABLE = ?, REMARKS = ?, CHAR_OCTET_LENGTH = ?, ORDINAL_POSITION = ?");
				try {
					pstmt.setString(1, rsmdRs.getString("TABLE_NAME"));
					pstmt.setString(2, rsmdRs.getString("COLUMN_NAME"));
					pstmt.setInt(3, rsmdRs.getInt("DATA_TYPE"));
					pstmt.setString(4, rsmdRs.getString("TYPE_NAME"));
					pstmt.setInt(5, rsmdRs.getInt("COLUMN_SIZE"));
					pstmt.setInt(6, rsmdRs.getInt("DECIMAL_DIGITS"));
					pstmt.setInt(7, rsmdRs.getInt("NULLABLE"));
					pstmt.setString(8, rsmdRs.getString("REMARKS"));
					pstmt.setInt(9, rsmdRs.getInt("CHAR_OCTET_LENGTH"));
					pstmt.setInt(10, ordinalIndex);
					pstmt.execute();
				} finally {
					pstmt.close();
				}
			} finally {
				rsmdRs.close();
			}
		}
	}
}
