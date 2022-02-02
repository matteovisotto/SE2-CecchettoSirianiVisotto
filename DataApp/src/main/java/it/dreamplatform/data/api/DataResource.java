package it.dreamplatform.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.dreamplatform.data.bean.DistrictBean;
import it.dreamplatform.data.bean.RankingBean;
import it.dreamplatform.data.controller.DataController;
import it.dreamplatform.data.entity.DataSource;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Path("/data")
public class DataResource {
    @Inject
    private DataController dataController;

    @Context
    HttpServletRequest request;

    private final Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    @POST
    @Path("/ranking")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response createRanking(/*@FormParam("dataSources") List<DataSource> dataSources,*/ @FormParam("districtId") String districtId){
        try {
            List<RankingBean> rankings = dataController.createRanking(new ArrayList<>(), districtId);
            return Response.ok().entity(gson.toJson(rankings)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    @GET
    @Path("/district/{districtId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response createFile(@PathParam("districtId") String districtId) {
        try {
            DistrictBean districtBean = dataController.createSingleDistrict(districtId);
            return Response.ok().entity(districtBean.getName()/*"{\"success\":1}"*/).build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }
}
