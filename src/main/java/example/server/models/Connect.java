package example.server.models;

import java.sql.Timestamp;

public class Connect {
    private int id;
    private String Request_Sender;
    private String Request_Receiver;
    private boolean accepted;
    private String notes;
    private Timestamp request_date;

    public Connect(int id, String request_Sender, String request_Receiver, boolean accepted , String notes, Timestamp request_date)  {
        this.id = id;
        Request_Sender = request_Sender;
        Request_Receiver = request_Receiver;
        this.accepted = accepted;
        this.notes = notes;
        this.request_date = request_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Connect(String request_Sender, String request_Receiver , String notes) {
        Request_Sender = request_Sender;
        Request_Receiver = request_Receiver;
        this.notes=notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest_Sender() {
        return Request_Sender;
    }

    public void setRequest_Sender(String request_Sender) {
        Request_Sender = request_Sender;
    }

    public String getRequest_Receiver() {
        return Request_Receiver;
    }

    public void setRequest_Receiver(String request_Receiver) {
        Request_Receiver = request_Receiver;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
