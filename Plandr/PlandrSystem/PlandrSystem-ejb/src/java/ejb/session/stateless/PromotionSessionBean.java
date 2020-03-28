package ejb.session.stateless;

import entity.AttractionEntity;
import entity.PromotionEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;

/**
 *
 * @author oimun
 */
@Stateless
public class PromotionSessionBean implements PromotionSessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public PromotionSessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
     public Long createNewPromotionEntity(PromotionEntity newPromotionEntity) throws InputDataValidationException
    {
        Set<ConstraintViolation<PromotionEntity>>constraintViolations = validator.validate(newPromotionEntity);
        
        if(constraintViolations.isEmpty())
        {
            List<AttractionEntity> attractionEntities = newPromotionEntity.getAttractionEntities();
            for(AttractionEntity attraction: attractionEntities)
            {
                attraction.getPromotionEntities().add(newPromotionEntity);
            }
            
            em.persist(newPromotionEntity);
            em.flush();

            return newPromotionEntity.getPromotionId();
            
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<PromotionEntity> retrieveAllPromotions()
    {
        Query query = em.createQuery("SELECT p FROM PromotionEntity p ORDER BY p.name ASC");
        List<PromotionEntity> promotionEntities = query.getResultList();
        
        for(PromotionEntity promotion : promotionEntities)
        {            
            promotion.getAttractionEntities().size();
        }
        
        return promotionEntities;
    }
    
    @Override
    public PromotionEntity retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException
    {
        PromotionEntity promotionEntity = em.find(PromotionEntity.class, promotionId);
        
        if(promotionEntity != null)
        {
            //Fetching
            promotionEntity.getAttractionEntities().size();
            
            return promotionEntity;
        }
        else
        {
            throw new PromotionNotFoundException("Promotion ID " + promotionId + " does not exist!");
        }               
    }
    
    @Override
    public void updatePromotion(PromotionEntity promotionEntity) throws InputDataValidationException, PromotionNotFoundException
    {
        Set<ConstraintViolation<PromotionEntity>>constraintViolations = validator.validate(promotionEntity);
        
        if(constraintViolations.isEmpty())
        {
            if(promotionEntity.getPromotionId()!= null)
            {
                PromotionEntity promotionEntityToUpdate = retrievePromotionByPromotionId(promotionEntity.getPromotionId());
                
                
                promotionEntityToUpdate.setDiscount(promotionEntity.getDiscount());
                promotionEntityToUpdate.setStartDate(promotionEntity.getStartDate());
                promotionEntityToUpdate.setEndDate(promotionEntity.getEndDate());
                
                List<AttractionEntity> attractionEntities = promotionEntity.getAttractionEntities();
                promotionEntityToUpdate.setAttractionEntities(attractionEntities);
                for(AttractionEntity attraction: attractionEntities)
                {
                    attraction.getPromotionEntities().add(promotionEntityToUpdate);
                }                            
            }
            else
            {
                throw new PromotionNotFoundException("Promotion ID not provided for tag to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deletePromotion(Long promotionId) throws PromotionNotFoundException
    {
        PromotionEntity promotionEntityToRemove = retrievePromotionByPromotionId(promotionId);
        
        List<AttractionEntity> attractionEntities = promotionEntityToRemove.getAttractionEntities();
        
        if(!attractionEntities.isEmpty())
        {
            for(AttractionEntity attraction : attractionEntities)
            {
                attraction.getPromotionEntities().size();
                attraction.getPromotionEntities().remove(promotionEntityToRemove);
            }
            em.remove(promotionEntityToRemove);
        }
        else
        {
            em.remove(promotionEntityToRemove);
        }                
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PromotionEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
}
