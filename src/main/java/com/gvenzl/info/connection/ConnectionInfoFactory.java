/*
* author:  gvenzl
* created: 30 Nov 2016
*/

package com.gvenzl.info.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Builds a {@link ConnectionInfo} object.
 * @author gvenzl
 *
 */
public class ConnectionInfoFactory {
	
	public static ConnectionInfo getConnectionInfo(Connection conn) throws SQLException {
		
		HashMap<String, String> info = new HashMap<String, String>();
		
		String infoStmt = "SELECT 'ACTION', SYS_CONTEXT('USERENV','ACTION') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'AUTHENTICATION_METHOD', SYS_CONTEXT('USERENV','AUTHENTICATION_METHOD') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'AUTHENTICATED_IDENTITY', SYS_CONTEXT('USERENV','AUTHENTICATED_IDENTITY') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'AUTHENTICATION_METHOD', SYS_CONTEXT('USERENV','AUTHENTICATION_METHOD') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CDB_NAME', SYS_CONTEXT('USERENV','CDB_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CLIENT_IDENTIFIER', SYS_CONTEXT('USERENV','CLIENT_IDENTIFIER') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CLIENT_INFO', SYS_CONTEXT('USERENV','CLIENT_INFO') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CLIENT_PROGRAM_NAME', SYS_CONTEXT('USERENV','CLIENT_PROGRAM_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CON_ID', SYS_CONTEXT('USERENV','CON_ID') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CON_NAME', SYS_CONTEXT('USERENV','CON_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CURRENT_EDITION_NAME', SYS_CONTEXT('USERENV','CURRENT_EDITION_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CURRENT_SCHEMA', SYS_CONTEXT('USERENV','CURRENT_SCHEMA') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'CURRENT_USER', SYS_CONTEXT('USERENV','CURRENT_USER') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'DATABASE_ROLE', SYS_CONTEXT('USERENV','DATABASE_ROLE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'DB_DOMAIN', SYS_CONTEXT('USERENV','DB_DOMAIN') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'DB_NAME', SYS_CONTEXT('USERENV','DB_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'DB_SUPPLEMENTAL_LOG_LEVEL', SYS_CONTEXT('USERENV','DB_SUPPLEMENTAL_LOG_LEVEL') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'DB_UNIQUE_NAME', SYS_CONTEXT('USERENV','DB_UNIQUE_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'DBLINK_INFO', SYS_CONTEXT('USERENV','DBLINK_INFO') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'ENTERPRISE_IDENTITY', SYS_CONTEXT('USERENV','ENTERPRISE_IDENTITY') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'GLOBAL_UID', SYS_CONTEXT('USERENV','GLOBAL_UID') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'HOST', SYS_CONTEXT('USERENV','HOST') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'IDENTIFICATION_TYPE', SYS_CONTEXT('USERENV','IDENTIFICATION_TYPE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'INSTANCE', SYS_CONTEXT('USERENV','INSTANCE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'INSTANCE_NAME', SYS_CONTEXT('USERENV','INSTANCE_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'IP_ADDRESS', SYS_CONTEXT('USERENV','IP_ADDRESS') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'IS_APPLY_SERVER', SYS_CONTEXT('USERENV','IS_APPLY_SERVER') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'IS_DG_ROLLING_UPGRADE', SYS_CONTEXT('USERENV','IS_DG_ROLLING_UPGRADE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'ISDBA', SYS_CONTEXT('USERENV','ISDBA') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'LANG', SYS_CONTEXT('USERENV','LANG') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'LANGUAGE', SYS_CONTEXT('USERENV','LANGUAGE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'MODULE', SYS_CONTEXT('USERENV','MODULE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NETWORK_PROTOCOL', SYS_CONTEXT('USERENV','NETWORK_PROTOCOL') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NLS_CALENDAR', SYS_CONTEXT('USERENV','NLS_CALENDAR') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NLS_CURRENCY', SYS_CONTEXT('USERENV','NLS_CURRENCY') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NLS_DATE_FORMAT', SYS_CONTEXT('USERENV','NLS_DATE_FORMAT') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NLS_DATE_LANGUAGE', SYS_CONTEXT('USERENV','NLS_DATE_LANGUAGE') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NLS_SORT', SYS_CONTEXT('USERENV','NLS_SORT') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'NLS_TERRITORY', SYS_CONTEXT('USERENV','NLS_TERRITORY') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'PLATFORM_SLASH', SYS_CONTEXT('USERENV','PLATFORM_SLASH') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'POLICY_INVOKER', SYS_CONTEXT('USERENV','POLICY_INVOKER') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'PROXY_ENTERPRISE_IDENTITY', SYS_CONTEXT('USERENV','PROXY_ENTERPRISE_IDENTITY') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'PROXY_USER', SYS_CONTEXT('USERENV','PROXY_USER') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'SERVER_HOST', SYS_CONTEXT('USERENV','SERVER_HOST') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'SERVICE_NAME', SYS_CONTEXT('USERENV','SERVICE_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'SESSION_EDITION_NAME', SYS_CONTEXT('USERENV','SESSION_EDITION_NAME') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'SESSION_USER', SYS_CONTEXT('USERENV','SESSION_USER') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'SID', SYS_CONTEXT('USERENV','SID') FROM DUAL " +
				"UNION ALL " +
				"SELECT 'TERMINAL', SYS_CONTEXT('USERENV','TERMINAL') FROM DUAL";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(infoStmt);
			ResultSet rslt = stmt.executeQuery();
			while (rslt.next()) {
				String key = rslt.getString(1).toLowerCase();
				String value = rslt.getString(2);
				// Put empty strings for null to prevent NPEs on getters.
				if (null == value) {
					value = "";
				}
				info.put(key, value);
			}
			rslt.close();
			stmt.close();
		}
		catch (Exception e) {
			throw new SQLException("Cannot retrieve connection information!", e);
		}
		
		return new ConnectionInfo(info);
		
	}

}
