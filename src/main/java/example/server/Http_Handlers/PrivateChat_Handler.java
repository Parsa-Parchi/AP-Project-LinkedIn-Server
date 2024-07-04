package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.PrivateChat_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.TimestampAdapter;
import example.server.Utilities.jwt_Util;
import example.server.models.Message;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class PrivateChat_Handler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Timestamp.class, new TimestampAdapter())
            .create();
    private static final String UPLOAD_DIRECTORY = "private_chat_uploads";

    public static void messageUpload(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfToken = jwt_Util.parseToken(token);

        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Check if the request is a multipart/form-data request
        if (ServletFileUpload.isMultipartContent(new HttpExchangeRequestContext(exchange))) {

            try {


                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // Configure a repository (to ensure a secure temp location is used)
                File repository = new File(System.getProperty("java.io.tmpdir"));
                factory.setRepository(repository);

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Parse the request
                List<FileItem> items = upload.parseRequest(new HttpExchangeRequestContext(exchange));

                // Variables to hold parsed values
                String mediaUrl = null;
                String text = null;
                String sender = null;
                String receiver = null;

                for (FileItem item : items) {
                    if (item.isFormField()) {
                        // Process regular form field
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getString();
                        switch (fieldName) {
                            case "text":
                                text = fieldValue;
                                break;
                            case "Sender":
                                sender = fieldValue;
                                if(!sender.equals(EmailOfToken)) {
                                    Server.sendResponse(exchange,409,"Invalid Sender");
                                }
                                break;
                            case "Receiver":
                                receiver = fieldValue;
                                break;
                        }
                    } else {
                        // Process form file field
                        String fileName = new File(item.getName()).getName();
                        String filePath = UPLOAD_DIRECTORY + File.separator + fileName;
                        File uploadedFile = new File(filePath);

                        if (!uploadedFile.exists()) {
                            // Save the file on disk
                            try (InputStream input = item.getInputStream();
                                 OutputStream output = new FileOutputStream(uploadedFile)) {
                                IOUtils.copy(input, output);
                            }
                        }

                        // Set the media URL to be returned or saved in the message
                        mediaUrl = UPLOAD_DIRECTORY + File.separator + fileName;
                    }
                }

                Message message = new Message(text, sender, receiver, mediaUrl);

                PrivateChat_Controller.insertMessage(message);
                Server.sendResponse(exchange, 200, null);

            }
            catch (SQLException e) {
                Server.sendResponse(exchange, 500, "Database error : " + e.getMessage());
            }
            catch (Exception e) {
                Server.sendResponse(exchange, 500, " internal Server error : " + e.getMessage());
            }
        }

    }



//    private static String extractFileName(String part) {
//        String[] contentDispositionParts = part.split(";");
//        for (String contentDispositionPart : contentDispositionParts) {
//            if (contentDispositionPart.trim().startsWith("filename")) {
//                return contentDispositionPart.split("=")[1].trim().replace("\"", "");
//            }
//        }
//        return UUID.randomUUID().toString();
//    }
//
//    private static String extractFormField(String part) {
//        int valueStartIndex = part.indexOf("\r\n\r\n") + 4;
//        return part.substring(valueStartIndex).trim();
//    }
//
//
//    // Extract the boundary from the Content-Type header
//    private static String getBoundary(String contentType) {
//        String[] params = contentType.split(";");
//        for (String param : params) {
//            if (param.trim().startsWith("boundary")) {
//                return param.split("=")[1];
//            }
//        }
//        return null;
//    }
//


    public static void RetrieveMessages(HttpExchange exchange) throws IOException {

        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfToken = jwt_Util.parseToken(token);

        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        String Email1 = pathParts[pathParts.length - 1];
        String Email2 = pathParts[pathParts.length - 2];

        try {
            if(!EmailOfToken.equals(Email1) && !EmailOfToken.equals(Email2)) {
                Server.sendResponse(exchange, 409, "Invalid Email");
            }
            ArrayList<Message> messages = PrivateChat_Controller.getHistory(Email1,Email2);
            String response = gson.toJson(messages);
            // Set response headers
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            // Write JSON response
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error");
        }

    }



    public static void deleteMessage(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        int messageId = Integer.parseInt(pathParts[pathParts.length - 1]);

        try {
            PrivateChat_Controller.deleteMessage(messageId);
            Server.sendResponse(exchange, 200, "message deleted successfully");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error");
        }

    }


    public static void deleteHistory(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfToken = jwt_Util.parseToken(token);


        String query = exchange.getRequestURI().getQuery();
        HashMap<String,String> queryParams = (HashMap<String, String>) exchange.getAttribute("requestParams");
        String user1 = queryParams.get("Email1");
        String user2 = queryParams.get("Email2");

        try {
            if(!EmailOfToken.equals(user1) && !EmailOfToken.equals(user2)) {
                Server.sendResponse(exchange, 409, "Invalid Email");
            }

            PrivateChat_Controller.deleteHistory(user1 ,user2 );
            Server.sendResponse(exchange, 200, "History deleted successfully");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error");
        }
    }


}

