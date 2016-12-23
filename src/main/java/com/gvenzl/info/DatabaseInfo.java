/*
* author:  gvenzl
* created: 23 Dec 2016
*/

package com.gvenzl.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides database specific information.
 * @author gvenzl
 *
 */
public class DatabaseInfo {
	
	private static int NO_PRIVILEGES = 1031;
	private static int TABLE_OR_VIEW_DOES_NOT_EXIST = 942;
	private String dbName = "";
	private boolean cdb;
	private String platformName = "";
	private String version = "";
	private String characterSet = "";
	
	/**
	 * Creates a new DatabaseInfo object.
	 * @param conn A database connection used to retrieve the information
	 * @throws SQLException Any SQL error received while creating the object
	 */
	public DatabaseInfo(Connection conn) throws SQLException {
		
		if (null == conn || conn.isClosed()) {
			throw new SQLException ("No connection to the database");
		}
		
		PreparedStatement psDBInfo = conn.prepareStatement(
				"SELECT name, platform_name, cdb FROM V$DATABASE");
		PreparedStatement psVersion = conn.prepareStatement(
				"SELECT banner FROM V$VERSION WHERE banner LIKE 'Oracle Database%'");
		PreparedStatement psCharacterSet = conn.prepareStatement(
				"SELECT value FROM V$NLS_PARAMETERS WHERE parameter = 'NLS_CHARACTERSET'");
		ResultSet rslt;
		
		try {
			rslt = psVersion.executeQuery();
			rslt.next();
			version = rslt.getString(1);
		}
		catch (SQLException e) {
			if (hasAccess(e)) { throw e; }
		}
		
		try {
			rslt = psDBInfo.executeQuery();
			rslt.next();
			dbName = rslt.getString(1);
			platformName = rslt.getString(2);
			cdb = rslt.getString(3).equals("YES");
		}
		catch (SQLException e) {
			if (hasAccess(e)) { throw e; }
		}
		
		try {
			rslt = psCharacterSet.executeQuery();
			rslt.next();
			characterSet = rslt.getString(1);
		}
		catch (SQLException e) {
			if (hasAccess(e)) { throw e; }
		}
	}
	
	/**
	 * Checks whether the {@link java.sql.SQLException} is due to lack of privileges.
	 * @param sqlEx The SQLException to check
	 * @return True if the SQLException is not related to insufficient privileges, otherwise false
	 */
	private boolean hasAccess(SQLException sqlEx) {
		return (sqlEx.getErrorCode() != NO_PRIVILEGES
				&& sqlEx.getErrorCode() != TABLE_OR_VIEW_DOES_NOT_EXIST);
	}
	
	/**
	 * 
	 * @return The characterset of the database
	 */
	public String getCharacterSet() {
		return characterSet;
	}
	
	/**
	 * 
	 * @return The database name
	 */
	public String getDBName() {
		return dbName;
	}
	
	/**
	 * 
	 * @return The platform (OS) name
	 */
	public String getPlatformName () {
		return platformName;
	}
	
	/**
	 * 
	 * @return The database version string
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * 
	 * @return True if the database is a container database, otherwise false
	 */
	public boolean isCDB() {
		return cdb;
	}

}
