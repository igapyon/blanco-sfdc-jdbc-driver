package blanco.jdbc.generic.driver.databasemetadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BlancoGenericJdbcDatabaseMetaDataCacheUtil {
	///////////////////////////////////////////////
	// DATABASEMETADATA

	public static final String DATABASEMETADATA_TABLES_DDL_H2 = "CREATE TABLE GMETA_TABLES (" //
			+ "TABLE_CAT VARCHAR_IGNORECASE, TABLE_SCHEM VARCHAR_IGNORECASE, TABLE_NAME VARCHAR_IGNORECASE, TABLE_TYPE VARCHAR_IGNORECASE DEFAULT 'TABLE'" //
			+ ", REMARKS VARCHAR_IGNORECASE, TYPE_CAT VARCHAR_IGNORECASE, TYPE_SCHEM VARCHAR_IGNORECASE, TYPE_NAME VARCHAR_IGNORECASE" //
			+ ", SELF_REFERENCING_COL_NAME VARCHAR_IGNORECASE, REF_GENERATION VARCHAR_IGNORECASE DEFAULT 'USER')";

	public static final String DATABASEMETADATA_COLUMNS_DDL_H2 = "CREATE TABLE GMETA_COLUMNS (" //
			+ "TABLE_CAT VARCHAR_IGNORECASE, TABLE_SCHEM VARCHAR_IGNORECASE, TABLE_NAME VARCHAR_IGNORECASE, COLUMN_NAME VARCHAR_IGNORECASE, DATA_TYPE INTEGER" //
			+ ", TYPE_NAME VARCHAR_IGNORECASE"
			+ ", COLUMN_SIZE INTEGER, DECIMAL_DIGITS INTEGER, NUM_PREC_RADIX INTEGER DEFAULT 10" //
			+ ", NULLABLE INTEGER DEFAULT " + ResultSetMetaData.columnNullableUnknown //
			+ ", REMARKS VARCHAR_IGNORECASE, COLUMN_DEF VARCHAR_IGNORECASE, SQL_DATA_TYPE INTEGER, SQL_DATETIME_SUB INTEGER, CHAR_OCTET_LENGTH INTEGER, ORDINAL_POSITION INTEGER" //
			+ ", IS_NULLABLE VARCHAR_IGNORECASE DEFAULT '', SCOPE_CATALOG VARCHAR_IGNORECASE, SCOPE_SCHEMA VARCHAR_IGNORECASE, SCOPE_TABLE VARCHAR_IGNORECASE, SOURCE_DATA_TYPE VARCHAR_IGNORECASE, IS_AUTOINCREMENT VARCHAR_IGNORECASE DEFAULT 'NO'" //
			+ ", IS_GENERATEDCOLUMN VARCHAR_IGNORECASE DEFAULT ''" //
			+ ")";

	public static void initGmetaTables(final Connection connCache) throws SQLException {
		// getTables
		PreparedStatement pstmt = connCache.prepareStatement(DATABASEMETADATA_TABLES_DDL_H2);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		// getColumns
		pstmt = connCache.prepareStatement(DATABASEMETADATA_COLUMNS_DDL_H2);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}
	}

	public static boolean isGmetaTablesCached(final Connection connCache) throws SQLException {
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

	public static boolean isGmetaColumnsCached(final Connection connCache, String catalog, String schemaPattern,
			String tableName) throws SQLException {
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

	public static ResultSet getColumnsFromCache(final Connection connCache, String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern) throws SQLException {
		String sql = "SELECT * FROM GMETA_COLUMNS";//
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
