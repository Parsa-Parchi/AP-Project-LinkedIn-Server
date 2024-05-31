package example.server.Controller;

import example.server.DataBase.Skill_DataBase;
import example.server.models.Skill;

import java.sql.SQLException;

public class Skill_Controller {

    private Skill_DataBase skillDataBase;

    public Skill_DataBase getSkillDataBase() {
        return skillDataBase;
    }

    public void setSkillDataBase(Skill_DataBase skillDataBase) {
        this.skillDataBase = skillDataBase;
    }

    public Skill_Controller() throws SQLException{

        skillDataBase = new Skill_DataBase();
    }

    public void insertSkill(Skill skill) throws SQLException {
        this.skillDataBase.insertSkill(skill);
    }

    public void updateSkill(Skill skill) throws SQLException {
        this.skillDataBase.updateSkill(skill);
    }

    public void deleteSkill(Skill skill) throws SQLException {
        this.skillDataBase.deleteSkill(skill);
    }

    public void deleteSkillByEmail(String email) throws SQLException {
        this.skillDataBase.deleteSkillByEmail(email);
    }

    public void deleteAllSkills() throws SQLException {
        skillDataBase.deleteAllSkills();
    }

    public Skill getSkillOfUser(String Email) throws SQLException {
        return skillDataBase.getSkill(Email);
    }
}
