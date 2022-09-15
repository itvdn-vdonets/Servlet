package Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    private DBConnect() {}

    private static Connection conn;
    private static final String URL = "jdbc:mysql://localhost:3306/classicmodels";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "rootroot";

    public static synchronized Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                return conn;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
