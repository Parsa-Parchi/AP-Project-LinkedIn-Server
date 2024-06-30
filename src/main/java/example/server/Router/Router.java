package example.server.Router;


import example.server.Http_Handlers.*;
import example.server.Server;

public class Router {

    public static void addRoute(Server server) {

        server.post("/signup", UserHandler::SignUpHandler);
        server.get("/login", UserHandler::LogInHandler);
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

        server.get("/search", Search_Handler::searchByString); //after "/search"  put a string to be searched and get list of users

        server.get("/user/profile", ProfileHandler::retrieveAnotherProfileHandler); // besides this path send json object
                                                                                            // including email that should be searched and then get profile of it


        server.post("/posts", PostHandler::newPostHandler);
        server.delete("/posts/delete",PostHandler::deletePostHandler); //besides this "/posts/delete" route send json object of post that has id
        server.post("/posts/like",PostHandler::postLikeHandler); // besides this "/posts/like" route send json object of Post class that has id
        server.delete("/posts/dislike",PostHandler::postDisLikeHandler); // besides this "/posts/dislike" route send json object from post class that has id
        server.post("/posts/comment",PostHandler::postAddComment); //besides this "/posts/comment" route send json object from Comment class that has postId and has comment
        server.delete("/posts/comment/delete",PostHandler::postDeleteComment); //besides this "/posts/comment/delete" route send json object from Comment class that has id of comment
        server.put("/posts/comment/update",PostHandler::postUpdateComment); // besides this "/posts/comment/update" route send json object from comment class that has message and id
        server.put("/posts/update",PostHandler::postUpdate); // besides this "/posts/update" route send json object from post Class that has postId
        server.get("/hashtag/search/posts",PostHandler::searchPostByHashtag); // beside this "/hashtag/search/posts" route send json object from hashtag Class that include Hashtag string to be searched
        server.post("/follow/{email}", Follow_Handler::FollowUser);
        server.delete("/unfollow/{email}", Follow_Handler::UnFollowUser);
        server.get("/followers", Follow_Handler::getFollowers);
        server.get("/followings", Follow_Handler::getFollowing);
        server.post("/connect/{receiver}/request",Connect_Handler::ConnectRequest);
        server.put("/connect/{sender}/accept",Connect_Handler::AcceptRequest);
        server.get("/connect/requests",Connect_Handler::getNotAcceptedConnectRequest);

        server.post("/media/upload/{postId}", Media_Handler::UploadMedia);
        server.get("/media/get/{postId}/{file_name}", Media_Handler::RetrieveMedia);
        server.delete("/media/delete/{postId}/{file_name}", Media_Handler::deleteMedia);
        server.delete("/media/delete/{postId}", Media_Handler::deleteMediasOfPost);

        server.post("/message", PrivateChat_Handler::messageUpload);
        server.get("/chat/{EmailOfUser1}/{EmailOfUser2}", PrivateChat_Handler::RetrieveMessages);
        server.delete("/messages/delete/{messageId}", PrivateChat_Handler::deleteMessage);
        server.delete("/deleteHistory", PrivateChat_Handler::deleteHistory);

    }

}
