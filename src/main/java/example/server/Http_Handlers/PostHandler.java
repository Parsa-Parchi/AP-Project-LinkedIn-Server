package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Post_Controller;
import example.server.Server;
import example.server.models.Post;

import java.io.IOException;
import java.sql.SQLException;


public class PostHandler {
    private static Post_Controller postController;
    private static final Gson gson = new Gson();

    public void PostHandler() throws SQLException {
        postController = new Post_Controller();
    }

    public static void newPostHandler(HttpExchange exchange) throws IOException {
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
}
