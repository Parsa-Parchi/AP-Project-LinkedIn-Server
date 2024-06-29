package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Media_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.models.Media_Post;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MediaOfPost_Handler {
    private static final Gson gson = new Gson();
    private static final String UPLOAD_DIRECTORY = "uploads";



    public static void UploadMedia(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[2]);

        try {

            List<FileItem> formItems = upload.parseRequest(new HttpExchangeRequestContext(exchange));
            if (formItems != null && !formItems.isEmpty()) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = UPLOAD_DIRECTORY + File.separator + fileName;
                        File storeFile = new File(filePath);
                        new File(storeFile.getParent()).mkdirs();
                        item.write(storeFile);

                        Media_Post media_post = new Media_Post( postId, filePath, fileName, item.getContentType(), item.getSize());
                        Media_Controller.insertMedia(media_post);
                        Server.sendResponse(exchange,200,"File uploaded successfully: " + filePath);
                    }
                }

            }
            else {
                Server.sendResponse(exchange, 400, "No file uploaded");
            }


        }
        catch(SQLException e)
        {
            Server.sendResponse(exchange,500,"problem in database ");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error : " + e.getMessage());
        }
    }

    public static void RetrieveMedia(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] url = exchange.getRequestURI().getPath().split("/");
        String requestedFile = url[3];
        int postId = Integer.parseInt(url[2]);

        Media_Post media_post = null;
        try {
            media_post = Media_Controller.getMedia(requestedFile,postId);
            if (media_post == null) {
                Server.sendResponse(exchange, 404, "Media not found in Database");
            }
            else {
                String filePath = media_post.getFilePath();
                File file = new File(filePath);
                if (file.exists() && !file.isDirectory()) {
                    String mimeType = Files.probeContentType(Paths.get(filePath));
                    exchange.getResponseHeaders().set("Content-Type", mimeType);
                    exchange.sendResponseHeaders(200, file.length());

                    try (OutputStream os = exchange.getResponseBody(); FileInputStream fis = new FileInputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
                else {
                    Server.sendResponse(exchange, 404, "File not found in Directory");
                }

            }

        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "problem in database ");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error : " + e.getMessage());
        }

    }


    public static void deleteMedia(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] url = exchange.getRequestURI().getPath().split("/");
        String requestedFile = url[3];
        int postId = Integer.parseInt(url[2]);
        try {
            Media_Controller.deleteMedia(requestedFile,postId);
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "problem in database ");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error : " + e.getMessage());
        }
    }


    public static void deleteMediasOfPost(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[2]);
        try {
            Media_Controller.deleteMediasOfPost(postId);
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "problem in database ");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error : " + e.getMessage());
        }
    }


}
