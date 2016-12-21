# OraclePerfUtils
A collection of APIs for various Oracle performance tuning features.

## Automatic Workload Repository (AWR)

### Create a text based AWR Report

```java
Connection dbConnection = DriverManager.getConnection(
    "jdbc:oracle:thin:system/<your password>@//localhost:1521/ORCLCDB");
				
AWR awr = new AWR(dbConnection);
awr.createSnapshot();
/********************/
/*** DO SOME WORK ***/
/********************/
awr.createSnapshot();
String awrReport = 
	awr.getAWRReport(AWR_MODE.TEXT);
```

### Create an HTML based AWR Report

```java
Connection dbConnection = DriverManager.getConnection(
    "jdbc:oracle:thin:system/<your password>@//localhost:1521/ORCLCDB");
				
AWR awr = new AWR(dbConnection);
awr.createSnapshot();
/********************/
/*** DO SOME WORK ***/
/********************/
awr.createSnapshot();
String awrReport = 
	awr.getAWRReport(AWR_MODE.HTML);
```

## Get and set various database connection attributes

### Get database connection session identifier (SID)
```java
Connection dbConnection = DriverManager.getConnection(
		"jdbc:oracle:thin:test/test@//localhost:1521/ORCLPDB1");

int sid = ConnectionInfoFactory.getConnectionInfo(dbConnection).getSid();
```

### Set and/or get application specific information

```java
Connection dbConnection = DriverManager.getConnection(
		"jdbc:oracle:thin:test/test@//localhost:1521/ORCLPDB1");

/* Initialize connection with application name */
ConnectionInfo.setApplicationName(dbConnection, "My application");

/* Set action for current task */
ConnectionInfo.setAction(dbConnection, "Insert some imporant data");

/* Set action for current task */
ConnectionInfo.setAction(dbConnection, "Analyze data");

/* Get application info */
ConnectionInfo info = ConnectionInfoFactory.getConnectionInfo(dbConnection);
String application = info.getApplicationName();
String action = info.getAction();
```

### Set NLS date format for connection
```java
Connection dbConnection = DriverManager.getConnection(
		"jdbc:oracle:thin:test/test@//localhost:1521/ORCLPDB1");

/* Get current date if date is greater than 1st of January 2000 */
PreparedStatement stmt = dbConnection.prepareStatement("SELECT sysdate FROM dual WHERE sysdate > '2000-01-01'");
ResultSet rslt;

try {
	/* Query throws "java.sql.SQLDataException: ORA-01861: literal does not match format string" because of incorrect NLS date format*/
	rslt = stmt.executeQuery();
	rslt.next();
	System.out.println(rslt.getDate(1));
}
catch (SQLDataException e) {
	e.printStackTrace();
}

/* Set NLS date format as wanted by the developer */
ConnectionInfo.setNlsDateFormat(dbConnection, "YYYY-MM-DD");
/* Execute same query, works now as format is matched */
rslt = stmt.executeQuery();
rslt.next();
System.out.println(rslt.getDate(1));
```