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
public class CreateBookingReq {
    private String username;
    private String password;
    private BookingEntity booking;
    private List<Long> attractionIds;

    public CreateBookingReq() {
    }

    public CreateBookingReq(String username, String password, BookingEntity booking, List<Long> attractionIds) {
        this.username = username;
        this.password = password;
        this.booking = booking;
        this.attractionIds = attractionIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getAttractionIds() {
        return attractionIds;
    }

    public void setAttractionIds(List<Long> attractionIds) {
        this.attractionIds = attractionIds;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }
    
    
}
