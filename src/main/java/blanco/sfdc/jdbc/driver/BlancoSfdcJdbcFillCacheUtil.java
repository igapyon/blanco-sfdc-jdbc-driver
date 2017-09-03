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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilDatabaseMetaData;

public class BlancoSfdcJdbcFillCacheUtil {
	public static void fillCacheTableOfResultSetMetaData(final BlancoSfdcJdbcConnection conn, final long timemillisecs,
			final SObject resultSetValue) throws SQLException {

		{
			String ddl = BlancoGenericJdbcCacheUtilDatabaseMetaData.DATABASEMETADATA_COLUMNS_DDL_H2
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

		// TODO これを、おぶじぇくとのIDは不要なので読み飛ばし。
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
