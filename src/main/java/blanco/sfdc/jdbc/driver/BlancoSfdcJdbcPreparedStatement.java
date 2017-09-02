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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

import blanco.jdbc.generic.driver.AbstractBlancoGenericJdbcPreparedStatement;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSet;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetMetaData;
import blanco.jdbc.generic.driver.BlancoGenericJdbcResultSetRow;

public class BlancoSfdcJdbcPreparedStatement extends AbstractBlancoGenericJdbcPreparedStatement {
	protected String sql = null;

	protected BlancoGenericJdbcResultSet rs = null;

	public BlancoSfdcJdbcPreparedStatement(final BlancoSfdcJdbcConnection conn, final String sql) {
		super(conn);
		this.sql = sql;
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		rs = new BlancoGenericJdbcResultSet(this);

		try {
			// TODO そもそもこの処理はResultSet側のフェッチ境界の考慮が必要だが、難易度が高いので一旦保留。
			// TODO ただし、これを解決しないと、巨大な検索結果の際に全件を持ってきてしまうのでまずい実装だと思う。
			QueryResult qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection().query(sql);
			for (;;) {
				final SObject[] sObjs = qryResult.getRecords();
				if (sObjs.length == 0) {
					break;
				}

				final BlancoGenericJdbcResultSetMetaData rsmd = BlancoSfdcJdbcStatement
						.getResultSetMetaData((BlancoSfdcJdbcConnection) conn, sObjs[0]);

				for (int indexRow = 0; indexRow < sObjs.length; indexRow++) {
					final BlancoGenericJdbcResultSetRow row = BlancoSfdcJdbcStatement.getRowObj(sObjs[indexRow], rsmd);
					rs.getRowList().add(row);
				}
				if (qryResult.isDone()) {
					break;
				}
				// TODO This should be more better.
				qryResult = ((BlancoSfdcJdbcConnection) conn).getPartnerConnection()
						.queryMore(qryResult.getQueryLocator());
			}
		} catch (ConnectionException ex) {
			throw new SQLException(ex);
		}

		return true;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return rs;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		return executeQuery(sql);
	}
}
