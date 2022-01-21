package it.dreamplatform.forum.api;

import com.google.gson.Gson;
import it.dreamplatform.forum.entities.User;
import it.dreamplatform.forum.services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResource {
    @EJB(name = "it.dreamplatform.forum.services/UserService")
    private UserService userService;
    private final Gson gson = new Gson();

    @Context
    HttpServletRequest request;

    @GET
    @Produces("application/json")
    @RolesAllowed({"user", "policy_maker"})
    public Response getUser() {
        User user = (User) request.getSession().getAttribute("user");
        return Response.ok().entity(gson.toJson(user)).build();
    }

    @GET
    @Path("/{uid: [0-9]+}")
    @Produces("application/json")
    @RolesAllowed("policy_maker")
    public Response getUserById(@PathParam("uid") Long uid){
        User user = userService.findById(uid);
        if(user == null){
            return Response.status(204).entity("{}").build();
        }
        return Response.ok().entity(gson.toJson(user)).build();
    }

    @GET
    @Path("/{mail}")
    @Produces("application/json")
    @RolesAllowed("policy_maker")
    public Response getUserByEmail(@PathParam("mail") String mail){
        User user = userService.findByMail(mail);
        if(user == null){
            return Response.status(204).entity("{}").build();
        }
        return Response.ok().entity(gson.toJson(user)).build();
    }

    @GET
    @Path("/wstoken")
    @Produces("application/json")
    @RolesAllowed({"user", "policy_maker", "admin"})
    public String getWebServerToken() {
        return "NO WS TOKEN AVAILABLE";
    }



}
