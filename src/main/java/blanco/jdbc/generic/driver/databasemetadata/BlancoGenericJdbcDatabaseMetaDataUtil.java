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
			+ "TABLE_CAT VARCHAR, TABLE_SCHEM VARCHAR, TABLE_NAME VARCHAR, TABLE_TYPE VARCHAR DEFAULT 'TABLE'" //
			+ ", REMARKS VARCHAR, TYPE_CAT VARCHAR, TYPE_SCHEM VARCHAR, TYPE_NAME VARCHAR" //
			+ ", SELF_REFERENCING_COL_NAME VARCHAR, REF_GENERATION VARCHAR DEFAULT 'USER')";

	public static final String DATABASEMETADATA_COLUMNS_DDL_H2 = "CREATE TABLE GMETA_COLUMNS (" //
			+ "TABLE_CAT VARCHAR, TABLE_SCHEM VARCHAR, TABLE_NAME VARCHAR, COLUMN_NAME VARCHAR, DATA_TYPE INTEGER" //
			+ ", TYPE_NAME VARCHAR"
			+ ", COLUMN_SIZE INTEGER, DECIMAL_DIGITS INTEGER, NUM_PREC_RADIX INTEGER, NULLABLE INTEGER DEFAULT "
			+ ResultSetMetaData.columnNullableUnknown + ")";

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
