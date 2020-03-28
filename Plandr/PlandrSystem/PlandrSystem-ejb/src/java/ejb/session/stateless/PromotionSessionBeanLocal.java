/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PromotionEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;

/**
 *
 * @author oimun
 */
@Local
public interface PromotionSessionBeanLocal {

    public Long createNewPromotionEntity(PromotionEntity newPromotionEntity) throws InputDataValidationException;

    public List<PromotionEntity> retrieveAllPromotions();

    public PromotionEntity retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException;

    public void updatePromotion(PromotionEntity promotionEntity) throws InputDataValidationException, PromotionNotFoundException;

    public void deletePromotion(Long promotionId) throws PromotionNotFoundException;
    
}
