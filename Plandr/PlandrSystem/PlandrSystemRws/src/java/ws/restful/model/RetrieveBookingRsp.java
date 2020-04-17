/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.BookingEntity;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveBookingRsp {
    private BookingEntity booking;

    public RetrieveBookingRsp() {
    }

    public RetrieveBookingRsp(BookingEntity booking) {
        this.booking = booking;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }
    
    
}
