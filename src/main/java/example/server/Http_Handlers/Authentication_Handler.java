package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Authentication_Controller;
import example.server.Server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class Authentication_Handler {
    private static final Gson gson = new Gson();

    public static void LogInHandler(HttpExchange exchange) throws IOException {
        String reqBody = new String(exchange.getRequestBody().readAllBytes());
        HashMap<String, String> loginData = gson.fromJson(reqBody, HashMap.class);
        String email = loginData.get("email");
        String password = loginData.get("password");

        try {
            String token = Authentication_Controller.LogIn(email, password);
            HashMap<String, String> responseHashMap = new HashMap<>();
            responseHashMap.put("ResponseToken", token);
            Server.sendResponse(exchange, 200, gson.toJson(responseHashMap));
        } catch (IllegalArgumentException e) {
            // Handle invalid request parameters
            String errorMessage = "Invalid request parameters: " + e.getMessage();
            Server.sendResponse(exchange, 400, gson.toJson(Collections.singletonMap("error", errorMessage)));
        } catch (Exception e) {
            // Handle unexpected errors
            String errorMessage = "Unexpected error occurred: " + e.getMessage();
            Server.sendResponse(exchange, 500, gson.toJson(Collections.singletonMap("error", errorMessage)));
        }
    }
}
