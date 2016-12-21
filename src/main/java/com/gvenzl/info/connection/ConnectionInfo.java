package com.gvenzl.info.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class ConnectionInfo {

	private final String	action;
	private final String	authenticated_identity;
	private final String	authentication_method;
	private final String	cdb_name;
	private final String	client_identifier;
	private final String	client_info;
	private final String	client_program_name;
	private final int		con_id;
	private final String	con_name;
	private final String	current_edition_name;
	private final String	current_schema;
	private final String	current_user;
	private final String	database_role;
	private final String	db_domain;
	private final String	db_name;
	private final String	db_supplemental_log_level;
	private final String	db_unique_name;
	private final String	dblink_info;
	private final String	enterprise_identity;
	private final String	global_uid;
	private final String	host;
	private final String	identification_type;
	private final int		instance;
	private final String	instance_name;
	private final String	ip_address;
	private final boolean	is_apply_server;
	private final boolean	is_dg_rolling_upgrade;
	private final boolean	isdba;
	private final String	lang;
	private final String	language;
	private final String	module;
	private final String	network_protocol;
	private final String	nls_calendar;
	private final String	nls_currency;
	private final String	nls_date_format;
	private final String	nls_date_language;
	private final String	nls_sort;
	private final String	nls_territory;
	private final String	platform_slash;
	private final String	policy_invoker;
	private final String	proxy_enterprise_identity;
	private final String	proxy_user;
	private final String	server_host;
	private final String	service_name;
	private final String	session_edition_name;
	private final String	session_user;
	private final int		sid;
	private final String	terminal;
	
	ConnectionInfo(HashMap<String, String> info) {
		action = info.get("action");
		authenticated_identity = info.get("authenticated_identity");
		authentication_method = info.get("authentication_method");
		cdb_name = info.get("cdb_name");
		client_identifier = info.get("client_identifier");
		client_info = info.get("client_info");
		client_program_name = info.get("client_program_name");
		con_id = Integer.valueOf(info.get("con_id")).intValue();
		con_name = info.get("con_name");
		current_edition_name = info.get("current_edition_name");
		current_schema = info.get("current_schema");
		current_user = info.get("current_user");
		database_role = info.get("database_role");
		db_domain = info.get("db_domain");
		db_name = info.get("db_name");
		db_supplemental_log_level = info.get("db_supplemental_log_level");
		db_unique_name = info.get("db_unique_name");
		dblink_info = info.get("dblink_info");
		enterprise_identity = info.get("enterprise_identity");
		global_uid = info.get("global_uid");
		host = info.get("host");
		identification_type = info.get("identification_type");
		instance = Integer.valueOf(info.get("instance")).intValue();
		instance_name = info.get("instance_name");
		ip_address = info.get("ip_address");
		is_apply_server = info.get("is_apply_server").equalsIgnoreCase("TRUE");
		is_dg_rolling_upgrade = info.get("is_dg_rolling_upgrade").equalsIgnoreCase("TRUE");
		isdba = info.get("isdba").equalsIgnoreCase("TRUE");
		lang = info.get("lang");
		language = info.get("language");
		module = info.get("module");
		network_protocol = info.get("network_protocol");
		nls_calendar = info.get("nls_calendar");
		nls_currency = info.get("nls_currency");
		nls_date_format = info.get("nls_date_format");
		nls_date_language = info.get("nls_date_language");
		nls_sort = info.get("nls_sort");
		nls_territory = info.get("nls_territory");
		platform_slash = info.get("platform_slash");
		policy_invoker = info.get("policy_invoker");
		proxy_enterprise_identity = info.get("proxy_enterprise_identity");
		proxy_user = info.get("proxy_user");
		server_host = info.get("server_host");
		service_name = info.get("service_name");
		session_edition_name = info.get("session_edition_name");
		session_user = info.get("session_user");
		sid = Integer.valueOf(info.get("sid")).intValue();
		terminal = info.get("terminal");
		
	}

	/**
	 * Identifies the position in the module (application name) and is set through the DBMS_APPLICATION_INFO package.
	 * @return The position in the module 
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the name of the current action within the current module.
	 * @param conn The connection for which the action should be set
	 * @param action The action name to be set
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setAction(Connection conn, String action) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("BEGIN DBMS_APPLICATION_INFO.SET_ACTION(?); END;");
		stmt.setString(1, action);
		stmt.execute();
	}

	/**
	 *
	 * @return The identity used in authentication.
	 */
	public String getAuthenticatedIdentity() {
		return authenticated_identity;
	}
	
	/**
	 * 
	 * @return The method of authentication.
	 */
	public String getAuthenticationMethod() {
		return authentication_method;
	}
	
	/**
	 * 
	 * @return If queried while connected to a multitenant container database (CDB), returns the name of the CDB. Otherwise, returns null.
	 */
	public String getCdbName() {
		return cdb_name;
	}
	
	/**
	 * 
	 * @return An identifier that is set by the application through the DBMS_SESSION.SET_IDENTIFIER procedure, or Oracle Dynamic Monitoring Service (DMS). This attribute is used by various database components to identify lightweight application users who authenticate as the same database user.
	 */
	public String getClientIdentifier() {
		return client_identifier;
	}
	
	/**
	 * Sets the client ID in the database session.
	 * @param conn The connection for which the client identifier should be set
	 * @param clientIdentifier The client identifier
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setClientIdentifier(Connection conn, String clientIdentifier) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("BEGIN DBMS_SESSION.SET_IDENTIFIER(?); END;");
		stmt.setString(1, clientIdentifier);
		stmt.execute();
	}
	
	/**
	 * 
	 * @return Returns up to 64 bytes of user session information that can be stored by an application using the DBMS_APPLICATION_INFO package.
	 */
	public String getClientInformation() {
		return client_info;
	}
	
	/**
	 * Supplies additional information about the client application.
	 * @param conn The connection for which the client information should be set
	 * @param clientInfo The client information. Only up to 64 bytes will be stored
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setClientInformation(Connection conn, String clientInfo) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("BEGIN DBMS_APPLICATION_INFO.SET_CLIENT_INFO(?); END;");
		stmt.setString(1, clientInfo);
		stmt.execute();
	}
	
	/**
	 * 
	 * @return The name of the program used for the database connection.
	 */
	public String getClientProgramName() {
		return client_program_name;
	}
	
	/**
	 * 
	 * @reutrn If queried while connected to a CDB, returns the current container ID. Otherwise, returns 0.
	 */
	public int getContainerId() {
		return con_id;
	}
	
	/**
	 * 
	 * @reutrn If queried while connected to a CDB, returns the current container name. Otherwise, returns the name of the database as specified in the DB_NAME initialization parameter.
	 */	
	public String getContainerName() {
		return con_name;
	}

	/**
	 * 
	 * @return The name of the current edition.
	 */
	public String getCurrentEdition() {
		return current_edition_name;
	}

	/**
	 * The name of the currently active default schema. This value may change during the duration of a session through use of an ALTER SESSION SET CURRENT_SCHEMA statement. This may also change during the duration of a session to reflect the owner of any active definer's rights object. When used directly in the body of a view definition, this returns the default schema used when executing the cursor that is using the view; it does not respect views used in the cursor as being definer's rights.
     * Note: Oracle recommends against issuing the SQL statement ALTER SESSION SET CURRENT_SCHEMA from within all types of stored PL/SQL units except logon triggers.
	 * @return The name of the currently active default schema.
	 */
	public String getCurrentSchema() {
		return current_schema;
	}

	/**
	 *
	 * @return The name of the database user whose privileges are currently active. This may change during the duration of a database session as Real Application Security sessions are attached or detached, or to reflect the owner of any active definer's rights object. When no definer's rights object is active, CURRENT_USER returns the same value as SESSION_USER. When used directly in the body of a view definition, this returns the user that is executing the cursor that is using the view; it does not respect views used in the cursor as being definer's rights. For enterprise users, returns schema. If a Real Application Security user is currently active, returns user XS$NULL.
	 */
	public String getCurrentUser() {
		return current_user;
	}
	
	/**
	 * 
	 * @return The database role using the SYS_CONTEXT function with the USERENV namespace. The role is one of the following: PRIMARY, PHYSICAL STANDBY, LOGICAL STANDBY, SNAPSHOT STANDBY.
	 */
	public String getDatabaseRole() {
		return database_role;
	}

	/**
	 * 
	 * @return Domain of the database as specified in the DB_DOMAIN initialization parameter.
	 */
	public String getDbDomain() {
		return db_domain;
	}

	/**
	 * 
	 * @return Name of the database as specified in the DB_NAME initialization parameter.
	 */
	public String getDbName() {
		return db_name;
	}

	/**
	 * 
	 * @return If supplemental logging is enabled, returns a string containing the list of enabled supplemental logging levels. Possible values are: ALL_COLUMN, FOREIGN_KEY, MINIMAL, PRIMARY_KEY, PROCEDURAL, and UNIQUE_INDEX. If supplemental logging is not enabled, returns null.
	 */
	public String getDbSupplementalLogLevel() {
		return db_supplemental_log_level;
	}

	/**
	 * 
	 * @return Name of the database as specified in the DB_UNIQUE_NAME initialization parameter.
	 */
	public String getDbUniqueName() {
		return db_unique_name;
	}

	/**
	 * 
	 * @return The source of a database link session.
	 */
	public String getDbLinkInfo() {
		return dblink_info;
	}

	/**
	 *
	 * @return The user's enterprise-wide identity.
	 */
	public String getEnterpriseIdentity() {
		return enterprise_identity;
	}

	/**
	 * 
	 * @return The global user ID from Oracle Internet Directory for Enterprise User Security (EUS) logins; returns null for all other logins.
	 */
	public String getGlobalUid() {
		return global_uid;
	}

	/**
	 *
	 * @return The name of the host machine from which the client has connected.
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 *
	 * @return The way the user's schema was created in the database. Specifically, it reflects the IDENTIFIED clause in the CREATE/ALTER USER syntax.
	 */
	public String getIdentificationType() {
		return identification_type;
	}
	
	/**
	 *
	 * @return The instance identification number of the current instance.
	 */
	public int getInstance() {
		return instance;
	}

	/**
	 * 
	 * @return The name of the instance.
	 */
	public String getInstanceName() {
		return instance_name;
	}
	
	/**
	 *
	 * @return The IP address of the machine from which the client is connected. If the client and server are on the same machine and the connection uses IPv6 addressing, then ::1 is returned.
	 */
	public String getIpAddress() {
		return ip_address;
	}
	
	/**
	 * 
	 * @return TRUE if queried from within a SQL Apply server in a logical standby database. Otherwise, FALSE.
	 */
	public boolean isApplyServer() {
		return is_apply_server;
	}
	
	/**
	 *
	 * @return TRUE if a rolling upgrade of the database software in a Data Guard configuration, initiated by way of the DBMS_ROLLING package, is active. Otherwise, FALSE.
	 */
	public boolean isDgRollingUpgrade() {
		return is_dg_rolling_upgrade;
	}
	
	/**
	 * 
	 * @return TRUE if the user has been authenticated as having DBA privileges either through the operating system or through a password file.
	 */
	public boolean isDba() {
		return isdba;
	}

	/**
	 * 
	 * @return The abbreviated name for the language, a shorter form than the existing 'LANGUAGE' parameter.
	 */
	public String getLang() {
		return lang;
	}
	
	/**
	 *
	 * @return The language and territory currently used by your session, along with the database character set, in this form: language_territory.characterset
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 *
	 * @return The application name (module) set through the DBMS_APPLICATION_INFO package.
	 */
	public String getApplicationName() {
		return module;
	}
	
	/**
	 * Sets the application name.
	 * @param conn The connection for which the application name should be set
	 * @param applicationName The application name
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setApplicationName(Connection conn, String applicationName) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("BEGIN DBMS_APPLICATION_INFO.SET_MODULE(?, NULL); END;");
		stmt.setString(1, applicationName);
		stmt.execute();
	}
	
	/**
	 * Sets the application name and action.
	 * @param conn The connection for which the application name should be set
	 * @param applicationName The application name
	 * @param action The action name
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setApplicationInfo(Connection conn, String applicationName, String action) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("BEGIN DBMS_APPLICATION_INFO.SET_MODULE(?, ?); END;");
		stmt.setString(1, applicationName);
		stmt.setString(2, action);
		stmt.execute();
	}
	
	/**
	 *
	 * @return The network protocol being used for communication, as specified in the 'PROTOCOL=protocol' portion of the connect string.
	 */
	public String getNetworkProtocol() {
		return network_protocol;
	}
	
	/**
	 *
	 * @return The current calendar of the current session.
	 */
	public String getNlsCalendar() {
		return nls_calendar;
	}
	
	/**
	 * Sets the NLS calendar for the database connection.
	 * @param conn The connection for which the NLS calendar should be set
	 * @param calendar The NLS calendar
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setNlsCalendar(Connection conn, String calendar) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET NLS_CALENDAR=" + calendar);
		stmt.execute();
	}
	
	/**
	 *
	 * @return The currency of the current session.
	 */
	public String getNlsCurrency() {
		return nls_currency;
	}
	
	/**
	 * Sets the NLS currency for the database connection.
	 * @param conn The connection for which the NLS currency should be set
	 * @param calendar The NLS currency
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setNlsCurrency(Connection conn, String currency) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET NLS_CURRENCY='" + currency + "'");
		stmt.execute();
	}
	
	/**
	 * 
	 * @return The date format for the session.
	 */
	public String getNlsDateFormat() {
		return nls_date_format;
	}
	
	/**
	 * Sets the NLS date format for the database connection.
	 * @param conn The connection for which the NLS date format should be set
	 * @param format The NLS date format
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setNlsDateFormat(Connection conn, String format) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET NLS_DATE_FORMAT='" + format + "'");
		stmt.execute();
	}
	
	/**
	 *
	 * @return The language used for expressing dates.
	 */
	public String getNlsDateLanguage() {
		return nls_date_language;
	}
	
	/**
	 * Sets the NLS date language for the database connection.
	 * @param conn The connection for which the NLS date language should be set
	 * @param language The NLS date language
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setNlsDateLanguage(Connection conn, String language) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET NLS_DATE_LANGUAGE=" + language);
		stmt.execute();
	}
	
	/**
	 * 
	 * @return Either BINARY or the linguistic sort basis.
	 */
	public String getNlsSort() {
		return nls_sort;
	}
	
	/**
	 * Sets the NLS sort for the database connection.
	 * @param conn The connection for which the NLS sort should be set
	 * @param sort The NLS sort
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setNlsSort(Connection conn, String sort) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET NLS_SORT=" + sort);
		stmt.execute();
	}
	
	/**
	 *
	 * @return The territory of the current session.
	 */
	public String getNlsTerritory() {
		return nls_territory;
	}
	
	/**
	 * Sets the NLS territory for the database connection.
	 * @param conn The connection for which the NLS territory should be set
	 * @param territory The NLS territory
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setNlsTerritory(Connection conn, String territory) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET NLS_TERRITORY=" + territory);
		stmt.execute();
	}
	
	
	/**
	 *
	 * @return The slash character that is used as the file path delimiter for your platform.
	 */
	public String getPlatformSlash() {
		return platform_slash;
	}
	
	/**
	 *
	 * @return The invoker of row-level security (RLS) policy functions.
	 */
	public String getPolicyInvoker() {
		return policy_invoker;
	}
	
	/**
	 *
	 * @return The Oracle Internet Directory DN when the proxy user is an enterprise user.
	 */
	public String getProxyEnterpriseIdentity() {
		return proxy_enterprise_identity;
	}
	
	/**
	 *
	 * @return The name of the database user who opened the current session on behalf of SESSION_USER.
	 */
	public String getProxyUser() {
		return proxy_user;
	}
	
	/**
	 *
	 * @return The host name of the machine on which the instance is running.
	 */
	public String getServerHost() {
		return server_host;
	}
	
	/**
	 *
	 * @return The name of the service to which a given session is connected.
	 */
	public String getServiceName() {
		return service_name;
	}
	
	/**
	 * 
	 * @return The name of the session edition.
	 */
	public String getSessionEdition() {
		return session_edition_name;
	}
	
	/**
	 * Sets the edition for a database connection.
	 * @param conn The connection for which the edition should be set
	 * @param edition The edition
	 * @throws SQLException Any SQL error during this operation
	 */
	public static void setSessionEdition(Connection conn, String edition) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("ALTER SESSION SET EDITION=" + edition);
		stmt.execute();
	}
	
	/**
	 *
	 * @return The name of the session user (the user who logged on). This may change during the duration of a database session as Real Application Security sessions are attached or detached. For enterprise users, returns the schema. For other users, returns the database user name. If a Real Application Security session is currently attached to the database session, returns user XS$NULL.
	 */
	public String getSessionUser() {
		return session_user;
	}
	
	/**
	 * 
	 * @return The session ID.
	 */
	public int getSid() {
		return sid;
	}
	
	/**
	 * 
	 * @return The operating system identifier for the client of the current session. In distributed SQL statements, this attribute returns the identifier for your local session. In a distributed environment, this is supported only for remote SELECT statements, not for remote INSERT, UPDATE, or DELETE operations. (The return length of this parameter may vary by operating system.)
	 */
	public String getTerminal() {
		return terminal;
	}
}
