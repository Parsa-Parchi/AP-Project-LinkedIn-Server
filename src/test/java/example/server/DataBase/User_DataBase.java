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
}
