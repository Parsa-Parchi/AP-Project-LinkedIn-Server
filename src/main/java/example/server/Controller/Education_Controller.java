package example.server.Controller;

import example.server.DataBase.Education_DataBase;
import example.server.models.Education;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Education_Controller {

    private static Education_DataBase educationDataBase;

   static {
       try {
           educationDataBase = new Education_DataBase();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

    public static void insertEducation(Education education) throws SQLException {
        if(isValid(education))
            educationDataBase.insertEducation(education);
        else
            throw new IllegalArgumentException("Invalid education ! : school name or field is empty");
    }

    public static void updateEducation(Education education) throws SQLException {
        if(isValid(education))
            educationDataBase.updateEducation(education);
        else
            throw new IllegalArgumentException("Invalid education ! : school name or field is empty");
    }

    public static void deleteEducation(int id) throws SQLException {
        educationDataBase.deleteEducation(id);
    }

    public static void deleteEducationOfUser(String email) throws SQLException {
        educationDataBase.deleteEducationOfUser(email);
    }

    public static void deleteAllEducations() throws SQLException {
        educationDataBase.deleteAllEducations();
    }

    public static ArrayList<Education> getAllEducations() throws SQLException {
        return educationDataBase.getAllEducations();
    }

    public static ArrayList<Education> getAllEducationsOfUser(String email) throws SQLException {
        return educationDataBase.getAllEducationsOfUser(email);
    }

    public static ArrayList<Education> getEducationOfField(String field) throws SQLException {
        return educationDataBase.getEducationOfField(field);
    }

    public static ArrayList<Education> getEducationOfSchool(String school) throws SQLException {
        return educationDataBase.getEducationOfSchool(school);
    }

    public static ArrayList<Education> getEducationOfActivityCommunity(String Activity_Community) throws SQLException {
        return educationDataBase.getEducationOfActivityCommunity(Activity_Community);
    }

    public static boolean schoolNameValidator(String schoolName) {
        return schoolName != null;
    }

    public static boolean fieldValidator(String field) {
        return field != null;
    }

    public static boolean isValid(Education education) {
        return schoolNameValidator(education.getSchoolName()) && fieldValidator(education.getField());
    }

}