package it.dreamplatform.data.dataapp.api;

import it.dreamplatform.data.dataapp.bean.DistrictBean;
import it.dreamplatform.data.dataapp.controller.DataController;
import it.dreamplatform.data.dataapp.entity.DataSource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/data")
public class DataResource {
    @Inject
    private DataController dataController;

    @Context
    HttpServletRequest request;
    @POST
    @Path("/ranking")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response createRanking(List< DataSource > dataSources, DistrictBean district){
        try {
            dataController.createRanking(dataSources, district);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }
}
