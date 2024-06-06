package example.server.Router;

import example.server.Http_Handlers.Authentication_Handler;
import example.server.Http_Handlers.UserHandler;
import example.server.Server;

public class Router {

    public static void addRoute(Server server) {

        server.post("/signup", UserHandler::SignUpHandler);
        server.post("/login", Authentication_Handler::LogInHandler);
        server.get("/user",UserHandler::retrieveUserHandler);

    }

}
