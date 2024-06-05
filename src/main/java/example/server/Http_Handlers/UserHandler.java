package example.server.Http_Handlers;

import example.server.Controller.UserController;
import example.server.Server;
import example.server.models.User;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.sql.SQLException;


public class UserHandler {
    private static final Gson gson = new Gson();

    public static void SignUpHandler(HttpExchange exchange) throws IOException {

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = gson.fromJson(requestBody, User.class);

        try
        {
            UserController.insertUser(user);
            Server.sendResponse(exchange, 200, "The User was successfully added to the Database");
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