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

package blanco.jdbc.generic.driver.databasemetadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import blanco.jdbc.generic.driver.cache.BlancoGenericJdbcCacheDatabaseMetaDataUtil;
import blanco.sfdc.jdbc.driver.BlancoSfdcJdbcConnection;

public abstract class AbstractBlancoGenericJdbcDatabaseMetaData implements DatabaseMetaData {
	protected BlancoSfdcJdbcConnection conn = null;

	public AbstractBlancoGenericJdbcDatabaseMetaData(final BlancoSfdcJdbcConnection conn) {
		this.conn = conn;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean allProceduresAreCallable() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean allTablesAreSelectable() throws SQLException {
		// non queryable are already filtered.
		return true;
	}

	public String getURL() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getUserName() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isReadOnly() throws SQLException {
		// Always Read-Only
		return true;
	}

	public boolean nullsAreSortedHigh() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean nullsAreSortedLow() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean nullsAreSortedAtStart() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean nullsAreSortedAtEnd() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getDatabaseProductName() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getDatabaseProductVersion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getDriverName() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getDriverVersion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getDriverMajorVersion() {
		return 1;
	}

	public int getDriverMinorVersion() {
		return 1;
	}

	public boolean usesLocalFiles() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean usesLocalFilePerTable() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean storesUpperCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean storesLowerCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean storesMixedCaseIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getIdentifierQuoteString() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getSQLKeywords() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getNumericFunctions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getStringFunctions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getSystemFunctions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getTimeDateFunctions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getSearchStringEscape() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getExtraNameCharacters() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsColumnAliasing() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean nullPlusNonNullIsNull() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsConvert() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsConvert(int fromType, int toType) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsTableCorrelationNames() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsExpressionsInOrderBy() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsOrderByUnrelated() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsGroupBy() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsGroupByUnrelated() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsGroupByBeyondSelect() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsLikeEscapeClause() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsMultipleResultSets() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsMultipleTransactions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsNonNullableColumns() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsMinimumSQLGrammar() throws SQLException {
		// No!
		return false;
	}

	public boolean supportsCoreSQLGrammar() throws SQLException {
		// No!
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
		throw new SQLException("Not Implemented.");
	}

	public String getProcedureTerm() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getCatalogTerm() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean isCatalogAtStart() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public String getCatalogSeparator() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsSchemasInDataManipulation() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsPositionedDelete() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsPositionedUpdate() throws SQLException {
		throw new SQLException("Not Implemented.");
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
		throw new SQLException("Not Implemented.");
	}

	public int getMaxCharLiteralLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxColumnNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxColumnsInGroupBy() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxColumnsInIndex() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxColumnsInOrderBy() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxColumnsInSelect() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxColumnsInTable() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxConnections() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxCursorNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxIndexLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxSchemaNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxProcedureNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxCatalogNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxRowSize() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxStatementLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxStatements() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxTableNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxTablesInSelect() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getMaxUserNameLength() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getDefaultTransactionIsolation() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsTransactions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern,
			String columnNamePattern) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getSchemas() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getCatalogs() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getTableTypes() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable,
			String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getTypeInfo() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsResultSetType(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean ownDeletesAreVisible(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean ownInsertsAreVisible(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean othersDeletesAreVisible(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean othersInsertsAreVisible(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean updatesAreDetected(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean deletesAreDetected(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean insertsAreDetected(int type) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsBatchUpdates() throws SQLException {
		// No!
		return false;
	}

	public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public abstract Connection getConnection() throws SQLException;

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
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern,
			String attributeNamePattern) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsResultSetHoldability(int holdability) throws SQLException {
		// No!
		return false;
	}

	public int getResultSetHoldability() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getDatabaseMajorVersion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getDatabaseMinorVersion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getJDBCMajorVersion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getJDBCMinorVersion() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public int getSQLStateType() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean locatorsUpdateCopy() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsStatementPooling() throws SQLException {
		// No!
		return false;
	}

	public RowIdLifetime getRowIdLifetime() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		// No!
		return false;
	}

	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getClientInfoProperties() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern)
			throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern,
			String columnNamePattern) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern,
			String columnNamePattern) throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	public boolean generatedKeyAlwaysReturned() throws SQLException {
		throw new SQLException("Not Implemented.");
	}

	/**
	 * TABLES
	 */
	public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
			throws SQLException {

		if (BlancoGenericJdbcCacheDatabaseMetaDataUtil.isGmetaTablesCached(conn.getCacheConnection()) == false) {
			// call abstract method
			fillCacheTableOfGetTables(catalog, schemaPattern, tableNamePattern, types);
		}

		return BlancoGenericJdbcCacheDatabaseMetaDataUtil.getTablesFromCache(conn.getCacheConnection(), catalog,
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
			if (BlancoGenericJdbcCacheDatabaseMetaDataUtil.isGmetaColumnsCached(conn.getCacheConnection(), catalog,
					schemaPattern, tableName) == false) {
				// call abstract method
				fillCacheTableOfGetColumns(catalog, schemaPattern, tableName, columnNamePattern);
			}
		}

		return BlancoGenericJdbcCacheDatabaseMetaDataUtil.getColumnsFromCache(conn.getCacheConnection(), null, catalog,
				schemaPattern, tableNamePattern, columnNamePattern);
	}

	protected abstract void fillCacheTableOfGetTables(String catalog, String schemaPattern, String tableNamePattern,
			String[] types) throws SQLException;

	protected abstract void fillCacheTableOfGetColumns(String catalog, String schema, String tableName,
			String columnNamePattern) throws SQLException;
}
