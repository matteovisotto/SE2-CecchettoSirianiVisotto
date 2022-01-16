package org.dream.forum.api;

import com.google.gson.Gson;
import org.dream.forum.entities.Post;
import org.dream.forum.services.PostService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/post")
public class PostResource {
    @EJB(name = "org.dream.forum.services/PostService")
    private PostService postService;
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    @Path("/{discussionId: [0-9]+}")
    public Response getByDiscussionId(@PathParam("discussionId") Long discussionId){
        List<Post> posts = postService.getByDiscussionId(discussionId);
        return Response.ok().entity(gson.toJson(posts)).build();
    }
}
