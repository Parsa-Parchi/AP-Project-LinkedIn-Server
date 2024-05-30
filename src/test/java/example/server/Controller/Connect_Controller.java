package example.server.Controller;

import example.server.DataBase.Connect_DataBase;
import example.server.models.Connect;

import java.sql.SQLException;

public class Connect_Controller {
    private Connect connect;
    private Connect_DataBase connectDataBase;

    public void Connect_Controller(int id, String request_Sender, String request_Receiver, boolean accepted) throws SQLException {
        this.connect = new Connect(id,request_Sender,request_Receiver,accepted);
        this.connectDataBase = new Connect_DataBase();
    }

}
