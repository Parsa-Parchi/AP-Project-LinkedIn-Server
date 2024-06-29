package example.server.models;

import java.sql.Timestamp;

public class Media_Post {
    private int id;
    private int postId;
    private String filePath;
    private String fileName;
    private String fileType;
    private long fileSize;
    private Timestamp uploadDate;

    public Media_Post(int id, int postId, String filePath, String fileName, String fileType, long fileSize, Timestamp uploadDate) {
        this.id = id;
        this.postId = postId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
    }

    public Media_Post(int postId, String fileName) {
        this.postId = postId;
        this.fileName = fileName;
    }

    public Media_Post(int postId, String filePath, String fileName, String fileType, long fileSize, Timestamp uploadDate) {
        this.postId = postId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
    }

    public Media_Post(int postId, String filePath, String fileName, String fileType, long fileSize) {
        this.postId = postId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
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

    public void setUserId(int userId) {
        this.postId = postId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

}
