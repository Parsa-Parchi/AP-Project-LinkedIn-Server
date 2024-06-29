package example.server.Controller;

import example.server.DataBase.MediaOfChatroom_DataBase;
import example.server.models.Media_Chatroom;

import java.sql.SQLException;
import java.util.ArrayList;

public class MediaOfChatroom_Controller {
    private static MediaOfChatroom_DataBase mediaOfChatroom_DataBase;
    static {
        try {
            mediaOfChatroom_DataBase = new MediaOfChatroom_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMedia(Media_Chatroom media_chatroom)throws SQLException {
        mediaOfChatroom_DataBase.insertMedia(media_chatroom);
    }

    public static void deleteMedia(Media_Chatroom media_chatroom) throws SQLException {
        mediaOfChatroom_DataBase.deleteMedia(media_chatroom);
    }

    public static void deleteMediaOfTwoPersons(String person1, String person2) throws SQLException {
        mediaOfChatroom_DataBase.deleteMediaOfTwoPersons(person1, person2);
    }
    public static void deleteAllMedias() throws SQLException {
        mediaOfChatroom_DataBase.deleteAllMedias();
    }

    public static Media_Chatroom getMediaOfChatroom(Media_Chatroom media_chatroom) throws SQLException {
      return   mediaOfChatroom_DataBase.getMediaOfChatroom(media_chatroom);
    }

    public static Media_Chatroom getMediaOfChatroom(int id) throws SQLException {
      return   mediaOfChatroom_DataBase.getMediaOfChatroom(id);
    }

    public static ArrayList<Media_Chatroom> getMediasOfTwoPersons(String person1 , String person2) throws SQLException {
      return   mediaOfChatroom_DataBase.getMediasOfTwoPerson(person1,person2);
    }


}
