package example.server.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void deleteAllFollows() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM follows");
        statement.executeUpdate();

    }




}
