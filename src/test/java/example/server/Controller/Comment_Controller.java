package example.server.Controller;

import example.server.DataBase.Comment_DataBase;
import example.server.models.Comment;

import java.sql.SQLException;
import java.util.ArrayList;

public class Comment_Controller {

    private Comment_DataBase comment_dataBase;

    public Comment_Controller() throws SQLException {
        comment_dataBase = new Comment_DataBase();
    }

    public void insertComment(Comment comment) throws SQLException {
        comment_dataBase.insertComment(comment);

    }

    public void updateComment(Comment comment) throws SQLException {
        comment_dataBase.updateComment(comment);

    }

    public void deleteComment(Comment comment) throws SQLException {
        comment_dataBase.deleteComment(comment);

    }

    public void deleteComment(int id) throws SQLException {
        comment_dataBase.deleteComment(id);
    }

    public void deleteAllComments() throws SQLException {
        comment_dataBase.deleteAllComments();
    }

    public void deleteCommentOfPost(int postId) throws SQLException {
        comment_dataBase.getCommentsOfPost(postId);

    }

    public void deleteCommentOfUser(String email) throws SQLException {
        comment_dataBase.deleteCommentByUser(email);
    }

    public ArrayList<Comment> getCommentsOfPost(int postId) throws SQLException {
        return comment_dataBase.getCommentsOfPost(postId);
    }

    public ArrayList<Comment> getCommentsOfUser(String email) throws SQLException {
        return comment_dataBase.getCommentsOfUser(email);
    }

    public ArrayList<Comment> getAllComments() throws SQLException {
        return comment_dataBase.getAllComments();
    }

    public ArrayList<Comment> getAllCommentsOfPostByUser(String email,int postId) throws SQLException {
        return comment_dataBase.getCommentsOfUserByPostId(email,postId);
    }
}
