package ejb.session.stateless;

import entity.BookingEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BookingNotFoundException;

@Stateless
public class BookingEntitySessionBean implements BookingEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewBooking(BookingEntity newBooking) {
        em.persist(newBooking);
        em.flush(); //because its using IDENTITY generation, need to flush then can get the ID back from the db management system 
        return newBooking.getBookingId(); 
    }

    @Override
    public List<BookingEntity> retrieveAllBookings(){
        Query query = em.createQuery("SELECT b from BookingEntity b");
        return query.getResultList(); 
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
}
