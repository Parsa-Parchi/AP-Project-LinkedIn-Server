package example.server.DataBase;

import example.server.models.Education;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Education_DataBase {

    private final Connection connection ;

    public Education_DataBase() throws SQLException{
        connection = SQLConnection.getConnection();
        createEducationTable();
    }
    public void createEducationTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS education ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "school_name VARCHAR(40) NOT NULL,"
                + "field VARCHAR(40) NOT NULL,"
                + "grade FLOAT NOT NULL,"
                + "start_date DATE NOT NULL,"
                + "end_date DATE,"
                + "Activity_Community VARCHAR(500),"
                + "description VARCHAR(1000),"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();
    }

    public void insertEducation(Education education) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO education (email, school_name, field, grade, start_date, end_date, Activity_Community, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, education.getEmail());
        statement.setString(2, education.getSchoolName());
        statement.setString(3, education.getField());
        statement.setDouble(4, education.getGrade());
        statement.setDate(5, (Date) education.getStartDate());
        statement.setDate(6, (Date) education.getEndDate());
        statement.setString(7,education.getActivity_Community());
        statement.setString(8,education.getDescription());
        statement.executeUpdate();

    }

    public void updateEducation(Education education) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE education "
                + "SET school_name = ?, field = ?, grade = ?, start_date = ?, end_date = ?, Activity_Community = ?, description = ?"
                + "WHERE email = ?");

        statement.setString(1, education.getSchoolName());
        statement.setString(2, education.getField());
        statement.setDouble(3, education.getGrade());
        statement.setDate(4, (Date) education.getStartDate());
        statement.setDate(5, (Date) education.getEndDate());
        statement.setString(6, education.getActivity_Community());
        statement.setString(7, education.getDescription());
        statement.setString(8, education.getEmail());
        statement.executeUpdate();

    }

    public void deleteEducation(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM education WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();

    }

    public void deleteEducationOfUser(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM education WHERE email = ?");
        statement.setString(1, email);
        statement.executeUpdate();
    }

    public void deleteAllEducations() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM education");
        statement.executeUpdate();

    }




}
