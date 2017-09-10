package blanco.sfdc.jdbc.driver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import junit.framework.TestCase;

public class BlancoSfdcJdbcConnectionTest extends TestCase {
	public void testPassword() throws Exception {
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

			final Connection conn = DriverManager.getConnection(url, user, pass + "error");
			fail();
		} catch (Exception ex) {
			// System.err.println("look: " + ex.getMessage());
		}
	}

	public void testUser() throws Exception {
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

			final Connection conn = DriverManager.getConnection(url, user + "error", pass);

			fail();
		} catch (Exception ex) {
			// System.err.println("look: " + ex.getMessage());
		}
	}

	public void testUrl() throws Exception {
		if (BlancoSfdcJdbcTestConstants.isTestWithSfdc() == false)
			return;

		Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");
		try {
			final Properties prop = new Properties();
			final InputStream inStream = new FileInputStream("soqlro.properties");
			prop.load(new BufferedInputStream(inStream));
			inStream.close();

			final String url = "jdbc:blanco:sfdc:soqlro://login.salesforce.com/services/Soap2/u/40.0";
			final String user = prop.getProperty("user", "NoUserSpesified");
			final String pass = prop.getProperty("password", "NoPassSpecified");

			final Connection conn = DriverManager.getConnection(url, user, pass);
			fail();
		} catch (Exception ex) {
			// System.err.println("look: " + ex.getMessage());
		}
	}
}