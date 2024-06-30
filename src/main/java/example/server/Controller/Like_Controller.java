package example.server.Controller;

import example.server.DataBase.Like_DataBase;
import example.server.models.Like;

import java.sql.SQLException;
import java.util.ArrayList;

public class Like_Controller {
    private static Like_DataBase likeDataBase;

    static {
        try {
            likeDataBase = new Like_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertLike(Like like) throws SQLException {
        if(!likeDataBase.ExistLike(like))
            likeDataBase.insertLike(like);
        else
            throw new SQLException("Like already exist");

    }

    public static void deleteLike(Like like) throws SQLException {
        likeDataBase.deleteLike(like.getPostId(),like.getEmail());
    }

    public static void deleteLikesOfPost(int postId) throws SQLException {
        likeDataBase.deleteLikesOfPost(postId);
    }


    public static void deleteAllLikes() throws SQLException {
        likeDataBase.deleteAllLikes();
    }

    public static ArrayList<Like> getLikesOfUser(String email) throws SQLException {
        return likeDataBase.getLikesOfUser(email);
    }

    public static ArrayList<Like> getLikesOfPost(int postId) throws SQLException {
        return likeDataBase.getLikesOfPost(postId);
    }


}
