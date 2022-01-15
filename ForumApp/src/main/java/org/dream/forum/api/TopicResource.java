package org.dream.forum.api;

import com.google.gson.Gson;
import org.dream.forum.entities.Topic;
import org.dream.forum.services.TopicService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/topic")
public class TopicResource {
   @EJB(name = "org.dream.forum.services/TopicService")
    private TopicService topicService;


    @GET
    @Produces("application/json")
    public Response getAllTopics() {
        List<Topic> topics = topicService.findAllTopics();
        Gson gson = new Gson();
        String resp = gson.toJson(topics);
        System.out.println(resp);
        return Response.ok().entity(resp).build();
    }


}
