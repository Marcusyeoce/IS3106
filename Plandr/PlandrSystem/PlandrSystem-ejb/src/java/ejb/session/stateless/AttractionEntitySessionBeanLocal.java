/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AttractionEntity;
import entity.EventEntity;
import entity.PlaceEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.AttractionNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;
import util.exception.TagNotFoundException;

/**
 *
 * @author oimun
 */
@Local
public interface AttractionEntitySessionBeanLocal {

    public List<AttractionEntity> retrieveAttractionsByCompanyId(Long companyId);

    public List<AttractionEntity> retrieveAllAttractions();

    public AttractionEntity retrieveAttractionByAttractionId(Long attractionId) throws AttractionNotFoundException;

    public void deleteAttraction(Long attractionId) throws AttractionNotFoundException;

    public Long createNewAttractionEntity(AttractionEntity newAttractionEntity, List<Long> tagIdsToAdd, List<Long> promotionIdsToAdd) throws InputDataValidationException, TagNotFoundException, PromotionNotFoundException;

    public List<EventEntity> retrieveAllEventAttractions();

    public List<PlaceEntity> retrieveAllPlaceAttractions();

    public Boolean isEvent(Long attractionId);

    public void updateAttraction(AttractionEntity attraction, List<Long> tagIdsToUpdate, List<Long> promotionIdsToUpdate) throws AttractionNotFoundException, InputDataValidationException, TagNotFoundException, PromotionNotFoundException;

    List<AttractionEntity> searchAttractions(String searchParam, Date visitDate, Date visitTime, List<Long> tagIds, BigDecimal priceLimit);
    
}
