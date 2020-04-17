/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.AttractionEntity;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class SearchAttractionsRsp {
    private List<AttractionEntity> attractions;

    public SearchAttractionsRsp() {
    }

    public SearchAttractionsRsp(List<AttractionEntity> attractions) {
        this.attractions = attractions;
    }

    public List<AttractionEntity> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<AttractionEntity> attractions) {
        this.attractions = attractions;
    }
    
    
}
