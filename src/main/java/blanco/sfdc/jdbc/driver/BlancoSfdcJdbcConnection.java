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

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.fault.LoginFault;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcConnection;
import blanco.sfdc.jdbc.driver.databasemetadata.BlancoSfdcJdbcDatabaseMetaData;

public class BlancoSfdcJdbcConnection extends AbstractBlancoGenericJdbcConnection {
	protected PartnerConnection partnerConn = null;

	protected boolean isClosed = false;

	public PartnerConnection getPartnerConnection() {
		return partnerConn;
	}

	public BlancoSfdcJdbcConnection(final String url, final String user, final String pass) throws SQLException {
		try {
			final ConnectorConfig connectorCfg = new ConnectorConfig();

			connectorCfg.setAuthEndpoint(url.substring(BlancoSfdcJdbcConstants.JDBC_DRIVER_URL_PREFIX.length()));
			connectorCfg.setUsername(user);
			connectorCfg.setPassword(pass);

			partnerConn = new PartnerConnection(connectorCfg);
		} catch (LoginFault ex) {
			System.err.println("[" + ex.getExceptionCode() + "] " + ex.getExceptionMessage());
			throw new SQLException("SFDC connection failed: " + ex.getExceptionCode() + ": " + ex.getExceptionMessage(),
					ex);
		} catch (ConnectionException ex) {
			throw new SQLException("SFDC connection failed: " + ex.toString(), ex);
		}
	}

	@Override
	public Statement createStatement() throws SQLException {
		return new BlancoSfdcJdbcStatement(this);
	}

	@Override
	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		return new BlancoSfdcJdbcPreparedStatement(this, sql);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// NOTE do nothing.
	}

	public boolean getAutoCommit() throws SQLException {
		// NOTE do nothing.
		return false;
	}

	public void commit() throws SQLException {
		// NOTE do nothing.
	}

	public void rollback() throws SQLException {
		// NOTE do nothing.
	}

	public void close() throws SQLException {
		isClosed = true;
	}

	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return new BlancoSfdcJdbcDatabaseMetaData(this);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		if (readOnly) {
			// OK
		} else {
			throw new SQLException("Not Supported.");
		}
	}

	public boolean isReadOnly() throws SQLException {
		// Read Only !
		return true;
	}
}
