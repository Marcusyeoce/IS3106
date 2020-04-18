package ejb.session.stateless;

import entity.BookingEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookingNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.MemberNotFoundException;
import util.exception.UnknownPersistenceException;

@Local
public interface BookingEntitySessionBeanLocal {
    public Long createNewBooking(BookingEntity newBookingEntity, String username, List<Long> attractionIds) throws InputDataValidationException, UnknownPersistenceException;
    
    public List<BookingEntity> retrieveBookingsByMember(String username) throws MemberNotFoundException;
    
    public BookingEntity retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException;
    
    public void deleteBooking(Long bookingId) throws BookingNotFoundException;

    void cancelBooking(Long bookingId) throws BookingNotFoundException;
}
