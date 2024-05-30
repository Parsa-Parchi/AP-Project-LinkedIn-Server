package example.server.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import example.server.models.Follow;

public class Follow_DataBase {
    private final Connection connection;

    public Follow_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createFollowTable();
    }

    public void createFollowTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS follows("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "follower VARCHAR(255) NOT NULL,"
                + "followed VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (follower) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (followed) REFERENCES users(email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();

    }


    public void insertFollow(Follow follow) throws SQLException{

        PreparedStatement statement =connection.prepareStatement("INSERT INTO follows(follower,followed) VALUES(?,?)");
        statement.setString(1, follow.getFollower());
        statement.setString(2, follow.getFollowed());
        statement.executeUpdate();

    }

    public void deleteFollow(Follow follow) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE follower = ? AND followed = ?");
        statement.setString(1, follow.getFollower());
        statement.setString(2, follow.getFollowed());
        statement.executeUpdate();

    }

    public void deleteFollow(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();

    }

    public void deleteFollowersOfUser(String followedEmail) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE followed = ?");
        statement.setString(1, followedEmail);
        statement.executeUpdate();

    }

    public void deleteFollowingOfUser(String followerEmail) throws SQLException{

        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE follower = ?");
        statement.setString(1, followerEmail);
        statement.executeUpdate();
    }

    public void deleteAllFollows() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows");
        statement.executeUpdate();

    }

    public ArrayList<String> getFollowersOfUser(String email) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM follows WHERE followed = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<String> followers = new ArrayList<>();
        while(resultSet.next()){
            followers.add(resultSet.getString("follower"));

        }
        return followers;

    }

    public ArrayList<String> getFollowingOfUser(String email) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM follows WHERE follower = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<String> following = new ArrayList<>();
        while(resultSet.next()){
            following.add(resultSet.getString("followed"));

        }
        return following;

    }

    public ArrayList<Follow> getAllFollows() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM follows");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Follow> follows = new ArrayList<>();
        while(resultSet.next()){
            follows.add(new Follow(resultSet.getString("follower"),resultSet.getString("followed")));

        }

        return follows;
    }

}
