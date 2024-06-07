package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Post_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.models.Post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;


public class PostHandler {
    private static Post_Controller postController;
    private static final Gson gson = new Gson();

    public void PostHandler() throws SQLException {
        postController = new Post_Controller();
    }

    public static void newPostHandler(HttpExchange exchange) throws IOException {
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

        String reqBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(reqBody, Post.class);
        try {
            postController.insertPost(post);
            Server.sendResponse(exchange, 200, "The Post was successfully added to the Database");
        }
        catch (SQLException e)
        {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : -->>" + e.getMessage());
        }

        catch (IllegalArgumentException e)
        {
            Server.sendResponse(exchange, 400, e.getMessage());
        }

        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void deletePostHandler(HttpExchange exchange) throws IOException {
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

        String [] uri = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(uri[1]);

        try {
            postController.deletePost(postController.getPostById(postId));
            Server.sendResponse(exchange,200,"Post was successfully deleted");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : -->>" + e.getMessage());
        }
    }
}
