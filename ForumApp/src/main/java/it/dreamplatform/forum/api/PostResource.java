package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.controller.PostController;
import it.dreamplatform.forum.services.PostService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/post")
public class PostResource {
    @EJB(name = "it.dreamplatform.forum.services/PostService")
    private PostService postService;
    @Inject
    private PostController postController;
    private final Gson gson = new Gson();

    /**
     * This function is the api used to retrieve a List of Post of a given Discussion, by going at "/post/discussionId".
     * @param discussionId is the id of the Discussion.
     * @return a response with a JSON format of the List of Post of a given Discussion.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/{discussionId: [0-9]+}")
    public Response getPostsByDiscussionId(@PathParam("discussionId") Long discussionId){
        List<PostBean> posts;
        try {
            posts = postController.getPostsByDiscussionId(discussionId);
            return Response.ok().entity(gson.toJson(posts)).build();
        } catch (Exception e) {
            return Response.status(204).entity("[]").build();
        }
    }

    /**
     * This function is the api used to retrieve a List of Post of a given User, by going at "/post/creator/creatorId".
     * @param creatorId is the id of the User that has created the Post.
     * @return a response with a JSON format of the List of Post created by a given User.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("creator/{creatorId: [0-9]+}")
    public Response getPostsByUser(@PathParam("creatorId") Long creatorId){
        List<PostBean> posts;
        try {
            posts = postController.getPostsByUser(creatorId);
            return Response.ok().entity(gson.toJson(posts)).build();
        } catch (Exception e) {
            return Response.status(204).entity("[]").build();
        }
    }

    /**
     * This function is the api used to approve a post in the pending list (the posts with status 0). It can be used by going at "/post/approve/postId".
     * @param postId is the id of the Post to approve.
     * @return a response with a JSON format about the success of the operation.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/approve/{postId: [0-9]+}")
    public Response approvePost(@PathParam("postId") Long postId){
        try {
            postController.approvePendingPost(postId);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    /**
     * This function is the api used to decline a post in the pending list (the posts with status 0). It can be used by going at "/post/decline/postId".
     * @param postId is the id of the Post to decline.
     * @return a response with a JSON format about the success of the operation.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/decline/{postId: [0-9]+}")
    public Response declinePost(@PathParam("postId") Long postId){
        try {
            postController.declinePendingPost(postId);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    /**
     * This function is the api used to retrieve the data of a Post that is going to be created. It can be used by going at "/post/publish".
     * @param post is the PostBean.
     * @return a response with a JSON format about the success of the operation.
     */
    @POST
    @Path("/publish")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed({"user", "policy_maker"})
    public Response publishPost(PostBean post){
        try {
            //TODO  postController.publishPost(post, user);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }
}
