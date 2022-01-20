package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.services.DiscussionService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/discussion")
public class DiscussionResource {
    @EJB(name = "org.dream.forum.services/DiscussionService")
    private DiscussionService discussionService;
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    @Path("/{topicId: [0-9]+}")
    public Response getByTopicId(@PathParam("topicId") Long topicId){
        List<Discussion> discussions = discussionService.getByTopicId(topicId);

        return Response.ok().entity(gson.toJson(discussions)).build();
    }
}
