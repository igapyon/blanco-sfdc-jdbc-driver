package blanco.sfdc.jdbc.driver;

public class BlancoSfdcJdbcTestConstants {
	public static final boolean IS_TEST_WITH_SFDC = false;

	public static boolean isTestWithSfdc() {
		if (IS_TEST_WITH_SFDC == false) {
			System.err.println("SFDC test is disabled.");
		}
		return IS_TEST_WITH_SFDC;
	}
}
