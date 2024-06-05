package example.server.Controller;

import example.server.DataBase.User_DataBase;
import example.server.models.User;
import example.server.Utilities.jwt_Util;

import java.nio.charset.StandardCharsets;
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
        if (user!=null && user.getPassword().equals(decodeBase64(password))) {
            return jwt_Util.generateToken(email);
        }
        else
            throw new IllegalArgumentException("Invalid email or password");
    }




    public static String decodeBase64(String base64EncodedString) {
        // Decode the Base64 encoded string
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        // Convert the decoded bytes back to a string
        return new String(decodedBytes);
    }

}
