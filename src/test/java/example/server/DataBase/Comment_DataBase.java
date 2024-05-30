package example.server.DataBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import example.server.models.Comment;

public class Comment_DataBase {
    private final Connection connection ;
    public Comment_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createCommentTable();

    }
    private void createCommentTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS comments ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "PostId INT NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "commentDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "message VARCHAR(1250) NOT NULL,"
                + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();

    }

    public void insertComment(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO comments (post_id, email, message) VALUES (?, ?, ?)");
        statement.setInt(1, comment.getPostId());
        statement.setString(2, comment.getEmail());
        statement.setString(3,comment.getComment());
        statement.executeUpdate();
    }

    public void updateComment(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE comments SET comment = ? WHERE id = ?");
        statement.setString(1, comment.getComment());
        statement.setInt(2, comment.getId());
        statement.executeUpdate();
    }

    public void deleteComment(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();

    }

    public void deleteAllComments() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments");
        statement.executeUpdate();


    }

    public void deleteCommentByPostId(int postId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE post_id = ?");
        statement.setInt(1, postId);
        statement.executeUpdate();

    }

    public void deleteCommentByUser(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE email = ?");
        statement.setString(1, email);
        statement.executeUpdate();

    }



}
