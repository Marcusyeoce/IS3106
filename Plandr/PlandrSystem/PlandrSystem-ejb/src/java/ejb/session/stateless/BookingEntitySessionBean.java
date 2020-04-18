package ejb.session.stateless;

import entity.AttractionEntity;
import entity.BookingEntity;
import entity.MemberEntity;
import entity.PromotionEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.UnknownPersistenceException;
import util.exception.BookingNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.MemberNotFoundException;

@Stateless
public class BookingEntitySessionBean implements BookingEntitySessionBeanLocal {

    @EJB(name = "MemberEntitySessionBeanLocal")
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;
    
    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BookingEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewBooking(BookingEntity newBooking, String username, List<Long> attractionIds) throws InputDataValidationException, UnknownPersistenceException {
        Set<ConstraintViolation<BookingEntity>>constraintViolations = validator.validate(newBooking);
        
        if(constraintViolations.isEmpty())
        {          
            try
            {
                if(attractionIds != null && (!attractionIds.isEmpty()) && username != null)
                    {
                        for(Long attractionId: attractionIds)
                        {
                            AttractionEntity attractionEntity = attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
                            newBooking.getAttractionEntities().add(attractionEntity);
                        }
                        
                        MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberByUsername(username);
                        newBooking.setMemberEntity(memberEntity);
                        memberEntity.getBookingEntities().add(newBooking);
                    }
                em.persist(newBooking);
                em.flush();

                return newBooking.getBookingId();
            }catch(Exception ex){
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<BookingEntity> retrieveBookingsByMember(String username) throws MemberNotFoundException {
        try {
            MemberEntity member = memberEntitySessionBeanLocal.retrieveMemberByUsername(username);
            
            Query query = em.createQuery("SELECT b FROM BookingEntity b WHERE b.memberEntity.username = :inUsername ORDER BY b.bookingId ASC");
            query.setParameter("inUsername", member.getUsername());
            List<BookingEntity> bookings = query.getResultList();
            
            return bookings;
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Member does not exist!");
        }
    } 
    
    @Override
    public BookingEntity retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException {
        BookingEntity bookingEntity = em.find(BookingEntity.class, bookingId);
        
        if(bookingEntity != null)
        {            
            return bookingEntity;
        }
        else
        {
            throw new BookingNotFoundException("Booking ID " + bookingId + " does not exist!");
        }                
    }
    
    //on server side will show the amount going to be refunded
    @Override
    public void deleteBooking(Long bookingId) throws BookingNotFoundException
    {
        BookingEntity bookingEntity = retrieveBookingByBookingId(bookingId);
        
        em.remove(bookingEntity);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<BookingEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }

    @Override
    public void cancelBooking(Long bookingId) throws BookingNotFoundException {
        try {
            BookingEntity booking = retrieveBookingByBookingId(bookingId);
            
            booking.setCancelled(true);
        } catch (BookingNotFoundException ex) {
            throw new BookingNotFoundException(ex.getMessage());
        }
    }
}
