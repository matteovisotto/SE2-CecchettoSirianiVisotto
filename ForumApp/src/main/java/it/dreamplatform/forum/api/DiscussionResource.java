package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.controller.DiscussionController;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.services.DiscussionService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/discussion")
public class DiscussionResource {
    @Inject
    private DiscussionController discussionController;
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    @Path("/{discussionId: [0-9]+}")
    public Response getByDiscussionId(@PathParam("discussionId") Long discussionId){
        DiscussionContentBean posts = discussionController.getPostByDiscussionId(discussionId);
        return Response.ok().entity(gson.toJson(posts)).build();
    }
}
