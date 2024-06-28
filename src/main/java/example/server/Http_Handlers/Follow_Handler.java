package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Follow_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.jwt_Util;
import example.server.models.Follow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Follow_Handler {
    private static final Gson gson = new Gson();


    public static void FollowUser(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfFollower = jwt_Util.parseToken(token);
        String [] uri = exchange.getRequestURI().getPath().split("/");
        String EmailOfFollowed = uri[1];

        try {
            Follow_Controller.insertFollow(new Follow(EmailOfFollower, EmailOfFollowed));
            Server.sendResponse(exchange,200,"Followed successfully");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in database");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error");
        }
    }

    public static void UnFollowUser(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfFollower = jwt_Util.parseToken(token);
        String [] uri = exchange.getRequestURI().getPath().split("/");
        String EmailOfFollowed = uri[1];

        try {
            Follow_Controller.deleteFollow(new Follow(EmailOfFollower,EmailOfFollowed));
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in database");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error");
        }
    }

    public static void getFollowers(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String Email = jwt_Util.parseToken(token);

        ArrayList<String> Followers ;
        try {
            Followers = Follow_Controller.getFollowersOfUser(Email);
            Server.sendResponse(exchange,200,gson.toJson(Followers));
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in database");

        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error");
        }
    }

    public static void getFollowing(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String Email = jwt_Util.parseToken(token);
        ArrayList<String> Followings;
        try {
           Followings =  Follow_Controller.getFollowingOfUser(Email);
            Server.sendResponse(exchange,200, gson.toJson(Followings));
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in database");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error");
        }
    }
}
