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
}
