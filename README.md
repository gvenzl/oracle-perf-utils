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
	awr.getAWRReport(AWR_MODE.TEXT);
```