/*
 *  blanco-jdbc-driver-simple
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

package blanco.jdbc.generic.driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class AbstractBlancoGenericJdbcDriver implements Driver {
	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 1;
	}

	public boolean acceptsURL(final String url) throws SQLException {
		if (url.startsWith(getDriverUrlPrefix())) {
			return true;
		}

		return false;
	}

	protected abstract String getDriverUrlPrefix();

	public abstract Connection connect(final String url, final Properties info) throws SQLException;

	public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLException("Not Implemented.");
		// return null;
	}

	public boolean jdbcCompliant() {
		// Default as No.
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("Not Implemented.");
	}

}
