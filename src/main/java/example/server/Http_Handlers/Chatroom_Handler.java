package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.MediaOfChatroom_Controller;
import example.server.Controller.Media_Controller;
import example.server.Controller.Message_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.models.Media_Chatroom;
import example.server.models.Message;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Chatroom_Handler {
    private static final Gson gson = new Gson();

    public static void whichMessageHandler(HttpExchange exchange) throws IOException {
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

        if (exchange.getRequestHeaders().containsKey("Content-Type") &&
                exchange.getRequestHeaders().getFirst("Content-Type").contains("multipart/form-data")) {
            messageWithMediaUpload(exchange);
        }
        else
            messageUpload(exchange);
    }

    public static void messageUpload(HttpExchange exchange) throws IOException {
        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Message message = gson.fromJson(RequestBody, Message.class);
        try {
            Message_Controller.insertMessage(message);
            Server.sendResponse(exchange, 200, null);
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error");
        }

    }

    public static void messageWithMediaUpload(HttpExchange exchange) throws IOException {
        File uploadDir = new File("uploads"); // Directory to store uploaded files
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create the directory if it doesn't exist
        }

        InputStream inputStream = exchange.getRequestBody();
        byte[] buffer = new byte[4096];
        int bytesRead = inputStream.read(buffer);

        // Get the boundary string from the Content-Type header
        String boundary = getBoundary(exchange.getRequestHeaders().getFirst("Content-Type"));

        // Split the request body into parts using the boundary
        String[] parts = new String(buffer, 0, bytesRead).split("--" + boundary);

        // Variables to hold parsed values
        String mediaUrl = null;
        String content = null;
        String sender = null;
        String receiver = null;

        // Loop through each part to extract the file and form data
        for (String part : parts) {
            // Check if the part contains a file
            if (part.contains("filename")) {
                // Generate a unique file name and save the file to the "uploads" directory
                String fileName = UUID.randomUUID().toString();
                String filePath = "uploads" + File.separator + fileName;
                try (OutputStream outputStream = new FileOutputStream(filePath)) {
                    outputStream.write(part.getBytes());
                }
                // Set the media URL to be returned or saved in the message
                mediaUrl = "http://localhost:8000/uploads/" + fileName;
            } else if (part.contains("Content-Disposition")) {
                // Extract form data fields from the part
                if (part.contains("name=\"content\"")) {
                    content = part.substring(part.indexOf("\r\n\r\n") + 4).trim(); // Extract message content
                } else if (part.contains("name=\"senderId\"")) {
                    sender = part.substring(part.indexOf("\r\n\r\n") + 4).trim(); // Extract sender ID
                } else if (part.contains("name=\"receiverId\"")) {
                   receiver = part.substring(part.indexOf("\r\n\r\n") + 4).trim(); // Extract receiver ID
                }
            }
        }

        // Create a Message object and set its fields
        Message message = new Message(sender,receiver,content,mediaUrl);


        // Save the message to the database
        try {
            Message_Controller.insertMessage(message);
            // Send a 200 OK response
            exchange.sendResponseHeaders(200, -1);
        } catch (SQLException e) {
            e.printStackTrace();
            // Send a 500 Internal Server Error response if an exception occurs
            exchange.sendResponseHeaders(500, -1);
        }

    }
    // Extract the boundary from the Content-Type header
    private static String getBoundary(String contentType) {
        String[] tokens = contentType.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("boundary")) {
                return token.split("=")[1];
            }
        }
        return "";
    }
    public static void MediaUpload(HttpExchange exchange) throws IOException {
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

        if (exchange.getRequestHeaders().containsKey("Content-Type") &&
                exchange.getRequestHeaders().getFirst("Content-Type").contains("multipart/form-data")){

            File uploadDir = new File("uploads"); // Directory to store uploaded files
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Create the directory if it doesn't exist
            }

            InputStream inputStream = exchange.getRequestBody();
            byte[] buffer = new byte[4096];
            int bytesRead = inputStream.read(buffer);
            String boundary = getBoundary(exchange.getRequestHeaders().getFirst("Content-Type"));
            String[] parts = new String(buffer, 0, bytesRead).split("--" + boundary);

            String mediaUrl = null;
            String sender = null;
            String receiver = null;


            for (String part : parts) {
                if (part.contains("filename")) {
                    String fileName = UUID.randomUUID().toString();
                    String filePath = "uploads" + File.separator + fileName;
                    try (OutputStream outputStream = new FileOutputStream(filePath)) {
                        outputStream.write(part.getBytes());
                    }
                    mediaUrl = "http://localhost:8000/uploads/" + fileName; // Set media URL
                } else if (part.contains("Content-Disposition")) {
                    if (part.contains("name=\"senderId\"")) {
                       sender = part.substring(part.indexOf("\r\n\r\n") + 4).trim();
                    } else if (part.contains("name=\"receiverId\"")) {
                        receiver = part.substring(part.indexOf("\r\n\r\n") + 4).trim();
                    }
                }
            }

            Media_Chatroom media_chatroom = new Media_Chatroom(sender,receiver,mediaUrl);

            try {
                MediaOfChatroom_Controller.insertMedia(media_chatroom); // Save the media to the database
                Server.sendResponse(exchange,200,null);
            } catch (SQLException e) {
                e.printStackTrace();
                Server.sendResponse(exchange,500, "Database error");
            }
            catch (Exception e) {
                Server.sendResponse(exchange, 500, "Internal Server error");
            }

        }
        else
            Server.sendResponse(exchange, 400, "Bad Request");

    }

    public static void RetrieveMedia(HttpExchange exchange) throws IOException {

    }

    public static void RetrieveMedias(HttpExchange exchange) throws IOException {
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
        String Email1 = pathParts[pathParts.length - 1];
        String Email2 = pathParts[pathParts.length - 2];

        try {
            ArrayList<Media_Chatroom> mediaChatrooms = MediaOfChatroom_Controller.getMediasOfTwoPersons(Email1,Email2);
            Server.sendResponse(exchange,200,gson.toJson(mediaChatrooms));
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error");
        }

    }

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

        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        String Email1 = pathParts[pathParts.length - 1];
        String Email2 = pathParts[pathParts.length - 2];

        try {
            ArrayList<Message> messages = Message_Controller.getMessagesOfTwoPersons(Email1,Email2);
            Server.sendResponse(exchange,200,gson.toJson(messages));
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error");
        }

    }

    public static void RetrieveMessage(HttpExchange exchange) throws IOException {

    }

    public static void deleteMessages(HttpExchange exchange) throws IOException {

    }

    public static void deleteMedias(HttpExchange exchange) throws IOException {

    }

    public static void deleteHistory(HttpExchange exchange) throws IOException {

    }


}

