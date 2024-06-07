package example.server.Router;


import example.server.Http_Handlers.PostHandler;
import example.server.Http_Handlers.ProfileHandler;
import example.server.Http_Handlers.Search_Handler;
import example.server.Http_Handlers.UserHandler;
import example.server.Server;

public class Router {

    public static void addRoute(Server server) {

        server.post("/signup", UserHandler::SignUpHandler);
        server.post("/login", UserHandler::LogInHandler);
        server.get("/user",UserHandler::retrieveUserHandler);
        server.get("/users",UserHandler::retrieveAllUserHandler);
        server.get("/user/skill",UserHandler::getSkillOfUserHandler);
        server.get("/user/contact-info",UserHandler::getContactInfoOfUserHandler);
        server.get("/user/educations",UserHandler::getEducationsOfUserHandler);
        server.get("/profile", ProfileHandler::retrieveProfileHandler);
        server.put("/profile/update/user", ProfileHandler::updateUserProfileHandler);
        server.put("/profile/update/skill", ProfileHandler::updateSkillProfileHandler);
        server.put("/profile/update/contact-info", ProfileHandler::updateContactInfoHandler);
        server.put("/profile/update/education", ProfileHandler::educationUpdateHandler);
        server.post("/profile/add/education", ProfileHandler::addEducationHandler);

        // Dynamic path without embedding the handler logic
        server.get("/search/{String}", Search_Handler::searchByString);
        server.get("/user/{firstname}/{lastName}/profile", ProfileHandler::retrieveAnotherProfileHandler);


        server.post("/new post", PostHandler::newPostHandler);
        server.delete("/post delete/{Id}",PostHandler::deletePostHandler);


    }

}
