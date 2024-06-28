package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Connect_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.jwt_Util;
import example.server.models.Connect;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Connect_Handler {
    private static final Gson gson = new Gson();

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
        String[] uri = exchange.getRequestURI().getPath().split("/");
        String EmailOfReceiver = uri[1];
        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Connect connect = gson.fromJson(RequestBody, Connect.class);
        connect.setAccepted(false);
        connect.setRequest_Sender(EmailOfSender);
        connect.setRequest_Receiver(EmailOfReceiver);

        try {
            Connect_Controller.insertConnect(connect);
            Server.sendResponse(exchange, 200, "Connect request sent");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Error in database");

        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "internal server error");
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
        String EmailOfSender = uri[1];
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
            NotAcceptedConnects = Connect_Controller.getAcceptedConnectionsOfReceiver(EmailOfReceiver,false);
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