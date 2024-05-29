package example.server.models;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int postId;
    private String email;
    private Timestamp commentDate;
    private String comment;

    public Comment(int id, int postId, String email, Timestamp commentDate, String comment) {
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.commentDate = commentDate;
        this.comment = comment;
    }

    public Comment(int postId, String email, String comment) {
        this.postId = postId;
        this.email = email;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

