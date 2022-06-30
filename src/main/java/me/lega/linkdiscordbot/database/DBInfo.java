package me.lega.linkdiscordbot.database;

public class DBInfo {

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String DB_NAME = "link_bot_db";

    public DBInfo() {

    }

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }
}
