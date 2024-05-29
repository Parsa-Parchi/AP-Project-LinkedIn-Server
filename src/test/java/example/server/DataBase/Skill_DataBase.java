package example.server.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Skill_DataBase {

    private final Connection connection;
    public Skill_DataBase() throws SQLException{
        connection = SQLConnection.getConnection();
        createSkillTable();
    }

    public void createSkillTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS skills ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "skill_1 VARCHAR(40),"
                + "skill_2 VARCHAR(40),"
                + "skill_3 VARCHAR(40),"
                + "skill_4 VARCHAR(40),"
                + "skill_5 VARCHAR(40),"
                + "FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE"
                + ");");
        statement.executeUpdate();
    }
}
