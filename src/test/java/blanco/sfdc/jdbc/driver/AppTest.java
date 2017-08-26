package blanco.sfdc.jdbc.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws Exception
	 */
	public void testApp() throws Exception {
		Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");
		try {
			final Connection conn = DriverManager.getConnection(
					"blanco:sfdc:jdbc:https://login.salesforce.com/services/Soap/u/40.0", "user", "pass");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
