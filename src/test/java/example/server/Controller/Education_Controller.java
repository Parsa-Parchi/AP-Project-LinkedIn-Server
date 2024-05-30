package example.server.Controller;

import example.server.DataBase.Education_DataBase;
import example.server.models.Education;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Date;

public class Education_Controller {
    private Education education;
    private Education_DataBase educationDataBase;
    public void Education_Controller(String email, String schoolName, String field, double grade, Data startDate) throws SQLException {
        this.education = new Education(email,schoolName,field,grade, (Date) startDate);
        educationDataBase = new Education_DataBase();
        educationDataBase.createEducationTable();
    }
}
