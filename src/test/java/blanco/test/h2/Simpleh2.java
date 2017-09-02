package blanco.test.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import junit.framework.TestCase;

public class Simpleh2 extends TestCase {

	public void test001() throws Exception {
		Class.forName("org.h2.Driver");

		final Connection conn = DriverManager.getConnection("jdbc:h2:mem:sfdcjdbc");
		final Statement stmt = conn.createStatement();
		stmt.execute("create table test (id int primary key,name varchar)");
		stmt.execute("CREATE TABLE SF_TABLES (TABLE_CAT VARCHAR, TABLE_SCHEM VARCHAR, TABLE_NAME VARCHAR" //
				+ ", TABLE_TYPE VARCHAR DEFAULT 'TABLE', REMARKS VARCHAR, TYPE_CAT VARCHAR, TYPE_SCHEM VARCHAR, TYPE_NAME VARCHAR, SELF_REFERENCING_COL_NAME VARCHAR, REF_GENERATION VARCHAR DEFAULT 'USER')");
		stmt.execute("insert into test values (1,'名前')");
		conn.commit();

		ResultSet rs = stmt.executeQuery("select * from test");

		for (; rs.next();) {
			final ResultSetMetaData rsmd = rs.getMetaData();
			for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
				System.out.println("val(" + index + "):" + rs.getString(index));
			}
			System.out.println("colname:" + rsmd.getColumnName(1));
			System.out.println("colname:" + rsmd.getColumnType(1));
			System.out.println("colname:" + rsmd.getColumnTypeName(1));
			break;
		}
		rs.close();

		stmt.close();

		if (false) {
			ResultSet rs2 = conn.getMetaData().getTables(null, null, null, null);
			System.out.println("table");
			for (; rs2.next();) {
				for (int index = 1; index <= rs2.getMetaData().getColumnCount(); index++) {
					System.out.println("  " + index + ": " + rs2.getString(index));
				}
			}
		}

		ResultSet rs3 = conn.getMetaData().getColumns(null, null, null, null);
		for (; rs3.next();) {
			// System.out.println(rs3.getString(1));
			// System.out.println(rs3.getString(2));
			for (int index = 1; index <= rs3.getMetaData().getColumnCount(); index++) {
				System.out.print(", " + rs3.getMetaData().getColumnName(index) + " "
						+ rs3.getMetaData().getColumnTypeName(index));
			}
			System.out.println();
			break;
		}

		conn.close();
	}
}
