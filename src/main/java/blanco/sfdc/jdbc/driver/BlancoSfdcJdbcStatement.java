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

import java.sql.SQLException;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcStatement;
import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilResultSet;

public class BlancoSfdcJdbcStatement extends AbstractBlancoGenericJdbcStatement {

	public BlancoSfdcJdbcStatement(final BlancoSfdcJdbcConnection conn) {
		super(conn);
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		final BlancoSfdcJdbcConnection pconn = ((BlancoSfdcJdbcConnection) conn);
		pconn.getPartnerConnection().setQueryOptions(rows);
	}

	@Override
	public int getFetchSize() throws SQLException {
		final BlancoSfdcJdbcConnection pconn = ((BlancoSfdcJdbcConnection) conn);
		return pconn.getPartnerConnection().getQueryOptions().getBatchSize();
	}

	QueryResult qryResult = null;

	public boolean hasNextBlock() throws SQLException {
		if (qryResult == null) {
			return true;
		}

		return (qryResult.isDone() == false);
	}

	public boolean firstBlock(final String sql) throws SQLException {
		try {
			// Do SOQL Query
			qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection().query(sql);
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		final SObject[] sObjs = qryResult.getRecords();
		if (sObjs.length == 0) {
			return false;
		}

		// Fill Cache ResultSetMetaData
		BlancoSfdcJdbcFillCacheCommon.fillCacheTableOfResultSetMetaData((BlancoSfdcJdbcConnection) conn,
				getGlobalUniqueKey(), sObjs[0]);

		// Create Cache ResultSet
		BlancoGenericJdbcCacheUtilResultSet.createCacheTableOfResultSet(conn.getCacheConnection(),
				getGlobalUniqueKey());

		// Fill Cache ResultSet
		BlancoSfdcJdbcFillCacheCommon.fillCacheTableOfResultSet(conn.getCacheConnection(), getGlobalUniqueKey(), sObjs);

		return true;
	}

	public boolean nextBlock() throws SQLException {
		final SObject[] sObjs = qryResult.getRecords();
		if (sObjs.length == 0) {
			return false;
		}

		BlancoSfdcJdbcFillCacheCommon.fillCacheTableOfResultSet(conn.getCacheConnection(), getGlobalUniqueKey(), sObjs);

		return true;
	}
}
