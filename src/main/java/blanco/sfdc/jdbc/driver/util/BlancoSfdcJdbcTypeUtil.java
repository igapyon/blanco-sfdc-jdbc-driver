package blanco.sfdc.jdbc.driver.util;

import java.sql.SQLException;

public class BlancoSfdcJdbcTypeUtil {
	public static int soqlTypeName2SqlTypes(final String soqlTypeName) throws SQLException {
		if ("VARCHAR".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("string".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("ID".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("picklist".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("multipicklist".compareToIgnoreCase(soqlTypeName) == 0) {
			// TODO
			return java.sql.Types.VARCHAR;
		} else if ("anyType".compareToIgnoreCase(soqlTypeName) == 0) {
			// TODO
			return java.sql.Types.VARCHAR;
		} else if ("reference".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("phone".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("url".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("email".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("currency".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.VARCHAR;
		} else if ("textarea".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.LONGVARCHAR;
		} else if ("address".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.LONGVARCHAR;
		} else if ("_boolean".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.BOOLEAN;
		} else if ("BOOLEAN".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.BOOLEAN;
		} else if ("INTEGER".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.INTEGER;
		} else if ("_int".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.INTEGER;
		} else if ("_double".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.DOUBLE;
		} else if ("DATE".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.DATE;
		} else if ("DATETIME".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.TIMESTAMP;
		} else if ("TIMESTAMP".compareToIgnoreCase(soqlTypeName) == 0) {
			return java.sql.Types.TIMESTAMP;
		}

		throw new SQLException("Unsupported type:" + soqlTypeName);
	}
}
