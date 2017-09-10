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
			{
				final ResultSet rs = conn.getMetaData().getTables(null, null, "ACC%", null);
				for (; rs.next();) {
					rs.getString("TABLE_NAME");
					rs.getString("REMARKS");
					rs.getString("TABLE_TYPE");
				}
				rs.close();
			}

			if (true) {
				final ResultSet rs = conn.getMetaData().getColumns(null, null, "ACC%", "p%");
				for (; rs.next();) {
					rs.getString("COLUMN_NAME");
					rs.getString("DATA_TYPE");
					rs.getString("TYPE_NAME");
					rs.getString("TABLE_NAME");
					rs.getString("COLUMN_SIZE");
					rs.getString("REMARKS");
					rs.getString("TABLE_CAT");
					rs.getString("TABLE_SCHEM");
					rs.getString("NULLABLE");
					rs.getString("IS_NULLABLE");// discard
					rs.getString("ORDINAL_POSITION");// discard
					rs.getString("SCOPE_TABLE"); // discard

					// DECIMAL_DIGITS int => 小数点以下の桁数。DECIMAL_DIGITS
					// が適用できないデータ型の場合は、Null が返される。
					// NUM_PREC_RADIX int 10;
					// CHAR_OCTET_LENGTH int => char の型については列の最大バイト数
					// SCOPE_CATLOG null
					// SCOPE_SCHEMA null
					// IS_AUTOINCREMENT String
				}
			}

			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			fail();
		}
	}
}