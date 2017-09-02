package blanco.jdbc.generic.driver.databasemetadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BlancoGenericJdbcDatabaseMetaDataUtil {
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

	public static void initGmetaTables(final Connection conn) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(DATABASEMETADATA_TABLES_DDL_H2);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}

		pstmt = conn.prepareStatement(DATABASEMETADATA_COLUMNS_DDL_H2);
		try {
			pstmt.execute();
		} finally {
			pstmt.close();
		}
	}

	public static boolean isGmetaTablesCached(final Connection conn) throws SQLException {
		boolean isCached = false;
		final PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM GMETA_TABLES");
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
}
