package com.gvenzl.info.connection;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionInfoTest {
	
	private Connection conn;
	private static final String user = "TEST";
	private static final String password = "test";
	private static final String host = "localhost";
	private static final String port = "1521";
	private static final String service = "ORCLPDB1";
	private static final String CDBNAME = "ORCLCDB";
	private static final String CONTAINERNAME = "ORCLPDB1";
	
	@Before
	public void setup() throws SQLException {
		conn = DriverManager.getConnection(
				"jdbc:oracle:thin:" + user + "/" + password + "@//" + host + ":" + port + "/" + service);
	}
	
	@Test
	public void test_getAction() throws SQLException {
		System.out.println("test_getAction()");
		String expected = "TestAction";
		ConnectionInfo.setAction(conn, expected);
		Assert.assertEquals(expected, ConnectionInfoFactory.getConnectionInfo(conn).getAction());
	}
	
	@Test
	public void test_getAuthenticatedIdentity() throws SQLException {
		System.out.println("test_getAuthenticatedIdentity()");
		Assert.assertEquals(user, ConnectionInfoFactory.getConnectionInfo(conn).getAuthenticatedIdentity());
	}
	
	@Test
	public void test_getAuthenticationMethod() throws SQLException {
		System.out.println("test_getAuthenticationMethod()");
		String expected = "PASSWORD, KERBEROS, SSL, RADIUS, OS, NONE, JOB, PQ_SLAVE";
		Assert.assertTrue(expected.contains(ConnectionInfoFactory.getConnectionInfo(conn).getAuthenticationMethod()));
		
	}
	
	@Test
	public void test_getCdbName() throws SQLException {
		System.out.println("test_getCdbName()");
		Assert.assertEquals(CDBNAME, ConnectionInfoFactory.getConnectionInfo(conn).getCdbName());
	}
	
	@Test
	public void test_getClientIdentifier() throws SQLException {
		System.out.println("test_getClientIdentifier()");
		String expected = "TestIdentifierMixedCase";
		ConnectionInfo.setClientIdentifier(conn, expected);
		Assert.assertEquals(expected, ConnectionInfoFactory.getConnectionInfo(conn).getClientIdentifier());
	}
	
	@Test
	public void test_getClientInformation() throws SQLException {
		System.out.println("test_getClientInformation()");
		String expected = "Additional client information for the database";
		ConnectionInfo.setClientInformation(conn, expected);
		Assert.assertEquals(expected, ConnectionInfoFactory.getConnectionInfo(conn).getClientInformation());
	}
	
	@Test
	public void test_getClientProgramName() throws SQLException {
		System.out.println("test_getClientProgramName()");
		String expected = "JDBC Thin Client";
		Assert.assertEquals(expected, ConnectionInfoFactory.getConnectionInfo(conn).getClientProgramName());
	}
	
	@Test
	public void test_getContainerId() throws SQLException {
		System.out.println("test_getContainerId()");
		// Test that the container Id >= 0 (container id must be positive)
		Assert.assertTrue(ConnectionInfoFactory.getConnectionInfo(conn).getContainerId() >= 0);
	}
	
	@Test
	public void test_getContainerName() throws SQLException {
		System.out.println("test_getContainerName()");
		Assert.assertEquals(CONTAINERNAME, ConnectionInfoFactory.getConnectionInfo(conn).getContainerName());
	}

	@Test
	public void test_getCurrentEdition() throws SQLException {
		System.out.println("test_getCurrentEdition()");
		String editionName = "ORA$BASE";
		Assert.assertEquals(editionName, ConnectionInfoFactory.getConnectionInfo(conn).getCurrentEdition());
	}
	
	@Test
	public void test_getCurrentSchema() throws SQLException {
		System.out.println("test_getCurrentSchema()");
		Assert.assertEquals(user, ConnectionInfoFactory.getConnectionInfo(conn).getCurrentSchema());
	}

	@Test
	public void test_getCurrentUser() throws SQLException {
		System.out.println("test_getCurrentUser()");
		Assert.assertEquals(user, ConnectionInfoFactory.getConnectionInfo(conn).getCurrentUser());
	}
	
	@Test
	public void test_getDatabaseRole() throws SQLException {
		System.out.println("test_getDatabaseRole()");
		String expected = "PRIMARY, PHYSICAL STANDBY, LOGICAL STANDBY, SNAPSHOT STANDBY";
		Assert.assertTrue(expected.contains(ConnectionInfoFactory.getConnectionInfo(conn).getDatabaseRole()));
	}
	
	@Test
	public void test_getDbDomain() throws SQLException {
		System.out.println("test_getDbDomain()");
		// DB domain can be null
		ConnectionInfoFactory.getConnectionInfo(conn).getDbDomain();
	}

	@Test
	public void test_getDbName() throws SQLException {
		System.out.println("test_getDbName()");
		Assert.assertEquals(CDBNAME, ConnectionInfoFactory.getConnectionInfo(conn).getDbName());
	}
	
	@Test
	public void test_getDbSupplementalLogLevel() throws SQLException {
		System.out.println("test_getDbSupplementalLogLevel()");
		// Supplemental log level is null if not set
		ConnectionInfoFactory.getConnectionInfo(conn).getDbSupplementalLogLevel();
	}
	
	@Test
	public void test_getDbUniqueName() throws SQLException {
		System.out.println("test_getDbUniqueName()");
		Assert.assertEquals(CDBNAME, ConnectionInfoFactory.getConnectionInfo(conn).getDbUniqueName());
	}

	@Test
	public void test_getDbLinkInfo() throws SQLException {
		System.out.println("test_getDbLinkInfo()");
		// DB Link can be null if not used
		ConnectionInfoFactory.getConnectionInfo(conn).getDbLinkInfo();
	}
	
	@Test
	public void test_getEnterpriseIdentity() throws SQLException {
		System.out.println("test_getEnterpriseIdentity()");
		// Enterprise identity can be null
		ConnectionInfoFactory.getConnectionInfo(conn).getEnterpriseIdentity();
	}
	
	@Test
	public void test_getGlobalUid() throws SQLException {
		System.out.println("test_getGlobalUid()");
		// Global UID can be null
		ConnectionInfoFactory.getConnectionInfo(conn).getGlobalUid();
	}

	@Test
	public void test_getHost() throws SQLException, UnknownHostException {
		System.out.println("test_getHost()");
		Assert.assertEquals(InetAddress.getLocalHost().getHostName(),
				ConnectionInfoFactory.getConnectionInfo(conn).getHost());
	}
	
	@Test
	public void test_getIdentificationType() throws SQLException {
		System.out.println("test_getIdentificationType()");
		String expected = "LOCAL, EXTERNAL, GLOBAL SHARED, GLOBAL PRIVATE";
		Assert.assertTrue(expected.contains(ConnectionInfoFactory.getConnectionInfo(conn).getIdentificationType()));
	}
	
	@Test
	public void test_getInstance() throws SQLException {
		System.out.println("test_getInstance()");
		// Test that instance id > 0
		Assert.assertTrue(ConnectionInfoFactory.getConnectionInfo(conn).getInstance() > 0);
	}
	
	@Test
	public void test_getInstanceName() throws SQLException {
		System.out.println("test_getInstanceName()");
		// TODO: Make test RAC aware
		Assert.assertTrue(CDBNAME.equals(ConnectionInfoFactory.getConnectionInfo(conn).getInstanceName()));
	}
	
	@Test
	public void test_getIpAddress() throws SQLException, UnknownHostException {
		System.out.println("test_getIpAddress()");
		// IP address can vary between different systems.
		// Check that IP address is not empty
		Assert.assertFalse(ConnectionInfoFactory.getConnectionInfo(conn).getIpAddress().isEmpty());
	}
	
	@Test
	public void test_isApplySever() throws SQLException {
		System.out.println("test_isApplyServer()");
		Assert.assertNotNull(ConnectionInfoFactory.getConnectionInfo(conn).isApplyServer());
	}
	
	@Test
	public void test_isDgRollingUpgrade() throws SQLException {
		System.out.println("test_isDgRollingUpgrade()");
		Assert.assertNotNull(ConnectionInfoFactory.getConnectionInfo(conn).isDgRollingUpgrade());
	}
	
	@Test
	public void test_isDba() throws SQLException {
		System.out.println("test_isDba()");
		Assert.assertNotNull(ConnectionInfoFactory.getConnectionInfo(conn).isDba());
	}
	
	@Test
	public void test_getLang() throws SQLException {
		System.out.println("test_getLang()");
		Assert.assertFalse(ConnectionInfoFactory.getConnectionInfo(conn).getLang().isEmpty());
	}
	
	@Test
	public void test_getLanguage() throws SQLException {
		System.out.println("test_getLanguage()");
		Assert.assertFalse(ConnectionInfoFactory.getConnectionInfo(conn).getLanguage().isEmpty());
	}
	
	@Test
	public void test_getApplicationInfo() throws SQLException {
		System.out.println("test_getApplicationInfo()");
		String testApplicationName = "TestApplicationName";
		ConnectionInfo.setApplicationName(conn, testApplicationName);
		
		Assert.assertEquals(testApplicationName, ConnectionInfoFactory
								.getConnectionInfo(conn)
									.getApplicationName());
	}

	@Test
	public void test_setApplicationName() throws SQLException {
		System.out.println("test_setApplicationName()");
		String testApplicationName = "TestApplicationName";
		ConnectionInfo.setApplicationName(conn, testApplicationName);
		
		Assert.assertEquals(testApplicationName, ConnectionInfoFactory
								.getConnectionInfo(conn)
									.getApplicationName());
	}
	
	@Test
	public void test_setApplicationNameAndAction() throws SQLException {
		System.out.println("test_setApplicationNameAndAction()");
		String testApplicationName = "TestApplicationName";
		String testAction = "TestActionName";
		ConnectionInfo.setApplicationInfo(conn, testApplicationName, testAction);
		
		Assert.assertEquals(testApplicationName, ConnectionInfoFactory
								.getConnectionInfo(conn)
									.getApplicationName());

		Assert.assertEquals(testAction, ConnectionInfoFactory
				.getConnectionInfo(conn)
					.getAction());
	}
	
	@Test
	public void test_getNetworkProtocol() throws SQLException {
		System.out.println("test_getNetworkProtocol()");
		String testNetworkProtocol = "tcp";
		Assert.assertEquals(testNetworkProtocol, ConnectionInfoFactory.getConnectionInfo(conn).getNetworkProtocol());
	}

	@Test
	public void test_getNlsCalendar() throws SQLException {
		System.out.println("test_getNlsCalendar()");
		String calendar = "GREGORIAN";
		ConnectionInfo.setNlsCalendar(conn, calendar);
		Assert.assertEquals(calendar, ConnectionInfoFactory.getConnectionInfo(conn).getNlsCalendar());
	}
	
	@Test
	public void test_getNlsCurrency() throws SQLException {
		System.out.println("test_getNlsCurrency()");
		String currency = "$";
		ConnectionInfo.setNlsCurrency(conn, currency);
		Assert.assertEquals(currency, ConnectionInfoFactory.getConnectionInfo(conn).getNlsCurrency());
	}
	
	@Test
	public void test_getNlsDateFormat() throws SQLException {
		System.out.println("test_getNlsDateFormat()");
		String dateFormat = "DD-MM-YYYY";
		ConnectionInfo.setNlsDateFormat(conn, dateFormat);
		Assert.assertEquals(dateFormat, ConnectionInfoFactory.getConnectionInfo(conn).getNlsDateFormat());
	}
	
	@Test
	public void test_getNlsDateLanguage() throws SQLException {
		System.out.println("test_getNlsDateLanguage()");
		String dateLanguage = "AMERICAN";
		ConnectionInfo.setNlsDateLanguage(conn, dateLanguage);
		Assert.assertEquals(dateLanguage, ConnectionInfoFactory.getConnectionInfo(conn).getNlsDateLanguage());
	}
	
	@Test
	public void test_getNlsSort() throws SQLException {
		System.out.println("test_getNlsSort()");
		String sort = "BINARY";
		ConnectionInfo.setNlsSort(conn, sort);
		Assert.assertEquals(sort, ConnectionInfoFactory.getConnectionInfo(conn).getNlsSort());
	}
	
	@Test
	public void test_getNlsTerritory() throws SQLException {
		System.out.println("test_getNlsTerritory()");
		String territory = "AMERICA";
		ConnectionInfo.setNlsTerritory(conn, territory);
		Assert.assertEquals(territory, ConnectionInfoFactory.getConnectionInfo(conn).getNlsTerritory());
	}
	
	@Test
	public void test_getPlatformSlash() throws SQLException {
		System.out.println("test_getPlatformSlash()");
		Assert.assertEquals(File.separator, ConnectionInfoFactory.getConnectionInfo(conn).getPlatformSlash());
	}

	@Test
	public void test_getPolicyInvoker() throws SQLException {
		System.out.println("test_getPolicyInvoker()");
		ConnectionInfoFactory.getConnectionInfo(conn).getPolicyInvoker();
	}
	
	@Test
	public void test_getProxyEnterpriseIdentity() throws SQLException {
		System.out.println("test_getProxyEnterpriseIdentity()");
		ConnectionInfoFactory.getConnectionInfo(conn).getProxyEnterpriseIdentity();
	}
	
	@Test
	public void test_getProxyUser() throws SQLException {
		System.out.println("test_getProxyUser()");
		ConnectionInfoFactory.getConnectionInfo(conn).getProxyUser();
	}
	
	@Test
	public void test_getServerHost() throws SQLException {
		System.out.println("test_getServerHost()");
		Assert.assertFalse(ConnectionInfoFactory.getConnectionInfo(conn).getServerHost().isEmpty());
	}

	@Test
	public void test_getServiceName() throws SQLException {
		System.out.println("test_getServiceName()");
		Assert.assertTrue(service.equalsIgnoreCase(ConnectionInfoFactory.getConnectionInfo(conn).getServiceName()));
	}
	
	@Test
	public void test_getSessionEdition() throws SQLException {
		System.out.println("test_getSessionEdition()");
		String editionName = "ORA$BASE";
		ConnectionInfo.setSessionEdition(conn, editionName);
		Assert.assertEquals(editionName, ConnectionInfoFactory.getConnectionInfo(conn).getSessionEdition());
	}
	
	@Test
	public void test_getSessionUser() throws SQLException {
		System.out.println("test_getSessionUser()");
		Assert.assertEquals(user, ConnectionInfoFactory.getConnectionInfo(conn).getSessionUser());
	}
	
	@Test
	public void test_getSid() throws SQLException {
		System.out.println("test_getSid()");
		Assert.assertTrue(ConnectionInfoFactory.getConnectionInfo(conn).getSid() > 0);
	}
	
	@Test
	public void test_getTerminal() throws SQLException {
		System.out.println("test_getTerminal()");
		Assert.assertNotNull(ConnectionInfoFactory.getConnectionInfo(conn).getTerminal());
	}
}
