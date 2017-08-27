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

package blanco.jdbc.driver.simple;

public class BlancoJdbcSimpleResultSetColumn {
	protected String columnValue = null;

	protected Integer columnValueByInteger = null;

	protected java.util.Date columnValueByDate = null;

	protected BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn = null;

	public BlancoJdbcSimpleResultSetColumn(final BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn) {
		this.metaDataColumn = metaDataColumn;
	}

	// columnType ???

	public BlancoJdbcSimpleResultSetMetaDataColumn getMetaDataColumn() {
		return metaDataColumn;
	}

	public void setMetaDataColumn(BlancoJdbcSimpleResultSetMetaDataColumn metaDataColumn) {
		this.metaDataColumn = metaDataColumn;
	}

	public String getColumnName() {
		return metaDataColumn.getColumnName();
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public java.util.Date getColumnValueByDate() {
		return columnValueByDate;
	}

	public void setColumnValueByDate(java.util.Date columnValueByDate) {
		this.columnValueByDate = columnValueByDate;
	}

	public Integer getColumnValueByInteger() {
		return columnValueByInteger;
	}

	public void setColumnValueByInteger(Integer columnValueByInteger) {
		this.columnValueByInteger = columnValueByInteger;
	}
}
