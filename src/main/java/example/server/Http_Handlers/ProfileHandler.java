package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Authentication_Controller;
import example.server.Controller.Profile_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.jwt_Util;
import example.server.models.Profile;
import example.server.models.Skill;
import example.server.models.User;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

public class ProfileHandler {
    private static final Gson gson = new Gson();

    public static void retrieveProfileOfUserHandler(HttpExchange exchange) throws IOException {
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
            Profile profile = Profile_Controller.getProfile(EmailOfToken);
            if(profile == null) {
                Server.sendResponse(exchange, 404, gson.toJson(Collections.singletonMap("error ", "Profile not found")));
            }
            else
                Server.sendResponse(exchange, 200, gson.toJson(profile));
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }


    public static void updateUserProfileHandler(HttpExchange exchange) throws IOException {
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
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = gson.fromJson(requestBody, User.class);
        if(user.getEmail().equals(EmailOfToken))
        {
            try {
                Profile_Controller.updateUser(user);
                Server.sendResponse(exchange, 200, "Profile updated successfully");
            } catch (SQLException e) {
                Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
            }
            catch (Exception e) {
                Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
            }
        }
        else
        {

            try {
                Profile_Controller.updateUser(user);
                String newToken = jwt_Util.generateToken(user.getEmail());
                HashMap<String, String> responseHashMap = new HashMap<>();
                responseHashMap.put("ResponseNewToken", newToken);
                responseHashMap.put("message", "Profile updated successfully");
                Server.sendResponse(exchange, 200, gson.toJson(responseHashMap));
            }
            catch (IllegalArgumentException e){
                Server.sendResponse(exchange, 400,"Bad Request : " + e.getMessage());
            }
            catch (SQLException e) {
                Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
            }

            catch (Exception e) {
                Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
            }
        }

    }

    public static void updateSkillProfileHandler(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }
        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Skill skill = gson.fromJson(requestBody, Skill.class);

        try {
            Profile_Controller.updateSkill(skill);
            Server.sendResponse(exchange, 200, "Profile updated successfully");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }

    }
}
