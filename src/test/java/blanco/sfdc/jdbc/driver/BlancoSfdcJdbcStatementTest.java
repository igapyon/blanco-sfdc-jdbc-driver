package blanco.sfdc.jdbc.driver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.TestCase;

public class BlancoSfdcJdbcStatementTest extends TestCase {

	public void test001() throws Exception {
		Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");

		try {
			final Properties prop = new Properties();
			final InputStream inStream = new FileInputStream("sfdc.properties");
			prop.load(new BufferedInputStream(inStream));
			inStream.close();

			final String url = prop.getProperty("url", "https://login.salesforce.com/services/Soap/u/40.0");
			final String user = prop.getProperty("user", "NoUserSpesified");
			final String pass = prop.getProperty("password", "NoPassSpecified");

			final Connection conn = DriverManager.getConnection("blanco:sfdc:jdbc:" + url, user, pass);

			final Statement stmt = conn.createStatement();
			final String sql = "SELECT Id, Name, LastModifiedDate FROM Account";
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				final String id = rs.getString("id");
				final String name = rs.getString("name");
				final java.sql.Date lastModifiedDate = rs.getDate("LastModifiedDate");
				final java.sql.Timestamp lastModifiedDateTimestamp = rs.getTimestamp("LastModifiedDate");

				System.err.println("id: " + id + ", name:" + name + ", LastModifiedDate:" + lastModifiedDate
						+ ", LastModifiedDate2:" + lastModifiedDateTimestamp);
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void test002() throws Exception {
		Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");

		final Properties prop = new Properties();
		final InputStream inStream = new FileInputStream("sfdc.properties");
		prop.load(new BufferedInputStream(inStream));
		inStream.close();

		final String url = prop.getProperty("url", "https://login.salesforce.com/services/Soap/u/40.0");
		final String user = prop.getProperty("user", "NoUserSpesified");
		final String pass = prop.getProperty("password", "NoPassSpecified");

		final Connection conn = DriverManager.getConnection("blanco:sfdc:jdbc:" + url, user, pass);

		final Statement stmt = conn.createStatement();
		final String sql = "SELECT Id, Name, LastModifiedDate FROM Account";
		final ResultSet rs = stmt.executeQuery(sql);
		if (true)
			while (rs.next()) {
				final String id = rs.getString(1);
				final String name = rs.getString(2);
				final java.sql.Date lastModifiedDate = rs.getDate(3);
				System.err.println("id: " + id + ", name:" + name + ", LastModifiedDate:" + lastModifiedDate);
			}
		rs.close();
		stmt.close();
		conn.close();
	}
}