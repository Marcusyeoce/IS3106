package ejb.session.stateless;

import entity.TagEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateTagException;

/**
 *
 * @author oimun
 */
@Stateless
public class TagEntitySessionBean implements TagEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    
    public TagEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    @Override
    public Long createNewTagEntity(TagEntity newTagEntity) throws InputDataValidationException, UnknownPersistenceException, CreateNewTagException
    {
        Set<ConstraintViolation<TagEntity>>constraintViolations = validator.validate(newTagEntity);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newTagEntity);
                em.flush();

                return newTagEntity.getTagId();
            }
            catch(PersistenceException ex)
            {                
                if(ex.getCause() != null && 
                        ex.getCause().getCause() != null &&
                        ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException"))
                {
                    throw new CreateNewTagException("Tag with same name already exist");
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            catch(Exception ex)
            {                
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    @Override
    public List<TagEntity> retrieveAllTags()
    {
        Query query = em.createQuery("SELECT t FROM TagEntity t ORDER BY t.name ASC");
        List<TagEntity> tagEntities = query.getResultList();
        
        for(TagEntity tagEntity:tagEntities)
        {            
            tagEntity.getAttractionEntities().size();
        }
        
        return tagEntities;
    }
    
    
    @Override
    public TagEntity retrieveTagByTagId(Long tagId) throws TagNotFoundException
    {
        TagEntity tagEntity = em.find(TagEntity.class, tagId);
        
        if(tagEntity != null)
        {
            return tagEntity;
        }
        else
        {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }               
    }
    
    
    @Override
    public void updateTag(TagEntity tagEntity) throws InputDataValidationException, TagNotFoundException, UpdateTagException
    {
        Set<ConstraintViolation<TagEntity>>constraintViolations = validator.validate(tagEntity);
        
        if(constraintViolations.isEmpty())
        {
            if(tagEntity.getTagId()!= null)
            {
                TagEntity tagEntityToUpdate = retrieveTagByTagId(tagEntity.getTagId());
                
                Query query = em.createQuery("SELECT t FROM TagEntity t WHERE t.name = :inName AND t.tagId <> :inTagId");
                query.setParameter("inName", tagEntity.getName());
                query.setParameter("inTagId", tagEntity.getTagId());
                
                if(!query.getResultList().isEmpty())
                {
                    throw new UpdateTagException("The name of the tag to be updated is duplicated");
                }
                
                tagEntityToUpdate.setName(tagEntity.getName());                               
            }
            else
            {
                throw new TagNotFoundException("Tag ID not provided for tag to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    @Override
    public void deleteTag(Long tagId) throws TagNotFoundException, DeleteTagException
    {
        TagEntity tagEntityToRemove = retrieveTagByTagId(tagId);
        
        if(!tagEntityToRemove.getAttractionEntities().isEmpty())
        {
            throw new DeleteTagException("Tag ID " + tagId + " is associated with existing attractions and cannot be deleted!");
        }
        else
        {
            em.remove(tagEntityToRemove);
        }                
    }
    
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<TagEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }

    
}
