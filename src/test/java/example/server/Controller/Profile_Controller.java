package example.server.Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import example.server.models.ContactInformation;
import example.server.models.Profile;
import example.server.models.User;
import example.server.models.Skill;
import example.server.models.Education;


public class Profile_Controller {
    private static UserController userController;
    private static Education_Controller educationController;
    private static Skill_Controller skillController;
    private static ContactInfo_Controller contactInfoController;

    public Profile_Controller() throws SQLException {
        userController = new UserController();
        educationController = new Education_Controller();
        skillController = new Skill_Controller();
        contactInfoController = new ContactInfo_Controller();
    }

    public static void updateUser(User user) throws SQLException {
        userController.updateUser(user);
    }

    public static void addEducation(Education education) throws SQLException {
        educationController.insertEducation(education);
    }

    public static void updateEducation(Education education) throws SQLException {
        educationController.updateEducation(education);
    }

    public static void updateSkill(Skill skill) throws SQLException {
        skillController.updateSkill(skill);
    }

    public static void updateContactInfo(ContactInformation contactInfo) throws SQLException {
        contactInfoController.updateContactInfo(contactInfo);
    }

    public static Profile getProfile(String email) throws SQLException {
        User user;
        if(userController.getUserByEmail(email)==null) {
            return null;
        }
        else
             user = userController.getUserByEmail(email);

        Skill skill = skillController.getSkillOfUser(email);
        ContactInformation contactInfo = contactInfoController.getContactInfoOfUser(email);
        ArrayList<Education> educations = educationController.getAllEducationsOfUser(email);

        return new Profile(user, skill, contactInfo, educations);
    }



}
