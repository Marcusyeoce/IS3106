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
    private String searchParam;
    private List<Long> tagIds;
    private BigDecimal priceLimit;
    private String visitDate;
    private String visitTime;

    public SearchAttractionsReq() {
    }
    
    public SearchAttractionsReq(String searchParam, List<Long> tagIds, BigDecimal priceLimit, String visitDate, String visitTime) {
        this.searchParam = searchParam;
        this.tagIds = tagIds;
        this.priceLimit = priceLimit;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
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

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

}
