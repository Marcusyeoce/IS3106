package ejb.session.stateless;

import entity.AttractionEntity;
import entity.CompanyEntity;
import entity.EventEntity;
import entity.PlaceEntity;
import entity.TagEntity;
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
import util.exception.AttractionNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author oimun
 */
@Stateless
public class AttractionEntitySessionBean implements AttractionEntitySessionBeanLocal {

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
    public Long createNewAttractionEntity(AttractionEntity newAttractionEntity) throws InputDataValidationException
    {
        Set<ConstraintViolation<AttractionEntity>>constraintViolations = validator.validate(newAttractionEntity);
        
        if(constraintViolations.isEmpty())
        {
            CompanyEntity companyEntity = newAttractionEntity.getCompanyEntity();
            companyEntity.getAttractionsEntities().add(newAttractionEntity);

            List<TagEntity> tagEntities = newAttractionEntity.getTagEntities();
            for(TagEntity tag: tagEntities)
            {
                tag.getAttractionEntities().add(newAttractionEntity);
            }
            
            em.persist(newAttractionEntity);
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
                
                attractionEntityToUpdate.setPromotionEntity(attraction.getPromotionEntity());
                attractionEntityToUpdate.setTicketEntity(attraction.getTicketEntity());
                attractionEntityToUpdate.setReviewEntities(attraction.getReviewEntities());
                
                List<TagEntity> tagEntities = attraction.getTagEntities();
                attractionEntityToUpdate.setTagEntities(tagEntities);
                for(TagEntity tag: tagEntities)
                {
                    tag.getAttractionEntities().add(attractionEntityToUpdate);
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
