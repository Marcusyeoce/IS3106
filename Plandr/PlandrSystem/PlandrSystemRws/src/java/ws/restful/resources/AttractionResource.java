/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.PromotionEntity;
import entity.ReviewEntity;
import entity.TagEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.AttractionNotFoundException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllAttractionsRsp;
import ws.restful.model.RetrieveAttractionRsp;
import ws.restful.model.SearchAttractionsReq;
import ws.restful.model.SearchAttractionsRsp;

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
    @Path("retrieveAllAttractions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAttractions() {
        
        try {
            System.out.println("********** AttractionResource.retrieveAllAttractions()");
            
            List<AttractionEntity> attractions = attractionEntitySessionBeanLocal.retrieveAllAttractions();
            
            for (AttractionEntity attraction: attractions) {
                attraction.setCompanyEntity(null);
                
                for (PromotionEntity promotion: attraction.getPromotionEntities()) {
                    promotion.getAttractionEntities().clear();
                }
                    
                for (TagEntity tag: attraction.getTagEntities()) {
                    tag.getAttractionEntities().clear();
                }
                    
                for (ReviewEntity review: attraction.getReviewEntities()) {
                    review.setAttractionEntity(null);
                    review.getMemberEntity().getReviewEntities().clear();
                }
            }
        
            RetrieveAllAttractionsRsp retrieveAllAttractionsRsp = new RetrieveAllAttractionsRsp(attractions);
        
            return Response.status(Status.OK).entity(retrieveAllAttractionsRsp).build();
        }
        catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAttraction/{attractionId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAttraction(@PathParam("attractionId") Long attractionId) {
        try {
            System.out.println("********** AttractionResource.retrieveAttraction()");
            
            AttractionEntity attraction = attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
            
            attraction.setCompanyEntity(null);    
            for (PromotionEntity promotion: attraction.getPromotionEntities()) {
                promotion.getAttractionEntities().clear();
            }

            for (TagEntity tag: attraction.getTagEntities()) {
                tag.getAttractionEntities().clear();
            }

            for (ReviewEntity review: attraction.getReviewEntities()) {
                review.setAttractionEntity(null);
                review.getMemberEntity().getReviewEntities().clear();
            }
            
            return Response.status(Status.OK).entity(new RetrieveAttractionRsp(attraction)).build();
        } catch (AttractionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("searchAttractions")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAttractions(SearchAttractionsReq searchAttractionReq) {
        if (searchAttractionReq != null) {
            try {
                System.out.println("*********** AttractionResource.searchAttractions()");
                List<AttractionEntity> attractions = attractionEntitySessionBeanLocal.searchAttractions(searchAttractionReq.getSearchParam(), 
                                                                                                        searchAttractionReq.getVisitDate(), 
                                                                                                        searchAttractionReq.getVisitTime(), 
                                                                                                        searchAttractionReq.getTagIds(), 
                                                                                                        searchAttractionReq.getPriceLimit());
                for (AttractionEntity attraction: attractions) {
                    attraction.setCompanyEntity(null);
                
                    for (PromotionEntity promotion: attraction.getPromotionEntities()) {
                        promotion.getAttractionEntities().clear();
                    }
                    
                    for (TagEntity tag: attraction.getTagEntities()) {
                        tag.getAttractionEntities().clear();
                    }
                    
                    for (ReviewEntity review: attraction.getReviewEntities()) {
                        review.setAttractionEntity(null);
                        review.getMemberEntity().getReviewEntities().clear();
                    }
                }
                
                return Response.status(Status.OK).entity(new SearchAttractionsRsp(attractions)).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid search request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
