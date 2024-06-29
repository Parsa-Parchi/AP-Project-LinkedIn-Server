package example.server.DataBase;

import example.server.models.Media_Chatroom;

import java.sql.*;
import java.util.ArrayList;

public class MediaOfChatroom_DataBase {
    private final Connection connection;
    public MediaOfChatroom_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        CreateTableMedia();
    }
    private void CreateTableMedia() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS MediaOfChatroom("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "Sender VARCHAR(255) NOT NULL,"
                + "Reciever VARCHAR(255) NOT NULL,"
                + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "mediaUrl VARCHAR(255)"
                + "FOREIGN KEY(Sender) REFERENCES users (email) ON DELETE CASCADE,"
                + "FOREIGN KEY(Receiver) REFERENCES users (email) ON DELETE CASCADE"
                + ")");

        preparedStatement.executeUpdate();
    }

    public void insertMedia(Media_Chatroom media_chatroom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO MediaOfChatroom(Sender, Reciever,mediaUrl) VALUES(?,?,?)");
        preparedStatement.setString(1, media_chatroom.getSender());
        preparedStatement.setString(2, media_chatroom.getReceiver());
        preparedStatement.setString(3, media_chatroom.getMedia_url());
        preparedStatement.executeUpdate();
    }

    public void deleteMedia(Media_Chatroom media_chatroom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM MediaOfChatroom WHERE id = ?");
        preparedStatement.setInt(1, media_chatroom.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteMediaOfTwoPersons(String person1,String person2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM MediaOfChatroom WHERE Sender = ? AND Reciever = ? OR Sender = ? AND Reciever = ?");
        preparedStatement.setString(1, person1);
        preparedStatement.setString(2, person2);
        preparedStatement.setString(3, person2);
        preparedStatement.setString(4, person1);
        preparedStatement.executeUpdate();
    }

    public void  deleteAllMedias() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM MediaOfChatroom");
        preparedStatement.executeUpdate();
    }

    public Media_Chatroom getMediaOfChatroom(Media_Chatroom media_chatroom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM MediaOfChatroom WHERE Sender = ? AND Reciever = ? AND MediaUrl = ? AND created_at = ?");
        preparedStatement.setString(1, media_chatroom.getSender());
        preparedStatement.setString(2, media_chatroom.getReceiver());
        preparedStatement.setString(3, media_chatroom.getMedia_url());
        preparedStatement.setTimestamp(4, media_chatroom.getTimestamp());
        ResultSet resultSet = preparedStatement.executeQuery();
        Media_Chatroom mediaChatroom = null;
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String sender = resultSet.getString("Sender");
            String receiver = resultSet.getString("Reciever");
            String mediaUrl = resultSet.getString("MediaUrl");
            Timestamp created_at = resultSet.getTimestamp("created_at");
            mediaChatroom = new Media_Chatroom(id, sender, receiver, mediaUrl, created_at);

        }
        return mediaChatroom;
    }

    public Media_Chatroom getMediaOfChatroom(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM MediaOfChatroom WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Media_Chatroom mediaChatroom = null;
        if (resultSet.next()) {
            String sender = resultSet.getString("Sender");
            String receiver = resultSet.getString("Reciever");
            String mediaUrl = resultSet.getString("MediaUrl");
            Timestamp created_at = resultSet.getTimestamp("created_at");
            mediaChatroom = new Media_Chatroom(id, sender, receiver, mediaUrl, created_at);
        }
        return mediaChatroom;
    }

    public ArrayList<Media_Chatroom> getMediasOfTwpPerson(String person1 ,String  person2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM MediaOfChatroom WHERE Sender=? AND Receiver=? OR Sender=? AND Receiver=?");
        preparedStatement.setString(1, person1);
        preparedStatement.setString(2, person2);
        preparedStatement.setString(3, person2);
        preparedStatement.setString(4, person1);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Media_Chatroom> mediaChatrooms = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String sender = resultSet.getString("Sender");
            String receiver = resultSet.getString("Reciever");
            String mediaUrl = resultSet.getString("MediaUrl");
            Timestamp created_at = resultSet.getTimestamp("created_at");
            mediaChatrooms.add(new Media_Chatroom(id, sender, receiver, mediaUrl, created_at));
        }
        return mediaChatrooms;
    }

}
