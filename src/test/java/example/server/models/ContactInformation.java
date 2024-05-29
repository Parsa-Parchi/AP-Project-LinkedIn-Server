package example.server.models;

import java.util.Date;

public class ContactInformation {
    private int id;
    private String email;
    private String viewLink;
    private String Mobile_PhoneNumber;
    private String Home_PhoneNumber;
    private String Workplace_PhoneNumber;
    private String address;
    private Date birthDate;
    private String fastConnect;
    private String visibility;


    public ContactInformation(String email, String viewLink, String mobile_PhoneNumber) {
        this.email = email;
        this.viewLink = viewLink;
        Mobile_PhoneNumber = mobile_PhoneNumber;
    }

    public ContactInformation(int id, String email, String viewLink, String mobile_PhoneNumber, String home_PhoneNumber, String workplace_PhoneNumber, String address, Date birthDate, String fastConnect, String visibility) {
        this.id = id;
        this.email = email;
        this.viewLink = viewLink;
        Mobile_PhoneNumber = mobile_PhoneNumber;
        Home_PhoneNumber = home_PhoneNumber;
        Workplace_PhoneNumber = workplace_PhoneNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.fastConnect = fastConnect;
        this.visibility = visibility;
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

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getMobile_PhoneNumber() {
        return Mobile_PhoneNumber;
    }

    public void setMobile_PhoneNumber(String mobile_PhoneNumber) {
        Mobile_PhoneNumber = mobile_PhoneNumber;
    }

    public String getHome_PhoneNumber() {
        return Home_PhoneNumber;
    }

    public void setHome_PhoneNumber(String home_PhoneNumber) {
        Home_PhoneNumber = home_PhoneNumber;
    }

    public String getWorkplace_PhoneNumber() {
        return Workplace_PhoneNumber;
    }

    public void setWorkplace_PhoneNumber(String workplace_PhoneNumber) {
        Workplace_PhoneNumber = workplace_PhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFastConnect() {
        return fastConnect;
    }

    public void setFastConnect(String fastConnect) {
        this.fastConnect = fastConnect;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

}
