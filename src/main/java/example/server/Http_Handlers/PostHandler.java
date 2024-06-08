package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Comment_Controller;
import example.server.Controller.Like_Controller;
import example.server.Controller.Post_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.models.Comment;
import example.server.models.Like;
import example.server.models.Post;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;


public class PostHandler {
    private static Post_Controller postController;
    private static Like_Controller likeController;
    private static Comment_Controller commentController;
    private static final Gson gson = new Gson();

    public void PostHandler() throws SQLException {
        postController = new Post_Controller();
        likeController = new Like_Controller();
        commentController = new Comment_Controller();
    }

    public static void newPostHandler(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String reqBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(reqBody, Post.class);
        try {
            postController.insertPost(post);
            Server.sendResponse(exchange, 200, "The Post was successfully added to the Database");
        }
        catch (SQLException e)
        {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : -->>" + e.getMessage());
        }

        catch (IllegalArgumentException e)
        {
            Server.sendResponse(exchange, 400, e.getMessage());
        }

        catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void deletePostHandler(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] uri = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(uri[1]);

        try {
            postController.deletePost(postController.getPostById(postId));
            Server.sendResponse(exchange,200,"Post was successfully deleted");
        }
        catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : -->>" + e.getMessage());
        }
    }

    public static void postLikeHandler(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        String emailOfPost = url[2];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Like like = new Like(postId,emailOfPost, timestamp);
        try{
            likeController.insertLike(like);
            Server.sendResponse(exchange,200,gson.toJson(like));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        } catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void postDisLikeHandler(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        String emailOfPost = url[2];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Like like = new Like(postId,emailOfPost, timestamp);
        try {
            likeController.deleteLike(url[2],postId);
            Server.sendResponse(exchange,200,gson.toJson(like));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        } catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void postAddComment(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        }

        else if(!Authorization_Util.validateAuthToken(exchange,token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);;
        String email = url[2];
        String comment = url[3];
        Comment comment1 = new Comment(postId,email,comment);
        try {
            commentController.insertComment(comment1);
            Server.sendResponse(exchange,200,gson.toJson(comment1));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        } catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void postDeleteComment(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String[] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        ;
        String email = url[2];
        String comment = url[3];
        Comment comment1 = new Comment(postId, email, comment);
        try {
            commentController.deleteComment(comment1);
            Server.sendResponse(exchange, 200, gson.toJson(comment1));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void postUpdateComment(HttpExchange exchange) throws IOException {
        //check Authorization
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String[] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        ;
        String email = url[2];
        String comment = url[3];
        Comment comment1 = new Comment(postId, email, comment);
        try {
            commentController.updateComment(comment1);
            Server.sendResponse(exchange, 200, gson.toJson(comment1));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }


}
