package example.server.DataBase;

import java.sql.*;
import java.util.ArrayList;

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
                + "FOREIGN KEY (PostId) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");");

        statement.executeUpdate();

    }

    public void insertComment(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO comments (PostId, email, message) VALUES (?, ?, ?)");
        statement.setInt(1, comment.getPostId());
        statement.setString(2, comment.getEmail());
        statement.setString(3,comment.getComment());
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE posts SET comments = comments + 1 WHERE id = ?");
        statement1.setInt(1, comment.getPostId());
        statement1.executeUpdate();
    }

    public void updateComment(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE comments SET message = ? WHERE id = ?");
        statement.setString(1, comment.getComment());
        statement.setInt(2, comment.getId());
        statement.executeUpdate();
    }

    public void deleteComment(int id) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM comments WHERE id = ?");
        statement1.setInt(1, id);
        ResultSet resultSet = statement1.executeQuery();
        int postId = 0;
        if (resultSet.next()) {
            postId = resultSet.getInt("id");
        }

        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();

        PreparedStatement statement2 = connection.prepareStatement("UPDATE posts SET comments = comments - 1 WHERE id = ?");
        statement2.setInt(1, postId);
        statement2.executeUpdate();
    }

    public void deleteComment(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");
        statement.setInt(1, comment.getId());
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE posts SET comments = comments - 1 WHERE id = ?");
        statement1.setInt(1, comment.getPostId());
        statement1.executeUpdate();
    }

    public void deleteAllComments() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments");
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE posts SET comments = 0 ");
        statement1.executeUpdate();

    }

    public void deleteCommentByPostId(int postId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE PostId = ?");
        statement.setInt(1, postId);
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE posts SET comments = comments - 1 WHERE id = ?");
        statement1.setInt(1, postId);
        statement1.executeUpdate();
    }

    public void deleteCommentByUser(String email) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM comments WHERE email = ?");
        statement1.setString(1, email);
        ResultSet resultSet = statement1.executeQuery();
        int postId = 0;
        if (resultSet.next()) {
            postId = resultSet.getInt("id");

        }

        PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE email = ?");
        statement.setString(1, email);
        statement.executeUpdate();

        PreparedStatement statement2 = connection.prepareStatement("UPDATE posts SET comments = comments - 1 WHERE id = ?");
        statement2.setInt(1, postId);
        statement2.executeUpdate();
    }

    public String getComment(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("email");
        }
        return null;
    }
    public ArrayList<Comment> getAllComments() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments");
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int postId = resultSet.getInt("PostId");
            String email = resultSet.getString("email");
            String comment = resultSet.getString("message");
            Timestamp commentDate = resultSet.getTimestamp("commentDate");
            comments.add(new Comment(id,postId,email,commentDate,comment));

        }
        return comments;
    }

    public ArrayList<Comment> getCommentsOfPost(int postId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE PostId = ?");
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String comment = resultSet.getString("message");
            Timestamp commentDate = resultSet.getTimestamp("commentDate");
            comments.add(new Comment(id,postId,email,commentDate,comment));

        }
        return comments;
    }

    public ArrayList<Comment> getCommentsOfUser(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comments WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int postId = resultSet.getInt("PostId");
            String comment = resultSet.getString("message");
            Timestamp commentDate = resultSet.getTimestamp("commentDate");
            comments.add(new Comment(id,postId,email,commentDate,comment));


        }
        return comments;

    }
    public ArrayList<Comment> getCommentsOfUserByPostId(String email,int postId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement( "SELECT * FROM comments WHERE PostId = ? AND email = ?");
        statement.setInt(1, postId);
        statement.setString(2, email);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String comment = resultSet.getString("message");
            Timestamp commentDate = resultSet.getTimestamp("commentDate");
            comments.add(new Comment(id,postId,email,commentDate,comment));
        }
        return comments;

    }













}
