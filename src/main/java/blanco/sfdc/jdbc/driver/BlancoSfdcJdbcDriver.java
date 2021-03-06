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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcDriver;

/**
 * mvn archetype:generate -DgroupId=blanco.sfdc.jdbc.driver
 * -DartifactId=blanco-sfdc-jdbc-driver
 * -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
 * 
 * @author Toshiki Iga
 */
public class BlancoSfdcJdbcDriver extends AbstractBlancoGenericJdbcDriver {
	/**
	 * Class.forName("blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver");
	 * 
	 * DriverManager.getConnection("blanco:sfdc:jdbc:https://login.salesforce.com/services/Soap/u/40.0","user","pass");
	 */
	static {
		final BlancoSfdcJdbcDriver driver = new BlancoSfdcJdbcDriver();

		try {
			DriverManager.registerDriver(driver);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Connection connect(final String url, final Properties info) throws SQLException {
		final String user = info.getProperty("user");
		final String pass = info.getProperty("password");

		return new BlancoSfdcJdbcConnection(url, user, pass);
	}

	@Override
	protected String getDriverUrlPrefix() {
		return BlancoSfdcJdbcConstants.JDBC_DRIVER_URL_PREFIX;
	}
}
