package example.server.DataBase;

import example.server.models.Hashtag;

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

    public void insertHashtag(Hashtag hashtag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO hashtags (post_id, hashtag) VALUES (?, ?)");
        preparedStatement.setInt(1, hashtag.getPostId());
        preparedStatement.setString(2, hashtag.getHashtag());
        preparedStatement.executeUpdate();

    }

    public void UpdateHashtag(Hashtag hashtag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hashtags SET hashtag = ? WHERE id = ?");
        preparedStatement.setString(1, hashtag.getHashtag());
        preparedStatement.setInt(2, hashtag.getId());
        preparedStatement.executeUpdate();

    }

    public void deleteHashtag(Hashtag hashtag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM hashtags WHERE id = ?");
        preparedStatement.setInt(1, hashtag.getId());
        preparedStatement.executeUpdate();

    }

    public void deleteHashtag(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM hashtags WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    public void deleteHashtag(String hashtag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM hashtags WHERE hashtag = ?");
        preparedStatement.setString(1, hashtag);
        preparedStatement.executeUpdate();

    }

    public void deleteHashtagsOfPost(int postId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM hashtags WHERE post_id = ?");
        preparedStatement.setInt(1, postId);
        preparedStatement.executeUpdate();

    }


}
