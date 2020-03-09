package ejb.session.stateless;

import entity.BookingEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BookingEntitiySessionBean implements BookingEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    public Long createNewBooking(BookingEntity newBooking) {
        em.persist(newBooking);
        em.flush(); //because its using IDENTITY generation, need to flush then can get the ID back from the db management system 
        return newBooking.getBookingId(); 
    }

    public List<BookingEntity> retrieveAllBookings(){
        Query query = em.createQuery("SELECT b from BookingEntity b");
        return query.getResultList(); 
    } 

}
