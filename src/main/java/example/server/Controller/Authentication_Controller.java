package example.server.Controller;

import example.server.DataBase.User_DataBase;
import example.server.models.User;
import example.server.Utilities.jwt_Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class Authentication_Controller {
    private static User_DataBase user_dataBase;

    static {
        try {
            user_dataBase = new User_DataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static String LogIn(String email, String password) throws Exception
    {
        User user = user_dataBase.getUserByEmail(email);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return jwt_Util.generateToken(email);
        }
        return null;
    }




    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
