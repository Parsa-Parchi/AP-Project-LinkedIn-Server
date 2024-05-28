package example.server.models;

public class Skill {

    private String Skill_1;
    private String skill_2;
    private String skill_3;
    private String skill_4;
    private String skill_5;
    private int id;
    private String email;



    public Skill(String skill_1, String skill_2, String skill_3, String skill_4, String skill_5, int id, String email) {
        Skill_1 = skill_1;
        this.skill_2 = skill_2;
        this.skill_3 = skill_3;
        this.skill_4 = skill_4;
        this.skill_5 = skill_5;
        this.id = id;
        this.email = email;
    }

    public String getSkill_1() {
        return Skill_1;
    }

    public void setSkill_1(String skill_1) {
        Skill_1 = skill_1;
    }

    public String getSkill_2() {
        return skill_2;
    }

    public void setSkill_2(String skill_2) {
        this.skill_2 = skill_2;
    }

    public String getSkill_3() {
        return skill_3;
    }

    public void setSkill_3(String skill_3) {
        this.skill_3 = skill_3;
    }

    public String getSkill_4() {
        return skill_4;
    }

    public void setSkill_4(String skill_4) {
        this.skill_4 = skill_4;
    }

    public String getSkill_5() {
        return skill_5;
    }

    public void setSkill_5(String skill_5) {
        this.skill_5 = skill_5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

