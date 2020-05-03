package ejb.session.stateless;

import entity.AttractionEntity;
import entity.PromotionEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AttractionNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;

/**
 *
 * @author oimun
 */
@Stateless
public class PromotionEntitySessionBean implements PromotionEntitySessionBeanLocal {

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public PromotionEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
     public Long createNewPromotionEntity(PromotionEntity newPromotionEntity, List<Long> attractionIdsToAdd) throws InputDataValidationException, AttractionNotFoundException
    {
        Set<ConstraintViolation<PromotionEntity>>constraintViolations = validator.validate(newPromotionEntity);
        
        if(constraintViolations.isEmpty())
        {          
            try
            {
                em.persist(newPromotionEntity);
                if(attractionIdsToAdd != null && (!attractionIdsToAdd.isEmpty()))
                    {
                        for(Long attractionId:attractionIdsToAdd)
                        {
                            AttractionEntity attractionEntity = attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
                            newPromotionEntity.addAttraction(attractionEntity);
                        }
                    }
                em.flush();

                return newPromotionEntity.getPromotionId();
            }catch(AttractionNotFoundException ex){
                throw new AttractionNotFoundException("Attraction cannot be found!");
            }
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
    public void updatePromotion(PromotionEntity promotionEntity, List<Long> attractionIdsToUpdate) throws InputDataValidationException, PromotionNotFoundException, AttractionNotFoundException
    {
        Set<ConstraintViolation<PromotionEntity>>constraintViolations = validator.validate(promotionEntity);
        
        if(constraintViolations.isEmpty())
        {
            if(promotionEntity.getPromotionId()!= null)
            {
                PromotionEntity promotionEntityToUpdate = retrievePromotionByPromotionId(promotionEntity.getPromotionId());
                
                promotionEntityToUpdate.setName(promotionEntity.getName());
                promotionEntityToUpdate.setDiscount(promotionEntity.getDiscount());
                promotionEntityToUpdate.setStartDate(promotionEntity.getStartDate());
                promotionEntityToUpdate.setEndDate(promotionEntity.getEndDate());
                
                                
                if(attractionIdsToUpdate != null && (!attractionIdsToUpdate.isEmpty()))
                {
                    //Remove related promotions if have
                    List<AttractionEntity> currentAttractions = promotionEntityToUpdate.getAttractionEntities();
                    if(!currentAttractions.isEmpty()){
                        for(AttractionEntity attraction: currentAttractions){
                            attraction.removePromotion(promotionEntity);
                        }
                    }
                    promotionEntityToUpdate.getAttractionEntities().clear();
                    try{
                        for(Long attractionId:attractionIdsToUpdate)
                        {
                            AttractionEntity attractionEntity = attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
                            promotionEntityToUpdate.addAttraction(attractionEntity);
                        }
                    }catch(AttractionNotFoundException ex){
                        throw new AttractionNotFoundException("Attraction cannot be found with this Id!");
                    }
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
