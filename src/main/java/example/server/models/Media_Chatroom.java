package example.server.models;

import java.sql.Timestamp;

public class Media_Chatroom {
    private int id;
    private String sender;
    private String receiver;
    private String media_url;
    private Timestamp timestamp;

    public Media_Chatroom(String sender, String receiver, String media_url) {
        this.sender = sender;
        this.receiver = receiver;
        this.media_url = media_url;
    }

    public Media_Chatroom(int id, String sender, String receiver, String media_url, Timestamp timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.media_url = media_url;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
