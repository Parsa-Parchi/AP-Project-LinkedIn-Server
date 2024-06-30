package example.server.Controller;

import example.server.DataBase.Post_DataBase;
import example.server.models.Post;

import java.sql.SQLException;
import java.util.ArrayList;

public class Post_Controller {

    private static Post_DataBase post_dataBase;

    static {
        try {
            post_dataBase = new Post_DataBase();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void insertPost(Post post) throws SQLException {
        if(isValidPost(post))
             post_dataBase.insertPost(post);
        else
            throw new IllegalArgumentException("Invalid Post : Content or title is empty");
    }

    public static void updatePost(Post post) throws SQLException {
        if(isValidPost(post)) {
            Post post1 = post_dataBase.getPost(post.getId());
            if(post.getContent()!=null)
                post1.setContent(post.getContent());
            if(post.getTitle()!=null)
                post1.setTitle(post.getTitle());
            post_dataBase.updatePost(post1);
        }
        else
            throw new IllegalArgumentException("Invalid Post : Content or title is empty");
    }

    public static void deletePost(Post post) throws SQLException {
        post_dataBase.deletePost(post);
    }

    public static void deletePostOfUser(String email) throws SQLException {
        post_dataBase.deletePostByEmail(email);
    }

    public static void deleteAllPosts() throws SQLException {
        post_dataBase.deleteAllPosts();
    }

    public static String getPoster(int id) throws SQLException {
        return post_dataBase.getPosterEmail(id);
    }

    public static Post getPostById(int id) throws SQLException {
        return post_dataBase.getPost(id);
    }
    public static int getPostId(Post post) throws SQLException {
        return post_dataBase.getPostId(post.getAuthor(),post.getTitle(),post.getContent());
    }

    public static ArrayList<Post> getAllPosts() throws SQLException {
        return post_dataBase.getAllPosts();
    }

    public static ArrayList<Post> getAllPostsOfUser(String email) throws SQLException {
        return post_dataBase.getPosts(email);
    }

    public static boolean isValidPost(Post post) throws SQLException {
        boolean Valid = true;
        if(post.getContent()!=null)
            Valid = Valid && !post.getContent().isEmpty();
        if(post.getTitle()!=null)
            Valid = Valid && !post.getTitle().isEmpty();
        return Valid;
    }

}
