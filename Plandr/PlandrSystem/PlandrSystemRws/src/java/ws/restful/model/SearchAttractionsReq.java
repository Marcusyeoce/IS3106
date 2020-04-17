/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class SearchAttractionsReq {
    private String username;
    private String password;
    private List<Long> tagIds;
    private BigDecimal priceLimit;
    private Date visitDate;
    private Date visitTime;

    public SearchAttractionsReq() {
    }
    
    public SearchAttractionsReq(String username, String password, List<Long> tagIds, BigDecimal priceLimit, Date visitDate, Date visitTime) {
        this.username = username;
        this.password = password;
        this.tagIds = tagIds;
        this.priceLimit = priceLimit;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
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

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public BigDecimal getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
    
    
}
