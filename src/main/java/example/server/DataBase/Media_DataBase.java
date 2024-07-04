package example.server.DataBase;

import example.server.models.Media;

import java.sql.*;
import java.util.ArrayList;

public class Media_DataBase {
    private final Connection connection;

    public Media_DataBase() throws SQLException {
        connection = SQLConnection.getConnection();
        createMediaTable();

    }
    private void createMediaTable() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Media("
        + "id INT AUTO_INCREMENT PRIMARY KEY,"
        + "post_id INT NOT NULL,"
        + "file_path VARCHAR(255) NOT NULL,"
        + "file_name VARCHAR(255) NOT NULL,"
        + "file_type VARCHAR(50),"
        + "file_size BIGINT,"
        + "upload_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
        + "FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");");


        preparedStatement.executeUpdate();
    }

    public void insertMedia(Media media) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Media (post_id, file_path, file_name, file_type, file_size) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, media.getPostId());
        preparedStatement.setString(2, media.getFilePath());
        preparedStatement.setString(3, media.getFileName());
        preparedStatement.setString(4, media.getFileType());
        preparedStatement.setLong(5, media.getFileSize());

        preparedStatement.executeUpdate();
    }

    public void deleteMedia(int id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Media WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    public void deleteMedia(Media media) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Media WHERE post_id = ? AND file_path = ? AND file_name = ? AND file_type = ? ");
        preparedStatement.setInt(1, media.getPostId());
        preparedStatement.setString(2, media.getFilePath());
        preparedStatement.setString(3, media.getFileName());
        preparedStatement.setString(4, media.getFileType());
        preparedStatement.executeUpdate();
    }

    public void deleteMedia(String filename,int postId) throws SQLException {
        PreparedStatement preparedStatement =connection.prepareStatement("DELETE FROM Media WHERE post_id = ? AND file_name = ?");
        preparedStatement.setInt(1, postId);
        preparedStatement.setString(2, filename);
        preparedStatement.executeUpdate();

    }

    public void deleteMedia(String fileName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Media WHERE file_name = ?");
        preparedStatement.setString(1, fileName);
        preparedStatement.executeUpdate();

    }

    public void deleteMediasOfPost(int postId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Media WHERE post_id = ? ");
        preparedStatement.setInt(1, postId);
        preparedStatement.executeUpdate();

    }

    public void deleteAllMedias() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Media");
        preparedStatement.executeUpdate();
    }

    public Media getMedia(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Media WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Media media = null;
        if (resultSet.next()) {
            int postId = resultSet.getInt("post_id");
            String filePath = resultSet.getString("file_path");
            String fileName = resultSet.getString("file_name");
            String fileType = resultSet.getString("file_type");
            long fileSize = resultSet.getLong("file_size");
            Timestamp uploadDate = resultSet.getTimestamp("upload_date");
            media =  new Media(postId, filePath, fileName, fileType, fileSize, uploadDate);


        }
        return media;
    }


    public Media getMedia(String fileName , int postId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Media WHERE file_name = ? AND post_id = ?");
        preparedStatement.setString(1, fileName);
        preparedStatement.setInt(2, postId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Media media = null;
        if (resultSet.next()) {

           media = new Media(resultSet.getInt("id"),
                   resultSet.getInt("post_id"),
                   resultSet.getString("file_path"),
                   resultSet.getString("file_name"),
                   resultSet.getString("file_type"),
                   resultSet.getLong("file_size"),
                   resultSet.getTimestamp("upload_date"));

        }
        return media;
    }

    public ArrayList<Media> getMediaOfPost(int postId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Media WHERE post_id = ?");
        preparedStatement.setInt(1, postId);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Media> mediaList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int post_id = resultSet.getInt("post_id");
            String filePath = resultSet.getString("file_path");
            String fileName = resultSet.getString("file_name");
            String fileType = resultSet.getString("file_type");
            long fileSize = resultSet.getLong("file_size");
            Timestamp uploadDate = resultSet.getTimestamp("upload_date");
            mediaList.add(new Media(id, post_id, filePath, fileName, fileType, fileSize, uploadDate));
        }
        return mediaList;
    }

    public ArrayList<Media> getAllMedia() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Media");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Media> mediaList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int postId = resultSet.getInt("post_id");
            String filePath = resultSet.getString("file_path");
            String fileName = resultSet.getString("file_name");
            String fileType = resultSet.getString("file_type");
            long fileSize = resultSet.getLong("file_size");
            Timestamp uploadDate = resultSet.getTimestamp("upload_date");

            mediaList.add(new Media(id, postId, filePath, fileName, fileType, fileSize, uploadDate));
        }
        return mediaList;
    }




}
