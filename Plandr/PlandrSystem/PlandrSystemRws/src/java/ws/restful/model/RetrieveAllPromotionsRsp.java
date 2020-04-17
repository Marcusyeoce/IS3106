/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.PromotionEntity;
import java.util.List;

/**
 *
 * @author Pham The Dzung
 */
public class RetrieveAllPromotionsRsp {
    private List<PromotionEntity> promotions;

    public RetrieveAllPromotionsRsp() {
    }

    public RetrieveAllPromotionsRsp(List<PromotionEntity> promotions) {
        this.promotions = promotions;
    }

    public List<PromotionEntity> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionEntity> promotions) {
        this.promotions = promotions;
    }
    
    
}
