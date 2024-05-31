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

        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET followers = followers + 1 WHERE email = ?");
        statement1.setString(1, follow.getFollowed());
        statement1.executeUpdate();

        PreparedStatement statement2 = connection.prepareStatement("UPDATE users SET followings = followings + 1 WHERE email = ?");
        statement2.setString(1, follow.getFollower());
        statement2.executeUpdate();
    }

    public void deleteFollow(Follow follow) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE follower = ? AND followed = ?");
        statement.setString(1, follow.getFollower());
        statement.setString(2, follow.getFollowed());
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET followers = followers - 1 WHERE email = ?");
        statement1.setString(1, follow.getFollowed());
        statement1.executeUpdate();

        PreparedStatement statement2 = connection.prepareStatement("UPDATE users SET followings = followings - 1 WHERE email = ?");
        statement2.setString(1, follow.getFollower());
        statement2.executeUpdate();

    }

    public void deleteFollow(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM follows WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        String follower="",followed="";
        if(resultSet.next()){
             follower = resultSet.getString("follower");
             followed = resultSet.getString("followed");
        }


        PreparedStatement statement1 = connection.prepareStatement("DELETE FROM follows WHERE id = ?");
        statement1.setInt(1, id);
        statement1.executeUpdate();

        PreparedStatement statement2 = connection.prepareStatement("UPDATE users SET followers = followers - 1 WHERE email = ?");
        statement2.setString(1,followed);
        statement2.executeUpdate();

        PreparedStatement statement3 = connection.prepareStatement("UPDATE users SET followings = followings - 1 WHERE email = ?");
        statement3.setString(1,follower);
        statement3.executeUpdate();

    }

    public void deleteFollowersOfUser(String followedEmail) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE followed = ?");
        statement.setString(1, followedEmail);
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET followers = 0 WHERE email = ?");
        statement1.setString(1, followedEmail);
        statement1.executeUpdate();

    }

    public void deleteFollowingOfUser(String followerEmail) throws SQLException{

        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows WHERE follower = ?");
        statement.setString(1, followerEmail);
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET followings = 0 WHERE email = ?");
        statement1.setString(1, followerEmail);
        statement1.executeUpdate();
    }

    public void deleteAllFollows() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows");
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET followers = 0 AND followed = 0");
        statement1.executeUpdate();

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
