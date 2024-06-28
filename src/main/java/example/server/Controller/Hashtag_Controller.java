package example.server.Controller;

import example.server.DataBase.Hashtag_DataBase;
import example.server.models.Hashtag;

import java.sql.SQLException;
import java.util.ArrayList;

public class Hashtag_Controller {
    private static Hashtag_DataBase hashtag_db;

    static {
        try {
            hashtag_db = new Hashtag_DataBase();
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertHashtag(Hashtag hashtag) throws SQLException {
        hashtag_db.insertHashtag(hashtag);
    }

    public static void updateHashtag(Hashtag hashtag) throws SQLException {
        hashtag_db.UpdateHashtag(hashtag);
    }
    public static void deleteHashtag(Hashtag hashtag) throws SQLException {
        hashtag_db.deleteHashtag(hashtag);
    }

    public static void deleteHashtagById(int hashtag_id) throws SQLException {
        hashtag_db.deleteHashtag(hashtag_id);
    }
    public static void deleteHashtagByString(String hashtag) throws SQLException {
        hashtag_db.deleteHashtag(hashtag);
    }
    public static void deleteHashtagsOfPost(int post_id) throws SQLException {
        hashtag_db.deleteHashtagsOfPost(post_id);
    }

    public static void deleteAllHashtags() throws SQLException {
        hashtag_db.deleteAllHashtags();
    }

    public static Hashtag getHashtagById(int hashtag_id) throws SQLException {
        return hashtag_db.getHashtag(hashtag_id);
    }
    public static ArrayList<String> getHashtagsOfPost(int post_id) throws SQLException {
       return hashtag_db.getHashtagsOfPost(post_id);
    }

    public static ArrayList<Integer> getPostIdsOfHashtag(String hashtag) throws SQLException {
        return hashtag_db.getPostIdsOfHashtag(hashtag);
    }
 }
