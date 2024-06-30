package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Search_Controller;
import example.server.Server;
import example.server.models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Search_Handler {
    private static final Gson gson = new Gson();

    public static void searchByString(HttpExchange exchange) throws IOException {
        String[] uri = exchange.getRequestURI().toString().split("/");
        String searchString = uri[uri.length - 1];

        try {
            ArrayList<User> users = Search_Controller.getUsersBySearch(searchString);
            Server.sendResponse(exchange , 200 , gson.toJson(users));
        }

        catch (SQLException e) {

            Server.sendResponse(exchange , 500 , "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e) {
            Server.sendResponse(exchange , 500 , "Internal Server error: " + e.getMessage());
        }
    }
}
