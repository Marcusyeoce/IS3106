/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.BookingEntity;
import entity.MemberEntity;
import entity.PromotionEntity;
import entity.ReviewEntity;
import entity.TagEntity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;
import util.exception.PasswordMismatchException;
import util.exception.UpdateMemberException;
import ws.restful.model.CreateMemberReq;
import ws.restful.model.CreateMemberRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.MemberLoginRsp;
import ws.restful.model.MemberSubscribeReq;
import ws.restful.model.MemberSubscribeRsp;
import ws.restful.model.UpdateMemberPasswordReq;
import ws.restful.model.UpdateMemberReq;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Member")
public class MemberResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of MemberResource
     */
    public MemberResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
    }

    @Path("memberLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberLogin(@QueryParam("username") String username,
                                @QueryParam("password") String password) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(username, password);
            System.out.println("*********** MemberResource.memberLogin(): Member " + member.getUsername() + " login remotely via ws");
            
            member.setPassword(null);
            member.setSalt(null);
            member.getReviewEntities().clear();
            for (BookingEntity booking: member.getBookingEntities()) {
                booking.setMemberEntity(null);
                
                for (AttractionEntity attraction: booking.getAttractionEntities()) {
                    attraction.setCompanyEntity(null);
                    
                    for (PromotionEntity promotion: attraction.getPromotionEntities()) {
                        promotion.getAttractionEntities().clear();
                    }
                    
                    for (TagEntity tag: attraction.getTagEntities()) {
                        tag.getAttractionEntities().clear();
                    }
                    
                    for (ReviewEntity review: attraction.getReviewEntities()) {
                        review.setAttractionEntity(null);
                    }
                }
            }
            
            return Response.status(Status.OK).entity(new MemberLoginRsp(member)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("registerMember")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(CreateMemberReq createMemberReq) {
        if (createMemberReq != null) {
            try {
                System.out.println("********** MemberResource.createMember()");
                
                CreateMemberRsp createMemberRsp = new CreateMemberRsp(memberEntitySessionBeanLocal.createNewMember(createMemberReq.getMember()));
                
                return Response.status(Status.OK).entity(createMemberRsp).build();
            } catch (InputDataValidationException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid register request");

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("updateProfile")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(UpdateMemberReq updateMemberReq) {
        if (updateMemberReq != null) {
            try {
                MemberEntity member = memberEntitySessionBeanLocal.memberLogin(updateMemberReq.getUsername(), updateMemberReq.getPassword());
                System.out.println("*********** MemberResource.updateProfile(): Member " + member.getUsername() + " login remotely via ws");

                memberEntitySessionBeanLocal.updateMember(updateMemberReq.getMember());
                
                return Response.status(Status.OK).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (UpdateMemberException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update profile request");
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("updateMemberPassword")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMemberPassword(UpdateMemberPasswordReq updateMemberPasswordReq) {
        if (updateMemberPasswordReq != null) {
            try {
                MemberEntity member = memberEntitySessionBeanLocal.memberLogin(updateMemberPasswordReq.getUsername(), 
                                                                               updateMemberPasswordReq.getPassword());
                System.out.println("*********** MemberResource.updateMemberPassword(): Member " + member.getUsername() + " login remotely via ws");
                memberEntitySessionBeanLocal.updateMemberPassword(updateMemberPasswordReq.getUsername(), 
                                                                  updateMemberPasswordReq.getPassword(), 
                                                                  updateMemberPasswordReq.getNewPassword(), 
                                                                  updateMemberPasswordReq.getRePassword());
                
                return Response.status(Status.OK).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (MemberNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            } catch (PasswordMismatchException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid change password request");
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("memberSubscribe")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response memberSubscribe(MemberSubscribeReq memberSubscribeReq) {
        if (memberSubscribeReq != null) {
            try {
                MemberEntity member = memberEntitySessionBeanLocal.memberLogin(memberSubscribeReq.getUsername(), 
                                                                               memberSubscribeReq.getPassword());
                System.out.println("*********** MemberResource.memberSubscribe(): Member " + member.getUsername() + " login remotely via ws");

                memberEntitySessionBeanLocal.memberSubscribe(memberSubscribeReq.getUsername(), memberSubscribeReq.getSubPackage());

                MemberEntity subscribedMember = memberEntitySessionBeanLocal.retrieveMemberByUsername(member.getUsername());
                
                return Response.status(Status.OK).entity(new MemberSubscribeRsp(subscribedMember.getSubscribedUntil())).build();
            } catch (MemberNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid subscribe request");
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
