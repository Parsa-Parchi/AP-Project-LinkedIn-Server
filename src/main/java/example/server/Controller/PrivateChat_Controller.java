package example.server.Controller;

import example.server.DataBase.PrivateChat_DataBase;
import example.server.models.Message;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PrivateChat_Controller {

    private static PrivateChat_DataBase privateChatDataBase;
    static {
        try {
            privateChatDataBase = new PrivateChat_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertMessage(Message message) throws SQLException {
        privateChatDataBase.insertMessage(message);
    }

    public static void updateMessage(Message message) throws SQLException {
        privateChatDataBase.editMessage(message);
    }
    public static void deleteMessage(Message message) throws SQLException {
        privateChatDataBase.deleteMessage(message);
    }
    public static void deleteMessage(int messageId) throws SQLException {
        privateChatDataBase.deleteMessage(messageId);
    }

    public static void deleteHistory(String person1,String person2) throws SQLException {
        privateChatDataBase.deleteHistory(person1,person2);
    }
    public static void deleteAllMessages() throws SQLException {
        privateChatDataBase.deleteAllMessages();
    }

    public static Message getParticularMessageByID(int messageID) throws SQLException {
        return privateChatDataBase.getParticularMessage(messageID);
    }

    public static Message getParticularMessage(Message message) throws SQLException {
        return privateChatDataBase.getParticularMessage(message);
    }

    public static ArrayList<Message> getHistory(String person1,String person2) throws SQLException {
        return privateChatDataBase.getHistory(person1,person2);
    }
    public static ArrayList<Message> getNewMessages(String sender, String receiver, Timestamp lastCheck) throws SQLException {
        return privateChatDataBase.getNewMessages(sender,receiver,lastCheck);
    }
}
