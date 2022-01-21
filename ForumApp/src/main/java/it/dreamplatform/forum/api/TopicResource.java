package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.entities.Topic;
import it.dreamplatform.forum.services.TopicService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/topic")
public class TopicResource {
   @EJB(name = "it.dreamplatform.forum.services/TopicService")
    private TopicService topicService;
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getAllTopics() {
        List<Topic> topics = topicService.findAllTopics();
        String resp = gson.toJson(topics);
        return Response.ok().entity(resp).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{topicId: [0-9]+}")
    public Response getTopicById(@PathParam("topicId") Long topicId){
        Topic topic = topicService.findTopicById(topicId);
        return Response.ok().entity(gson.toJson(topic)).build();
    }

}
