package example.server.models;

import java.util.ArrayList;

public class Profile {
    private User user;
    private Skill skill;
    private ContactInformation contactInformation;
    private ArrayList<Education> educations;


    public Profile(User user, Skill skill, ContactInformation contactInformation, ArrayList<Education> educations) {
        this.user = user;
        this.skill = skill;
        this.contactInformation = contactInformation;
        this.educations = educations;
    }

    public Profile(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public ArrayList<Education> getEducations() {
        return educations;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }
}
