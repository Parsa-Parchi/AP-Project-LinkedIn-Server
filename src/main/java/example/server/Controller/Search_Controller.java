package example.server.Controller;

import example.server.DataBase.User_DataBase;
import example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class Search_Controller {
    private  static User_DataBase user_db  ;

    static {
        try{
            user_db = new User_DataBase();
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<User> getUsersBySearch(String search) throws SQLException{
        return user_db.getUsersBySearchedString(search);
    }
}
