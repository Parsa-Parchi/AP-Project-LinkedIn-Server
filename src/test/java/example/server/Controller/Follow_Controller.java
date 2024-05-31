package example.server.Controller;

import example.server.DataBase.Follow_DataBase;
import example.server.models.Follow;

import java.sql.SQLException;
import java.util.ArrayList;

public class Follow_Controller {

    private Follow_DataBase followDataBase;

    public Follow_Controller() throws SQLException {
        followDataBase = new Follow_DataBase();
    }

    public void insertFollow(Follow follow) throws SQLException {
        this.followDataBase.insertFollow(follow);
    }

    public void deleteFollow(Follow follow) throws SQLException {
        this.followDataBase.deleteFollow(follow);
    }

    public void  deleteFollowById(int id) throws SQLException {
        this.followDataBase.deleteFollow(id);
    }

    public void deleteFollowersOfUser(String followedEmail) throws SQLException {
        this.followDataBase.deleteFollowersOfUser(followedEmail);
    }

    public void deleteFollowingOfUser(String followerEmail) throws SQLException {
        this.followDataBase.deleteFollowingOfUser(followerEmail);
    }

    public void deleteAllFollows() throws SQLException {
        this.followDataBase.deleteAllFollows();
    }

    public ArrayList<String> getFollowersOfUser(String email) throws SQLException {
        return this.followDataBase.getFollowersOfUser(email);
    }

    public ArrayList<String> getFollowingOfUser(String email) throws SQLException {
        return this.followDataBase.getFollowingOfUser(email);
    }

    public ArrayList<Follow> getAllFollows() throws SQLException {
        return this.followDataBase.getAllFollows();
    }

}
