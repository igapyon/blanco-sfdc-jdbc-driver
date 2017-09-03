package blanco.jdbc.generic.driver;

import java.sql.SQLException;
import java.sql.Statement;

public interface BlancoGenericJdbcBlockReadStatement extends Statement {
	boolean hasNextBlock() throws SQLException;

	/**
	 * 
	 * @param sql
	 * @param cacheTableName
	 *            "GMETA_COLUMNS_" + timeMillis
	 * @return
	 * @throws SQLException
	 */
	boolean firstBlock(final String sql, final String cacheTableName) throws SQLException;

	boolean nextBlock() throws SQLException;
}
