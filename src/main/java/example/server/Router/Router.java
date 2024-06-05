package example.server.Router;

import example.server.Http_Handlers.UserHandler;
import example.server.Server;

public class Router {

    public static void addRoute(Server server) {
        server.post("/signup", UserHandler::SignUpHandler);
    }

}
