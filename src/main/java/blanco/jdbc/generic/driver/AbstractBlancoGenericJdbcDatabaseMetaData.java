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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheUtilDatabaseMetaData;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public abstract class AbstractBlancoGenericJdbcDatabaseMetaData implements DatabaseMetaData {
	protected BlancoSfdcJdbcConnection conn = null;

	public AbstractBlancoGenericJdbcDatabaseMetaData(final BlancoSfdcJdbcConnection conn) {
		this.conn = conn;
	}

	public Connection getConnection() throws SQLException {
		return conn;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented: unwrap(Class<T> iface)");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented: isWrapperFor(Class<?> iface)");
	}

	public boolean allProceduresAreCallable() throws SQLException {
		return false;
	}

	public boolean allTablesAreSelectable() throws SQLException {
		// non queryable are already filtered.
		return true;
	}

	public String getURL() throws SQLException {
		throw new SQLException("Not Implemented: getURL()");
	}

	public String getUserName() throws SQLException {
		throw new SQLException("Not Implemented: getUserName()");
	}

	public boolean isReadOnly() throws SQLException {
		// Always Read-Only
		return true;
	}

	public boolean nullsAreSortedHigh() throws SQLException {
		throw new SQLException("Not Implemented: nullsAreSortedHigh()");
	}

	public boolean nullsAreSortedLow() throws SQLException {
		throw new SQLException("Not Implemented: nullsAreSortedLow()");
	}

	public boolean nullsAreSortedAtStart() throws SQLException {
		throw new SQLException("Not Implemented: nullsAreSortedAtStart()");
	}

	public boolean nullsAreSortedAtEnd() throws SQLException {
		throw new SQLException("Not Implemented: nullsAreSortedAtEnd()");
	}

	public abstract String getDatabaseProductName() throws SQLException;

	public abstract String getDatabaseProductVersion() throws SQLException;

	public abstract String getDriverName() throws SQLException;

	public abstract String getDriverVersion() throws SQLException;

	public int getDriverMajorVersion() {
		return 1;
	}

	public int getDriverMinorVersion() {
		return 1;
	}

	public boolean usesLocalFiles() throws SQLException {
		// default impl do not use local file.
		return false;
	}

	public boolean usesLocalFilePerTable() throws SQLException {
		// default impl do not use local file.
		return false;
	}

	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: supportsMixedCaseIdentifiers()");
	}

	public boolean storesUpperCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: storesUpperCaseIdentifiers()");
	}

	public boolean storesLowerCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: storesLowerCaseIdentifiers()");
	}

	public boolean storesMixedCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: storesMixedCaseIdentifiers()");
	}

	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		// FIXME
		return false;
	}

	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: storesUpperCaseQuotedIdentifiers()");
	}

	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: storesLowerCaseQuotedIdentifiers()");
	}

	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented: storesMixedCaseQuotedIdentifiers()");
	}

	public String getIdentifierQuoteString() throws SQLException {
		throw new SQLException("Not Implemented: getIdentifierQuoteString()");
	}

	public String getSQLKeywords() throws SQLException {
		throw new SQLException("Not Implemented: getSQLKeywords()");
	}

	public String getNumericFunctions() throws SQLException {
		throw new SQLException("Not Implemented: getNumericFunctions()");
	}

	public String getStringFunctions() throws SQLException {
		throw new SQLException("Not Implemented: getStringFunctions()");
	}

	public String getSystemFunctions() throws SQLException {
		throw new SQLException("Not Implemented: getSystemFunctions()");
	}

	public String getTimeDateFunctions() throws SQLException {
		throw new SQLException("Not Implemented: getTimeDateFunctions()");
	}

	public String getSearchStringEscape() throws SQLException {
		throw new SQLException("Not Implemented: getSearchStringEscape()");
	}

	public String getExtraNameCharacters() throws SQLException {
		throw new SQLException("Not Implemented: getExtraNameCharacters()");
	}

	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		// this driver is read only
		return false;
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		// this driver is read only
		return false;
	}

	public boolean supportsColumnAliasing() throws SQLException {
		throw new SQLException("Not Implemented: supportsColumnAliasing()");
	}

	public boolean nullPlusNonNullIsNull() throws SQLException {
		throw new SQLException("Not Implemented: nullPlusNonNullIsNull()");
	}

	public boolean supportsConvert() throws SQLException {
		throw new SQLException("Not Implemented: supportsConvert()");
	}

	public boolean supportsConvert(int fromType, int toType) throws SQLException {
		throw new SQLException("Not Implemented: supportsConvert(int fromType, int toType)");
	}

	public boolean supportsTableCorrelationNames() throws SQLException {
		throw new SQLException("Not Implemented: supportsTableCorrelationNames()");
	}

	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		throw new SQLException("Not Implemented: supportsDifferentTableCorrelationNames()");
	}

	public boolean supportsExpressionsInOrderBy() throws SQLException {
		throw new SQLException("Not Implemented: supportsExpressionsInOrderBy()");
	}

	public boolean supportsOrderByUnrelated() throws SQLException {
		return false;
	}

	public boolean supportsGroupBy() throws SQLException {
		return true;
	}

	public boolean supportsGroupByUnrelated() throws SQLException {
		return false;
	}

	public boolean supportsGroupByBeyondSelect() throws SQLException {
		throw new SQLException("Not Implemented: supportsGroupByBeyondSelect()");
	}

	public boolean supportsLikeEscapeClause() throws SQLException {
		throw new SQLException("Not Implemented: supportsLikeEscapeClause()");
	}

	public boolean supportsMultipleResultSets() throws SQLException {
		// do not support multiple resultset
		return false;
	}

	public boolean supportsMultipleTransactions() throws SQLException {
		return false;
	}

	public boolean supportsNonNullableColumns() throws SQLException {
		return true;
	}

	public boolean supportsMinimumSQLGrammar() throws SQLException {
		// No!
		// TODO
		return false;
	}

	public boolean supportsCoreSQLGrammar() throws SQLException {
		// No!
		// TODO
		return false;
	}

	public boolean supportsExtendedSQLGrammar() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsANSI92FullSQL() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsOuterJoins() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsFullOuterJoins() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsLimitedOuterJoins() throws SQLException {
		// No!
		return false;
	}

	public String getSchemaTerm() throws SQLException {
		throw new SQLException("Not Implemented: getSchemaTerm()");
	}

	public String getProcedureTerm() throws SQLException {
		throw new SQLException("Not Implemented: getProcedureTerm()");
	}

	public String getCatalogTerm() throws SQLException {
		throw new SQLException("Not Implemented: getCatalogTerm()");
	}

	public boolean isCatalogAtStart() throws SQLException {
		throw new SQLException("Not Implemented: isCatalogAtStart()");
	}

	public String getCatalogSeparator() throws SQLException {
		throw new SQLException("Not Implemented: getCatalogSeparator()");
	}

	/////////////////////////////
	// Schema シリーズ

	public boolean supportsSchemasInDataManipulation() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		return false;
	}

	/////////////////////////////
	// Catalogs シリーズ

	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsPositionedDelete() throws SQLException {
		return false;
	}

	public boolean supportsPositionedUpdate() throws SQLException {
		return false;
	}

	public boolean supportsSelectForUpdate() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsStoredProcedures() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsSubqueriesInComparisons() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsSubqueriesInExists() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsSubqueriesInIns() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsCorrelatedSubqueries() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsUnion() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsUnionAll() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		// No!
		return false;
	}

	public int getMaxBinaryLiteralLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxBinaryLiteralLength()");
	}

	public int getMaxCharLiteralLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxCharLiteralLength()");
	}

	public int getMaxColumnNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxColumnNameLength()");
	}

	public int getMaxColumnsInGroupBy() throws SQLException {
		throw new SQLException("Not Implemented: getMaxColumnsInGroupBy()");
	}

	public int getMaxColumnsInIndex() throws SQLException {
		throw new SQLException("Not Implemented: getMaxColumnsInIndex()");
	}

	public int getMaxColumnsInOrderBy() throws SQLException {
		throw new SQLException("Not Implemented: getMaxColumnsInOrderBy()");
	}

	public int getMaxColumnsInSelect() throws SQLException {
		throw new SQLException("Not Implemented: getMaxColumnsInSelect()");
	}

	public int getMaxColumnsInTable() throws SQLException {
		throw new SQLException("Not Implemented: getMaxColumnsInTable()");
	}

	public int getMaxConnections() throws SQLException {
		throw new SQLException("Not Implemented: getMaxConnections()");
	}

	public int getMaxCursorNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxCursorNameLength()");
	}

	public int getMaxIndexLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxIndexLength()");
	}

	public int getMaxSchemaNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxSchemaNameLength()");
	}

	public int getMaxProcedureNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxProcedureNameLength()");
	}

	public int getMaxCatalogNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxCatalogNameLength()");
	}

	public int getMaxRowSize() throws SQLException {
		throw new SQLException("Not Implemented: getMaxRowSize()");
	}

	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		throw new SQLException("Not Implemented: doesMaxRowSizeIncludeBlobs()");
	}

	public int getMaxStatementLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxStatementLength()");
	}

	public int getMaxStatements() throws SQLException {
		throw new SQLException("Not Implemented: getMaxStatements()");
	}

	public int getMaxTableNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxTableNameLength()");
	}

	public int getMaxTablesInSelect() throws SQLException {
		throw new SQLException("Not Implemented: getMaxTablesInSelect()");
	}

	public int getMaxUserNameLength() throws SQLException {
		throw new SQLException("Not Implemented: getMaxUserNameLength()");
	}

	public int getDefaultTransactionIsolation() throws SQLException {
		// FIXME
		throw new SQLException("Not Implemented: getDefaultTransactionIsolation()");
	}

	public boolean supportsTransactions() throws SQLException {
		// read only
		return false;
	}

	public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
		throw new SQLException("Not Implemented: supportsTransactionIsolationLevel(int level)");
	}

	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		// read only
		return false;
	}

	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		// read only
		return false;
	}

	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		// read only
		return false;
	}

	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		// read only
		return false;
	}

	public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
			throws SQLException {
		return conn.getCacheConnection().createStatement().executeQuery("SELECT * FROM GMETA_PROCEDURES");
	}

	public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern,
			String columnNamePattern) throws SQLException {
		throw new SQLException(
				"Not Implemented: getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern)");
	}

	public ResultSet getSchemas() throws SQLException {
		return conn.getCacheConnection().createStatement().executeQuery("SELECT * FROM GMETA_SCHEMAS");
	}

	public ResultSet getCatalogs() throws SQLException {
		return conn.getCacheConnection().createStatement().executeQuery("SELECT * FROM GMETA_CATALOGS");
	}

	public ResultSet getTableTypes() throws SQLException {
		conn.getCacheConnection().createStatement().execute("DELETE FROM GMETA_TABLETYPES");
		conn.getCacheConnection().createStatement().execute("INSERT INTO GMETA_TABLETYPES SET TABLE_TYPE = 'TABLE'");
		return conn.getCacheConnection().createStatement().executeQuery("SELECT * FROM GMETA_TABLETYPES");
	}

	public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)");
	}

	public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)");
	}

	public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)");
	}

	public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented: getVersionColumns(String catalog, String schema, String table)");
	}

	public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented: getPrimaryKeys(String catalog, String schema, String table)");
	}

	public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented: getImportedKeys(String catalog, String schema, String table)");
	}

	public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented: getExportedKeys(String catalog, String schema, String table)");
	}

	public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable,
			String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
		throw new SQLException(
				"Not Implemented: getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable)");
	}

	public ResultSet getTypeInfo() throws SQLException {
		conn.getCacheConnection().createStatement().execute("DELETE FROM GMETA_TYPEINFO");

		{
			int rowNum = 1;
			final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement(
					"INSERT INTO GMETA_TYPEINFO SET TYPE_NAME = ?, DATA_TYPE = ?, LITERAL_PREFIX = ?, LITERAL_SUFFIX = ?, NULLABLE = ?, CASE_SENSITIVE = ?, SEARCHABLE = ?, LOCAL_TYPE_NAME = ?");
			pstmt.setString(rowNum++, "id");
			pstmt.setInt(rowNum++, java.sql.Types.VARCHAR);
			pstmt.setString(rowNum++, "'");
			pstmt.setString(rowNum++, "'");
			pstmt.setShort(rowNum++, (short) ResultSetMetaData.columnNullableUnknown);
			pstmt.setBoolean(rowNum++, true);
			pstmt.setShort(rowNum++, (short) DatabaseMetaData.typeSearchable);
			pstmt.setString(rowNum++, "id");
			// 18桁 FIXME
			pstmt.execute();
		}

		{
			int rowNum = 1;
			final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement(
					"INSERT INTO GMETA_TYPEINFO SET TYPE_NAME = ?, DATA_TYPE = ?, LITERAL_PREFIX = ?, LITERAL_SUFFIX = ?, NULLABLE = ?, CASE_SENSITIVE = ?, SEARCHABLE = ?, LOCAL_TYPE_NAME = ?");
			pstmt.setString(rowNum++, "string");
			pstmt.setInt(rowNum++, java.sql.Types.VARCHAR);
			pstmt.setString(rowNum++, "'");
			pstmt.setString(rowNum++, "'");
			pstmt.setShort(rowNum++, (short) ResultSetMetaData.columnNullableUnknown);
			pstmt.setBoolean(rowNum++, true);
			pstmt.setShort(rowNum++, (short) DatabaseMetaData.typeSearchable);
			pstmt.setString(rowNum++, "string");
			pstmt.execute();
		}

		{
			int rowNum = 1;
			final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement(
					"INSERT INTO GMETA_TYPEINFO SET TYPE_NAME = ?, DATA_TYPE = ?, LITERAL_PREFIX = ?, LITERAL_SUFFIX = ?, NULLABLE = ?, CASE_SENSITIVE = ?, SEARCHABLE = ?, LOCAL_TYPE_NAME = ?");
			pstmt.setString(rowNum++, "picklist");
			pstmt.setInt(rowNum++, java.sql.Types.VARCHAR);
			pstmt.setString(rowNum++, "'");
			pstmt.setString(rowNum++, "'");
			pstmt.setShort(rowNum++, (short) ResultSetMetaData.columnNullableUnknown);
			pstmt.setBoolean(rowNum++, true);
			pstmt.setShort(rowNum++, (short) DatabaseMetaData.typeSearchable);
			pstmt.setString(rowNum++, "picklist");
			pstmt.execute();
		}

		{
			int rowNum = 1;
			final PreparedStatement pstmt = conn.getCacheConnection().prepareStatement(
					"INSERT INTO GMETA_TYPEINFO SET TYPE_NAME = ?, DATA_TYPE = ?, LITERAL_PREFIX = ?, LITERAL_SUFFIX = ?, NULLABLE = ?, CASE_SENSITIVE = ?, SEARCHABLE = ?, LOCAL_TYPE_NAME = ?");
			pstmt.setString(rowNum++, "address");
			pstmt.setInt(rowNum++, java.sql.Types.VARCHAR);
			pstmt.setString(rowNum++, "'");
			pstmt.setString(rowNum++, "'");
			pstmt.setShort(rowNum++, (short) ResultSetMetaData.columnNullableUnknown);
			pstmt.setBoolean(rowNum++, true);
			pstmt.setShort(rowNum++, (short) DatabaseMetaData.typeSearchable/* FIXME */);
			pstmt.setString(rowNum++, "address");
			pstmt.execute();
		}

		return conn.getCacheConnection().createStatement()
				.executeQuery("SELECT * FROM GMETA_TYPEINFO ORDER BY DATA_TYPE");
	}

	public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)");
	}

	public boolean supportsResultSetType(int type) throws SQLException {
		if (type == ResultSet.TYPE_FORWARD_ONLY) {
			return true;
		}
		if (type == ResultSet.TYPE_SCROLL_INSENSITIVE) {
			// TODO あとでよく見る。
			return false;
		}
		if (type == ResultSet.TYPE_SCROLL_SENSITIVE) {
			// TODO あとでよく見る。
			return true;
		}
		return false;
	}

	public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
		throw new SQLException("Not Implemented: supportsResultSetConcurrency(int type, int concurrency)");
	}

	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		// read only
		return false;
	}

	public boolean ownDeletesAreVisible(int type) throws SQLException {
		// read only
		return false;
	}

	public boolean ownInsertsAreVisible(int type) throws SQLException {
		// read only
		return false;
	}

	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		// FIXME
		return false;
	}

	public boolean othersDeletesAreVisible(int type) throws SQLException {
		// FIXME
		return false;
	}

	public boolean othersInsertsAreVisible(int type) throws SQLException {
		// FIXME
		return false;
	}

	public boolean updatesAreDetected(int type) throws SQLException {
		// FIXME
		return false;
	}

	public boolean deletesAreDetected(int type) throws SQLException {
		// FIXME
		return false;
	}

	public boolean insertsAreDetected(int type) throws SQLException {
		// FIXME
		return false;
	}

	public boolean supportsBatchUpdates() throws SQLException {
		// No!
		return false;
	}

	public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
			throws SQLException {
		return conn.getCacheConnection().createStatement().executeQuery("SELECT * FROM GMETA_UDTS");
	}

	public boolean supportsSavepoints() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsNamedParameters() throws SQLException {
		return true;
	}

	public boolean supportsMultipleOpenResults() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsGetGeneratedKeys() throws SQLException {
		// No!
		return false;
	}

	public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
		throw new SQLException(
				"Not Implemented: getSuperTypes(String catalog, String schemaPattern, String typeNamePattern)");
	}

	public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
		throw new SQLException(
				"Not Implemented: getSuperTables(String catalog, String schemaPattern, String tableNamePattern)");
	}

	public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern,
			String attributeNamePattern) throws SQLException {
		throw new SQLException(
				"Not Implemented: getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern)");
	}

	public boolean supportsResultSetHoldability(int holdability) throws SQLException {
		// No!
		return false;
	}

	public int getResultSetHoldability() throws SQLException {
		throw new SQLException("Not Implemented: getResultSetHoldability()");
	}

	public abstract int getDatabaseMajorVersion() throws SQLException;

	public abstract int getDatabaseMinorVersion() throws SQLException;

	public int getJDBCMajorVersion() throws SQLException {
		return 4;
	}

	public int getJDBCMinorVersion() throws SQLException {
		return 2;
	}

	public int getSQLStateType() throws SQLException {
		throw new SQLException("Not Implemented: getSQLStateType()");
	}

	public boolean locatorsUpdateCopy() throws SQLException {
		throw new SQLException("Not Implemented: locatorsUpdateCopy()");
	}

	public boolean supportsStatementPooling() throws SQLException {
		// No!
		return false;
	}

	public RowIdLifetime getRowIdLifetime() throws SQLException {
		throw new SQLException("Not Implemented: getRowIdLifetime()");
	}

	public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
		throw new SQLException("Not Implemented: getSchemas(String catalog, String schemaPattern)");
	}

	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		// No!
		return false;
	}

	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		throw new SQLException("Not Implemented: autoCommitFailureClosesAllResultSets()");
	}

	public ResultSet getClientInfoProperties() throws SQLException {
		throw new SQLException("Not Implemented: getClientInfoProperties()");
	}

	public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern)
			throws SQLException {
		throw new SQLException(
				"Not Implemented: getFunctions(String catalog, String schemaPattern, String functionNamePattern)");
	}

	public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern,
			String columnNamePattern) throws SQLException {
		throw new SQLException(
				"Not Implemented: getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern)");
	}

	public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern,
			String columnNamePattern) throws SQLException {
		throw new SQLException(
				"Not Implemented: getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)");
	}

	public boolean generatedKeyAlwaysReturned() throws SQLException {
		return false;
	}

	/**
	 * TABLES
	 */
	public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
			throws SQLException {

		if (BlancoGenericJdbcCacheUtilDatabaseMetaData
				.isCacheDatabaseMetaDataTablesCached(conn.getCacheConnection()) == false) {
			// call abstract method
			fillCacheTableOfGetTables(catalog, schemaPattern, tableNamePattern, types);
		}

		return BlancoGenericJdbcCacheUtilDatabaseMetaData.getTablesFromCache(conn.getCacheConnection(), catalog,
				schemaPattern, tableNamePattern, types);
	}

	/**
	 * COLUMNS
	 */
	public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
			throws SQLException {

		// Build table name list to process.
		final List<String> tableNameList = new ArrayList<String>();
		{
			final ResultSet rs = conn.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, null);
			for (; rs.next();) {
				tableNameList.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
		}

		// process every table.
		for (String tableName : tableNameList) {
			if (BlancoGenericJdbcCacheUtilDatabaseMetaData.isCacheDatabaseMetaDataColumnsCached(
					conn.getCacheConnection(), catalog, schemaPattern, tableName) == false) {
				// call abstract method
				fillCacheTableOfGetColumns(catalog, schemaPattern, tableName, columnNamePattern);
			}
		}

		return BlancoGenericJdbcCacheUtilDatabaseMetaData.getColumnsFromCache(conn.getCacheConnection(), null, catalog,
				schemaPattern, tableNamePattern, columnNamePattern);
	}

	protected abstract void fillCacheTableOfGetTables(String catalog, String schemaPattern, String tableNamePattern,
			String[] types) throws SQLException;

	protected abstract void fillCacheTableOfGetColumns(String catalog, String schema, String tableName,
			String columnNamePattern) throws SQLException;
}
