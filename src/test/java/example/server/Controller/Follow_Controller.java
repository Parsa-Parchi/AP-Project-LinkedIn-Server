package example.server.Controller;

import example.server.DataBase.Follow_DataBase;
import example.server.models.Follow;

import java.sql.SQLException;

public class Follow_Controller {
    private Follow follow;
    private Follow_DataBase followDataBase;
    public void Follow_Controller(String follower, String followed) throws SQLException {
        this.follow = new Follow(follower,followed);
        this.followDataBase.createFollowTable();
    }
}
