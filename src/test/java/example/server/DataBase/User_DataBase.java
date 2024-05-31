package example.server.DataBase;

import example.server.models.User;

import java.sql.*;
import java.util.ArrayList;

public class User_DataBase {

    private final Connection connection;

    public User_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createUserTable();
    }


    public void createUserTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "firstname VARCHAR(20) NOT NULL,"
                + "lastName VARCHAR(40) NOT NULL,"
                + "additionalName VARCHAR(40),"
                + "avatar_url VARCHAR(255),"
                + "background_url VARCHAR(255),"
                + "headline VARCHAR(220),"
                + "country VARCHAR(60),"
                + "city VARCHAR(60),"
                + "followers INT NOT NULL DEFAULT 0,"
                + "followings INT NOT NULL DEFAULT 0,"
                + "connections INT NOT NULL DEFAULT 0"
                + ")");
        statement.executeUpdate();
    }

    public void insertUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (email, password, name, lastName, additionalName, avatar_url, background_url, headline, country, city)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getAdditionalName());
        preparedStatement.setString(6, user.getAvatar_url());
        preparedStatement.setString(7, user.getBackground_url());
        preparedStatement.setString(8, user.getHeadline());
        preparedStatement.setString(9, user.getCountry());
        preparedStatement.setString(10, user.getCity());
        preparedStatement.executeUpdate();
    }

    public void updateUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET email = ?, password = ?, name = ?, lastName = ?, additionalName = ?, avatar_url = ?, background_url = ?, headline = ?, country = ?, city = ? WHERE id = ?");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getAdditionalName());
        preparedStatement.setString(6, user.getAvatar_url());
        preparedStatement.setString(7, user.getBackground_url());
        preparedStatement.setString(8, user.getHeadline());
        preparedStatement.setString(9, user.getCountry());
        preparedStatement.setString(10, user.getCity());
        preparedStatement.setInt(11, user.getId());
        preparedStatement.executeUpdate();

    }

    public void deleteUserById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM skills WHERE email = ?");

    }

    public void deleteUserByEmail(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE email = ?");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.executeUpdate();

    }

    public void deleteAllUsers() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users");
        statement.executeUpdate();
    }

    public User getUserByEmail(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            String additionalName = resultSet.getString("additionalName");
            String avatarUrl = resultSet.getString("avatar_url");
            String backgroundUrl = resultSet.getString("background_url");
            String headline = resultSet.getString("headline");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            int followers = resultSet.getInt("followers");
            int followings = resultSet.getInt("followings");
            int connections = resultSet.getInt("connections");

            return new User(id, email, password, firstName, lastName, additionalName, avatarUrl,
                    backgroundUrl, headline, country, city, followers, followings, connections);
        }

        return null;

    }

    public User getUserById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String userEmail = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            String additionalName = resultSet.getString("additionalName");
            String avatarUrl = resultSet.getString("avatar_url");
            String backgroundUrl = resultSet.getString("background_url");
            String headline = resultSet.getString("headline");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            int followers = resultSet.getInt("followers");
            int followings = resultSet.getInt("followings");
            int connections = resultSet.getInt("connections");

            return new User(id, userEmail, password, firstName, lastName, additionalName, avatarUrl,
                    backgroundUrl, headline, country, city, followers, followings, connections);
        }
        return null;
    }


    public ArrayList<User> getAllUsers() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String userEmail = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            String additionalName = resultSet.getString("additionalName");
            String avatarUrl = resultSet.getString("avatar_url");
            String backgroundUrl = resultSet.getString("background_url");
            String headline = resultSet.getString("headline");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            int followers = resultSet.getInt("followers");
            int followings = resultSet.getInt("followings");
            int connections = resultSet.getInt("connections");

            User user = new User(id, userEmail, password, firstName, lastName, additionalName,
                    avatarUrl, backgroundUrl, headline, country, city, followers, followings, connections);

            users.add(user);
        }

        return users;

    }


}
