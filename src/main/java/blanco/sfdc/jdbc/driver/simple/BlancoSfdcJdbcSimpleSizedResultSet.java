package blanco.sfdc.jdbc.driver.simple;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class BlancoSfdcJdbcSimpleSizedResultSet extends BlancoSfdcJdbcSimpleResultSet {
	// for next, prev, ...
	protected int resultSetIndex = -1;
	protected int resultSetSize = 0;

	public BlancoSfdcJdbcSimpleSizedResultSet(final Statement stmt) {
		super(stmt);
	}

	@Override
	public void close() throws SQLException {
		super.close();

		resultSetIndex = -1;
		resultSetSize = 0;
	}

	public boolean next() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetSize <= 0) {
			return false;
		}

		resultSetIndex++;

		if (resultSetIndex < resultSetSize) {
			return true;
		} else {
			return false;
		}
	}

	public boolean previous() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetSize <= 0) {
			return false;
		}

		resultSetIndex--;

		if (resultSetIndex >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBeforeFirst() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetIndex < 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAfterLast() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetIndex >= resultSetSize) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFirst() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetIndex == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLast() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if ((resultSetIndex + 1) == resultSetSize) {
			return true;
		} else {
			return false;
		}
	}

	public void beforeFirst() throws SQLException {
		resultSetIndex = (-1);
	}

	public void afterLast() throws SQLException {

		resultSetIndex = resultSetSize;
	}

	public boolean first() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetSize <= 0) {
			return false;
		}

		resultSetIndex = 0;
		return true;
	}

	public boolean last() throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetSize <= 0) {
			return false;
		}

		resultSetIndex = resultSetSize - 1;
		return true;
	}

	public int getRow() throws SQLException {
		return resultSetIndex;
	}

	public boolean absolute(int row) throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetSize <= 0) {
			return false;
		}

		if (row < 0) {
			return false;
		}

		if (row >= resultSetSize) {
			return false;
		}

		resultSetIndex = row;

		return true;
	}

	public boolean relative(int rows) throws SQLException {
		if (isClosed()) {
			return false;
		}

		if (resultSetSize <= 0) {
			return false;
		}

		if ((resultSetIndex + rows) < 0) {
			return false;
		}

		if ((resultSetIndex + rows) >= resultSetSize) {
			return false;
		}

		resultSetIndex += rows;

		return true;
	}

}
