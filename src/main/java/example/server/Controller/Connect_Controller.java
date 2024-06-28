package example.server.Controller;

import example.server.DataBase.Connect_DataBase;
import example.server.models.Connect;

import java.sql.SQLException;
import java.util.ArrayList;

public class Connect_Controller {

    private static Connect_DataBase connectDataBase;

    static {
        try {
            connectDataBase = new Connect_DataBase();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertConnect(Connect conn) throws SQLException {
        connectDataBase.insertConnect(conn);
    }

    public static void updateConnect(Connect conn) throws SQLException {
        connectDataBase.updateConnect(conn);
    }

    public static void deleteConnect(int id) throws SQLException {
        connectDataBase.deleteConnect(id);
    }

    public static void deleteConnect(Connect conn) throws SQLException {
        connectDataBase.deleteConnect(conn);
    }

    public static void deleteConnectOfSender(String sender) throws SQLException {
        connectDataBase.deleteConnectOfSender(sender);
    }

    public static void deleteConnectOfReceiver(String receiver) throws SQLException {
        connectDataBase.deleteConnectOfReceiver(receiver);
    }

    public static void deleteConnectOfAccepted(boolean accepted) throws SQLException {
        connectDataBase.deleteConnectOfAccepted(accepted);
    }

    public static void deleteAllConnects() throws SQLException {
        connectDataBase.deleteAllConnects();
    }

    public static ArrayList<Connect> getAllConnects() throws SQLException {
        return connectDataBase.getAllConnects();
    }

    public static ArrayList<Connect> getConnectionsOfSender(String sender) throws SQLException {
        return connectDataBase.getConnectionsOfSender(sender);
    }

    public static ArrayList<Connect> getConnectionsOfReceiver(String receiver) throws SQLException {
        return connectDataBase.getConnectionsOfReceiver(receiver);
    }

    public static ArrayList<Connect> getConnectionsOfAccepted(boolean accepted) throws SQLException {
        return connectDataBase.getConnectionsOfAccepted(accepted);
    }

    public static Connect getConnectionOfSenderAndReceiver(String sender, String receiver) throws SQLException {
        return connectDataBase.getConnectionOfSenderAndReceiver(sender, receiver);
    }
    public static Connect getConnectionOfSenderAndReceiver(String sender ,String receiver , boolean accepted) throws SQLException{
        return connectDataBase.getConnectionOfSenderAndReceiver(sender,receiver,accepted);
    }
    public static ArrayList<Connect> getAcceptedConnectionsOfSender(String sender ,boolean accepted) throws SQLException{
        return connectDataBase.getAcceptedConnectionsOfSender(sender,accepted);
    }
    public static ArrayList<Connect> getAcceptedConnectionsOfReceiver(String receiver ,boolean accepted) throws SQLException{
        return connectDataBase.getAcceptedConnectionsOfReceiver(receiver,accepted);
    }

}
