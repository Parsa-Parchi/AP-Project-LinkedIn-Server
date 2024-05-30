package example.server.DataBase;

import java.sql.Connection;
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



}
