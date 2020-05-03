/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import ejb.session.stateless.BookingEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.BookingEntity;
import entity.MemberEntity;
import entity.PromotionEntity;
import entity.ReviewEntity;
import entity.TagEntity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import ws.restful.model.CancelBookingReq;
import ws.restful.model.CreateBookingReq;
import ws.restful.model.CreateBookingRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.GenerateBookingReq;
import ws.restful.model.GenerateBookingRsp;
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
    private final AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;
    
    /**
     * Creates a new instance of BookingResource
     */
    public BookingResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        bookingEntitySessionBeanLocal = sessionBeanLookup.lookupBookingEntitySessionBeanLocal();
        memberEntitySessionBeanLocal = sessionBeanLookup.lookupMemberEntitySessionBeanLocal();
        attractionEntitySessionBeanLocal = sessionBeanLookup.lookupAttractionEntitySessionBeanLocal();
    }

    @Path("retrieveBookingsByMember")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBookingsByMember(@QueryParam("username") String username,
                                             @QueryParam("password") String password) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(username, password);
            System.out.println("*********** BookingResource.retrieveBookingsByMember(): Member " + member.getUsername() + " login remotely via ws");
            
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
            System.out.println("*********** BookingResource.retrieveBooking(): Member " + member.getUsername() + " login remotely via ws");
            
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
                System.out.println("*********** BookingResource.createBooking(): Member " + member.getUsername() + " login remotely via ws");
                
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelBooking(CancelBookingReq cancelBookingReq) {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.memberLogin(cancelBookingReq.getUsername(), cancelBookingReq.getPassword());
            System.out.println("*********** BookingResource.cancelBooking(): Member " + member.getUsername() + " login remotely via ws");
            
            bookingEntitySessionBeanLocal.cancelBooking(cancelBookingReq.getBookingId());
            
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
    
    @Path("generateBooking")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateBooking(GenerateBookingReq generateBookingReq) {
        if (generateBookingReq != null) {
            try {
                MemberEntity member = memberEntitySessionBeanLocal.memberLogin(generateBookingReq.getUsername(), generateBookingReq.getPassword());
                System.out.println("*********** BookingResource.generateBooking(): Member " + member.getUsername() + " login remotely via ws");
                
                if (generateBookingReq.getPriceLimit().compareTo(BigDecimal.ZERO) < 0) {
                    ErrorRsp errorRsp = new ErrorRsp("Invalid price input");

                    return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
                }
                
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date startTime = format.parse(generateBookingReq.getStartTime());
                Date endTime = format.parse(generateBookingReq.getEndTime());
                if (endTime.getTime() <= startTime.getTime()) {
                    ErrorRsp errorRsp = new ErrorRsp("Invalid time input");

                    return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
                }

                List<AttractionEntity> attractions = attractionEntitySessionBeanLocal.generateBookingAttractions(generateBookingReq.getPriceLimit(),
                                                                                                                 generateBookingReq.getTagIds(),
                                                                                                                 new SimpleDateFormat("yyyy-MM-dd").parse(generateBookingReq.getVisitDate()),
                                                                                                                 startTime, endTime);
                //Generate a random list of attractions, 1 attraction for each 3-hour interval
                BigDecimal bestPromotion = BigDecimal.ZERO;
                BigDecimal totalTicketPrice = BigDecimal.ZERO;
                List<Long> attractionIds = new ArrayList<>();
                
                for (AttractionEntity attraction: attractions) {
                    attractionIds.add(attraction.getAttractionId());
                    totalTicketPrice = totalTicketPrice.add(attraction.getUnitPrice());
                    attraction.setCompanyEntity(null);
                
                    for (PromotionEntity promotion: attraction.getPromotionEntities()) {
                        promotion.getAttractionEntities().clear();
                        if (promotion.getDiscount().compareTo(bestPromotion) > 0) {
                            bestPromotion = promotion.getDiscount();
                        }
                    }
                    
                    for (TagEntity tag: attraction.getTagEntities()) {
                        tag.getAttractionEntities().clear();
                    }
                    
                    for (ReviewEntity review: attraction.getReviewEntities()) {
                        review.setAttractionEntity(null);
                        review.getMemberEntity().getReviewEntities().clear();
                    }
                }
    
                BigDecimal multiplier = new BigDecimal(generateBookingReq.getNumPax());
                if (totalTicketPrice.compareTo(bestPromotion) < 0) {
                    totalTicketPrice = BigDecimal.ZERO;
                } else {
                    totalTicketPrice = totalTicketPrice.multiply(multiplier);
                    totalTicketPrice = totalTicketPrice.subtract(bestPromotion);
                }
                
                return Response.status(Status.OK).entity(new GenerateBookingRsp(attractions, totalTicketPrice, attractionIds)).build();
                
            } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid generate booking request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
