package ejb.session.stateless;

import entity.MessageOfTheDayEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;



@Stateless
@Local(MessageOfTheDayEntitySessionBeanLocal.class)

public class MessageOfTheDayEntitySessionBean implements MessageOfTheDayEntitySessionBeanLocal
{
    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;


    
    
    public MessageOfTheDayEntitySessionBean() 
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    
    @Override
    public MessageOfTheDayEntity createNewMessageOfTheDay(MessageOfTheDayEntity newMessageOfTheDayEntity) throws InputDataValidationException
    {
        Set<ConstraintViolation<MessageOfTheDayEntity>>constraintViolations = validator.validate(newMessageOfTheDayEntity);
        
        if(constraintViolations.isEmpty())
        {
            em.persist(newMessageOfTheDayEntity);
            em.flush();
            
            return newMessageOfTheDayEntity;
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    
    @Override
    public List<MessageOfTheDayEntity> retrieveAllMessagesOfTheDay()
    {
        Query query = em.createQuery("SELECT motd FROM MessageOfTheDayEntity motd ORDER BY motd.motdId ASC");
        
        return query.getResultList();
    }
    
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MessageOfTheDayEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
