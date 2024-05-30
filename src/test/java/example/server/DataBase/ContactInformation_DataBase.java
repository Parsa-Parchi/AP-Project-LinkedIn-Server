package example.server.DataBase;

import example.server.models.ContactInformation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class ContactInformation_DataBase {

    private Connection connection;

    public ContactInformation_DataBase() throws SQLException {
        connection  = SQLConnection.getConnection();
        createContactInformationTable();

    }

    private void createContactInformationTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS contact ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) UNIQUE NOT NULL,"
                + "view_link VARCHAR(40) NOT NULL,"
                + "Mobile_PhoneNumber VARCHAR(40) NOT NULL,"
                + "Home_PhoneNumber VARCHAR(40) ,"
                + "Workplace_PhoneNumber VARCHAR(40) ,"
                + "address VARCHAR(220),"
                + "birth_date DATE,"
                + "fast_connect VARCHAR(40),"
                + "access_level VARCHAR(40),"
                + "FOREIGN KEY (email) REFERENCES users (email) ON DELETE CASCADE"
                + ");");

        statement.executeUpdate();
    }

    public void insertContact(ContactInformation contact) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO contact (email, view_link, Mobile_PhoneNumber, Home_PhoneNumber, Workplace_PhoneNumber, address, birth_date, fast_connect, access_level) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, contact.getEmail());
        statement.setString(2, contact.getViewLink());
        statement.setString(3,contact.getMobile_PhoneNumber());
        statement.setString(4,contact.getHome_PhoneNumber());
        statement.setString(5,contact.getWorkplace_PhoneNumber());
        statement.setString(6,contact.getAddress());
        statement.setDate(7, (Date) contact.getBirthDate());
        statement.setString(8,contact.getFastConnect());
        statement.setString(9,contact.getAccess_level());
        statement.executeUpdate();
    }

    public void updateContact(ContactInformation contact) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE contact"
                + "SET view_link = ?, Mobile_PhoneNumber = ?, Home_PhoneNumber = ?, Workplace_PhoneNumber = ?, address = ?, birth_date = ?, fast_connect = ? , access_level = ?,WHERE email = ?");

        statement.setString(1, contact.getViewLink());
        statement.setString(2, contact.getMobile_PhoneNumber());
        statement.setString(3, contact.getHome_PhoneNumber());
        statement.setString(4, contact.getWorkplace_PhoneNumber());
        statement.setString(5, contact.getAddress());
        statement.setDate(6, (Date) contact.getBirthDate());
        statement.setString(7, contact.getFastConnect());
        statement.setString(8, contact.getAccess_level());
        statement.setString(9, contact.getEmail());
        statement.executeUpdate();

    }

    public void deleteContactOfUser(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM contact WHERE email = ?");
        statement.setString(1, email);
        statement.executeUpdate();

    }

    public void deleteContact(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM contact WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void deleteAllContacts() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM contact");
        statement.executeUpdate();

    }






}
