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
	private int beginSnapshot = -1;
	private int endSnapshot = -1;
	private long dbid = -1;
	
	private final SQLException noConnectionException =
			new SQLException("No connection to the database!");
	
	private final OutOfSequenceException noBeginOutOfSequenceException =
			new OutOfSequenceException("No begin snapshot available, create begin and end snapshots first!");
	
	private final OutOfSequenceException noEndOutOfSequenceException =
			new OutOfSequenceException("No end snapshot available, create end snapshot first!");
	
	/**
	 * Constructs a new AWR object.
	 */
	public AWR() {
	}
	
	/**
	 * Constructs a new AWR object.
	 * @param conn The connection to be used for AWR generation.
	 * The connection being used needs privileges on DBMS_WORKLOAD_REPOSITORY and V$DATABASE.
	 * 
	 */
	public AWR(Connection conn) {
		myConn = conn;
	}

	/**
	 * Sets the connection to be used for AWR generation.
	 * @param conn The connection to be used for AWR generation.
	 * The connection being used needs privileges on DBMS_WORKLOAD_REPOSITORY and V$DATABASE.
	 */
	public void setConnection(Connection conn) {
		myConn = conn;
	}
	
	/**
	 * Creates a new snapshot.
	 * @return The snapshot id
	 * @throws SQLException	Any SQL error that occurs during the operation
	 */
	public Integer createSnapshot() throws SQLException {
		
		if (null == myConn) {
			throw noConnectionException;
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
		
		// Return snapshot id
		return Integer.valueOf(id);
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
			throw noConnectionException;
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
	 * @throws OutOfSequenceException An error with the snapshot sequence between begin and end snapshot
	 */
	public String getAWRReport(AWR_MODE mode) throws SQLException, OutOfSequenceException {
		if (null == myConn) {
			throw noConnectionException;
		}
		
		if (beginSnapshot == -1) {
			throw noBeginOutOfSequenceException;
		}
		
		if (endSnapshot == -1) {
			throw noEndOutOfSequenceException;
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
	 * Resets the AWR object.
	 * This can be used to reset the AWR object and start with a new set of snapshots.
	 * @param closeDBConnection If the parameter is set to true the connection used will be closed.
	 * If the parameter is set to false the connection will remain open and is just dereferenced.
	 */
	public void reset(boolean closeDBConnection) {
		if (closeDBConnection) {
			try { myConn.close(); }
			catch (SQLException e) { /* Ignore SQLException on close. */ }
		}
		reset();
	}
	
	/**
	 * Resets the AWR object.
	 * This can be used to reset the AWR object and start with a new set of snapshots.
	 * The database connection will only be dereferenced but not closed.
	 */
	public void reset() {
		myConn = null;
		beginSnapshot = -1;
		endSnapshot = -1;
		dbid = -1;
	}
}
