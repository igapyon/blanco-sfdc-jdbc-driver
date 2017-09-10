package blanco.sfdc.jdbc.driver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.TestCase;

public class BlancoSfdcJdbcStatement1Test extends TestCase {

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

			final Statement stmt = conn.createStatement();

			stmt.setFetchSize(200);
			stmt.getFetchSize();

			// final String sql = "SELECT Id, Name, LastModifiedDate FROM
			// Account";

			// final String sql = "select ApiVersion, Body, BodyCrc,
			// CreatedById, CreatedDate, Id, IsValid, LastModifiedById,
			// LastModifiedDate, LengthWithoutComments, Name, NamespacePrefix,
			// Status, SystemModstamp from ApexClass";

			final String sql = "SELECT Id, Name, LastModifiedDate FROM Contact";
			final ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				final String id = rs.getString("id");
				final String name = rs.getString("name");
				final java.sql.Date lastModifiedDate = rs.getDate("LastModifiedDate");
				final java.sql.Timestamp lastModifiedDateTimestamp = rs.getTimestamp("LastModifiedDate");

				final String line = "id: " + id + ", name:" + name + ", LastModifiedDate:" + lastModifiedDate
						+ ", LastModifiedDate2:" + lastModifiedDateTimestamp;
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
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

			final Statement stmt = conn.createStatement();
			final String sql = "SELECT Id, Name, LastModifiedDate FROM Account";
			final ResultSet rs = stmt.executeQuery(sql);
			if (true)
				while (rs.next()) {
					final String id = rs.getString(1);
					final String name = rs.getString(2);
					final java.sql.Date lastModifiedDate = rs.getDate(3);
					final String line = "id: " + id + ", name:" + name + ", LastModifiedDate:" + lastModifiedDate;
				}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail();
		}
	}

	public void test003() throws Exception {
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

			final Statement stmt = conn.createStatement();
			final String sql = "SELECT Id, Name, LastModifiedDate FROM Account";
			final ResultSet rs = stmt.executeQuery(sql);

			rs.next();
			final ResultSetMetaData rsmd = rs.getMetaData();
			for (int indexCol = 1; indexCol <= rsmd.getColumnCount(); indexCol++) {
				String result = "";
				result += rsmd.getColumnName(indexCol) + " (" + rsmd.getColumnTypeName(indexCol) + "): "
						+ rsmd.getColumnType(indexCol);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail();
		}
	}
}