package example.server.DataBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import example.server.models.Connect;

public class Connect_DataBase {
    private Connection connection;

    public Connect_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createConnectTable();
    }
    private void createConnectTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS connections ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "Request_Sender VARCHAR(255) NOT NULL,"
                + "Request_Receiver VARCHAR(255) NOT NULL,"
                + "accepted BOOLEAN DEFAULT FALSE,"
                + "FOREIGN KEY (Request_Sender) REFERENCES users(email) ON DELETE CASCADE,"
                + "FOREIGN KEY (accepted BOOLEAN) REFERENCES users(email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();
    }

    public void insertConnect(Connect conn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO connections(Request_Sender, Request_Receiver) VALUES (?, ?)");

        statement.setString(1,conn.getRequest_Receiver());
        statement.setString(2,conn.getRequest_Sender());
        statement.executeUpdate();
    }

    public void deleteConnect(int id) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE id = ?");
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    public void deleteConnect(Connect conn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE id = ?");
        statement.setInt(1,conn.getId());
        statement.executeUpdate();

    }
    public void deleteConnectOfSender(String sender) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE Request_Sender = ?");
        statement.setString(1,sender);
        statement.executeUpdate();
    }

    public void deleteConnectOfReceiver(String receiver) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE Request_Receiver = ?");
        statement.setString(1,receiver);
        statement.executeUpdate();

    }




}
