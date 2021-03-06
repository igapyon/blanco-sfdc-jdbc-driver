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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import blanco.sfdc.jdbc.driver.util.BlancoSfdcJdbcTypeUtil;

public class BlancoGenericJdbcCacheUtilDatabaseMetaData {
	///////////////////////////////////////////////
	// DATABASEMETADATA

	public static final String DDL_CACHE_DATABASEMETADATA_TABLES = "CREATE TABLE IF NOT EXISTS GMETA_TABLES (" //
			+ "TABLE_CAT VARCHAR_IGNORECASE, TABLE_SCHEM VARCHAR_IGNORECASE, TABLE_NAME VARCHAR_IGNORECASE, TABLE_TYPE VARCHAR_IGNORECASE DEFAULT 'TABLE'" //
			+ ", REMARKS VARCHAR_IGNORECASE, TYPE_CAT VARCHAR_IGNORECASE, TYPE_SCHEM VARCHAR_IGNORECASE, TYPE_NAME VARCHAR_IGNORECASE" //
			+ ", SELF_REFERENCING_COL_NAME VARCHAR_IGNORECASE, REF_GENERATION VARCHAR_IGNORECASE DEFAULT 'USER')";

	/**
	 * Note: GMETA_COLUMNS を変更しつつ再利用しています。
	 */
	public static final String DDL_CACHE_DATABASEMETADATA_COLUMNS = "CREATE TABLE IF NOT EXISTS GMETA_COLUMNS (" //
			+ "TABLE_CAT VARCHAR_IGNORECASE"//
			+ ",TABLE_SCHEM VARCHAR_IGNORECASE"//
			+ ",TABLE_NAME VARCHAR_IGNORECASE"//
			+ ",COLUMN_NAME VARCHAR_IGNORECASE"//
			+ ",DATA_TYPE INTEGER" //
			+ ",TYPE_NAME VARCHAR_IGNORECASE"//
			+ ",BUFFER_LENGTH VARCHAR"// nouse
			+ ",DECIMAL_DIGITS INTEGER"//
			+ ",COLUMN_SIZE INTEGER"//
			+ ",NUM_PREC_RADIX INTEGER DEFAULT 10" //
			+ ",NULLABLE INTEGER DEFAULT " + ResultSetMetaData.columnNullableUnknown //
			+ ",REMARKS VARCHAR_IGNORECASE"//
			+ ",COLUMN_DEF VARCHAR_IGNORECASE"//
			+ ",SQL_DATA_TYPE INTEGER"// nouse
			+ ",SQL_DATETIME_SUB INTEGER"// nouse
			+ ",CHAR_OCTET_LENGTH INTEGER"//
			+ ",ORDINAL_POSITION INTEGER" // 1 origin
			+ ",IS_NULLABLE VARCHAR_IGNORECASE DEFAULT ''"//
			+ ",SCOPE_CATALOG VARCHAR_IGNORECASE"//
			+ ",SCOPE_SCHEMA VARCHAR_IGNORECASE"//
			+ ",SCOPE_TABLE VARCHAR_IGNORECASE"//
			+ ",SOURCE_DATA_TYPE SMALLINT"//
			+ ",IS_AUTOINCREMENT VARCHAR_IGNORECASE DEFAULT 'NO'" //
			+ ",IS_GENERATEDCOLUMN VARCHAR_IGNORECASE DEFAULT 'NO'" //
			+ ")";

	public static final String DML_CACHE_DATABASEMETADATA_COLUMNS_INSERT = "INSERT INTO GMETA_COLUMNS "
			+ " SET TABLE_CAT = ?"//
			+ ", TABLE_SCHEM = ?"//
			+ ", TABLE_NAME = ?"//
			+ ", COLUMN_NAME = ?"//
			+ ", DATA_TYPE = ?"//
			+ ", TYPE_NAME = ?" //
			+ ", COLUMN_SIZE = ?"//
			+ ", DECIMAL_DIGITS = ?"//
			+ ", NUM_PREC_RADIX = ?"//
			+ ", NULLABLE = ?"//
			+ ", COLUMN_DEF= ?"//
			+ ", REMARKS = ?" //
			+ ", SQL_DATA_TYPE = ?"//
			+ ", SQL_DATETIME_SUB = ?"//
			+ ", CHAR_OCTET_LENGTH = ?"//
			+ ", ORDINAL_POSITION = ?" //
			+ " , IS_NULLABLE = ?"//
			+ ", SCOPE_CATALOG = ?"//
			+ ", SCOPE_SCHEMA = ?"//
			+ ", SCOPE_TABLE = ?"//
			+ ", SOURCE_DATA_TYPE = ?" //
			+ ", IS_AUTOINCREMENT = ?"//
			+ ", IS_GENERATEDCOLUMN = ?";

	public static final String DDL_CACHE_DATABASEMETADATA_TABLETYPES = "CREATE TABLE IF NOT EXISTS GMETA_TABLETYPES (" //
			+ "TABLE_TYPE VARCHAR" //
			+ ")";

	public static final String DDL_CACHE_DATABASEMETADATA_PROCEDURES = "CREATE TABLE IF NOT EXISTS GMETA_PROCEDURES (" //
			+ "PROCEDURE_CAT VARCHAR, PROCEDURE_SCHEM VARCHAR, PROCEDURE_NAME VARCHAR, RESERVED1 VARCHAR, RESERVED2 VARCHAR, RESERVED3 VARCHAR, REMARKS VARCHAR, PROCEDURE_TYPE SMALLINT, SPECIFIC_NAME VARCHAR" //
			+ ")";

	public static final String DDL_CACHE_DATABASEMETADATA_SCHEMAS = "CREATE TABLE IF NOT EXISTS GMETA_SCHEMAS (" //
			+ "TABLE_SCHEM VARCHAR, TABLE_CATALOG VARCHAR" //
			+ ")";

	public static final String DDL_CACHE_DATABASEMETADATA_CATALOGS = "CREATE TABLE IF NOT EXISTS GMETA_CATALOGS (" //
			+ "TABLE_CAT VARCHAR" //
			+ ")";

	public static final String DDL_CACHE_DATABASEMETADATA_UDTS = "CREATE TABLE IF NOT EXISTS GMETA_UDTS (" //
			+ "TYPE_CAT VARCHAR, TYPE_SCHEM VARCHAR, TYPE_NAME VARCHAR, CLASS_NAME VARCHAR, DATA_TYPE INT, REMARKS VARCHAR, BASE_TYPE SMALLINT" //
			+ ")";

	public static final String DDL_CACHE_DATABASEMETADATA_TYPEINFO = "CREATE TABLE IF NOT EXISTS GMETA_TYPEINFO (" //
			+ "TYPE_NAME VARCHAR_IGNORECASE" //
			+ ",DATA_TYPE INT"//
			+ ",PRECISION INT"//
			+ ",LITERAL_PREFIX VARCHAR"//
			+ ",LITERAL_SUFFIX VARCHAR"//
			+ ",CREATE_PARAMS VARCHAR"//
			+ ",NULLABLE SMALLINT"//
			+ ",CASE_SENSITIVE BOOLEAN"//
			+ ",SEARCHABLE SMALLINT"//
			+ ",UNSIGNED_ATTRIBUTE BOOLEAN"//
			+ ",FIXED_PREC_SCALE BOOLEAN"//
			+ ",AUTO_INCREMENT BOOLEAN DEFAULT FALSE"//
			+ ",LOCAL_TYPE_NAME VARCHAR"//
			+ ",MINIMUM_SCALE SMALLINT"//
			+ ",MAXIMUM_SCALE SMALLINT"//
			+ ",SQL_DATA_TYPE INTEGER"// unused
			+ ",SQL_DATETIME_SUB INTEGER"// unused
			+ ",NUM_PREC_RADIX INTEGER DEFAULT 10"//
			+ ")";

	public static void createCacheTables(final Connection connCache) throws SQLException {
		// getTables
		PreparedStatement pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_TABLES);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		// getColumns
		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_COLUMNS);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		{
			// COLUMNS にダミーを追加。
			pstmt = connCache.prepareStatement(
					BlancoGenericJdbcCacheUtilDatabaseMetaData.DML_CACHE_DATABASEMETADATA_COLUMNS_INSERT);

			int rowNum = 1;
			try {
				final int sqlDataType = BlancoSfdcJdbcTypeUtil.soqlTypeName2SqlTypes("VARCHAR");

				// "TABLE_CAT"
				pstmt.setString(rowNum++, null);
				// "TABLE_SCHEM"
				pstmt.setString(rowNum++, null);
				// "TABLE_NAME"
				pstmt.setString(rowNum++, "GMETA_STRING_COLUMN");
				// "COLUMN_NAME"
				pstmt.setString(rowNum++, "GMETA_STRING_COLUMN");
				// "DATA_TYPE"
				pstmt.setInt(rowNum++, sqlDataType);
				// "TYPE_NAME"
				pstmt.setString(rowNum++, "VARCHAR");

				// "COLUMN_SIZE"
				pstmt.setInt(rowNum++, 255);

				// "DECIMAL_DIGITS"
				// "NUM_PREC_RADIX"

				// "DECIMAL_DIGITS"
				pstmt.setNull(rowNum++, sqlDataType);
				// "NUM_PREC_RADIX"
				pstmt.setNull(rowNum++, sqlDataType);

				/// "NULLABLE"
				pstmt.setInt(rowNum++, ResultSetMetaData.columnNullable);
				// "COLUMN_DEF"
				pstmt.setString(rowNum++, null);
				// "REMARKS"
				pstmt.setString(rowNum++, null);
				// "SQL_DATA_TYPE" // reserved future
				// always null
				pstmt.setNull(rowNum++, sqlDataType);

				// "SQL_DATETIME_SUB"
				// always null.
				pstmt.setNull(rowNum++, sqlDataType);

				// "CHAR_OCTET_LENGTH"
				pstmt.setInt(rowNum++, 255);

				// ORDINAL_POSITION should not populate
				pstmt.setInt(rowNum++, 1);

				// "IS_NULLABLE"
				pstmt.setString(rowNum++, "");
				// "SCOPE_CATALOG"
				pstmt.setString(rowNum++, null);
				// "SCOPE_SCHEMA"
				pstmt.setString(rowNum++, null);
				// "SCOPE_TABLE"
				pstmt.setString(rowNum++, null);
				// "SOURCE_DATA_TYPE"
				pstmt.setString(rowNum++, null);
				// "IS_AUTOINCREMENT"
				pstmt.setString(rowNum++, null);
				// "IS_GENERATEDCOLUMN"
				pstmt.setString(rowNum++, null);

				pstmt.execute();
			} finally {
				pstmt.close();
			}
		}

		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_TABLETYPES);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_PROCEDURES);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_SCHEMAS);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_CATALOGS);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_UDTS);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		pstmt = connCache.prepareStatement(DDL_CACHE_DATABASEMETADATA_TYPEINFO);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}
	}

	public static boolean isCacheDatabaseMetaDataTablesCached(final Connection connCache) throws SQLException {
		boolean isCached = false;
		final PreparedStatement pstmt = connCache.prepareStatement("SELECT COUNT(*) FROM GMETA_TABLES");
		try {
			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			rs.next();
			if (rs.getInt(1) > 0) {
				isCached = true;
			}
			rs.close();
			return isCached;
		} finally {
			pstmt.close();
		}
	}

	public static boolean isCacheDatabaseMetaDataColumnsCached(final Connection connCache, String catalog,
			String schemaPattern, String tableName) throws SQLException {
		boolean isCached = false;

		String sql = "SELECT COUNT(*) FROM GMETA_COLUMNS";

		boolean isFirstCondition = true;
		if (catalog != null && catalog.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_CAT = ?";
		}
		if (schemaPattern != null && schemaPattern.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_SCHEM LIKE ?";
		}
		{
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_NAME = ?";
		}

		final PreparedStatement pstmt = connCache.prepareStatement(sql);
		try {
			int indexCol = 1;
			if (catalog != null && catalog.trim().length() != 0) {
				pstmt.setString(indexCol++, catalog);
			}
			if (schemaPattern != null && schemaPattern.trim().length() != 0) {
				pstmt.setString(indexCol++, schemaPattern);
			}
			pstmt.setString(indexCol++, tableName);

			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			rs.next();
			if (rs.getInt(1) > 0) {
				isCached = true;
			}
			rs.close();
			return isCached;
		} finally {
			pstmt.close();
		}
	}

	public static ResultSet getTablesFromCache(final Connection connCache, String catalog, String schemaPattern,
			String tableNamePattern, String[] types) throws SQLException {
		int indexCol = 1;
		String sql = "SELECT * FROM GMETA_TABLES";
		boolean isFirstCondition = true;
		if (catalog != null && catalog.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_CAT LIKE ?";
		}
		if (schemaPattern != null && schemaPattern.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_SCHEM LIKE ?";
		}
		if (tableNamePattern != null && tableNamePattern.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_NAME LIKE ?";
		}
		sql += " ORDER BY TABLE_TYPE, TABLE_CAT, TABLE_SCHEM, TABLE_NAME";

		final PreparedStatement pstmt = connCache.prepareStatement(sql);

		if (catalog != null && catalog.trim().length() != 0) {
			pstmt.setString(indexCol++, catalog);
		}
		if (schemaPattern != null && schemaPattern.trim().length() != 0) {
			pstmt.setString(indexCol++, schemaPattern);
		}
		if (tableNamePattern != null && tableNamePattern.trim().length() != 0) {
			pstmt.setString(indexCol++, tableNamePattern);
		}

		pstmt.executeQuery();
		return pstmt.getResultSet();
	}

	public static ResultSet getColumnsFromCache(final Connection connCache, String cacheTableName, String catalog,
			String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
		if (cacheTableName == null) {
			cacheTableName = "GMETA_COLUMNS";
		}

		String sql = "SELECT * FROM " + cacheTableName; //
		boolean isFirstCondition = true;
		if (catalog != null && catalog.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_CAT = ?";
		}
		if (schemaPattern != null && schemaPattern.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_SCHEM LIKE ?";
		}
		if (tableNamePattern != null && tableNamePattern.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " TABLE_NAME LIKE ?";
		}
		if (columnNamePattern != null && columnNamePattern.trim().length() != 0) {
			if (isFirstCondition) {
				isFirstCondition = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}
			sql += " COLUMN_NAME LIKE ?";
		}

		sql += " ORDER BY TABLE_CAT, TABLE_SCHEM, TABLE_NAME, ORDINAL_POSITION";

		final PreparedStatement pstmt = connCache.prepareStatement(sql);

		int indexCol = 1;
		if (catalog != null && catalog.trim().length() != 0) {
			pstmt.setString(indexCol++, catalog);
		}
		if (schemaPattern != null && schemaPattern.trim().length() != 0) {
			pstmt.setString(indexCol++, schemaPattern);
		}
		if (tableNamePattern != null && tableNamePattern.trim().length() != 0) {
			pstmt.setString(indexCol++, tableNamePattern);
		}
		if (columnNamePattern != null && columnNamePattern.trim().length() != 0) {
			pstmt.setString(indexCol++, columnNamePattern);
		}

		pstmt.executeQuery();
		return pstmt.getResultSet();
	}
}
