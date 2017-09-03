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

package blanco.jdbc.generic.driver.cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlancoGenericJdbcCacheUtilResultSet {
	public static void createCacheTableOfResultSet(final Connection connCache, final String globalUniqueKey)
			throws SQLException {
		final ResultSet columnsRs = BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(connCache,
				"GMETA_COLUMNS_" + globalUniqueKey, null, null, null, null);

		String ddl = "CREATE TABLE IF NOT EXISTS GMETA_RS_" + globalUniqueKey + " (";

		boolean isFirst = true;
		for (; columnsRs.next();) {
			if (isFirst) {
				isFirst = false;
			} else {
				ddl += ",";
			}
			ddl += columnsRs.getString("COLUMN_NAME");
			ddl += " ";
			switch (columnsRs.getInt("DATA_TYPE")) {
			case java.sql.Types.VARCHAR:
				ddl += "VARCHAR";
				break;
			case java.sql.Types.INTEGER:
				ddl += "INTEGER";
				break;
			case java.sql.Types.DATE:
				ddl += "DATE";
				break;
			case java.sql.Types.TIMESTAMP:
				ddl += "TIMESTAMP";
				break;
			default:
				throw new SQLException("Unsupported type:" + columnsRs.getInt("DATA_TYPE") + " ("
						+ columnsRs.getString("TYPE_NAME") + ")");
			}
		}

		ddl += ")";
		connCache.createStatement().execute(ddl);
		// System.err.println("[DDL]" + ddl);
	}
}
