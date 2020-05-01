/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import entity.AttractionEntity;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllAttractionsRsp;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Attraction")
public class AttractionResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private final AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;

    /**
     * Creates a new instance of AttractionResource
     */
    public AttractionResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        attractionEntitySessionBeanLocal = sessionBeanLookup.lookupAttractionEntitySessionBeanLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.AttractionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAttractions() {
        
        try {
            List<AttractionEntity> attractions = attractionEntitySessionBeanLocal.retrieveAllAttractions();
        
            RetrieveAllAttractionsRsp retrieveAllAttractionsRsp = new RetrieveAllAttractionsRsp(attractions);
        
            return Response.status(Status.OK).entity(retrieveAllAttractionsRsp).build();
        }
        catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
        
    }
}
