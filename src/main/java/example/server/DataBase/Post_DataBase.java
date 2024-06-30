package example.server.DataBase;

import example.server.models.Post;

import java.sql.*;
import java.util.ArrayList;

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
                + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "likes INT NOT NULL DEFAULT 0,"
                + "comments INT NOT NULL DEFAULT 0,"
                + "FOREIGN KEY(email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");");

        statement.executeUpdate();
    }

    public void insertPost(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO posts (email, title, content) VALUES (?, ?, ?)");
        statement.setString(1,post.getAuthor());
        statement.setString(2,post.getTitle());
        statement.setString(3,post.getContent());
        statement.executeUpdate();
    }

    public void updatePost(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE posts SET title=?, content=? WHERE id=?");
        statement.setString(1,post.getTitle());
        statement.setString(2,post.getContent());
        statement.setInt(3,post.getId());
        statement.executeUpdate();
    }

    public void deletePost(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM posts WHERE id=?");
        statement.setInt(1,post.getId());
        statement.executeUpdate();
    }

    public void deletePostByEmail(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM posts WHERE email=?");
        statement.setString(1,email);
        statement.executeUpdate();
    }

    public void deleteAllPosts() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM posts");
        statement.executeUpdate();
    }

    public Post getPost(int id) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE id=?");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int postId = resultSet.getInt("id");
            String author = resultSet.getString("email");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int likes = resultSet.getInt("likes");
            int comments = resultSet.getInt("comments");

            return new Post(postId,likes,comments,author,title,content,createdAt);
        }

        return null;
    }

    public int getPostId(String email , String title , String content) throws SQLException {
        int postId = -1;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE email=? AND title=? AND content=?");
        statement.setString(1,email);
        statement.setString(2,title);
        statement.setString(3,content);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {

             postId = resultSet.getInt("id");
            return postId;
        }
        return postId;
    }

    public ArrayList<Post> getPosts(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE email = ?");
        statement.setString(1,email);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Post> posts = new ArrayList<>();

        while (resultSet.next()) {
            int postId = resultSet.getInt("id");
            String author = resultSet.getString("email");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int likes = resultSet.getInt("likes");
            int comments = resultSet.getInt("comments");

            Post post = new Post(postId,likes,comments,author,title,content,createdAt);

            posts.add(post);
        }

        return posts;

    }

    public ArrayList<Post> getAllPosts() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Post> posts = new ArrayList<>();
        while (resultSet.next()) {

            int postId = resultSet.getInt("id");
            String author = resultSet.getString("email");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            int likes = resultSet.getInt("likes");
            int comments = resultSet.getInt("comments");

            Post post = new Post(postId,likes,comments,author,title,content,createdAt);

            posts.add(post);
        }
        return posts;
    }






}
