package org.dream.forum.api;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("/user")
public class UserResource {
    @Context
    HttpServletRequest request;

    @GET
    @Produces("application/json")
    @RolesAllowed({"user", "policy_maker"})
    public String getUser() {
        return "Current user info";
    }

    @GET
    @Path("/{uid: [0-9]+}")
    @Produces("application/json")
    @RolesAllowed("policy_maker")
    public String getUserById(@PathParam("uid") Long uid){
        return "User with id " + uid;
    }

    @GET
    @Path("/{mail}")
    @Produces("application/json")
    @RolesAllowed("policy_maker")
    public String getUserByEmail(@PathParam("mail") String mail){
        return "User with mail " + mail;
    }

    @GET
    @Path("/wstoken")
    @Produces("application/json")
    @RolesAllowed({"user", "policy_maker", "admin"})
    public String getWebServerToken() {
        return "";
    }



}
