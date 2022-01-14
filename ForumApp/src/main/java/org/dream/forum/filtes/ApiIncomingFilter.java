package org.dream.forum.filtes;

import org.dream.forum.utils.AuthorizationRoleEnum;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;



@Provider
public class ApiIncomingFilter implements ContainerRequestFilter {
    @Context ResourceInfo resourceInfo;
    @Context HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        AuthorizationRoleEnum requestingRole = AuthorizationRoleEnum.VISITOR;
        // User user = null;
        if (request.getParameter("wstoken") != null){
            String token = request.getParameter("wstoken");
            //In this case the API are RESTFul
        } else {
            //User authenticate with the session
            HttpSession session = request.getSession();
            if (!session.isNew() && session.getAttribute("user") != null) {
                // user = (User) session.getAttribute("user")
            }
        }

        /*
            if(user != null){ //A valid user exists
                //TODO:- Create the user role for further permission check
            }
         */

        if(resourceInfo.getResourceMethod() == null || resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class) == null){
            return; //No security constraints are required
        }

        RolesAllowed rolesAllowed = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class);

        if (!Arrays.asList(rolesAllowed.value()).contains(requestingRole.toString())) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return; //The user doesn't respect the required role
        }

    }
}