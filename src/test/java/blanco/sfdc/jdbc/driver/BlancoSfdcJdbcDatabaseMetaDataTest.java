package blanco.sfdc.jdbc.driver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import junit.framework.TestCase;

public class BlancoSfdcJdbcDatabaseMetaDataTest extends TestCase {

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
			final ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
			for (; rs.next();) {
				System.err.println(rs.getString("TABLE_NAME"));
				System.err.println("  " + rs.getString("TABLE_TYPE"));
				System.err.println("  " + rs.getString("REMARKS"));
				System.err.println("  " + rs.getString("TABLE_CAT"));
				System.err.println("  " + rs.getString("TABLE_SCHEM"));
			}

			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}