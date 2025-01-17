package example.server.models;

public class Hashtag {
    private int id;
    private int postId;
    private String hashtag;

    public Hashtag(int postId, String hashtag) {
        this.postId = postId;
        this.hashtag = hashtag;
    }

    public Hashtag(int id, int postId, String hashtag) {
        this.id = id;
        this.postId = postId;
        this.hashtag = hashtag;
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

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
