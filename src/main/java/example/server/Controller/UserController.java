package example.server.Controller;

import example.server.DataBase.ContactInformation_DataBase;
import example.server.DataBase.Education_DataBase;
import example.server.DataBase.Skill_DataBase;
import example.server.DataBase.User_DataBase;
import example.server.models.ContactInformation;
import example.server.models.Education;
import example.server.models.Skill;
import example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
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

    public static void insertUser(User user) throws Exception {
        boolean ValidationOfUser = ValidUser(user);
        if(ValidationOfUser){
            user_db.insertUser(user);
            Skill_Controller.insertSkill(new Skill("","","","","",user.getEmail()));
            Education_Controller.insertEducation(new Education(user.getEmail(),"","",0.0,null));
            ContactInfo_Controller.insertContactInfo(new ContactInformation(user.getEmail(),"","","",""));
        }

        else
            throw new IllegalArgumentException("Invalid data for user !");
    }

    public static void updateUser(User user) throws SQLException {
            boolean ValidationOfUser = ValidUser(user);
            if (ValidationOfUser) {
                User user1 = user_db.getUserById(user.getId());
                if(user.getEmail()!=null)
                    user1.setEmail(user.getEmail());
                if(user.getFirstName()!=null)
                    user1.setFirstName(user.getFirstName());
                if(user.getLastName()!=null)
                    user1.setLastName(user.getLastName());
                if(user.getPassword()!=null)
                    user1.setPassword(user.getPassword());
                if(user.getAdditionalName()!=null)
                    user1.setAdditionalName(user.getAdditionalName());
                if(user.getAvatar_url()!=null)
                    user1.setAvatar_url(user.getAvatar_url());
                if(user.getBackground_url()!=null)
                    user1.setBackground_url(user.getBackground_url());
                if(user.getCity()!=null)
                    user1.setCity(user.getCity());
                if(user.getCountry()!=null)
                    user1.setCountry(user.getCountry());
                if (user.getHeadline()!=null)
                    user1.setHeadline(user.getHeadline());
                user_db.updateUser(user1);
            } else
                throw new IllegalArgumentException("Invalid data for user !");

    }

    public static void deleteUserByEmail(User user) throws SQLException {
        user_db.deleteUserByEmail(user);
    }

    public static void deleteUserById(int id) throws SQLException {
        user_db.deleteUserById(id);
    }
    public static void deleteAllUsers() throws SQLException {
        user_db.deleteAllUsers();

    }

    public static User getUserById(int id) throws SQLException {
       return user_db.getUserById(id);
    }

    public static User getUserByEmail(String email) throws SQLException {
        return user_db.getUserByEmail(email);
    }

    public static ArrayList<User> getAllUsers() throws SQLException {
            return user_db.getAllUsers();
    }

    public static boolean ValidUser(User user) throws SQLException {
        boolean Valid = true;
        if(user.getEmail()!=null){
            Valid = Valid && ValidEmail(user.getEmail());
        }
        if (user.getPassword()!=null){
            Valid = Valid && ValidPassword(user.getPassword());
        }
        if(user.getFirstName()!=null){
            Valid = Valid && ValidName(user.getFirstName());
        }
        if(user.getLastName()!=null){
            Valid = Valid && ValidName(user.getLastName());
        }
        return Valid;
    }


    public static boolean ValidName(String name)  {
        return name.matches("^[a-zA-Z]+$");
    }

    public static boolean ValidEmail(String email)  {
        return email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public static boolean ValidPassword(String password)  {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$") && password.length() >= 8;
    }

}
