package ejb.session.stateless;

import entity.AttractionEntity;
import entity.CompanyEntity;
import entity.EventEntity;
import entity.PlaceEntity;
import entity.PromotionEntity;
import entity.TagEntity;
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
import util.exception.TagNotFoundException;

/**
 *
 * @author oimun
 */
@Stateless
public class AttractionEntitySessionBean implements AttractionEntitySessionBeanLocal {

    @EJB(name = "PromotionSessionBeanLocal")
    private PromotionSessionBeanLocal promotionSessionBeanLocal;

    @EJB(name = "TagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    
    public AttractionEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewAttractionEntity(AttractionEntity newAttractionEntity, List<Long> tagIdsToAdd, List<Long> promotionIdsToAdd) throws InputDataValidationException, TagNotFoundException, PromotionNotFoundException
    {
        Set<ConstraintViolation<AttractionEntity>>constraintViolations = validator.validate(newAttractionEntity);
        
        if(constraintViolations.isEmpty())
        {
            CompanyEntity companyEntity = newAttractionEntity.getCompanyEntity();
            companyEntity.getAttractionsEntities().add(newAttractionEntity);

            em.persist(newAttractionEntity);
            if(tagIdsToAdd != null && (!tagIdsToAdd.isEmpty()))
            {
                for(Long tagId:tagIdsToAdd)
                {
                    try{
                        TagEntity tagEntity = tagEntitySessionBeanLocal.retrieveTagByTagId(tagId);
                        newAttractionEntity.addTag(tagEntity);
                    }catch(TagNotFoundException ex){
                        throw new TagNotFoundException("Tag with this ID does not exist!");
                    }
                }
            }
            
            if(promotionIdsToAdd != null && (!promotionIdsToAdd.isEmpty()))
            {
                for(Long promotionId:promotionIdsToAdd)
                {
                    try{
                        PromotionEntity promotionEntity = promotionSessionBeanLocal.retrievePromotionByPromotionId(promotionId);
                        newAttractionEntity.addPromotion(promotionEntity);
                    }catch(PromotionNotFoundException ex){
                        throw new PromotionNotFoundException("Promotion with this ID does not exist!");
                    }
                }
            }
            em.flush();

            return newAttractionEntity.getAttractionId();
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<AttractionEntity> retrieveAttractionsByCompanyId(Long companyId){
        Query query = em.createQuery("SELECT a FROM AttractionEntity a WHERE a.companyEntity.companyId = :inCompanyId ORDER BY a.attractionId ASC");
        query.setParameter("inCompanyId", companyId);
        List<AttractionEntity> attractionEntities = query.getResultList();
        
        return attractionEntities;
    }
    
    @Override
    public List<AttractionEntity> retrieveAllAttractions()
    {
        Query query = em.createQuery("SELECT a FROM AttractionEntity a ORDER BY a.attractionId ASC");
        List<AttractionEntity> attractionEntities = query.getResultList();
        
        for(AttractionEntity attraction:attractionEntities)
        {           
            attraction.getReviewEntities().size();
            attraction.getTagEntities().size();
        }
        
        return attractionEntities;
    }
    
    
    @Override
    public AttractionEntity retrieveAttractionByAttractionId(Long attractionId) throws AttractionNotFoundException
    {
        AttractionEntity attractionEntity = em.find(AttractionEntity.class, attractionId);
        
        if(attractionEntity != null)
        {
            //Fetching
            attractionEntity.getCompanyEntity();
            attractionEntity.getReviewEntities().size();
            attractionEntity.getTagEntities().size();
            
            return attractionEntity;
        }
        else
        {
            throw new AttractionNotFoundException("Attraction ID " + attractionId + " does not exist!");
        }               
    }
    
    @Override
    public void updateAttraction(AttractionEntity attraction) throws AttractionNotFoundException, InputDataValidationException {
        if(attraction != null && attraction.getAttractionId() != null)
        {
            Set<ConstraintViolation<AttractionEntity>>constraintViolations = validator.validate(attraction);
        
            if(constraintViolations.isEmpty())
            {
                AttractionEntity attractionEntityToUpdate = retrieveAttractionByAttractionId(attraction.getAttractionId());
                
                attractionEntityToUpdate.setReviewEntities(attraction.getReviewEntities());
                
                List<TagEntity> tagEntities = attraction.getTagEntities();
                attractionEntityToUpdate.setTagEntities(tagEntities);
                for(TagEntity tag: tagEntities)
                {
                    tag.getAttractionEntities().add(attractionEntityToUpdate);
                }
                
                List<PromotionEntity> promotionEntities = attraction.getPromotionEntities();
                attractionEntityToUpdate.setPromotionEntities(promotionEntities);
                for(PromotionEntity promotion: promotionEntities)
                {
                    promotion.getAttractionEntities().add(attractionEntityToUpdate);
                }
                
                attractionEntityToUpdate.setCompanyEntity(attraction.getCompanyEntity());
                attraction.getCompanyEntity().getAttractionsEntities().add(attractionEntityToUpdate);
                
                if(attraction instanceof PlaceEntity)
                {
                    ((PlaceEntity) attractionEntityToUpdate).setClosingTime(((PlaceEntity) attraction).getClosingTime());
                    ((PlaceEntity) attractionEntityToUpdate).setOpeningTime(((PlaceEntity) attraction).getOpeningTime());
                }else{
                    ((EventEntity) attractionEntityToUpdate).setStartDate(((EventEntity) attraction).getStartDate());
                    ((EventEntity) attractionEntityToUpdate).setEndDate(((EventEntity) attraction).getEndDate());
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new AttractionNotFoundException("Attraction ID not provided for attraction to be updated");
        }
    }
   
    @Override
   public void deleteAttraction(Long attractionId) throws AttractionNotFoundException
    {
        AttractionEntity attractionEntityToRemove = retrieveAttractionByAttractionId(attractionId);
        
        //Disconnecting from associated relationships
        
        em.remove(attractionEntityToRemove);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<AttractionEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
