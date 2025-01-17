package example.server.DataBase;

import java.sql.*;
import java.util.ArrayList;

import example.server.models.Connect;
import example.server.models.Post;

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
                + "notes VARCHAR(500) NOT NULL,"
                + "accepted BOOLEAN DEFAULT FALSE,"
                + "requestDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (Request_Sender) REFERENCES users(email) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY (Request_Receiver) REFERENCES users(email) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");");

        statement.executeUpdate();
    }

    public void insertConnect(Connect conn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO connections(Request_Sender, Request_Receiver , notes) VALUES (?, ?, ?)");

        statement.setString(1,conn.getRequest_Sender());
        statement.setString(2,conn.getRequest_Receiver());
        statement.setString(3,conn.getNotes());
        statement.executeUpdate();

    }

    public void updateConnect(Connect connect) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE connections SET accepted = True WHERE Request_Sender = ? AND Request_Receiver = ? AND accepted = FALSE AND notes = ? ");
        statement.setString(1,connect.getRequest_Sender());
        statement.setString(2,connect.getRequest_Receiver());
        statement.setString(3,connect.getNotes());
        statement.executeUpdate();

    }

    public void deleteConnect(int id) throws SQLException {

        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM connections WHERE id = ?");
        statement1.setInt(1, id);
        ResultSet resultSet = statement1.executeQuery();

        String sender = "", receiver = "";
        boolean accepted = false;
        if (resultSet.next()) {
             sender = resultSet.getString("Request_Sender");
             receiver = resultSet.getString("Request_Receiver");
             accepted = resultSet.getBoolean("accepted");
        }

        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE id = ?");
        statement.setInt(1,id);
        statement.executeUpdate();

        if(accepted) {
            PreparedStatement statement2 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement2.setString(1, sender);
            statement2.executeUpdate();

            PreparedStatement statement3 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement3.setString(1, receiver);
            statement3.executeUpdate();

            PreparedStatement statement4 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement4.setString(1, sender);
            statement4.executeUpdate();

            PreparedStatement statement5 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement5.setString(1, receiver);
            statement5.executeUpdate();

        }

    }

    public void deleteConnect(Connect conn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE id = ?");
        statement.setInt(1, conn.getId());
        statement.executeUpdate();

        if(conn.isAccepted())
        {
            PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement1.setString(1, conn.getRequest_Receiver());
            statement1.executeUpdate();

            PreparedStatement statement2 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement2.setString(1, conn.getRequest_Sender());
            statement2.executeUpdate();

            PreparedStatement statement4 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement4.setString(1, conn.getRequest_Sender());
            statement4.executeUpdate();

            PreparedStatement statement5 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement5.setString(1,conn.getRequest_Receiver());
            statement5.executeUpdate();
        }
    }
    public void deleteRequest(Connect conn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE Request_Sender = ? AND Request_Receiver = ? AND accepted = FALSE ");
        statement.setString(1,conn.getRequest_Sender());
        statement.setString(2,conn.getRequest_Receiver());
        statement.executeUpdate();

    }

    public boolean ExistRequest(Connect conn) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND Request_Receiver = ? AND accepted = FALSE");
        preparedStatement.setString(1,conn.getRequest_Sender());
        preparedStatement.setString(2,conn.getRequest_Receiver());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return true;
        }
        return false;
    }


    public void deleteConnectOfSender(String sender) throws SQLException {
        PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ?");
        statement2.setString(1, sender);
        ResultSet resultSet = statement2.executeQuery();

        String receiver = "" ;
        boolean accepted = false ;
        if(resultSet.next()) {
          receiver = resultSet.getString("Request_Receiver");
          accepted = resultSet.getBoolean("accepted");
        }

        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE Request_Sender = ?");
        statement.setString(1,sender);
        statement.executeUpdate();

        if(accepted)
        {
            PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement1.setString(1, sender);
            statement1.executeUpdate();

            PreparedStatement statement3 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement3.setString(1, receiver);
            statement3.executeUpdate();

            PreparedStatement statement4 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement4.setString(1, sender);
            statement4.executeUpdate();

            PreparedStatement statement5 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement5.setString(1, receiver);
            statement5.executeUpdate();
        }
    }

    public void deleteConnectOfReceiver(String receiver) throws SQLException {
        PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM connections WHERE Request_Receiver = ?");
        statement2.setString(1, receiver);
        ResultSet resultSet = statement2.executeQuery();

        String sender = "" ;
        boolean accepted = false ;
        if(resultSet.next()) {
            sender = resultSet.getString("Request_Receiver");
            accepted = resultSet.getBoolean("accepted");
        }

        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE Request_Receiver = ?");
        statement.setString(1,receiver);
        statement.executeUpdate();

        if(accepted)
        {
            PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement1.setString(1, sender);
            statement1.executeUpdate();

            PreparedStatement statement3 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement3.setString(1, receiver);
            statement3.executeUpdate();

            PreparedStatement statement4 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement4.setString(1, sender);
            statement4.executeUpdate();

            PreparedStatement statement5 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement5.setString(1, receiver);
            statement5.executeUpdate();
        }
    }

    public void deleteConnectOfAccepted(boolean accepted) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM connections WHERE accepted = ?");
        statement1.setBoolean(1, accepted);
        ResultSet resultSet = statement1.executeQuery();
        String receiver = "" ,sender = "";
        if(resultSet.next()) {
            receiver = resultSet.getString("Request_Receiver");
            sender = resultSet.getString("Request_Sender");
        }

        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections WHERE accepted = ?");
        statement.setBoolean(1,accepted);
        statement.executeUpdate();

        if(accepted)
        {
            PreparedStatement statement2 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement2.setString(1, receiver);
            statement2.executeUpdate();

            PreparedStatement statement3 = connection.prepareStatement("UPDATE users SET connections = connections -1 WHERE email = ?");
            statement3.setString(1, sender);
            statement3.executeUpdate();

            PreparedStatement statement4 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement4.setString(1, sender);
            statement4.executeUpdate();

            PreparedStatement statement5 = connection.prepareStatement("UPDATE users SET followers = followers -1 AND followings = followings -1 WHERE email = ?");
            statement5.setString(1, receiver);
            statement5.executeUpdate();
        }

    }

    public void deleteAllConnects() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM connections");
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET connections = 0");
        statement1.executeUpdate();
    }

    public ArrayList<Connect> getAllConnects() throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Sender"),resultSet.getString("Request_Receiver"),resultSet.getString("notes"));
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
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"),resultSet.getString("notes"));
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
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"),resultSet.getString("notes"));
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
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"),resultSet.getString("notes"));
            connects.add(connect);

        }
        return connects;
    }

    public Connect getConnectionOfSenderAndReceiver(String sender, String receiver) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND Request_Receiver = ?");
        statement.setString(1,sender);
        statement.setString(2,receiver);
        ResultSet resultSet = statement.executeQuery();
        Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"),resultSet.getString("notes"));
        return connect;
    }

    public Connect getConnectionOfSenderAndReceiver(String sender ,String receiver , boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND Request_Receiver = ? AND accepted = ?");
        statement.setString(1,sender);
        statement.setString(2,receiver);
        statement.setBoolean(3,accepted);
        ResultSet resultSet = statement.executeQuery();
        Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"),resultSet.getString("notes"));
        return connect;

    }

    public ArrayList<Connect> getAcceptedConnectionsOfSender(String sender ,boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Sender = ? AND accepted = ?");
        statement.setString(1,sender);
        statement.setBoolean(2,accepted);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            Connect connect = new Connect(resultSet.getString("Request_Receiver"),resultSet.getString("Request_Sender"),resultSet.getString("notes"));
            connects.add(connect);

        }
        return connects;
    }
    public ArrayList<Connect> getConnectionsOfReceiverByBoolean(String receiver ,boolean accepted) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM connections WHERE Request_Receiver = ? AND accepted = ? ORDER BY requestDate");
        statement.setString(1,receiver);
        statement.setBoolean(2,accepted);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Connect> connects = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String Request_Receiver = resultSet.getString("Request_Receiver");
            String Request_Sender = resultSet.getString("Request_Sender");
            String notes = resultSet.getString("notes");
            Timestamp requestDate = resultSet.getTimestamp("requestDate");
            Connect connect = new Connect(id,Request_Sender,Request_Receiver,false,notes,requestDate);
            connects.add(connect);
        }
        return connects;
    }
}

