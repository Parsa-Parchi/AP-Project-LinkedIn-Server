package example.server.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

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

    public void deleteConnectOfAccepted(boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE accepted = ?");
        statement.setBoolean(1,accepted);
        statement.executeUpdate();

    }

    public void deleteAllConnects() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections");
        statement.executeUpdate();

    }

    public ArrayList<Connect> getAllConnects() throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Sender"),resultSet.getString("Request_Receiver"));
            connects.add(connect);

        }
        return connects;
    }

    public ArrayList<Connect> getConnectionsOfSender(String sender) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ?");

        statement.setString(1,sender);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
            connects.add(connect);

        }
        return connects;
    }

    public ArrayList<Connect> getConnectionsOfReceiver(String receiver) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Receiver = ?");
        statement.setString(1,receiver);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
            connects.add(connect);

        }
        return connects;

    }

    public ArrayList<Connect> getConnectionsOfAccepted(boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE accepted = ?");
        statement.setBoolean(1,accepted);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
            connects.add(connect);

        }
        return connects;
    }

    public Connect getConnectionOfSenderAndReceiver(String sender, String receiver) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND Request_Receiver = ?");
        statement.setString(1,sender);
        statement.setString(2,receiver);
        ResultSet resultSet = statement.executeQuery();
        Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
        return connect;
    }

    public Connect getConnectionOfSenderAndReceiver(String sender ,String receiver , boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND Request_Receiver = ? AND accepted = ?");
        statement.setString(1,sender);
        statement.setString(2,receiver);
        statement.setBoolean(3,accepted);
        ResultSet resultSet = statement.executeQuery();
        Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
        return connect;

    }

    public ArrayList<Connect> getAcceptedConnectionsOfSender(String sender ,boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND accepted = ?");
        statement.setString(1,sender);
        statement.setBoolean(2,accepted);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
            connects.add(connect);

        }
        return connects;
    }
    public ArrayList<Connect> getAcceptedConnectionsOfReceiver(String receiver ,boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Receiver = ?");
        statement.setString(1,receiver);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"));
            connects.add(connect);
        }
        return connects;
    }
}

