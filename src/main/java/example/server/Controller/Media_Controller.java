package example.server.Controller;

import example.server.DataBase.MediaOfPost_DataBase;
import example.server.models.Media_Post;

import java.sql.SQLException;
import java.util.ArrayList;

public class Media_Controller {
    private static MediaOfPost_DataBase mediaDataBase;

    static {
        try {
            mediaDataBase = new MediaOfPost_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMedia(Media_Post media) throws SQLException {
        mediaDataBase.insertMedia(media);
    }

    public static void deleteMedia(String filename,int postId) throws SQLException {
        mediaDataBase.deleteMedia(filename,postId);
    }

    public static void deleteMedia(Media_Post media) throws SQLException {
        mediaDataBase.deleteMedia(media);
    }

    public static void deleteMedia(int id) throws SQLException {
        mediaDataBase.deleteMedia(id);
    }

    public static void deleteMedia(String fileName) throws SQLException {
        mediaDataBase.deleteMedia(fileName);
    }

    public static void deleteMediasOfPost(int postId) throws SQLException {
        mediaDataBase.deleteMediasOfPost(postId);

    }

    public static void deleteAllMedias() throws SQLException {
        mediaDataBase.deleteAllMedias();
    }

    public static Media_Post getMedia(int id) throws SQLException {
        return mediaDataBase.getMedia(id);
    }

    public static Media_Post getMedia(String fileName , int postId) throws SQLException {
        return mediaDataBase.getMedia(fileName , postId);
    }

    public static ArrayList<Media_Post> getMediasOfPost(int postId) throws SQLException {
        return mediaDataBase.getMediaOfPost(postId);
    }

    public static ArrayList<Media_Post> getAllMedias() throws SQLException {
        return mediaDataBase.getAllMedia();
    }





}
