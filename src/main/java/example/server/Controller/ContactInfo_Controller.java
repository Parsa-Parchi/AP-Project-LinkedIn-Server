package example.server.Controller;

import example.server.DataBase.ContactInformation_DataBase;
import example.server.models.ContactInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ContactInfo_Controller {
    private static final Pattern NUMBER_FORMAT = Pattern.compile("^\\+?[0-9. ()-]{7,15}$");
    private static ContactInformation_DataBase contact_info_db ;

    static {
        try {
            contact_info_db = new ContactInformation_DataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void insertContactInfo(ContactInformation contactInformation) throws SQLException {
            contact_info_db.insertContact(contactInformation);
    }

    public static void updateContactInfo(ContactInformation contactInformation) throws SQLException {
        if(isValid(contactInformation)) {
            ContactInformation contactInformation1 = contact_info_db.getContactInfoOfUser(contactInformation.getEmail());
            if(contactInformation.getWorkplace_PhoneNumber()!=null)
                contactInformation1.setWorkplace_PhoneNumber(contactInformation.getWorkplace_PhoneNumber());
            if(contactInformation.getHome_PhoneNumber()!=null)
                contactInformation1.setHome_PhoneNumber(contactInformation.getHome_PhoneNumber());
            if(contactInformation.getMobile_PhoneNumber()!=null)
                contactInformation1.setMobile_PhoneNumber(contactInformation.getMobile_PhoneNumber());
            if(contactInformation.getAccess_level()!=null)
                contactInformation1.setAccess_level(contactInformation.getAccess_level());
            if(contactInformation.getAddress()!=null)
                contactInformation1.setAddress(contactInformation.getAddress());
            if (contactInformation.getBirthDate()!=null)
                contactInformation1.setBirthDate(contactInformation.getBirthDate());
            if(contactInformation.getViewLink()!=null)
                contactInformation1.setViewLink(contactInformation.getViewLink());
            if(contactInformation.getFastConnect()!=null)
                contactInformation1.setFastConnect(contactInformation.getFastConnect());

            contact_info_db.updateContact(contactInformation1);
        }
        else
            throw new IllegalArgumentException("Contact information is not valid : view link is null or phone number is not valid " );
    }

    public static void deleteContactInfoById(int id) throws SQLException {
        contact_info_db.deleteContact(id);
    }

    public static void deleteContactInfoByEmail(String email) throws SQLException {
        contact_info_db.deleteContactOfUser(email);
    }

    public static void deleteAllContactInfo() throws SQLException {
        contact_info_db.deleteAllContacts();
    }

    public static ArrayList<ContactInformation> getAllContactInfo() throws SQLException {
        return contact_info_db.getAllContactInfo();
    }

    public static ContactInformation getContactInfoOfUser(String email) throws SQLException {
        return contact_info_db.getContactInfoOfUser(email);
    }

    public static ContactInformation getContactInfoById(int id) throws SQLException {
        return contact_info_db.getContactInfoById(id);
    }


    public static boolean viewLinkValidator(String viewLink) {
        return  !viewLink.isEmpty();
    }

    public static boolean phoneNumberValidator(String phoneNumber) {
        return   NUMBER_FORMAT.matcher(phoneNumber).matches();
    }

    public static boolean isValid(ContactInformation contact) {

        boolean valid = true;
        if(contact.getWorkplace_PhoneNumber()!=null)
            valid =  phoneNumberValidator(contact.getWorkplace_PhoneNumber());
        if(contact.getHome_PhoneNumber()!=null)
            valid = valid && phoneNumberValidator(contact.getHome_PhoneNumber());
        if(contact.getMobile_PhoneNumber()!=null)
            valid = valid && phoneNumberValidator(contact.getMobile_PhoneNumber());
        if(contact.getViewLink()!=null)
            valid = valid && phoneNumberValidator(contact.getViewLink());

        return valid;

    }
}
