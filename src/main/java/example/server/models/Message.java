package example.server.models;

import java.sql.Timestamp;

public class Message {
    private int id ;
    private String text ;
    private String Sender;
    private String Receiver ;
    private Timestamp timestamp ;
    private String mediaUrl;


    public Message(int id, String text, String sender, String receiver, Timestamp timestamp, String mediaUrl) {
        this.id = id;
        this.text = text;
        Sender = sender;
        Receiver = receiver;
        this.timestamp = timestamp;
        this.mediaUrl = mediaUrl;
    }

    public Message(int id, String text, String sender, String receiver, Timestamp timestamp) {
        this.id = id;
        this.text = text;
        Sender = sender;
        Receiver = receiver;
        this.timestamp = timestamp;
    }

    public Message(String text, String sender, String receiver, Timestamp timestamp) {
        this.text = text;
        Sender = sender;
        Receiver = receiver;
        this.timestamp = timestamp;
    }



    public Message(String text , String sender, String receiver, String mediaUrl) {
        Sender = sender;
        Receiver = receiver;
        this.text = text;
        this.mediaUrl = mediaUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return Sender;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
