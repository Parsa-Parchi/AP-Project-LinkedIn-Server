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

    private User_DataBase user_db;
    private Education_Controller education_controller;
    private Skill_Controller skill_controler;
    private ContactInfo_Controller contact_info_controller;

    public UserController() throws SQLException {
        user_db = new User_DataBase();
        education_controller = new Education_Controller();
        skill_controler = new Skill_Controller();
        contact_info_controller = new ContactInfo_Controller();
    }

    public void insertUser(User user) throws SQLException {
        boolean ValidationOfUser = ValidUser(user);
        if(ValidationOfUser){
            user_db.insertUser(user);
            skill_controler.getSkillDataBase().insertSkill(new Skill("","","","","",user.getEmail()));
            education_controller.getEducationDataBase().insertEducation(new Education(user.getEmail(),"","",0.0,null));
            skill_controler.getSkillDataBase().insertSkill(new Skill("","","","","",user.getEmail()));
            contact_info_controller.insertContactInfo(new ContactInformation(user.getEmail(),"","","",""));

        }

        else
            throw new IllegalArgumentException("Invalid data for user !");
    }

    public void updateUser(User user) throws SQLException {
        boolean ValidationOfUser = ValidUser(user);
        if(ValidationOfUser){
            user_db.updateUser(user);
        }
        else
            throw new IllegalArgumentException("Invalid data for user !");
    }

    public void deleteUserByEmail(User user) throws SQLException {
        user_db.deleteUserByEmail(user);
    }

    public void deleteUserById(int id) throws SQLException {
        user_db.deleteUserById(id);
    }
    public void deleteAllUsers() throws SQLException {
        user_db.deleteAllUsers();

    }

    public User getUserById(int id) throws SQLException {
       return user_db.getUserById(id);
    }

    public User getUserByEmail(String email) throws SQLException {
        return user_db.getUserByEmail(email);
    }

    public ArrayList<User> getAllUsers() throws SQLException {
            return user_db.getAllUsers();
    }

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
