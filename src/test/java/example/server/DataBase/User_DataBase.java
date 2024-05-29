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
}
