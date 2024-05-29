package example.server.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Hashtag_DataBase {
    private final Connection connection;
    public Hashtag_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createHashtagTable();

    }
    private void createHashtagTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS hashtags ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "post_id INT NOT NULL,"
                + "hashtag VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE"
                + ");");

        preparedStatement.executeUpdate();
    }

}
