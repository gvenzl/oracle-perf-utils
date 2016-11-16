package com.gvenzl.awr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Exposes various Automatic Workload Repository (AWR) interfaces.
 * @author gvenzl
 *
 */
public class AWR {
	
	private Connection myConn = null;
	private static AWR instance = null;
	private int beginSnapshot = -1;
	private int endSnapshot = -1;
	private long dbid = -1;
	
	protected AWR() {
		// Exists only to defeat instantiation.
		// The AWR class is a global representation, i.e only one instance exists.
	}
	   
	/**
	 * Returns an instance of the AWR class.
	 * @return An instance of the AWR class
	 */
	public static AWR getInstance() {
		if(instance == null) {
			instance = new AWR();
		}
		return instance;
	}

	/**
	 * Sets the connection to be used for AWR generation.
	 * @param conn The connection to be used for AWR generation
	 */
	public void setConnection(Connection conn) {
		myConn = conn;
	}
	
	/**
	 * Creates a new snapshot.
	 * @throws SQLException	Any SQL error that occurs during the operation
	 */
	public void createSnapshot() throws SQLException {
		
		if (null == myConn) {
			throw new SQLException("No connection to the database");
		}
		
		PreparedStatement stmt = myConn.prepareStatement(
				"SELECT DBMS_WORKLOAD_REPOSITORY.CREATE_SNAPSHOT() FROM dual");
		ResultSet rslt = stmt.executeQuery();
		rslt.next();
		int id = rslt.getInt(1);
		stmt.close();
		
		if (beginSnapshot == -1) {
			beginSnapshot = id;
			endSnapshot = -1;
		}
		else {
			endSnapshot = id;
		}
	}
	
	/**
	 * Sets the DBID for AWR generation.
	 * @param conn	A connection to the database
	 * @throws SQLException Any SQL error that occurs during the operation
	 */
	private void setDBID() throws SQLException {
		// DBID is already set
		if (dbid != -1) {
			return;
		}
		
		if (null == myConn) {
			throw new SQLException("No connection to the database");
		}
		
		PreparedStatement stmt = myConn.prepareStatement("SELECT dbid FROM v$database");
		ResultSet rslt = stmt.executeQuery();
		rslt.next();
		dbid = rslt.getLong(1);
		rslt.close();
		stmt.close();
	}
	
	/**
	 * Returns an AWR report in plain text.
	 * @param mode The {@link} AWR_MODE format of the report
	 * @return The AWR report
	 * @throws SQLException Any SQL error that occurs during the operation
	 * @throw OutOfSequenceException An error with the snapshot sequence between begin and end snapshot
	 */
	public String getAWRReport(AWR_MODE mode) throws SQLException, OutOfSequenceException {
		if (null == myConn) {
			throw new SQLException("No connection to the database");
		}
		
		if (beginSnapshot == -1) {
			throw new OutOfSequenceException("No begin snapshot available, create begin and end snapshots first!");
		}
		
		if (endSnapshot == -1) {
			throw new OutOfSequenceException("No end snapshot available, create end snapshot first!");
		}
		
		setDBID();

		PreparedStatement stmt;
		switch (mode) {
			case HTML: {
				stmt = myConn.prepareStatement(
						"SELECT * FROM TABLE(DBMS_WORKLOAD_REPOSITORY.AWR_REPORT_HTML(?, 1, ?, ?))");
				break;
			}
			case TEXT:
			default: {
				stmt = myConn.prepareStatement(
						"SELECT * FROM TABLE(DBMS_WORKLOAD_REPOSITORY.AWR_REPORT_TEXT(?, 1, ?, ?))");
			}
		}
		
		stmt.setLong(1, dbid);
		stmt.setInt(2, beginSnapshot);
		stmt.setInt(3, endSnapshot);
		ResultSet rslt = stmt.executeQuery();
		
		String retAWRReport = "";
		while (rslt.next()) {
			retAWRReport += rslt.getString(1) + '\n';
		}
		stmt.close();
		return retAWRReport;
	}
	
	/**
	 * Closes the connection to the database.
	 * This can be used to avoid long lived connections to the database.
	 * @throws SQLException Any SQL error that occurs on closing the connection.
	 */
	public void closeConnection() throws SQLException {
		myConn.close();
		myConn = null;
	}
}
