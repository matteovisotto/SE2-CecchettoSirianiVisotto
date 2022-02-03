package it.dreamplatform.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.dreamplatform.data.bean.DataBean;
import it.dreamplatform.data.bean.DataSetBean;
import it.dreamplatform.data.bean.DistrictBean;
import it.dreamplatform.data.bean.RankingBean;
import it.dreamplatform.data.controller.DataController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This class contains the api call from the "/data" path.
 */
@Path("/data")
public class DataResource {
    @Inject
    private DataController dataController;

    @Context
    HttpServletRequest request;

    private final Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    /**
     * This function is the api used to retrieve the ranking of a district according to all the dataset present at DB, by going at "/data/ranking".
     * @param districtId is the id of the District.
     * @return a response with a JSON format of the ranking retrieved.
     */
    @POST
    @Path("/ranking")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @RolesAllowed("policy_maker")
    public Response createRanking(@FormParam("districtId") String districtId){
        try {
            List<RankingBean> rankings = dataController.createRanking(districtId);
            return Response.ok().entity(gson.toJson(rankings)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    /**
     * This function is the api used to retrieve a searched district by going at "/data/district/{districtId}".
     * @param districtId is the id of the District.
     * @return a response with a JSON format of the searched district.
     */
    @GET
    @Path("/district/{districtId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response retrieveDistrict(@PathParam("districtId") String districtId) {
        try {
            DistrictBean districtBean = dataController.createSingleDistrict(districtId);
            return Response.ok().entity(gson.toJson(districtBean)).build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }

    /**
     * This function is the api used the dataSet of a searched district by going at "/data/datasets/{districtId}".
     * @param districtId is the id of the District.
     * @return a response with a JSON format of the searched dataset.
     */
    @GET
    @Path("/datasets/{districtId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response retrieve(@PathParam("districtId") String districtId) {
        try {
            List<List<DataBean>> dataBeansOfDistrict = dataController.getDataOfDistrict(districtId);
            //DistrictBean districtBean = dataController.createSingleDistrict(districtId);
            return Response.ok().entity(gson.toJson(dataBeansOfDistrict)).build();
        } catch (Exception e) {
            return Response.status(400).entity("{\"success\":0}").build();
        }
    }
}
