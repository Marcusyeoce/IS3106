/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class GenerateBookingReq {
    private String username;
    private String password;
    private int numPax;
    private BigDecimal priceLimit;
    private List<Long> tagIds;
    private String visitDate;
    private String startTime;
    private String endTime;

    public GenerateBookingReq() {
    }

    public GenerateBookingReq(String username, String password, int numPax, BigDecimal priceLimit, List<Long> tagIds, String visitDate, String startTime, String endTime) {
        this.username = username;
        this.password = password;
        this.numPax = numPax;
        this.priceLimit = priceLimit;
        this.tagIds = tagIds;
        this.visitDate = visitDate;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public BigDecimal getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNumPax() {
        return numPax;
    }

    public void setNumPax(int numPax) {
        this.numPax = numPax;
    }
    
}
