package example.server.Controller;

import example.server.DataBase.Message_DataBase;
import example.server.models.Message;

import java.sql.SQLException;
import java.util.ArrayList;

public class Message_Controller {

    private static Message_DataBase messageDataBase;
    static {
        try {
            messageDataBase = new Message_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertMessage(Message message) throws SQLException {
        messageDataBase.insertMessage(message);
    }

    public static void updateMessage(Message message) throws SQLException {
        messageDataBase.editMessage(message);
    }
    public static void deleteMessage(Message message) throws SQLException {
        messageDataBase.deleteMessage(message);
    }

    public static void deleteMessagesOfTwoPersons(String person1,String person2) throws SQLException {
        messageDataBase.deleteMessagesOfTwoPersons(person1,person2);
    }
    public static void deleteAllMessages() throws SQLException {
        messageDataBase.deleteAllMessages();
    }

    public static Message getParticularMessageByID(int messageID) throws SQLException {
        return messageDataBase.getParticularMessage(messageID);
    }

    public static Message getParticularMessage(Message message) throws SQLException {
        return messageDataBase.getParticularMessage(message);
    }

    public static ArrayList<Message> getMessagesOfTwoPersons(String person1,String person2) throws SQLException {
        return messageDataBase.getMessagesOfTwoPerson(person1,person2);
    }
}
