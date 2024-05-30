package example.server.Controller;

import example.server.DataBase.Skill_DataBase;
import example.server.models.Skill;

import java.sql.SQLException;

public class Skill_Controler {
    private Skill skill;
    private Skill_DataBase skillDataBase;
    public Skill_Controler(String skill_1, String skill_2, String skill_3, String skill_4, String skill_5,int id, String email) throws SQLException {
        this.skill = new Skill(skill_1,skill_2,skill_3,skill_4,skill_5,id,email);
        this.skillDataBase = new Skill_DataBase();
        skillDataBase.createSkillTable();
    }
    public void insertSkill() throws SQLException {
        this.skillDataBase.insertSkill(this.skill);
    }
    public void updateSkill() throws SQLException {
        this.skillDataBase.updateSkill(this.skill);
    }
    public void deleteSkill() throws SQLException {
        this.skillDataBase.deleteSkill(this.skill);
    }
    public void deleteSkillByEmail(String email) throws SQLException {
        this.skillDataBase.deleteSkillByEmail(email);
    }
    public void deleteAllSkills() throws SQLException {
        skillDataBase.deleteAllSkills();
    }
    public Skill getSkill() {
        return skill;
    }
}
