/*
 *  blanco-generic-jdbc-driver
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

// TODO このクラス不要かどうかチェック。
public class BlancoGenericJdbcResultSetMetaDataColumn {
	// COLUMN_NAME
	protected String columnName = "N/A";

	// TABLE_NAME
	protected String tableName = null;

	protected String columnLabel = null;

	// DATA_TYPE int => java.sql.Types
	protected int dataType = java.sql.Types.VARCHAR;

	// TYPE_NAME String => type on source
	protected String typeName = "string";

	// COLUMN_SIZE int => 列サイズ
	protected int columnSize = 1;

	// CHAR_OCTET_LENGTH int

	// NULLABLE int => columnNullableUnknown
	// IS_NULLABLE ""
	protected boolean nullable;

	protected String schemaName;

	protected int precision;

	// DECIMAL_DIGITS (FIXME)
	protected int scale;

	// REMARKS
	protected String remarks;

	// ORDINAL_POSITION int => テーブル中の列のインデックス (1 から始まる)
	protected int ordinalPosition = -1;

	// NUM_PREC_RADIX int 10;
	// SCOPE_CATLOG null
	// SCOPE_SCHEMA null
	// TABLE_CAT null
	// TABLE_SCHEM null
	// COLUMN_DEF null
	// SCOPE_TABLE String null
	// IS_AUTOINCREMENT String ""

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
}
