package example.server.Utilities;

import com.sun.net.httpserver.HttpExchange;
import example.server.Server;

import java.io.IOException;

public class Authorization_Util {

    public static String getAuthToken(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            Server.sendResponse(exchange, 401, "Missing or invalid authorization token");
            return null;
        }
        return authorizationHeader.substring(7); // Strip "Bearer " prefix
    }

    public static boolean validateAuthToken(HttpExchange exchange, String token) throws IOException {
        if (token == null || !jwt_Util.validateToken(token)) {
            Server.sendResponse(exchange, 401, "Invalid authorization token");
            return false;
        }
        return true;
    }

    public static boolean checkUserAccess(HttpExchange exchange, String token, String userEmail) throws IOException {
        if (token == null) {
            return false;
        }

        String tokenEmail = jwt_Util.parseToken(token);
        if (!userEmail.equals(tokenEmail)) {
            Server.sendResponse(exchange, 403, "Access denied: Unauthorized user");
            return false;
        }
        return true;
    }

    public static boolean authorizeRequest(HttpExchange exchange, String userEmail) throws IOException {
        String token = getAuthToken(exchange);

        if (token == null) {
            return false;
        }

        if (!validateAuthToken(exchange, token)) {
            return false;
        }

        if (!checkUserAccess(exchange, token, userEmail)) {
            return false;
        }

        return true;
    }
}

