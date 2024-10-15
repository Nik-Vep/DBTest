package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "root";
    private static volatile Connection connection;

    private Util() {
    }

    public static Connection getConnection() {
        Connection localConnection = connection;
        try {
            if (localConnection == null || localConnection.isClosed()) {
                synchronized (Util.class) {
                    localConnection = connection;
                    if (localConnection == null || localConnection.isClosed()) {
                        try {
                            Driver driver = new com.mysql.cj.jdbc.Driver();
                            DriverManager.registerDriver(driver);
                            localConnection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                            connection = localConnection;
                        } catch (SQLException e) {
                            System.out.println("Connection failed: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check connection state: " + e.getMessage());
        }
        return localConnection;
    }
}

