/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author Pham The Dzung
 */
public class CancelBookingReq {
    private String username;
    private String password;
    private Long bookingId;

    public CancelBookingReq() {
    }

    public CancelBookingReq(String username, String password, Long bookingId) {
        this.username = username;
        this.password = password;
        this.bookingId = bookingId;
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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    
    
}
