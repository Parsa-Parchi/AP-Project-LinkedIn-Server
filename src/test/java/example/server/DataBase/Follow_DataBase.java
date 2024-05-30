package example.server.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Follow_DataBase {
    private final Connection connection;

    public Follow_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createFollowTable();
    }

    public void createFollowTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS follows("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "follower VARCHAR(255) NOT NULL,"
                + "followed VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (follower) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (followed) REFERENCES users(email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();

    }


}
