package example.server.Controller;

import example.server.DataBase.Connect_DataBase;
import example.server.models.Connect;

import java.sql.SQLException;
import java.util.ArrayList;

public class Connect_Controller {
    private Connect conn;
    private Connect_DataBase connectDataBase;

    public void Connect_Controller(int id, String request_Sender, String request_Receiver, boolean accepted) throws SQLException {
        this.conn = new Connect(id, request_Sender, request_Receiver, accepted);
        this.connectDataBase = new Connect_DataBase();
    }

    public void insertConnect() throws SQLException {
        this.connectDataBase.insertConnect(this.conn);
    }

    public void deleteConnect(int id) throws SQLException {
        this.connectDataBase.deleteConnect(id);
    }

    public void deleteConnect(Connect conn) throws SQLException {
        this.connectDataBase.deleteConnect(conn);
    }

    public void deleteConnectOfSender(String sender) throws SQLException {
        this.connectDataBase.deleteConnectOfSender(sender);
    }

    public void deleteConnectOfReceiver(String receiver) throws SQLException {
        this.connectDataBase.deleteConnectOfReceiver(receiver);
    }

    public void deleteConnectOfAccepted(boolean accepted) throws SQLException {
        this.connectDataBase.deleteConnectOfAccepted(accepted);
    }

    public void deleteAllConnects() throws SQLException {
        this.connectDataBase.deleteAllConnects();
    }

    public ArrayList<Connect> getAllConnects() throws SQLException {
        return this.connectDataBase.getAllConnects();
    }

    public ArrayList<Connect> getConnectionsOfSender(String sender) throws SQLException {
        return this.connectDataBase.getConnectionsOfSender(sender);
    }

    public ArrayList<Connect> getConnectionsOfReceiver(String receiver) throws SQLException {
        return this.connectDataBase.getConnectionsOfReceiver(receiver);
    }

    public ArrayList<Connect> getConnectionsOfAccepted(boolean accepted) throws SQLException {
        return this.connectDataBase.getConnectionsOfAccepted(accepted);
    }

    public Connect getConnectionOfSenderAndReceiver(String sender, String receiver) throws SQLException {
        return this.connectDataBase.getConnectionOfSenderAndReceiver(sender, receiver);
    }

}
