package example.server.models;


public class Like {
    private int id;
    private int postId;
    private String email;
    private String likeTime;



    public Like(int id, int postId, String email, String likeTime) {
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.likeTime = likeTime;
    }

    public Like(int postId, String email) {
        this.postId = postId;
        this.email = email;
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

    public String getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(String likeTime) {
        this.likeTime = likeTime;
    }
}
