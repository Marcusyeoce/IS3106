/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AttractionEntity;
import entity.MemberEntity;
import entity.ReviewEntity;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

@Stateless
public class ReviewEntitySessionBean implements ReviewEntitySessionBeanLocal {

    @EJB(name = "MemberEntitySessionBeanLocal")
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;
    
    
    
    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ReviewEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    //for reviews only can create, but not edit & delete 
    //for retrieval also get by attraction rather than id
    @Override
    public Long createNewReview(ReviewEntity newReview, Long attractionId, String username) throws InputDataValidationException, UnknownPersistenceException {
        Set<ConstraintViolation<ReviewEntity>>constraintViolations = validator.validate(newReview);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                if(attractionId != null && username != null)
                {
                    AttractionEntity attractionEntity = attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
                    MemberEntity memberEntity = memberEntitySessionBeanLocal.retrieveMemberByUsername(username);
                    
                    newReview.setAttractionEntity(attractionEntity);
                    attractionEntity.addReview(newReview);
                    
                    memberEntity.getReviewEntities().add(newReview);
                }
                em.persist(newReview);
                em.flush();

                return newReview.getReviewId();
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
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ReviewEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
