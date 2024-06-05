package example.server.Controller;

import example.server.DataBase.Education_DataBase;
import example.server.models.Education;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Education_Controller {

    private Education_DataBase educationDataBase;

    public Education_Controller() throws SQLException {
        educationDataBase = new Education_DataBase();

    }

    public void insertEducation(Education education) throws SQLException {
        if(isValid(education))
            this.educationDataBase.insertEducation(education);
        else
            throw new IllegalArgumentException("Invalid education ! : school name or field is empty");
    }

    public void updateEducation(Education education) throws SQLException {
        if(isValid(education))
            this.educationDataBase.updateEducation(education);
        else
            throw new IllegalArgumentException("Invalid education ! : school name or field is empty");
    }

    public void deleteEducation(int id) throws SQLException {
        this.educationDataBase.deleteEducation(id);
    }

    public void deleteEducationOfUser(String email) throws SQLException {
        this.educationDataBase.deleteEducationOfUser(email);
    }

    public void deleteAllEducations() throws SQLException {
        this.educationDataBase.deleteAllEducations();
    }

    public ArrayList<Education> getAllEducations() throws SQLException {
        return this.educationDataBase.getAllEducations();
    }

    public ArrayList<Education> getAllEducationsOfUser(String email) throws SQLException {
        return this.educationDataBase.getAllEducationsOfUser(email);
    }

    public ArrayList<Education> getEducationOfField(String field) throws SQLException {
        return this.educationDataBase.getEducationOfField(field);
    }

    public ArrayList<Education> getEducationOfSchool(String school) throws SQLException {
        return this.educationDataBase.getEducationOfSchool(school);
    }

    public Education_DataBase getEducationDataBase() {
        return educationDataBase;
    }

    public void setEducationDataBase(Education_DataBase educationDataBase) {
        this.educationDataBase = educationDataBase;
    }

    public ArrayList<Education> getEducationOfActivityCommunity(String Activity_Community) throws SQLException {
        return this.educationDataBase.getEducationOfActivityCommunity(Activity_Community);
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