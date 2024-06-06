package example.server.Http_Handlers;

import example.server.Controller.ContactInfo_Controller;
import example.server.Controller.Education_Controller;
import example.server.Controller.Skill_Controller;
import example.server.Controller.UserController;
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
            ContactInformation contactInformation = ContactInfo_Controller.getContactInfoOfUser(EmailOfToken);
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