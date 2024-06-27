package example.server.Controller;

import example.server.DataBase.Hashtag_DataBase;
import example.server.models.Hashtag;

import java.sql.SQLException;
import java.util.ArrayList;

public class Hashtag_Controller {
    private Hashtag_DataBase hashtag_db;
    public Hashtag_Controller() throws SQLException {
        hashtag_db = new Hashtag_DataBase();
    }

    public void insertHashtag(Hashtag hashtag) throws SQLException {
        hashtag_db.insertHashtag(hashtag);
    }

    public void updateHashtag(Hashtag hashtag) throws SQLException {
        hashtag_db.UpdateHashtag(hashtag);
    }
    public void deleteHashtag(Hashtag hashtag) throws SQLException {
        hashtag_db.deleteHashtag(hashtag);
    }

    public void deleteHashtagById(int hashtag_id) throws SQLException {
        hashtag_db.deleteHashtag(hashtag_id);
    }
    public void deleteHashtagByString(String hashtag) throws SQLException {
        hashtag_db.deleteHashtag(hashtag);
    }
    public void deleteHashtagsOfPost(int post_id) throws SQLException {
        hashtag_db.deleteHashtagsOfPost(post_id);
    }

    public void deleteAllHashtags() throws SQLException {
        hashtag_db.deleteAllHashtags();
    }

    public Hashtag getHashtagById(int hashtag_id) throws SQLException {
        return hashtag_db.getHashtag(hashtag_id);
    }
    public ArrayList<String> getHashtagsOfPost(int post_id) throws SQLException {
       return hashtag_db.getHashtagsOfPost(post_id);
    }

}
