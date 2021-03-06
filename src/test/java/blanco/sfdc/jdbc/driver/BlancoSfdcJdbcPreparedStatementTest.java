package blanco.sfdc.jdbc.driver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import junit.framework.TestCase;

public class BlancoSfdcJdbcPreparedStatementTest extends TestCase {
	public void test001() throws Exception {
		if (BlancoSfdcJdbcTestConstants.isTestWithSfdc() == false)
			return;

		Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");
		try {
			final Properties prop = new Properties();
			final InputStream inStream = new FileInputStream("soqlro.properties");
			prop.load(new BufferedInputStream(inStream));
			inStream.close();

			final String url = prop.getProperty("url",
					"jdbc:blanco:sfdc:soqlro://login.salesforce.com/services/Soap/u/40.0");
			final String user = prop.getProperty("user", "NoUserSpesified");
			final String pass = prop.getProperty("password", "NoPassSpecified");

			final Connection conn = DriverManager.getConnection(url, user, pass);

			final String sql = "SELECT Id, Name, LastModifiedDate FROM Account";
			final PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setFetchSize(200);
			stmt.getFetchSize();

			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				final String id = rs.getString("id");
				final String name = rs.getString("name");
				final java.sql.Date lastModifiedDate = rs.getDate("lastmodifieddate");
				if (false)
					System.err.println("id: " + id + ", name:" + name + ", LastModifiedDate:" + lastModifiedDate);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	public void test002() throws Exception {
		if (BlancoSfdcJdbcTestConstants.isTestWithSfdc() == false)
			return;

		Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");
		try {
			final Properties prop = new Properties();
			final InputStream inStream = new FileInputStream("soqlro.properties");
			prop.load(new BufferedInputStream(inStream));
			inStream.close();

			final String url = prop.getProperty("url",
					"jdbc:blanco:sfdc:soqlro://login.salesforce.com/services/Soap/u/40.0");
			final String user = prop.getProperty("user", "NoUserSpesified");
			final String pass = prop.getProperty("password", "NoPassSpecified");

			final Connection conn = DriverManager.getConnection(url, user, pass);

			final String sql = "SELECT Id, Name, LastModifiedDate FROM Account";
			final PreparedStatement stmt = conn.prepareStatement(sql);
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				final String id = rs.getString(1);
				final String name = rs.getString(2);
				final java.sql.Date lastModifiedDate = rs.getDate(3);
				final String line = "id: " + id + ", name:" + name + ", LastModifiedDate:" + lastModifiedDate;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			fail();
		}
	}
}