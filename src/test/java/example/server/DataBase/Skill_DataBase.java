package example.server.DataBase;

import example.server.models.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void insertSkill(Skill skill) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO skills (email, skill_1, skill_2, skill_3, skill_4, skill_5) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setString(1, skill.getEmail());
        statement.setString(2, skill.getSkill_1());
        statement.setString(3, skill.getSkill_2());
        statement.setString(4, skill.getSkill_3());
        statement.setString(5, skill.getSkill_4());
        statement.setString(6, skill.getSkill_5());
        statement.executeUpdate();

    }

    public void updateSkill(Skill skill) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE skills SET skill_1 = ?, skill_2 = ?, skill_3 = ?, skill_4 = ?, skill_5 = ? WHERE email = ?");
        statement.setString(1, skill.getSkill_1());
        statement.setString(2, skill.getSkill_2());
        statement.setString(3, skill.getSkill_3());
        statement.setString(4, skill.getSkill_4());
        statement.setString(5, skill.getSkill_5());
        statement.setString(6, skill.getEmail());
        statement.executeUpdate();

    }

    public void deleteSkill(Skill skill) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setInt(1, skill.getId());
        statement.executeUpdate();

    }

    public void deleteAllSkills() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM skills");
        statement.executeUpdate();

    }

    public Skill getSkill(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM skills WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String skill_1 = resultSet.getString("skill_1");
            String skill_2 = resultSet.getString("skill_2");
            String skill_3 = resultSet.getString("skill_3");
            String skill_4 = resultSet.getString("skill_4");
            String skill_5 = resultSet.getString("skill_5");

            return new Skill(skill_1,skill_2,skill_3,skill_4,skill_5,id,email);
        }
        return null;
    }


}
