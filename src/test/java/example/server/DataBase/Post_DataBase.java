package example.server.DataBase;

import example.server.models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Post_DataBase {
    private final Connection connection;

    public Post_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createPostTable();
    }

    public void createPostTable() throws SQLException {

        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS posts ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) NOT NULL,"
                + "title VARCHAR(255) NOT NULL,"
                + "content VARCHAR(3000) NOT NULL,"
                + "created_at VARCHAR(40) NOT NULL"
                + "likes INT NOT NULL DEFAULT 0,"
                + "comments INT NOT NULL DEFAULT 0,"
                + "FOREIGN KEY(email) REFERENCES users (email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();
    }

    public void insertPost(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO posts (email, title, content, created_at) VALUES (?, ?, ?, ?)");
        statement.setString(1,post.getAuthor());
        statement.setString(2,post.getTitle());
        statement.setString(3,post.getContent());
        statement.setString(4,post.getCreatedAt());
        statement.executeUpdate();
    }

    public void updatePost(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE posts SET title=?, content=? WHERE id=?");
        statement.setString(1,post.getTitle());
        statement.setString(2,post.getContent());
        statement.setInt(3,post.getId());
        statement.executeUpdate();
    }
}
