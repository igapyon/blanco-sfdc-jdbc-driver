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

import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcConnection;
import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilDatabaseMetaData;

public class BlancoSfdcJdbcFillCacheCommon {
	public static final boolean IS_DEBUG = false;

	public static final String getInsertIntoGmetaColumnsSql(final String globalUniqueKey) {
		return "INSERT INTO GMETA_COLUMNS_" + globalUniqueKey
				+ " SET TABLE_CAT = ?, TABLE_SCHEM = ?, TABLE_NAME = ?, COLUMN_NAME = ?, DATA_TYPE = ?, TYPE_NAME = ?" //
				+ ", COLUMN_SIZE = ?, DECIMAL_DIGITS = ?, NUM_PREC_RADIX = ?, NULLABLE = ?, COLUMN_DEF= ?, REMARKS = ?" //
				+ ", SQL_DATA_TYPE = ?, SQL_DATETIME_SUB = ?, CHAR_OCTET_LENGTH = ?, ORDINAL_POSITION = ?" //
				+ " , IS_NULLABLE = ?, SCOPE_CATALOG = ?, SCOPE_SCHEMA = ?, SCOPE_TABLE = ?, SOURCE_DATA_TYPE = ?" //
				+ ", IS_AUTOINCREMENT = ?, IS_GENERATEDCOLUMN = ?";
	}

	/**
	 * ０件の検索結果
	 */
	public static void fillCacheTableOfResultSetMetaDataNOTFOUND(final AbstractBlancoGenericJdbcConnection conn,
			final String globalUniqueKey) throws SQLException {

		// FIXME 仮でアカウントから借りてきています。
		final ResultSet rsmdRs = conn.getMetaData().getColumns(null, null, "Account", "Name");
		try {
			// System.out.println("child, Name:" +
			// objChild.getName().getLocalPart());
			// System.out.println("child, Value:" + objChild.getValue());
			rsmdRs.next();

			final PreparedStatement pstmt = conn.getCacheConnection()
					.prepareStatement(getInsertIntoGmetaColumnsSql(globalUniqueKey));
			try {
				int rowNum = 1;

				pstmt.setString(rowNum++, rsmdRs.getString("TABLE_CAT"));
				pstmt.setString(rowNum++, rsmdRs.getString("TABLE_SCHEM"));
				pstmt.setString(rowNum++, rsmdRs.getString("TABLE_NAME"));
				pstmt.setString(rowNum++, rsmdRs.getString("COLUMN_NAME"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("DATA_TYPE"));
				pstmt.setString(rowNum++, rsmdRs.getString("TYPE_NAME"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("COLUMN_SIZE"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("DECIMAL_DIGITS"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("NUM_PREC_RADIX"));

				pstmt.setInt(rowNum++, rsmdRs.getInt("NULLABLE"));
				pstmt.setString(rowNum++, rsmdRs.getString("COLUMN_DEF"));
				pstmt.setString(rowNum++, rsmdRs.getString("REMARKS"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("SQL_DATA_TYPE"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("SQL_DATETIME_SUB"));
				pstmt.setInt(rowNum++, rsmdRs.getInt("CHAR_OCTET_LENGTH"));

				// ORDINAL_POSITION should not populate
				pstmt.setInt(rowNum++, 1);

				pstmt.setString(rowNum++, rsmdRs.getString("IS_NULLABLE"));
				pstmt.setString(rowNum++, rsmdRs.getString("SCOPE_CATALOG"));
				pstmt.setString(rowNum++, rsmdRs.getString("SCOPE_SCHEMA"));
				pstmt.setString(rowNum++, rsmdRs.getString("SCOPE_TABLE"));
				pstmt.setString(rowNum++, rsmdRs.getString("SOURCE_DATA_TYPE"));
				pstmt.setString(rowNum++, rsmdRs.getString("IS_AUTOINCREMENT"));
				pstmt.setString(rowNum++, rsmdRs.getString("IS_GENERATEDCOLUMN"));

				pstmt.execute();
			} finally {
				pstmt.close();
			}
		} finally {
			rsmdRs.close();
		}
	}

	/**
	 * Investigate result of query and fill ResultSetMetaData
	 * 
	 * @param conn
	 * @param globalUniqueKey
	 * @param responseOfPartner
	 * @throws SQLException
	 */
	public static void fillCacheTableOfResultSetMetaData(final AbstractBlancoGenericJdbcConnection conn,
			final String globalUniqueKey, final SObject responseOfPartner) throws SQLException {

		final XmlObject xmlSObject = (XmlObject) responseOfPartner;
		final Iterator<XmlObject> ite = xmlSObject.getChildren();
		final XmlObject objObjectName = (XmlObject) ite.next();

		// First one should be : type, value=Account, children=[]}

		if (objObjectName.getName().getLocalPart().equals("type") == false) {
			System.err.println("FATAL: if(objObjectName.getName().getLocalPart().equals(type)==false){");
		}
		final String sObjectName = objObjectName.getValue().toString();

		// TODO オブジェクトIDですが、これは不要. 読み飛ばし。
		final XmlObject sObjectId = (XmlObject) ite.next();

		for (int ordinalIndex = 1; ite.hasNext(); ordinalIndex++) {
			final XmlObject objChild = (XmlObject) ite.next();
			ResultSet rsmdRs = conn.getMetaData().getColumns(null, null, sObjectName,
					objChild.getName().getLocalPart());
			try {
				// System.out.println("child, Name:" +
				// objChild.getName().getLocalPart());
				// System.out.println("child, Value:" + objChild.getValue());
				if (rsmdRs.next() == false) {
					// 見つからなかったのでダミー利用。
					rsmdRs = conn.getMetaData().getColumns(null, null, "GMETA_STRING_COLUMN", "GMETA_STRING_COLUMN");
					if (rsmdRs.next() == false) {
						throw new SQLException("IllegalCase: NOT FOUND GMETA_STRING_COLUMN.");
					}
				}

				final PreparedStatement pstmt = conn.getCacheConnection()
						.prepareStatement(getInsertIntoGmetaColumnsSql(globalUniqueKey));
				try {
					int rowNum = 1;

					pstmt.setString(rowNum++, rsmdRs.getString("TABLE_CAT"));
					pstmt.setString(rowNum++, rsmdRs.getString("TABLE_SCHEM"));
					pstmt.setString(rowNum++, rsmdRs.getString("TABLE_NAME"));
					pstmt.setString(rowNum++, rsmdRs.getString("COLUMN_NAME"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("DATA_TYPE"));
					pstmt.setString(rowNum++, rsmdRs.getString("TYPE_NAME"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("COLUMN_SIZE"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("DECIMAL_DIGITS"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("NUM_PREC_RADIX"));

					pstmt.setInt(rowNum++, rsmdRs.getInt("NULLABLE"));
					pstmt.setString(rowNum++, rsmdRs.getString("COLUMN_DEF"));
					pstmt.setString(rowNum++, rsmdRs.getString("REMARKS"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("SQL_DATA_TYPE"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("SQL_DATETIME_SUB"));
					pstmt.setInt(rowNum++, rsmdRs.getInt("CHAR_OCTET_LENGTH"));

					// ORDINAL_POSITION should not populate
					pstmt.setInt(rowNum++, ordinalIndex);

					pstmt.setString(rowNum++, rsmdRs.getString("IS_NULLABLE"));
					pstmt.setString(rowNum++, rsmdRs.getString("SCOPE_CATALOG"));
					pstmt.setString(rowNum++, rsmdRs.getString("SCOPE_SCHEMA"));
					pstmt.setString(rowNum++, rsmdRs.getString("SCOPE_TABLE"));
					pstmt.setString(rowNum++, rsmdRs.getString("SOURCE_DATA_TYPE"));
					pstmt.setString(rowNum++, rsmdRs.getString("IS_AUTOINCREMENT"));
					pstmt.setString(rowNum++, rsmdRs.getString("IS_GENERATEDCOLUMN"));

					pstmt.execute();
				} finally {
					pstmt.close();
				}
			} finally {
				rsmdRs.close();
			}
		}
	}

	/**
	 * Fill ResultSet
	 * 
	 * @param connCache
	 * @param globalUniqueKey
	 * @param sObjs
	 * @throws SQLException
	 */
	public static void fillCacheTableOfResultSet(final Connection connCache, final String globalUniqueKey,
			final XmlObject[] sObjs) throws SQLException {
		if (IS_DEBUG)
			System.err.println("TRACE: BlancoSfdcJdbcFillCacheCommon#fillCacheTableOfResultSet");
		for (int indexRow = 0; indexRow < sObjs.length; indexRow++) {

			final XmlObject xmlSObject = sObjs[indexRow];

			String sql = "INSERT INTO GMETA_RS_" + globalUniqueKey + " SET ";
			{
				final Iterator<XmlObject> ite = xmlSObject.getChildren();
				int indexColumn = 0;
				for (; ite.hasNext();) {
					final XmlObject obj = (XmlObject) ite.next();

					// First one should be : type, value=Account,
					// children=[]}
					// Second one should be : Id, value=0012800000lbaM2AAI,
					// children=[]}

					if (obj.getName().equals("BillingAddress") || obj.getName().equals("ShippingAddress")) {
						// FIXME 他の方法でスキップするすべを探すべき。
						continue;
					}

					String sObjectName = null;
					String rowIdString = null;

					if (indexColumn == 0) {
						if (IS_DEBUG)
							System.err.println("TRACE: XmlObject#0: " + obj.toString());
						if ("type".equals(obj.getName().getLocalPart()) == false) {
							throw new SQLException("Unexpected result it must be 'type': " + obj.toString());
						}
						sObjectName = obj.getValue().toString();
					} else if (indexColumn == 1) {
						if (IS_DEBUG)
							System.err.println("TRACE: XmlObject#1: " + obj.toString());
						if ("Id".compareToIgnoreCase(obj.getName().getLocalPart()) != 0) {
							throw new SQLException("Unexpected result it must be 'Id': " + obj.toString());
						}
						if (obj.getValue() == null) {
							rowIdString = null;
						} else {
							rowIdString = obj.getValue().toString();
						}
					} else {
						if (IS_DEBUG)
							System.err.println("TRACE: XmlObject#" + indexColumn + ": " + obj.toString());
						ResultSet metadataRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(connCache,
								"GMETA_COLUMNS_" + globalUniqueKey, null, null, sObjectName,
								obj.getName().getLocalPart());
						if (metadataRs.next() == false) {
							// 落ちるのを防止。
							// COUNT(Id) の際にここに入るはず。
							metadataRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(connCache,
									"GMETA_COLUMNS_" + globalUniqueKey, null, null, sObjectName, "GMETA_STRING_COLUMN");
							if (metadataRs.next() == false) {
								throw new SQLException("ここに入ってはダメ:270");
							}
						}

						if (obj.getValue() != null) {
							// null のときはなにもしない。

							if (indexColumn != 2) {
								sql += ",";
							}

							sql += (metadataRs.getString("COLUMN_NAME") + " = ?");
						}

						// TODO tablename?
						// TODO set Object ID?
					}
					indexColumn++;
				}
			}

			final PreparedStatement pstmt = connCache.prepareStatement(sql);

			{
				int pstmtIndex = 1;
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
						if (obj.getValue() == null) {
							rowIdString = null;
						} else {
							rowIdString = obj.getValue().toString();
						}
					} else {
						ResultSet metadataRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(connCache,
								"GMETA_COLUMNS_" + globalUniqueKey, null, null, sObjectName,
								obj.getName().getLocalPart());
						if (metadataRs.next() == false) {
							// 落ちるのを防止。
							// COUNT(Id) の際にここに入るはず。
							metadataRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(connCache,
									"GMETA_COLUMNS_" + globalUniqueKey, null, null, sObjectName, "GMETA_STRING_COLUMN");
							if (metadataRs.next() == false) {
								throw new SQLException("ここに入ってはダメ:326");
							}
						}

						if (obj.getValue() != null) {
							// null のときはなにもしない。

							String value = obj.getValue().toString();

							// DATA変換
							switch (metadataRs.getInt("DATA_TYPE")) {
							case java.sql.Types.VARCHAR:
							case java.sql.Types.LONGVARCHAR:
								pstmt.setString(pstmtIndex++, value);
								break;
							case java.sql.Types.BIT:
							case java.sql.Types.BOOLEAN:
								pstmt.setBoolean(pstmtIndex++, Boolean.valueOf(value));
								break;
							case java.sql.Types.TINYINT:
								// FIXME
								pstmt.setShort(pstmtIndex++, Short.valueOf(value));
								break;
							case java.sql.Types.SMALLINT:
								pstmt.setShort(pstmtIndex++, Short.valueOf(value));
								break;
							case java.sql.Types.INTEGER:
								pstmt.setInt(pstmtIndex++, Integer.valueOf(value));
								break;
							case java.sql.Types.BIGINT:
								pstmt.setLong(pstmtIndex++, Long.valueOf(value));
								break;
							case java.sql.Types.DOUBLE:
								pstmt.setDouble(pstmtIndex++, Double.valueOf(value));
								break;
							case java.sql.Types.DATE:
							case java.sql.Types.TIME: /// ????????
							case java.sql.Types.TIME_WITH_TIMEZONE: /// ???????????????
								pstmt.setDate(pstmtIndex++, new java.sql.Date(soqlDateToDate(value).getTime()));
								break;
							case java.sql.Types.TIMESTAMP:
							case java.sql.Types.TIMESTAMP_WITH_TIMEZONE: /// ???????????????
								pstmt.setTimestamp(pstmtIndex++,
										new java.sql.Timestamp(soqlDateToDate(value).getTime()));
								break;
							default:
								// pstmt.setString(pstmtIndex++, value);
								throw new SQLException("Non supported type: " + metadataRs.getString("TYPE_NAME"));
								// break;
							}
						}

						// TODO tablename?
						// TODO set Object ID?
					}
				}
			}
			// TODO Check result of execute() method
			pstmt.execute();
		}
	}

	///////////////////////////
	// common func

	public static java.util.Date soqlDateToDate(final String soqlDateString) {
		if (soqlDateString == null || soqlDateString.length() == 0) {
			return null;
		}

		String workDateString = soqlDateString.replace("T", " ");
		workDateString = workDateString.replace("Z", "");

		// FIXME TODO DO NOT USE JST.
		final TimeZone jst = TimeZone.getTimeZone("JST");

		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date result = sdf.parse(workDateString);

			final Calendar calForGmt = Calendar.getInstance();
			calForGmt.setTime(result);
			calForGmt.add(Calendar.MILLISECOND, jst.getOffset(result.getTime()));
			result = calForGmt.getTime();

			return result;
		} catch (ParseException e) {
			// ignore
		}

		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
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
}
