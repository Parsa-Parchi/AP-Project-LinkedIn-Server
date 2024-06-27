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


        server.post("/posts", PostHandler::newPostHandler);
        server.delete("/posts/{Id}/delete",PostHandler::deletePostHandler);
        server.post("/posts/{postId}/like",PostHandler::postLikeHandler);
        server.delete("/posts/{postID}/dislike",PostHandler::postDisLikeHandler);
        server.post("/posts/{postId}/comments",PostHandler::postAddComment);
        server.delete("/posts/{postId}/comments/delete",PostHandler::postDeleteComment);
        server.put("/posts/{postId}/comments/update",PostHandler::postUpdateComment);
        server.put("/posts/{postId}/update",PostHandler::postUpdate);
        server.post("/posts/{postId}/hashtag",PostHandler::newHashtagOfPost);
        server.put("/posts/{postId}/hashtag/update",PostHandler::UpdateHashtagOfPost);
        server.delete("/posts/{postId}/hashtag/delete",PostHandler::DeleteHashtagOfPost);
        server.delete("/posts/{postId}/hashtags/delete",PostHandler::DeleteHashtagsOfPost);


    }

}
