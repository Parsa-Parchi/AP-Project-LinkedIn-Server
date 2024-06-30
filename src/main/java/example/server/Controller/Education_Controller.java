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

    public static void insertEducation(Education education) throws Exception {
        if(isValid(education) ) {
            if (!educationDataBase.ExistEducation(education))
                educationDataBase.insertEducation(education);
            else
                throw new IllegalArgumentException("Education already exists");
        }
        else
            throw new IllegalArgumentException("Invalid education ! : school name or field is empty");
    }

    public static void updateEducation(Education education) throws SQLException {
        if(isValid(education)) {
            Education education1 = educationDataBase.getEducation(education.getId());

            if(education.getGrade()!=0)
                education1.setGrade(education.getGrade());
            if(education.getActivity_Community()!= null)
                education1.setActivity_Community(education.getActivity_Community());
            if(education.getDescription()!=null)
                education1.setDescription(education.getDescription());
            if(education.getEndDate()!=null)
                education1.setEndDate(education.getEndDate());
            if(education.getStartDate()!=null)
                education1.setStartDate(education.getStartDate());
            if(education.getField()!=null)
                education1.setField(education.getField());
            if(education.getSchoolName()!=null)
                education1.setSchoolName(education.getSchoolName());

            educationDataBase.updateEducation(education1);
        }
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