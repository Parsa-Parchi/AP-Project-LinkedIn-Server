package example.server.Http_Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import example.server.Controller.Comment_Controller;
import example.server.Controller.Hashtag_Controller;
import example.server.Controller.Like_Controller;
import example.server.Controller.Post_Controller;
import example.server.Server;
import example.server.Utilities.Authorization_Util;
import example.server.Utilities.jwt_Util;
import example.server.models.Comment;
import example.server.models.Hashtag;
import example.server.models.Like;
import example.server.models.Post;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;


public class PostHandler {


    private static final Gson gson = new Gson();


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
        ArrayList<String> hashtags = returnHashTags(post);

        try {
            Post_Controller.insertPost(post);
            int postId = Post_Controller.getPostId(post);
            for(String hashtag : hashtags) {
                Hashtag_Controller.insertHashtag(new Hashtag(postId, hashtag));
            }
            Server.sendResponse(exchange, 200, "The Post was successfully added to the Database");
        }
        catch (SQLException e)
        {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : -->>" + e.getMessage());
        }

        catch (IllegalArgumentException e)
        {
            Server.sendResponse(exchange, 400,"Bad Request : " + e.getMessage());
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
            Post_Controller.deletePost(Post_Controller.getPostById(postId));
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

        String emailOfLike = jwt_Util.parseToken(token);
        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        Like like = new Like(postId,emailOfLike);
        try{
            Like_Controller.insertLike(like);
            Server.sendResponse(exchange,200,"Liked Successfully");
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

        String emailOfLike = jwt_Util.parseToken(token);
        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        Like like = new Like(postId,emailOfLike);
        try {
            Like_Controller.deleteLike(url[2],postId);
            Server.sendResponse(exchange,200,"Disliked Successfully");
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

        String emailOfComment = jwt_Util.parseToken(token);
        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Comment comment = gson.fromJson(RequestBody,Comment.class);
        String [] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        comment.setPostId(postId);
        comment.setEmail(emailOfComment);
        try {
            Comment_Controller.insertComment(comment);
            Server.sendResponse(exchange,200,"Comment was successfully added to this post");
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

        String emailOfComment = jwt_Util.parseToken(token);
        String RequestBody = new String(exchange.getRequestBody().readAllBytes());
        Comment comment = gson.fromJson(RequestBody,Comment.class);
        String[] url = exchange.getRequestURI().getPath().split("/");
        int postId = Integer.parseInt(url[1]);
        comment.setPostId(postId);
        comment.setEmail(emailOfComment);

        try {
            Comment_Controller.deleteComment(comment);
            Server.sendResponse(exchange, 200, "Comment was successfully deleted from this post");
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
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Comment comment = gson.fromJson(requestBody,Comment.class);
        int postId = Integer.parseInt(url[1]);
        comment.setPostId(postId);
        comment.setEmail(jwt_Util.parseToken(token));
          try {
            Comment_Controller.updateComment(comment);
            Server.sendResponse(exchange, 200, "Comment Updated successfully");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void postUpdate (HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String[] url = exchange.getRequestURI().getPath().split("/");
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody,Post.class);
        int postId = Integer.parseInt(url[1]);
        post.setId(postId);


        try {
            if(post.getContent()==null){
            Post_Controller.updatePost(post);
            Server.sendResponse(exchange, 200, "Post Updated successfully");
            }
            else {
               Post_Controller.updatePost(post);
                Hashtag_Controller.deleteHashtagsOfPost(postId);
                ArrayList<String> hashtags = returnHashTags(post);
                for(String hashtag : hashtags){
                    Hashtag_Controller.insertHashtag(new Hashtag(postId,hashtag));
                }
            }
        }
        catch (IllegalArgumentException e){
            Server.sendResponse(exchange, 400,"Bad Request : " + e.getMessage());
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());
        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }
    }

    public static void searchPostByHashtag(HttpExchange exchange) throws IOException {
        String token = Authorization_Util.getAuthToken(exchange);
        if (token == null) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Authorization token is missing ")));
            return;
        } else if (!Authorization_Util.validateAuthToken(exchange, token)) {
            Server.sendResponse(exchange, 401, gson.toJson(Collections.singletonMap("error ", "Invalid or expired token")));
            return;
        }

        String[] url = exchange.getRequestURI().getPath().split("/");
        String hashtag = url[0];
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList <Integer> postIds ;
        try {
            postIds = Hashtag_Controller.getPostIdsOfHashtag(hashtag);
           for (Integer postId : postIds) {
               posts.add(Post_Controller.getPostById(postId));
           }
           Server.sendResponse(exchange, 200, gson.toJson(posts));
        }
        catch (SQLException e){
            Server.sendResponse(exchange, 500, "A problem was found in the Database : " + e.getMessage());

        }
        catch (Exception e){
            Server.sendResponse(exchange, 500, "Internal Server error: " + e.getMessage());
        }

    }


    private static ArrayList<String> returnHashTags(Post post) {
        String postContent = post.getContent();
        ArrayList<String> Hashtags = new ArrayList<>();
        int i = 0;
        int j;

        while (postContent.length() != i) {
            if (postContent.charAt(i) == '#') {
                j = i;
                while (postContent.charAt(i) != ' ') {
                    i++;
                }
                Hashtags.add(postContent.substring(j, i)) ;

            }
            i++;
        }
        return Hashtags;
    }

}
