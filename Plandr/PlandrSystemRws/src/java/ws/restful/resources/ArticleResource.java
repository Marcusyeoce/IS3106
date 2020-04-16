/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ArticleEntitySessionBeanLocal;
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
@Path("Article")
public class ArticleResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private final ArticleEntitySessionBeanLocal articleEntitySessionBeanLocal;

    /**
     * Creates a new instance of ArticleResource
     */
    public ArticleResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        articleEntitySessionBeanLocal = sessionBeanLookup.lookupArticleEntitySessionBeanLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.ArticleResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ArticleResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
}
