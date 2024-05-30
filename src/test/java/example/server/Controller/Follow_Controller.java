package example.server.Controller;

import example.server.DataBase.Follow_DataBase;
import example.server.models.Follow;

import java.sql.SQLException;
import java.util.ArrayList;

public class Follow_Controller {
    private Follow follow;
    private Follow_DataBase followDataBase;
    public void Follow_Controller(String follower, String followed) throws SQLException {
        this.follow = new Follow(follower,followed);
        this.followDataBase.createFollowTable();
    }
    public void insertFollow() throws SQLException {
        this.followDataBase.insertFollow(this.follow);
    }
    public void deleteFollow() throws SQLException {
        this.followDataBase.deleteFollow(this.follow);
    }
    public void  deleteFollowOfId(int id) throws SQLException {
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
