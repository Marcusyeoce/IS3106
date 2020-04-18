/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.TagEntity;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllTagsRsp;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Tag")
public class TagResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final TagEntitySessionBeanLocal tagEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of TagResource
     */
    public TagResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        tagEntitySessionBeanLocal = sessionBeanLookup.lookupTagEntitySessionBeanLocal();
    }

    @Path("retrieveAllTags")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags() {
        try {
            System.out.println("********** TagResource.retrieveAllTags()");

            List<TagEntity> tags = tagEntitySessionBeanLocal.retrieveAllTags();

            for(TagEntity tag: tags) {
               tag.getAttractionEntities().clear();
            }

            return Response.status(Status.OK).entity(new RetrieveAllTagsRsp(tags)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

}
