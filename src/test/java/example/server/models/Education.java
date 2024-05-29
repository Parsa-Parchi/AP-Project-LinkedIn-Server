package example.server.models;

import java.util.Date;

public class Education {
    private int id;
    private String email;
    private String schoolName;
    private String field;
    private double grade;
    private Date startDate;
    private Date endDate;
    private String Activity_Community;
    private String description;

    public Education(String email, String schoolName, String field, double grade, Date startDate) {
        this.email = email;
        this.schoolName = schoolName;
        this.field = field;
        this.grade = grade;
        this.startDate = startDate;
    }

    public Education(int id, String email, String schoolName, String field, double grade, Date startDate,Date endDate, String Activity_Community, String description) {
        this.id = id;
        this.email = email;
        this.schoolName = schoolName;
        this.field = field;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Activity_Community = Activity_Community;
        this.description = description;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getActivity_Community() {
        return Activity_Community;
    }

    public void setActivity_Community(String activity_Community) {
        Activity_Community = activity_Community;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
