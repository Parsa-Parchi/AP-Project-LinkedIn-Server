package example.server.Controller;

import example.server.DataBase.Skill_DataBase;
import example.server.models.Skill;

import java.sql.SQLException;

public class Skill_Controller {

    private static Skill_DataBase skillDataBase;


    static {
        try {
            skillDataBase = new Skill_DataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertSkill(Skill skill) throws SQLException {
        skillDataBase.insertSkill(skill);
    }

    public static void updateSkill(Skill skill) throws SQLException {
        skillDataBase.updateSkill(skill);
    }

    public static void deleteSkill(Skill skill) throws SQLException {
        skillDataBase.deleteSkill(skill);
    }

    public static void deleteSkillByEmail(String email) throws SQLException {
        skillDataBase.deleteSkillByEmail(email);
    }

    public static void deleteAllSkills() throws SQLException {
        skillDataBase.deleteAllSkills();
    }

    public static Skill getSkillOfUser(String Email) throws SQLException {
        return skillDataBase.getSkill(Email);
    }

}