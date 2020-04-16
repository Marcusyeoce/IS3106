/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.AttractionEntity;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveAttractionRsp {
    private AttractionEntity attraction;

    public RetrieveAttractionRsp() {
    }

    public RetrieveAttractionRsp(AttractionEntity attraction) {
        this.attraction = attraction;
    }

    public AttractionEntity getAttraction() {
        return attraction;
    }

    public void setAttraction(AttractionEntity attraction) {
        this.attraction = attraction;
    }
    
    
}
