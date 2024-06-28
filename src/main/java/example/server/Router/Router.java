package example.server.Router;


import example.server.Http_Handlers.*;
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
        server.get("/{hashtag}/search/posts",PostHandler::searchPostByHashtag);
        server.post("/follow/{email}", Follow_Handler::FollowUser);
        server.delete("/unfollow/{email}", Follow_Handler::UnFollowUser);
        server.get("/followers", Follow_Handler::getFollowers);
        server.get("/followings", Follow_Handler::getFollowing);
        server.post("/connect/{receiver}/request",Connect_Handler::ConnectRequest);
        server.put("/connect/{sender}/accept",Connect_Handler::AcceptRequest);
        server.get("/connect/requests",Connect_Handler::getNotAcceptedConnectRequest);

        server.post("/media/upload/{postId}/", Media_Handler::UploadMedia);
        server.get("/media/get/{postId}/{file_name}",Media_Handler::RetrieveMedia);
        server.delete("/media/delete/{postId}/{file_name}",Media_Handler::deleteMedia);

    }

}
