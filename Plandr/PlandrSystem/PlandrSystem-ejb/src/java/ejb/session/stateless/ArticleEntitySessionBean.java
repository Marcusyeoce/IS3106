package ejb.session.stateless;

import entity.ArticleEntity;
import entity.StaffEntity;
import java.util.Date;
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
import util.exception.ArticleNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author oimun
 */
@Stateless
public class ArticleEntitySessionBean implements ArticleEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    @EJB(name = "StaffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public ArticleEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewArticle(Long staffId, ArticleEntity newArticleEntity) throws StaffNotFoundException, InputDataValidationException
    {
        newArticleEntity.setPublishedDate(new Date());
        Set<ConstraintViolation<ArticleEntity>>constraintViolations = validator.validate(newArticleEntity);
        
        if(constraintViolations.isEmpty())
        {
            try{
                StaffEntity staffEntity = staffEntitySessionBeanLocal.retrieveStaffByStaffId(staffId);
                newArticleEntity.setStaffEntity(staffEntity);
                staffEntity.getArticleEntities().add(newArticleEntity);

                em.persist(newArticleEntity);
                em.flush();

                return newArticleEntity.getArticleId();

            }catch(StaffNotFoundException ex){
                throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
            }
        }else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
           
    @Override
    public ArticleEntity retrieveArticleByArticleId(Long atricleId) throws ArticleNotFoundException {
        ArticleEntity articleEntity = em.find(ArticleEntity.class, atricleId);
        
        if(articleEntity != null)
        {            
            return articleEntity;
        }
        else
        {
            throw new ArticleNotFoundException("Article ID " + atricleId + " does not exist!");
        }                
    }
    
    @Override
    public List<ArticleEntity> retrieveAllArticles()
    {
        Query query = em.createQuery("SELECT a FROM ArticleEntity a ORDER BY a.publishedDate DESC");
        List<ArticleEntity> articleEntities = query.getResultList();
        
        return articleEntities;
    }
    
    @Override
    public List<ArticleEntity> retrieveAllArticlesByStaffId(Long staffId)
    {
        Query query = em.createQuery("SELECT a FROM ArticleEntity a WHERE a.staffEntity.staffId = :inStaffId ORDER BY a.publishedDate DESC");
        query.setParameter("inStaffId", staffId);
        List<ArticleEntity> articleEntities = query.getResultList();
        
        return articleEntities;
    }
    
    @Override
    public void updateArticle(ArticleEntity article) throws ArticleNotFoundException, StaffNotFoundException, InputDataValidationException {
        Set<ConstraintViolation<ArticleEntity>>constraintViolations = validator.validate(article);
        
        if(constraintViolations.isEmpty())
        {
            if(article != null && article.getArticleId() != null)
            {
                ArticleEntity articleEntityToUpdate = retrieveArticleByArticleId(article.getArticleId());

                articleEntityToUpdate.setContent(article.getContent());
                articleEntityToUpdate.setPicture(article.getPicture());
                articleEntityToUpdate.setTitle(article.getTitle());

                StaffEntity staffEntity = staffEntitySessionBeanLocal.retrieveStaffByStaffId(article.getStaffEntity().getStaffId());
                articleEntityToUpdate.setStaffEntity(staffEntity);
                staffEntity.getArticleEntities().add(articleEntityToUpdate);  
            }
            else
            {
                throw new ArticleNotFoundException("Article ID not provided for article to be updated");
            }
        }else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteArticle(Long articleId) throws ArticleNotFoundException, StaffNotFoundException
    {
        ArticleEntity articleEntityToRemove = retrieveArticleByArticleId(articleId);
        
        StaffEntity staffEntity = staffEntitySessionBeanLocal.retrieveStaffByStaffId(articleEntityToRemove.getStaffEntity().getStaffId());
        staffEntity.getArticleEntities().remove(articleEntityToRemove);
        
        em.remove(articleEntityToRemove);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ArticleEntity>> constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
