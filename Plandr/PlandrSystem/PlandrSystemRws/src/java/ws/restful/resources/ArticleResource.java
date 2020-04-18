/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ArticleEntitySessionBeanLocal;
import entity.ArticleEntity;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.ArticleNotFoundException;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllArticlesRsp;
import ws.restful.model.RetrieveArticleRsp;

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

    @Path("retrieveAllArticles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllArticles() {
        try {
            System.out.println("********** ArticleResource.retrieveAllArticles()");
            
            List<ArticleEntity> articles = articleEntitySessionBeanLocal.retrieveAllArticles();
            
            for (ArticleEntity article: articles) {
                article.setStaffEntity(null);
            }
            
            return Response.status(Status.OK).entity(new RetrieveAllArticlesRsp(articles)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveArticle/{articleId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveArticle(@PathParam("articleId") Long articleId) {
        try {
            System.out.println("********** ArticleResource.retrieveArticle()");
            
            ArticleEntity article = articleEntitySessionBeanLocal.retrieveArticleByArticleId(articleId);
            article.setStaffEntity(null);
            
            return Response.status(Status.OK).entity(new RetrieveArticleRsp(article)).build();
        } catch (ArticleNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
}
