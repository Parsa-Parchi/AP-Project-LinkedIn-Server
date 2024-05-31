package example.server.Controller;

import example.server.DataBase.Like_DataBase;
import example.server.models.Like;

import java.sql.SQLException;
import java.util.ArrayList;

public class Like_Controller {
    private Like_DataBase likeDataBase;

    public Like_Controller() throws SQLException {
        likeDataBase = new Like_DataBase();
    }

    public void insertLike(Like like) throws SQLException {
        likeDataBase.insertLike(like);
    }

    public void deleteLike(String email , int postId) throws SQLException {
        likeDataBase.deleteLike(postId, email);
    }

    public void deleteLikesOfPost(int postId) throws SQLException {
        likeDataBase.deleteLikesOfPost(postId);
    }


    public void deleteAllLikes() throws SQLException {
        likeDataBase.deleteAllLikes();
    }

    public ArrayList<Like> getLikesOfUser(String email) throws SQLException {
        return likeDataBase.getLikesOfUser(email);
    }

    public ArrayList<Like> getLikesOfPost(int postId) throws SQLException {
        return likeDataBase.getLikesOfPost(postId);
    }


}
