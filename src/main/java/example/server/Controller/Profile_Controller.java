package example.server.Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import example.server.models.ContactInformation;
import example.server.models.Profile;
import example.server.models.User;
import example.server.models.Skill;
import example.server.models.Education;


public class Profile_Controller {

    public static void updateUser(User user) throws Exception {
        UserController.updateUser(user);
    }

    public static void addEducation(Education education) throws SQLException {
        Education_Controller.insertEducation(education);
    }

    public static void updateEducation(Education education) throws SQLException {
        Education_Controller.updateEducation(education);
    }

    public static void updateSkill(Skill skill) throws SQLException {
        Skill_Controller.updateSkill(skill);
    }

    public static void updateContactInfo(ContactInformation contactInfo) throws SQLException {
        ContactInfo_Controller.updateContactInfo(contactInfo);
    }

    public static Profile getProfile(String email) throws SQLException {
        User user;
        if(UserController.getUserByEmail(email)==null) {
            return null;
        }
        else
             user = UserController.getUserByEmail(email);

        Skill skill = Skill_Controller.getSkillOfUser(email);
        ContactInformation contactInfo = ContactInfo_Controller.getContactInfoOfUser(email);
        ArrayList<Education> educations = Education_Controller.getAllEducationsOfUser(email);

        return new Profile(user, skill, contactInfo, educations);
    }

}
