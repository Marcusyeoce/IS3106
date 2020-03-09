package ejb.session.stateless;

import entity.BookingEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author celes
 */
@Local
public interface BookingEntitySessionBeanLocal {
    public Long createNewBooking(BookingEntity newBookingEntity);
    public List<BookingEntity> retrieveAllBookings();
}
