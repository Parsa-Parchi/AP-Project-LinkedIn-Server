package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Connect_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.TimestampAdapter;
import example.server.Utilities.jwt_Util;
import example.server.models.Connect;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

public class Connect_Handler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Timestamp.class, new TimestampAdapter())
            .create();

    public static void ConnectRequest(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfSender = jwt_Util.parseToken(token);


        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Connect connect = gson.fromJson(RequestBody, Connect.class);
        connect.setRequest_Sender(EmailOfSender);

        try {
            if(!Connect_Controller.ExistRequest(connect)) {
                Connect_Controller.insertConnect(connect);
                Server.sendResponse(exchange, 200, "Connect request sent");
            }
            else
                Server.sendResponse(exchange, 409, "Connect request already exist");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Error in database : " + e.getMessage());

        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error : " + e.getMessage());
        }
    }

    public static void DeleteRequest(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfSender = jwt_Util.parseToken(token);

        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Connect connect = gson.fromJson(RequestBody, Connect.class);
        connect.setRequest_Sender(EmailOfSender);
        try {
            Connect_Controller.deleteRequest(connect);
            Server.sendResponse(exchange, 200, null);
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Error in database : " + e.getMessage());
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error : " + e.getMessage());
        }
    }

    public static void AcceptRequest(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }


        String EmailOfReceiver = jwt_Util.parseToken(token);

        String[] uri = exchange.getRequestURI().getPath().split("/");
        String EmailOfSender = uri[uri.length - 1];
        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Connect connect = gson.fromJson(RequestBody, Connect.class);
        connect.setAccepted(true);
        connect.setRequest_Sender(EmailOfSender);
        connect.setRequest_Receiver(EmailOfReceiver);
        try {
            Connect_Controller.updateConnect(connect);
            Server.sendResponse(exchange, 200, "Connect request Accepted");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Error in database");
        }
        catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error");
        }
    }

    public static void getNotAcceptedConnectRequest(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);

        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String EmailOfReceiver = jwt_Util.parseToken(token);
        ArrayList<Connect> NotAcceptedConnects ;
        try {
            NotAcceptedConnects = Connect_Controller.getConnectionsOfReceiverByBoolean(EmailOfReceiver,false);
            Server.sendResponse(exchange, 200, gson.toJson(NotAcceptedConnects));
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "Error in database");
        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "internal server error");
        }

    }
}