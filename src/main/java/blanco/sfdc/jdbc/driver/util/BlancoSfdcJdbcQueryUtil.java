package blanco.sfdc.jdbc.driver.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlancoSfdcJdbcQueryUtil {
	public static final String DDL_CACHE_DATABASEMETADATA_SFDC_RECORD_NOT_FOUND = "CREATE TABLE IF NOT EXISTS GMETA_SFDC_RECORD_NOT_FOUND (" //
			+ "RECORD_NOT_FOUND VARCHAR" //
			+ ")";

	/**
	 * SFDC固有の空検索結果を作成するためのコード。
	 * 
	 * @param connCache
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getBlancResultSet(final Connection connCache) throws SQLException {
		connCache.createStatement().execute(DDL_CACHE_DATABASEMETADATA_SFDC_RECORD_NOT_FOUND);
		return connCache.createStatement().executeQuery("SELECT RECORD_NOT_FOUND FROM GMETA_SFDC_RECORD_NOT_FOUND");
	}
}
