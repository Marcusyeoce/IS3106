/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

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
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AttractionResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

}
