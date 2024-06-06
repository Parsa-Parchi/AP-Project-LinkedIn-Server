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
        if(isValid(contactInformation))
            contact_info_db.updateContact(contactInformation);
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
        return viewLink != null && !viewLink.isEmpty();
    }

    public static boolean Mobile_phoneNumberValidator(String phoneNumber) {
        return phoneNumber != null && NUMBER_FORMAT.matcher(phoneNumber).matches();
    }

    public static boolean Home_phoneNumberValidator(String phoneNumber) {
        return  NUMBER_FORMAT.matcher(phoneNumber).matches();
    }

    public static boolean Work_phoneNumberValidator(String phoneNumber) {
        return  NUMBER_FORMAT.matcher(phoneNumber).matches();
    }

    public static boolean isValid(ContactInformation contact) {
        if(!contact.getHome_PhoneNumber().isEmpty() && !contact.getWorkplace_PhoneNumber().isEmpty())
        {
            return viewLinkValidator(contact.getViewLink()) && Mobile_phoneNumberValidator(contact.getMobile_PhoneNumber())
                    && Home_phoneNumberValidator(contact.getHome_PhoneNumber()) && Work_phoneNumberValidator(contact.getWorkplace_PhoneNumber());
        }

        else if(contact.getHome_PhoneNumber().isEmpty() && !contact.getWorkplace_PhoneNumber().isEmpty())
        {
            return viewLinkValidator(contact.getViewLink()) && Mobile_phoneNumberValidator(contact.getMobile_PhoneNumber())
                     && Work_phoneNumberValidator(contact.getWorkplace_PhoneNumber());
        }

        else if(contact.getWorkplace_PhoneNumber().isEmpty() && !contact.getHome_PhoneNumber().isEmpty())
        {
            return viewLinkValidator(contact.getViewLink()) && Mobile_phoneNumberValidator(contact.getMobile_PhoneNumber())
                    && Home_phoneNumberValidator(contact.getHome_PhoneNumber());
        }
        else
            return viewLinkValidator(contact.getViewLink()) && Mobile_phoneNumberValidator(contact.getMobile_PhoneNumber());
    }
}
