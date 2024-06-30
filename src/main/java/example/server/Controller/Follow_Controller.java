package example.server.Controller;

import example.server.DataBase.Follow_DataBase;
import example.server.models.Follow;

import java.sql.SQLException;
import java.util.ArrayList;

public class Follow_Controller {

    private static Follow_DataBase followDataBase;

    static {

        try {
            followDataBase = new Follow_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertFollow(Follow follow) throws SQLException {
        followDataBase.insertFollow(follow);
    }

    public static void deleteFollow(Follow follow) throws SQLException {
        followDataBase.deleteFollow(follow);
    }
    public static boolean ExistFollow(Follow follow) throws SQLException {
        return followDataBase.ExistFollow(follow);
    }

    public static void  deleteFollowById(int id) throws SQLException {
        followDataBase.deleteFollow(id);
    }

    public static void deleteFollowersOfUser(String followedEmail) throws SQLException {
        followDataBase.deleteFollowersOfUser(followedEmail);
    }

    public static void deleteFollowingOfUser(String followerEmail) throws SQLException {
        followDataBase.deleteFollowingOfUser(followerEmail);
    }

    public static void deleteAllFollows() throws SQLException {
        followDataBase.deleteAllFollows();
    }

    public static ArrayList<String> getFollowersOfUser(String email) throws SQLException {
        return followDataBase.getFollowersOfUser(email);
    }

    public static ArrayList<String> getFollowingOfUser(String email) throws SQLException {
        return followDataBase.getFollowingOfUser(email);
    }

    public static ArrayList<Follow> getAllFollows() throws SQLException {
        return followDataBase.getAllFollows();
    }
}
