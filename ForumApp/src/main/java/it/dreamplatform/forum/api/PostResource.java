package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.bean.PostBean;
import it.dreamplatform.forum.controller.PostController;
import it.dreamplatform.forum.entities.Post;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/{discussionId: [0-9]+}")
    public Response getByDiscussionId(@PathParam("discussionId") Long discussionId){

        List<PostBean> posts = null;
        try {
            posts = postController.getPostsByDiscussionId(discussionId);
            return Response.ok().entity(gson.toJson(posts)).build();
        } catch (Exception e) {
            return Response.status(204).entity("[]").build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("creator/{creatorId: [0-9]+}")
    public Response getPostsByUser(@PathParam("creatorId") Long creatorId){

        List<PostBean> posts = null;
        try {
            posts = postController.getPostsByUser(creatorId);
            return Response.ok().entity(gson.toJson(posts)).build();
        } catch (Exception e) {
            return Response.status(204).entity("[]").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/approve/{PostId: [0-9]+}")
    public Response approvePost(@PathParam("PostId") Long postId){
        try {
            postController.approvePendingPost(postId);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/decline/{PostId: [0-9]+}")
    public Response declinePost(@PathParam("PostId") Long postId){
        try {
            postController.declinePendingPost(postId);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    @POST
    @Path("/publish")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")

    public Response publishPost(){
        try {
            //TODO  postController.publishPost(post, user);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }
}
