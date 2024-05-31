package example.server.Controller;

import example.server.DataBase.ContactInformation_DataBase;
import example.server.DataBase.Education_DataBase;
import example.server.DataBase.Skill_DataBase;
import example.server.DataBase.User_DataBase;
import example.server.models.Education;
import example.server.models.User;

import java.sql.SQLException;

public class UserController {

    public static boolean ValidUser(User user) throws SQLException {
        return ValidName(user.getFirstName()) && ValidName(user.getLastName())
                && ValidEmail(user.getEmail()) && ValidPassword(user.getPassword());
    }

    public static boolean ValidName(String name) throws SQLException {
        return name.matches("^[a-zA-Z]+$");
    }

    public static boolean ValidEmail(String email) throws SQLException {
        return email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public static boolean ValidPassword(String password) throws SQLException {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$") && password.length() >= 8;
    }

}
