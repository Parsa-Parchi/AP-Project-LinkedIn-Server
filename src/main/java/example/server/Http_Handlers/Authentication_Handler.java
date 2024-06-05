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

    public static void loginHandler(HttpExchange exchange) throws IOException {
        try {

            String reqBody = new String(exchange.getRequestBody().readAllBytes());
            HashMap<String, String> loginData = gson.fromJson(reqBody, HashMap.class);
            String email = loginData.get("email");
            String password = loginData.get("password");
            String token = Authentication_Controller.LogIn(email, password);
            HashMap<String, String> responseHashMap = new HashMap<>();
            responseHashMap.put("ResponseToken", token);

            Server.sendResponse(exchange, 200, gson.toJson(responseHashMap));
        } catch (IllegalArgumentException e) {
            // Handle invalid request parameters
            Server.sendResponse(exchange, 400, gson.toJson(Collections.singletonMap("error", e.getMessage())));
        } catch (Exception e) {
            // Handle unexpected errors
            Server.sendResponse(exchange, 500, gson.toJson(Collections.singletonMap("error", "Internal Server Error")));
        }
    }

}
