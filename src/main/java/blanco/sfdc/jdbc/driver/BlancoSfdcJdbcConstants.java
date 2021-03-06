/*
 *  blanco-sfdc-jdbc-driver
 *  Copyright (C) 2017  Toshiki Iga
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 *  Copyright 2017 Toshiki Iga
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package blanco.sfdc.jdbc.driver;

public class BlancoSfdcJdbcConstants {
	/**
	 * jdbc:blanco:sfdc:soqlro://login.salesforce.com/services/Soap/u/40.0
	 */
	public static final String JDBC_DRIVER_URL_PREFIX = "jdbc:blanco:sfdc:soqlro:";

	public static final String[] SOQLKEYWORDS = new String[] { "SELECT", "TYPEOF", "FROM", "USING", "SCOPE", "WHERE",
			"IN", "LIKE", "NOT", "INCLUDES", "EXCLUDES", "WITH", "ABOVE", "BELOW", "ABOVE_OR_BELOW", "OR", "AND", "AT",
			"NULL", "DATA", "CATEGORY", "GROUP", "BY", "ROLLUP", "CUBE", "HAVING", "ORDER", "ASC", "DESC", "NULLS",
			"FIRST", "LAST", "LIMIT", "OFFSET", "FOR", "VIEW", "REFERENCE", "UPDATE", "TRACKING", "VIEWSTAT", };

	/*
	 * TODO ...
	 * 
	 * FUNCTINOS AVG() COUNT() COUNT_DISTINCT() MIN() MAX() SUM()
	 */

}
