/*
* author:  gvenzl
* created: 23 Dec 2016
*/

package com.gvenzl.stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.gvenzl.OutOfSequenceException;

/**
 * Provides statistic for the database connection.
 * @author gvenzl
 *
 */
public class ConnectionStats {

	private static final int INIT_CAPACITY = 1000;
	private Connection myConn;
	private HashMap<String, Long> beginStats;
	private HashMap<String, Long> endStats;
	
	/**
	 * Creates a new ConnectionStats object.
	 * @param conn The connection used to retrieve the statistics
	 * @throws SQLException Any SQL error during the instantiation
	 */
	public ConnectionStats(Connection conn) throws SQLException {
		
		if (null == conn || conn.isClosed()) {
			throw new SQLException ("No connection to the database");
		}
		myConn = conn;
	}
	
	/**
	 * Creates a new snapshot for the current connection.
	 * @throws SQLException Any SQL error during this operation
	 */
	public void createSnapshot() throws SQLException {
		HashMap<String, Long> results = getCurrentStats();
		
		if (null == beginStats) {
			beginStats = results;
		}
		else {
			endStats = results;
		}
	}
	
	/**
	 * Returns the current statistics for the database connection.
	 * @return A HashMap of the current statistics
	 * @throws SQLException Any SQL error during this operation
	 */
	public HashMap<String, Long> getCurrentStats() throws SQLException {
		HashMap<String, Long> results = new HashMap<String, Long>(INIT_CAPACITY);

		PreparedStatement stmt = myConn.prepareStatement(
				"SELECT n.name, s.value FROM v$mystat s, v$statname n"
				+ " WHERE s.statistic# = n.statistic#"
				+ " ORDER BY n.name");
		ResultSet rslt = stmt.executeQuery();
		while (rslt.next()) {
			results.put(rslt.getString(1), rslt.getLong(2));
		}
		stmt.close();
		
		return results;
	}
	
	/**
	 * Creates a new snapshot for the database connection with a given session id.
	 * @param sessionId The session id (SID) for which to take the snapshot
	 * @throws SQLException Any SQL error during this operation
	 */
	public void createSnapshot(int sessionId) throws SQLException {
		HashMap<String, Long> results = getCurrentStats(sessionId);
		
		if (null == beginStats) {
			beginStats = results;
		}
		else {
			endStats = results;
		}
	}
	
	/**
	 * Returns the current statistics for the database connection with the given session id.
	 * @param sessionId The session id (SID) for which to retrieve the statistics
	 * @return A HashMap of the current statistics
	 * @throws SQLException Any SQL error during this operation
	 */
	public HashMap<String, Long> getCurrentStats(int sessionId) throws SQLException {
		HashMap<String, Long> results = new HashMap<String, Long>(INIT_CAPACITY);
		
		PreparedStatement stmt = myConn.prepareStatement(
				"SELECT n.name, s.value FROM v$sesstat s, v$statname n"
				+ " WHERE s.sid = ? AND s.statistic# = n.statistic#"
				+ " ORDER BY n.name");
		stmt.setInt(1, sessionId);
		ResultSet rslt = stmt.executeQuery();
		while (rslt.next()) {
			results.put(rslt.getString(1), rslt.getLong(2));
		}
		stmt.close();
		
		return results;
	}
	
	/**
	 * Returns a delta of the statistics of the connection. Only statistics with a delta are returned.
	 * @return A HashMap containing all statistics with deltas
	 * @throws OutOfSequenceException If the begin or end snapshot is out of sequence
	 */
	public HashMap<String, Long> getDelta() throws OutOfSequenceException {
		if (null == beginStats) {
			throw new OutOfSequenceException("No begin snapshot available, create begin and end snapshots first!");
		}
		
		if (null == endStats) {
			throw new OutOfSequenceException("No end snapshot available, create begin snapshot first!");
		}
		
		HashMap<String, Long> delta = new HashMap<String, Long>();
		
		for (Entry<String, Long> entry : beginStats.entrySet()) {
			String beginKey = entry.getKey();
			Long beginValue = entry.getValue();
			Long endValue = endStats.get(beginKey);
			
			// Only store delats
			long deltaLong = endValue.longValue()-beginValue.longValue();
			if (deltaLong != 0) {
				delta.put(beginKey, deltaLong);
			}
		}
		
		beginStats = null;
		endStats = null;
		
		return delta;
	}
	
	/**
	 * Returns all statistic names of the database.
	 * @return A list of all statistic names
	 * @throws SQLException Any SQL error during this operation
	 */
	public ArrayList<String> getAllStatisticNames() throws SQLException {
		ArrayList<String> stats = new ArrayList<String>(INIT_CAPACITY);
		
		PreparedStatement stmt = myConn.prepareStatement(
				"SELECT n.name FROM v$statname n ORDER BY n.name");
		ResultSet rslt = stmt.executeQuery();
		while (rslt.next()) {
			stats.add(rslt.getString(1));
		}
		return stats;
	}
}
