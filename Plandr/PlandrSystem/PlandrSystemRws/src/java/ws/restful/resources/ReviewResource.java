/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.ReviewEntitySessionBeanLocal;
import entity.MemberEntity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import ws.restful.model.CreateReviewReq;
import ws.restful.model.CreateReviewRsp;
import ws.restful.model.ErrorRsp;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Review")
public class ReviewResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final ReviewEntitySessionBeanLocal reviewEntitySessionBeanLocal;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        reviewEntitySessionBeanLocal = sessionBeanLookup.lookupReviewEntitySessionBeanLocal();
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
    }

    @Path("createReview")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(CreateReviewReq createReviewReq) {
        if (createReviewReq != null) {
            try {
                MemberEntity member = memberEntitySessionBeanLocal.memberLogin(createReviewReq.getUsername(), createReviewReq.getPassword());
                System.out.println("*********** ReviewResource.createReview(): Member " + member.getUsername() + " login remotely via ws");
                
                CreateReviewRsp createReviewRsp = new CreateReviewRsp(reviewEntitySessionBeanLocal.createNewReview(createReviewReq.getReview(), createReviewReq.getAttractionId(), createReviewReq.getUsername()));
                
                return Response.status(Status.OK).entity(createReviewRsp).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new review request");

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

}
