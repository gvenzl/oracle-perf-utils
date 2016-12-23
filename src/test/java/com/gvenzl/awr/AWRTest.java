/*
* author:  gvenzl
* created: 30 Nov 2016
*/

package com.gvenzl.awr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.gvenzl.OutOfSequenceException;

public class AWRTest {
	
	private AWR awr;
	private Connection conn;
	
	@Before
	public void setup() throws SQLException {
		conn = DriverManager.getConnection(
				"jdbc:oracle:thin:system/oracle@//localhost:1521/ORCLCDB");
		awr = new AWR(conn);
	}

	@Test
	public void test_instantiation() {
		System.out.println("test_instantiation()");
		new AWR();
	}
	
	@Test
	public void test_instantiationWithConn() {
		System.out.println("test_instantiationWithConn()");
		new AWR(conn);
	}

	@Test
	public void test_setConnection() {
		System.out.println("test_setConnection()");
		awr.setConnection(conn);
	}
	
	@Test
	public void test_createSnapshot() throws SQLException {
		System.out.println("test_createSnapshot()");
		awr.createSnapshot();
	}
	
	@Test
	public void test_getAWRReportText() throws SQLException, OutOfSequenceException {
		System.out.println("test_getAWRReportText()");
		String expected = "End of Report";
		awr.createSnapshot();
		awr.createSnapshot();
		String result = awr.getAWRReport(AWR_MODE.TEXT);
		Assert.assertNotEquals(-1, result.lastIndexOf(expected));
	}
	
	@Test
	public void test_getAWRReportHTML() throws SQLException, OutOfSequenceException {
		System.out.println("test_getAWRReportHTML()");
		String expected = "End of Report\n</body></html>";
		awr.createSnapshot();
		awr.createSnapshot();
		String result = awr.getAWRReport(AWR_MODE.HTML);
		Assert.assertNotEquals(-1, result.lastIndexOf(expected));
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void test_negativeBeginSnapshotOutOfSequenceText()
			throws OutOfSequenceException, SQLException {
		System.out.println("test_negativeBeginSnapshotOutOfSequenceText()");
		thrown.expect(OutOfSequenceException.class);
		thrown.expectMessage("No begin snapshot available, create begin and end snapshots first!");
		awr.getAWRReport(AWR_MODE.TEXT);
	}

	@Test
	public void test_negativeBeginSnapshotOutOfSequenceHTML()
			throws OutOfSequenceException, SQLException {
		System.out.println("test_negativeBeginSnapshotOutOfSequenceHTML()");
		thrown.expect(OutOfSequenceException.class);
		thrown.expectMessage("No begin snapshot available, create begin and end snapshots first!");
		awr.getAWRReport(AWR_MODE.HTML);
	}

	@Test
	public void test_negativeEndSnapshotOutOfSequenceText()
			throws OutOfSequenceException, SQLException {
		System.out.println("test_negativeEndSnapshotOutOfSequenceText()");
		awr.createSnapshot();
		thrown.expect(OutOfSequenceException.class);
		thrown.expectMessage("No end snapshot available, create end snapshot first!");
		awr.getAWRReport(AWR_MODE.TEXT);
	}

	@Test
	public void test_negativeEndSnapshotOutOfSequenceHTML()
			throws OutOfSequenceException, SQLException {
		System.out.println("test_negativeEndSnapshotOutOfSequenceHTML()");
		awr.createSnapshot();
		thrown.expect(OutOfSequenceException.class);
		thrown.expectMessage("No end snapshot available, create end snapshot first!");
		awr.getAWRReport(AWR_MODE.HTML);
	}

	@Test
	public void test_negativeSnapshotOnClosedConnection() throws SQLException {
		System.out.println("test_negativeSnapshotOnClosedConnection()");
		awr.setConnection(null);
		thrown.expect(SQLException.class);
		thrown.expectMessage("No connection to the database!");
		awr.createSnapshot();
	}
	
	@Test
	public void test_negativeAWRReportTextOnClosedConnection() throws SQLException, OutOfSequenceException {
		System.out.println("test_negativeAWRReportTextOnClosedConnection()");
		awr.createSnapshot();
		awr.createSnapshot();
		awr.setConnection(null);
		thrown.expect(SQLException.class);
		thrown.expectMessage("No connection to the database!");
		awr.getAWRReport(AWR_MODE.TEXT);
	}
	
	@Test
	public void test_negativeAWRReportHTMLOnClosedConnection() throws SQLException, OutOfSequenceException {
		System.out.println("test_negativeAWRReportHTMLOnClosedConnection()");
		awr.createSnapshot();
		awr.createSnapshot();
		awr.setConnection(null);
		thrown.expect(SQLException.class);
		thrown.expectMessage("No connection to the database!");
		awr.getAWRReport(AWR_MODE.HTML);
	}
	
	@Test
	public void test_resetCloseConnection() {
		System.out.println("test_resetCloseConnection()");
		awr.reset(true);
	}
	
	@Test
	public void test_resetNotCloseConnection() {
		System.out.println("test_resetNotCloseConnection()");
		awr.reset(false);
	}
	
	@Test
	public void test_reset() {
		System.out.println("test_reset()");
		awr.reset();
	}
}
