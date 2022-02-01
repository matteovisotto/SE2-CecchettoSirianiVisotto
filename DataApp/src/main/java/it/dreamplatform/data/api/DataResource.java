package it.dreamplatform.data.api;

import com.google.gson.Gson;
import it.dreamplatform.data.bean.DistrictBean;
import it.dreamplatform.data.controller.DataController;
import it.dreamplatform.data.entity.DataSource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
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

    private final Gson gson = new Gson();

    @POST
    @Path("/ranking")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response createRanking(List<DataSource> dataSources, DistrictBean district){
        try {
            dataController.createRanking(dataSources, district);
            return Response.ok().entity("{\"success\":1}").build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    @GET
    @Path("/district/{districtId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response createFile(@PathParam("districtId") String districtId){
        String result = dataController.retrieveDistrict(districtId);
        return Response.ok().entity(gson.toJson(result)).build();
    }
}
