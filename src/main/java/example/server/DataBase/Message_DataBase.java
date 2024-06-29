package example.server.DataBase;

import example.server.models.Message;

import java.sql.*;
import java.util.ArrayList;

public class Message_DataBase {
    private final Connection connection;

    public Message_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        CreateMessageTable();
    }

    private void CreateMessageTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Message("
                +"id INT AUTO_INCREMENT PRIMARY KEY,"
                +"Sender VARCHAR(255) NOT NULL,"
                +"Reciever VARCHAR(255) NOT NULL,"
                +"message VARCHAR(1900) NOT NULL,"
                +"created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                +"mediaUrl VARCHAR(255)"
                +"FOREIGN KEY(Sender) REFERENCES users (email) ON DELETE CASCADE,"
                +"FOREIGN KEY(Receiver) REFERENCES users (email) ON DELETE CASCADE"
                + ")");

        preparedStatement.executeUpdate();
    }

    public void insertMessage(Message message) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Message(Sender, Reciever, message,mediaUrl) VALUES(?,?,?,?)");
        preparedStatement.setString(1, message.getSender());
        preparedStatement.setString(2, message.getReceiver());
        preparedStatement.setString(3,message.getText());
        preparedStatement.setString(4, message.getMediaUrl());
        preparedStatement.executeUpdate();

    }

    public void editMessage(Message message) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Message SET message=?, mediaUrl=? WHERE id=?");
        preparedStatement.setString(1, message.getText());
        preparedStatement.setString(2, message.getMediaUrl());
        preparedStatement.setInt(3, message.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteMessage(Message message) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Message WHERE id=?");
        preparedStatement.setInt(1, message.getId());
        preparedStatement.executeUpdate();
    }
    public void deleteMessagesOfTwoPersons(String person1 , String person2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Message WHERE Sender=? AND Receiver=? OR Sender=? AND Receiver=?");
        preparedStatement.setString(1, person1);
        preparedStatement.setString(2, person2);
        preparedStatement.setString(3, person2);
        preparedStatement.setString(4,person1);
        preparedStatement.executeUpdate();
    }

    public void deleteAllMessages() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Message");
        preparedStatement.executeUpdate();
    }

    public Message getParticularMessage(Message message) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Message WHERE Sender=? AND Receiver=? AND message=? AND created_at=?");
        preparedStatement.setString(1, message.getSender());
        preparedStatement.setString(2, message.getReceiver());
        preparedStatement.setString(3, message.getText());
        preparedStatement.setString(4, message.getMediaUrl());
        ResultSet resultSet = preparedStatement.executeQuery();
        Message particularMessage = null;
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String sender = resultSet.getString("Sender");
            String receiver = resultSet.getString("Receiver");
            String text = resultSet.getString("message");
            String mediaUrl = resultSet.getString("mediaUrl");
            Timestamp created_at = resultSet.getTimestamp("created_at");

            particularMessage = new Message(id,text,sender,receiver,created_at,mediaUrl);
        }
        return particularMessage;
    }

    public Message getParticularMessage(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Message WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Message particularMessage = null;

        if (resultSet.next()) {
            String sender = resultSet.getString("Sender");
            String receiver = resultSet.getString("Receiver");
            String text = resultSet.getString("message");
            String mediaUrl = resultSet.getString("mediaUrl");
            Timestamp created_at = resultSet.getTimestamp("created_at");
            particularMessage = new Message(id,text,sender,receiver,created_at,mediaUrl);
        }
        return particularMessage;
    }

    public ArrayList<Message> getMessagesOfTwoPerson(String person1,String person2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Message WHERE Sender=? AND Receiver=? OR Sender=? AND Receiver=?");
        preparedStatement.setString(1, person1);
        preparedStatement.setString(2, person2);
        preparedStatement.setString(3, person2);
        preparedStatement.setString(4, person1);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String sender = resultSet.getString("Sender");
            String receiver = resultSet.getString("Receiver");
            String text = resultSet.getString("message");
            String mediaUrl = resultSet.getString("mediaUrl");
            Timestamp created_at = resultSet.getTimestamp("created_at");
            messages.add(new Message(id,text,sender,receiver,created_at,mediaUrl));
        }
        return messages;
    }





}