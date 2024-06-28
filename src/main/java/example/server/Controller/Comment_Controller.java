package example.server.Controller;

import example.server.DataBase.Comment_DataBase;
import example.server.models.Comment;

import java.sql.SQLException;
import java.util.ArrayList;

public class Comment_Controller {

    private static Comment_DataBase comment_dataBase;

    static {
        try {
            comment_dataBase = new Comment_DataBase();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void insertComment(Comment comment) throws SQLException {
        comment_dataBase.insertComment(comment);

    }

    public static void updateComment(Comment comment) throws SQLException {
        comment_dataBase.updateComment(comment);

    }

    public static void deleteComment(Comment comment) throws SQLException {
        comment_dataBase.deleteComment(comment);

    }

    public static void deleteComment(int id) throws SQLException {
        comment_dataBase.deleteComment(id);
    }

    public static void deleteAllComments() throws SQLException {
        comment_dataBase.deleteAllComments();
    }

    public static void deleteCommentOfPost(int postId) throws SQLException {
        comment_dataBase.getCommentsOfPost(postId);

    }

    public static void deleteCommentOfUser(String email) throws SQLException {
        comment_dataBase.deleteCommentByUser(email);
    }

    public static ArrayList<Comment> getCommentsOfPost(int postId) throws SQLException {
        return comment_dataBase.getCommentsOfPost(postId);
    }

    public static ArrayList<Comment> getCommentsOfUser(String email) throws SQLException {
        return comment_dataBase.getCommentsOfUser(email);
    }

    public static ArrayList<Comment> getAllComments() throws SQLException {
        return comment_dataBase.getAllComments();
    }

    public static ArrayList<Comment> getAllCommentsOfPostByUser(String email,int postId) throws SQLException {
        return comment_dataBase.getCommentsOfUserByPostId(email,postId);
    }
}
