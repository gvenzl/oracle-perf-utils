# OraclePerfUtils
A collection of APIs for various Oracle performance tuning features.

## Automatic Workload Repository (AWR)

### Create a text based AWR Report

    Connection dbConnection = DriverManager.getConnection(
        "jdbc:oracle:thin:system/<your password>@//localhost:1521/ORCLCDB");
				
    AWR awr = AWR.getInstance();
    awr.setConnection(dbConnection);
    awr.createSnapshot();
    /********************/
    /*** DO SOME WORK ***/
    /********************/
    awr.createSnapshot();
    String awrReport = 
        awr.getAWRReport(AWR_MODE.TEXT);
    awr.closeConnection();

### Create an HTML based AWR Report

    Connection dbConnection = DriverManager.getConnection(
        "jdbc:oracle:thin:system/<your password>@//localhost:1521/ORCLCDB");
		
    AWR awr = AWR.getInstance();
    awr.setConnection(dbConnection);
    awr.createSnapshot();
    /********************/
    /*** DO SOME WORK ***/
    /********************/
    awr.createSnapshot();
    String awrReport = 
        awr.getAWRReport(AWR_MODE.HTML);
    awr.closeConnection();

