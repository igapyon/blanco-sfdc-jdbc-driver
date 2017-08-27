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
			{
				final ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
				for (; rs.next();) {
					System.err.println(rs.getString("TABLE_NAME"));
					System.err.println("  " + rs.getString("TABLE_TYPE"));
					System.err.println("  " + rs.getString("REMARKS"));
					System.err.println("  " + rs.getString("TABLE_CAT"));
					System.err.println("  " + rs.getString("TABLE_SCHEM"));
				}
			}

			{
				final ResultSet rs = conn.getMetaData().getColumns(null, null, null, null);
				for (; rs.next();) {
					System.err.println(rs.getString("TABLE_NAME"));
					System.err.println("  " + rs.getString("TABLE_TYPE"));
					System.err.println("  " + rs.getString("REMARKS"));
					System.err.println("  " + rs.getString("TABLE_CAT"));
					System.err.println("  " + rs.getString("TABLE_SCHEM"));

					// TABLE_NAME
					// COLUMN_NAME
					// DATA_TYPE int => java.sql.Types
					// TYPE_NAME String => データソース依存の型名
					// COLUMN_SIZE int => 列サイズ
					// DECIMAL_DIGITS int => 小数点以下の桁数。DECIMAL_DIGITS
					// が適用できないデータ型の場合は、Null が返される。
					// NUM_PREC_RADIX int 10;
					// NULLABLE int => NULL は許されるか
					// columnNoNulls - NULL 値を許さない可能性がある
					// columnNullable - 必ず NULL 値を許す
					// columnNullableUnknown - NULL 値を許すかどうかは不明
					// REMARKS String => コメント記述列 (null の可能性がある)
					// CHAR_OCTET_LENGTH int => char の型については列の最大バイト数
					// IS_NULLABLE ""
					// SCOPE_CATLOG null
					// SCOPE_SCHEMA null

					// TABLE_CAT null
					// TABLE_SCHEM null
					
					// COLUMN_DEF null
					// ORDINAL_POSITION int => テーブル中の列のインデックス (1 から始まる)

					// SCOPE_TABLE String null
					// IS_AUTOINCREMENT String => この列が自動インクリメントされるかどうかを示す
				}
			}

			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}