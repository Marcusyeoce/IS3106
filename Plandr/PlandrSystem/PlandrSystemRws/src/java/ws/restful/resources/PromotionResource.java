/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import entity.PromotionEntity;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllPromotionsRsp;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Promotion")
public class PromotionResource {
    
    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final PromotionEntitySessionBeanLocal promotionEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of PromotionResource
     */
    public PromotionResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        promotionEntitySessionBeanLocal = sessionBeanLookup.lookupPromotionSessionBeanLocal();
    }

    @Path("retrieveAllPromotions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPromotions() {
        try {
            System.out.println("********** PromotionResource.retrieveAllPromotions()");

            List<PromotionEntity> promotions = promotionEntitySessionBeanLocal.retrieveAllPromotions();

            for(PromotionEntity promotion: promotions) {
               promotion.getAttractionEntities().clear();
            }

            return Response.status(Status.OK).entity(new RetrieveAllPromotionsRsp(promotions)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

}
