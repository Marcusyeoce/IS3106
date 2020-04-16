/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.BookingEntity;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveBookingsByMemberRsp {
    private List<BookingEntity> bookings;

    public RetrieveBookingsByMemberRsp() {
    }

    public RetrieveBookingsByMemberRsp(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }
    
    
}
