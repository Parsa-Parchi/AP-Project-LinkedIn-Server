package example.server.DataBase;

import example.server.models.Like;

import java.sql.*;
import java.util.ArrayList;

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
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");");

        preparedStatement.executeUpdate();
    }

    public void insertLike(Like like) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO likes (post_id, email) VALUES (?, ?)");
        preparedStatement.setInt(1, like.getPostId());
        preparedStatement.setString(2, like.getEmail());
        preparedStatement.executeUpdate();

        PreparedStatement statement = connection.prepareStatement("UPDATE posts SET likes = likes + 1 WHERE id = ?");
        statement.setInt(1, like.getPostId());
        statement.executeUpdate();
    }

    public boolean ExistLike(Like like) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM likes WHERE post_id = ? AND email = ?");
        preparedStatement.setInt(1, like.getPostId());
        preparedStatement.setString(2, like.getEmail());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    public void deleteLike(int postId ,String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likes WHERE post_id = ? AND email = ?");
        preparedStatement.setInt(1, postId);
        preparedStatement.setString(2, email);
        preparedStatement.executeUpdate();

        PreparedStatement statement = connection.prepareStatement("UPDATE posts SET likes = likes - 1 WHERE id = ?");
        statement.setInt(1, postId);
        statement.executeUpdate();

    }


    public void deleteLikesOfPost(int postId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likes WHERE post_id = ?");
        preparedStatement.setInt(1, postId);
        preparedStatement.executeUpdate();

        PreparedStatement statement = connection.prepareStatement("UPDATE posts SET likes = 0 WHERE id = ?");
        statement.setInt(1, postId);
        statement.executeUpdate();
    }

    public void deleteAllLikes() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likes");
        preparedStatement.executeUpdate();

        PreparedStatement statement = connection.prepareStatement("UPDATE posts SET likes = 0 ");
        statement.executeUpdate();

    }

    public ArrayList<Like> getLikesOfPost(int postId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM likes WHERE post_id = ?");
        preparedStatement.setInt(1, postId);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Like> likes = new ArrayList<>();
        while (resultSet.next()) {
            Like like = new Like(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getTimestamp("like_time"));
            likes.add(like);

        }
        return likes;
    }

    public ArrayList<Like> getLikesOfUser(String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM likes WHERE email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Like> likes = new ArrayList<>();
        while (resultSet.next()) {

            Like like = new Like(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getTimestamp("like_time"));
            likes.add(like);

        }
        return likes;
    }

}
