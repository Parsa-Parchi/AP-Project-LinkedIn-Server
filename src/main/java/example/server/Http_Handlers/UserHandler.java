package example.server.Http_Handlers;

import com.google.gson.GsonBuilder;
import example.server.Controller.*;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.jwt_Util;
import example.server.models.ContactInformation;
import example.server.models.Education;
import example.server.models.Skill;
import example.server.models.User;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class UserHandler {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    public static void SignUpHandler(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = gson.fromJson(requestBody, User.class);
        try
        {
            User user2 = UserController.getUserByEmail(user.getEmail());
            if(user2 == null) {
                UserController.insertUser(user);
                String token = jwt_Util.generateToken(user.getEmail());
                HashMap<String, String> responseHashMap = new HashMap<>();
                responseHashMap.put("ResponseToken", token);
                Server.sendResponse(exchange, 200, gson.toJson(responseHashMap));
            }
            else
                Server.sendResponse(exchange, 409, "The User with this email already exists");
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


    public static void retrieveUserHandler(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfToken = jwt_Util.parseToken(token);

        try {
            User user = UserController.getUserByEmail(EmailOfToken);

            if (user == null)
                Server.sendResponse(exchange, 404, gson.toJson(Collections.singletonMap("error", "User not found")));
             else
                Server.sendResponse(exchange, 200, gson.toJson(user));

        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void retrieveAllUserHandler(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        try {
            ArrayList<User> users = UserController.getAllUsers();
            Server.sendResponse(exchange, 200, gson.toJson(users));
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void getSkillOfUserHandler(HttpExchange exchange) throws IOException {
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

        try {
            Skill skill = Skill_Controller.getSkillOfUser(EmailOfToken);

            if (skill == null)
                Server.sendResponse(exchange, 404, gson.toJson(Collections.singletonMap("error", "Skill not found")));
            else
                Server.sendResponse(exchange, 200, gson.toJson(skill));
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void getContactInfoOfUserHandler(HttpExchange exchange) throws IOException {
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

        try {
            ContactInformation contactInformation = ContactInfo_Controller.getContactInfoOfUser("ParsaPrc78653@gmail.com");
            if(contactInformation == null)
                Server.sendResponse(exchange, 404, gson.toJson(Collections.singletonMap("error", "ContactInformation not found")));
            else
                Server.sendResponse(exchange, 200, gson.toJson(contactInformation));
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void getEducationsOfUserHandler(HttpExchange exchange) throws IOException {
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


        try {
            ArrayList<Education> educations = Education_Controller.getAllEducationsOfUser(EmailOfToken);
            if (educations == null)
                Server.sendResponse(exchange, 404, gson.toJson(Collections.singletonMap("error", "no educations found")));
            else
                Server.sendResponse(exchange, 200, gson.toJson(educations));
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }
}