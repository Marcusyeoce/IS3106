/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.BookingEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.BookingEntity;
import entity.MemberEntity;
import entity.PromotionEntity;
import entity.ReviewEntity;
import entity.TagEntity;
import java.util.List;
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
import util.exception.BookingNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import ws.restful.model.CreateBookingReq;
import ws.restful.model.CreateBookingRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveBookingRsp;
import ws.restful.model.RetrieveBookingsByMemberRsp;

/**
 * REST Web Service
 *
 * @author Pham The Dzung
 */
@Path("Booking")
public class BookingResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private final BookingEntitySessionBeanLocal bookingEntitySessionBeanLocal;
    private final MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of BookingResource
     */
    public BookingResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        bookingEntitySessionBeanLocal = sessionBeanLookup.lookupBookingEntitySessionBeanLocal();
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
    }

    @Path("retrieveBookingsByMember")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBookingsByMember(@QueryParam("username") String username,
                                             @QueryParam("password") String password) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(username, password);
            System.out.println("*********** BookingResource.retrieveBookingsByMember(): Member" + member.getUsername() + " login remotely via ws");
            
            List<BookingEntity> bookings = bookingEntitySessionBeanLocal.retrieveBookingsByMember(member.getUsername());
            
            for (BookingEntity booking: bookings) {
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
                        review.getMemberEntity().getReviewEntities().clear();
                    }
                }
            }
            
            return Response.status(Status.OK).entity(new RetrieveBookingsByMemberRsp(bookings)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveBooking/{bookingId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBooking(@QueryParam("username") String username,
                                    @QueryParam("password") String password,
                                    @PathParam("bookingId") Long bookingId) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(username, password);
            System.out.println("*********** BookingResource.retrieveBooking(): Member" + member.getUsername() + " login remotely via ws");
            
            BookingEntity booking = bookingEntitySessionBeanLocal.retrieveBookingByBookingId(bookingId);
            
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
                    review.getMemberEntity().getReviewEntities().clear();
                }
            }
            
            return Response.status(Status.OK).entity(new RetrieveBookingRsp(booking)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (BookingNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("createBooking")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(CreateBookingReq createBookingReq) {
        if (createBookingReq != null) {
            try {
                MemberEntity member = memberEntitySessionBeanLocal.memberLogin(createBookingReq.getUsername(), createBookingReq.getPassword());
                System.out.println("*********** BookingResource.createBooking(): Member" + member.getUsername() + " login remotely via ws");
                
                CreateBookingRsp createBookingRsp = new CreateBookingRsp(bookingEntitySessionBeanLocal.createNewBooking(createBookingReq.getBooking(), 
                                                                                                                        createBookingReq.getUsername(), 
                                                                                                                        createBookingReq.getAttractionIds()));
                return Response.status(Status.OK).entity(createBookingRsp).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (InputDataValidationException | UnknownPersistenceException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create booking request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("cancelBooking")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelBooking(@QueryParam("username") String username,
                                  @QueryParam("password") String password,
                                  @PathParam("bookingId") Long bookingId) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(username, password);
            System.out.println("*********** BookingResource.cancelBooking(): Member" + member.getUsername() + " login remotely via ws");
            
            bookingEntitySessionBeanLocal.cancelBooking(bookingId);
            
            return Response.status(Status.OK).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (BookingNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}
