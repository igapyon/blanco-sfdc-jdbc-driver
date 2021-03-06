package blanco.jdbc.generic.driver;

import java.sql.SQLException;
import java.sql.Statement;

public interface BlancoGenericJdbcBlockReadStatement extends Statement {
	boolean hasNextBlock() throws SQLException;

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	boolean firstBlock(final String sql) throws SQLException;

	boolean nextBlock() throws SQLException;
}
