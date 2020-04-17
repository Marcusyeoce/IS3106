/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PromotionEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AttractionNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;

/**
 *
 * @author oimun
 */
@Local
public interface PromotionEntitySessionBeanLocal {

    public List<PromotionEntity> retrieveAllPromotions();

    public PromotionEntity retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException;

    public void deletePromotion(Long promotionId) throws PromotionNotFoundException;

    public Long createNewPromotionEntity(PromotionEntity newPromotionEntity, List<Long> attractionIdsToAdd) throws InputDataValidationException, AttractionNotFoundException;

    public void updatePromotion(PromotionEntity promotionEntity, List<Long> attractionIdsToUpdate) throws InputDataValidationException, PromotionNotFoundException, AttractionNotFoundException;
    
}
