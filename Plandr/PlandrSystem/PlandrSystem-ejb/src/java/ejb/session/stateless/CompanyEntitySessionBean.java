package ejb.session.stateless;

import entity.CompanyEntity;
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
import util.exception.CompanyExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author oimun
 */
@Stateless
public class CompanyEntitySessionBean implements CompanyEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CompanyEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewCompany(CompanyEntity newCompanyEntity) throws CompanyExistException, UnknownPersistenceException, InputDataValidationException {
        try
        {
            Set<ConstraintViolation<CompanyEntity>>constraintViolations = validator.validate(newCompanyEntity);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newCompanyEntity);
                em.flush();

                return newCompanyEntity.getCompanyId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new CompanyExistException("Company with the same email and contact number already exists");
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public CompanyEntity retrieveCompanyByCompanyId(Long companyId) throws CompanyNotFoundException {
        CompanyEntity companyEntity = em.find(CompanyEntity.class, companyId);
        
        if(companyEntity != null)
        {            
            return companyEntity;
        }
        else
        {
            throw new CompanyNotFoundException("Comapany ID " + companyId + " does not exist!");
        }                
    }
    
    @Override
    public List<CompanyEntity> retrieveAllCompanies()
    {
        Query query = em.createQuery("SELECT c FROM CompanyEntity c ORDER BY c.name ASC");
        List<CompanyEntity> companyEntities = query.getResultList();
        
        return companyEntities;
    }
    
    @Override
    public void updateCompany(CompanyEntity company) throws CompanyNotFoundException, InputDataValidationException {
        if(company != null && company.getCompanyId()!= null)
        {
            Set<ConstraintViolation<CompanyEntity>>constraintViolations = validator.validate(company);
        
            if(constraintViolations.isEmpty())
            {
                CompanyEntity companyEntityToUpdate = retrieveCompanyByCompanyId(company.getCompanyId());
                
                companyEntityToUpdate.setContactNumber(company.getContactNumber());
                companyEntityToUpdate.setEmail(company.getEmail());
                companyEntityToUpdate.setName(company.getName());
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new CompanyNotFoundException("Company ID not provided for company to be updated");
        }
    }
    
    @Override
    public void deleteCompany(Long companyId) throws CompanyNotFoundException, DeleteCompanyException
    {
        CompanyEntity companyEntityToRemove = retrieveCompanyByCompanyId(companyId);
        
        if(companyEntityToRemove.getAttractionsEntities().isEmpty())
        {
            em.remove(companyEntityToRemove);
        }
        else
        {
            throw new DeleteCompanyException("Company ID " + companyId + " is associated with existing attractions and cannot be deleted!");
        }
    }
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CompanyEntity>> constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
