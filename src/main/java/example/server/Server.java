package example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;

import example.server.Router.Router;

public class Server  {
    private HttpServer server;

    public Server(int port) throws IOException {
        createServer(port);
    }

    private void createServer(int port) throws IOException {
        InetAddress localAddress = InetAddress.getByName("127.0.0.1");
        this.server = HttpServer.create(new InetSocketAddress(localAddress, port), 0);
        Router.addRoute(this);
        server.start();
    }


    private HashMap<String, String> parseQueryParameters(String query) {
        HashMap<String, String> queryParams = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                int separatorIndex = param.indexOf("=");
                if (separatorIndex > 0) {
                    String key = param.substring(0, separatorIndex);
                    String value = param.substring(separatorIndex + 1);
                    queryParams.put(key, value);
                }
            }
        }
        return queryParams;
    }

    public static void sendResponse(HttpExchange exchange, int statusCode, String response)throws IOException  {
        if (response == null || response.isEmpty()) {
            exchange.sendResponseHeaders(statusCode, -1); // No response body
        } else {
            exchange.sendResponseHeaders(statusCode, response.length());
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(response.getBytes());
            }
        }
    }

    private void sendMethodNotAllowedResponse(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 405, "Method Not Allowed");
    }



    public void get (String path , HttpHandler requestHandler) {
        server.createContext(path, createGetRequestHandler(requestHandler));
    }
    private HttpHandler createGetRequestHandler(HttpHandler requestHandler) {
        return exchange -> {
            if (exchange.getRequestMethod().equals("GET")) {
                HashMap<String, String> queryParams = parseQueryParameters(exchange.getRequestURI().getQuery());
                exchange.setAttribute("requestParams", queryParams);
                requestHandler.handle(exchange);
            }else {
                sendMethodNotAllowedResponse(exchange);
            }
        };
    }



    public void post(String path , HttpHandler requestHandler) {
        server.createContext(path, createPostRequestHandler(requestHandler));
    }
    private HttpHandler createPostRequestHandler(HttpHandler requestHandler) {
        return exchange -> {
            if (exchange.getRequestMethod().equals("POST")) {
                HashMap<String, String> queryParams = parseQueryParameters(exchange.getRequestURI().getQuery());
                exchange.setAttribute("requestParams", queryParams);
                requestHandler.handle(exchange);
            } else {
                sendMethodNotAllowedResponse(exchange);
            }
        };
    }


    public void delete(String path , HttpHandler requestHandler) {
        server.createContext(path, createDeleteRequestHandler(requestHandler));
    }
    private HttpHandler createDeleteRequestHandler(HttpHandler requestHandler) {
        return exchange -> {
            if (exchange.getRequestMethod().equals("DELETE")) {
                HashMap<String, String> queryParams = parseQueryParameters(exchange.getRequestURI().getQuery());
                exchange.setAttribute("requestParams", queryParams);
                requestHandler.handle(exchange);

            }else {
                sendMethodNotAllowedResponse(exchange);
            }
        };
    }


    public void put (String path , HttpHandler requestHandler) {
        server.createContext(path, createPutRequestHandler(requestHandler));
    }
    private HttpHandler createPutRequestHandler(HttpHandler requestHandler) {
        return exchange -> {
            if (exchange.getRequestMethod().equals("PUT")) {
                HashMap<String, String> queryParams = parseQueryParameters(exchange.getRequestURI().getQuery());
                exchange.setAttribute("requestParams", queryParams);
                requestHandler.handle(exchange);
            }else {
                sendMethodNotAllowedResponse(exchange);
            }
        };
    }


}
