package blanco.sfdc.jdbc.driver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcConnection;
import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcStatement;
import junit.framework.TestCase;

public class BlancoSfdcJdbcStatement3Test extends TestCase {
	public void test004() throws Exception {
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
		final String sql = "select Name from Account";
		final ResultSet rs = stmt.executeQuery(sql);

		{
			final AbstractBlancoGenericJdbcStatement stmt2 = (AbstractBlancoGenericJdbcStatement) stmt;
			final String tableName = "GMETA_COLUMNS_" + stmt2.getGlobalUniqueKey();
			final AbstractBlancoGenericJdbcConnection conn2 = (AbstractBlancoGenericJdbcConnection) stmt2
					.getConnection();
			final ResultSet rs2 = conn2.getCacheConnection().createStatement()
					.executeQuery("select * from " + tableName);
			final ResultSetMetaData rsmd2 = rs2.getMetaData();
			for (; rs2.next();) {
				for (int colNum2 = 1; colNum2 <= rs2.getMetaData().getColumnCount(); colNum2++) {
					rsmd2.getColumnName(colNum2);
					rs2.getString(colNum2);
				}
			}
		}

		rs.next();
		final ResultSetMetaData rsmd = rs.getMetaData();
		for (int indexCol = 1; indexCol <= rsmd.getColumnCount(); indexCol++) {
			String result = "";
			result += rsmd.getColumnName(indexCol) + " (" + rsmd.getColumnTypeName(indexCol) + "): "
					+ rsmd.getColumnType(indexCol);
			// System.out.println(result);
		}

		rs.close();
		stmt.close();
		conn.close();
	}
}