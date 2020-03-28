package ejb.session.stateless;

import entity.BookingEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookingNotFoundException;

@Local
public interface BookingEntitySessionBeanLocal {
    public Long createNewBooking(BookingEntity newBookingEntity);
    public List<BookingEntity> retrieveAllBookings();
    public BookingEntity retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException;
    public void deleteBooking(Long bookingId) throws BookingNotFoundException;
}
