package blanco.sfdc.jdbc.driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class BlancoSfdcJdbcDriver implements Driver {
	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 1;
	}

	public Connection connect(final String url, final Properties info) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLException("Not Implemented.");
		// return null;
	}

	public boolean acceptsURL(final String url) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLException("Not Implemented.");
		// return null;
	}

	public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLException("Not Implemented.");
		// return null;
	}

	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("Not Implemented.");
	}

}
