package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.bean.DiscussionBean;
import it.dreamplatform.forum.bean.DiscussionContentBean;
import it.dreamplatform.forum.controller.DiscussionController;
import it.dreamplatform.forum.entities.Discussion;
import it.dreamplatform.forum.services.DiscussionService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/discussion")
public class DiscussionResource {
    @Inject
    private DiscussionController discussionController;
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/{discussionId: [0-9]+}")
    public Response getByDiscussionId(@PathParam("discussionId") Long discussionId){
        DiscussionContentBean posts = discussionController.getPostByDiscussionId(discussionId);
        return Response.ok().entity(gson.toJson(posts)).build();
    }

    @GET
    @Path("/policyMaker/{policyMakerId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response getDiscussionByPolicyMaker(@PathParam("policyMakerId") String policyMakerId){
        try {
            List<DiscussionBean> discussions = discussionController.getDiscussionByPolicyMaker(policyMakerId);
            return Response.ok().entity(gson.toJson(discussions)).build();
        } catch (Exception e) {
            return Response.status(204).entity("[]").build();
        }
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response createDiscussion(DiscussionContentBean discussion){
        try {
            discussionController.createDiscussion(discussion);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    @POST
    @Path("/delete/{discussionId:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response deleteDiscussion(@PathParam("discussionId") Long discussionId){
        try {
            discussionController.deleteDiscussion(discussionId);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }
}
