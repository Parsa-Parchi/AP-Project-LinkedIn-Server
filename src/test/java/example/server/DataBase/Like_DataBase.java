package example.server.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Like_DataBase {
    private final Connection connection;

    public Like_DataBase() throws SQLException {
    connection = SQLConnection.getConnection();
    createLikeTable();
    }

    private void createLikeTable() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS likes("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "like_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");");

        preparedStatement.executeUpdate();
    }
}
