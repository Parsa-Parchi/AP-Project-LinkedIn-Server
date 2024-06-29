package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.PrivateChat_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.models.Message;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class PrivateChat_Handler {
    private static final Gson gson = new Gson();

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
            PrivateChat_Controller.insertMessage(message);
            // Send a 200 OK response
            Server.sendResponse(exchange, 200, null);
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, " internal Server error");
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


        String query = exchange.getRequestURI().getQuery();
        HashMap<String,String> queryParams = (HashMap<String, String>) exchange.getAttribute("requestParams");
        String user1 = queryParams.get("Email1");
        String user2 = queryParams.get("Email2");

        try {
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

