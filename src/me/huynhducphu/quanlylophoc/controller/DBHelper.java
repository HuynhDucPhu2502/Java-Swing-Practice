package me.huynhducphu.quanlylophoc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String USER_NAME = "sa";
    private static final String USER_PASSWORD = "sapassword";
    private static final String DATABASE_NAME = "QuanLyLopHoc";
    public static Connection getConnection() throws SQLException {
        return getConnection(DATABASE_NAME, USER_NAME, USER_PASSWORD);
    }
    public static Connection getConnection(String dbName, String userName, String userPassword) throws SQLException {

        String dbURL = "jdbc:sqlserver://localhost;" +
                "databaseName=%s;" +
                "encrypt=true;" +
                "trustServerCertificate=true";
        Connection connection = DriverManager.getConnection(String.format(dbURL, dbName), userName, userPassword);
        if (connection != null)  return connection;
        return null;
    }
}
