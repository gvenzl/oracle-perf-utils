/*
* author:  gvenzl
* created: 23 Dec 2016
*/

package com.gvenzl.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gvenzl.OutOfSequenceException;
import com.gvenzl.info.connection.ConnectionInfoFactory;

public class ConnectionStatsTest {
	
	private Connection conn;
	private static final String user = "test";
	private static final String password = "test";
	private static final String host = "localhost";
	private static final String port = "1521";
	private static final String service = "ORCLPDB1";	

	@Before
	public void setup() throws SQLException {
		conn = DriverManager.getConnection(
				"jdbc:oracle:thin:" + user + "/" + password + "@//" + host + ":" + port + "/" + service);
	}

	@Test
	public void test_instantiation() throws SQLException {
		System.out.println("test_instantiation()");
		new ConnectionStats(conn);		
	}
	
	@Test
	public void test_createSnapshot() throws SQLException {
		System.out.println("test_createSnapshot()");
		ConnectionStats stats = new ConnectionStats(conn);
		stats.createSnapshot();
	}
	
	@Test
	public void test_createSnapshotDifferentSID() throws SQLException, OutOfSequenceException {
		System.out.println("test_createSnapshotDifferentSID()");
		Connection testConn = DriverManager.getConnection(
				"jdbc:oracle:thin:" + user + "/" + password + "@//" + host + ":" + port + "/" + service);
		int sid = ConnectionInfoFactory.getConnectionInfo(testConn).getSid();
		
		ConnectionStats stats = new ConnectionStats(conn);
		stats.createSnapshot(sid);
		stats.createSnapshot(sid);
		Assert.assertNotNull(stats.getDelta());
	}
	
	@Test
	public void test_getDelta() throws SQLException, OutOfSequenceException {
		System.out.println("test_getDelta()");
		ConnectionStats stats = new ConnectionStats(conn);
		stats.createSnapshot();
		stats.createSnapshot();
		Assert.assertNotNull(stats.getDelta());
	}
	
	@Test
	public void test_getAllStatisticNames() throws SQLException {
		System.out.println("test_getAllStatisticNames()");
		ConnectionStats stats = new ConnectionStats(conn);
		Assert.assertNotNull(stats.getAllStatisticNames());
	}
	
	@Test
	public void test_getCurrentStatistics() throws SQLException {
		System.out.println("test_getCurrentStatistics()");
		ConnectionStats stats = new ConnectionStats(conn);
		Assert.assertNotNull(stats.getCurrentStats());
	}

	@Test
	public void test_getCurrentStatisticsDifferentSID() throws SQLException {
		System.out.println("test_getCurrentStatisticsDifferentSID()");
		
		System.out.println("test_createSnapshotDifferentSID()");
		Connection testConn = DriverManager.getConnection(
				"jdbc:oracle:thin:" + user + "/" + password + "@//" + host + ":" + port + "/" + service);
		int sid = ConnectionInfoFactory.getConnectionInfo(testConn).getSid();
		
		ConnectionStats stats = new ConnectionStats(conn);
		Assert.assertNotNull(stats.getCurrentStats(sid));
	}
}
