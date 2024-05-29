package example.server.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/linkedin";
    private static final String USER = "root";
    private static final String PASSWORD = "Parsa_prc78";

    private static Connection conn ;

    private SQLConnection() {
        // Singleton design pattern
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return conn;
    }
}
