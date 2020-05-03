/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.AttractionEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class GenerateBookingRsp {
    private List<AttractionEntity> attractions;
    private BigDecimal totalTicketPrice;
    private List<Long> attractionIds;

    public GenerateBookingRsp() {
    }

    public GenerateBookingRsp(List<AttractionEntity> attractions, BigDecimal totalTicketPrice, List<Long> attractionIds) {
        this.attractions = attractions;
        this.totalTicketPrice = totalTicketPrice;
        this.attractionIds = attractionIds;
    }

    public List<AttractionEntity> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<AttractionEntity> attractions) {
        this.attractions = attractions;
    }

    public BigDecimal getTotalTicketPrice() {
        return totalTicketPrice;
    }

    public void setTotalTicketPrice(BigDecimal totalTicketPrice) {
        this.totalTicketPrice = totalTicketPrice;
    }

    public List<Long> getAttractionIds() {
        return attractionIds;
    }

    public void setAttractionIds(List<Long> attractionIds) {
        this.attractionIds = attractionIds;
    }
    
    
}
