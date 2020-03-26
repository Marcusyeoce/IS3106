package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordMismatchException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStaffException;
import util.exception.UsernameExistException;
import util.security.CryptographicHelper;

/**
 *
 * @author oimun
 */
@Stateless
public class StaffEntitySessionBean implements StaffEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StaffEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewStaff(StaffEntity newStaffEntity) throws UsernameExistException, UnknownPersistenceException, InputDataValidationException {
        try
        {
            Set<ConstraintViolation<StaffEntity>>constraintViolations = validator.validate(newStaffEntity);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newStaffEntity);
                em.flush();

                return newStaffEntity.getStaffId();
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
                    throw new UsernameExistException();
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
    
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<StaffEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    
    
    @Override
    public StaffEntity staffLogin(String username, String password) throws InvalidLoginCredentialException{
        try
        {
            StaffEntity staff = retrieveStaffByUsername(username);            
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + staff.getSalt()));
            
            if(staff.getPassword().equals(passwordHash))
            {                
                staff.getArticleEntities().size();
                return staff;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }
    }

    @Override
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM StaffEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (StaffEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new StaffNotFoundException("Staff username " + username + " does not exist!");
        }
    }
    
    @Override
    public StaffEntity retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException {
        StaffEntity staffEntity = em.find(StaffEntity.class, staffId);
        
        if(staffEntity != null)
        {
            staffEntity.getArticleEntities().size();
            
            return staffEntity;
        }
        else
        {
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }                
    }
    
    @Override
    public List<StaffEntity> retrieveAllStaffs()
    {
        Query query = em.createQuery("SELECT s FROM StaffEntity s ORDER BY s.name ASC");
        List<StaffEntity> staffEntities = query.getResultList();
        
        return staffEntities;
    }

    @Override
    public void updateStaff(StaffEntity staff) throws StaffNotFoundException, UpdateStaffException, InputDataValidationException {
        if(staff != null && staff.getStaffId() != null)
        {
            Set<ConstraintViolation<StaffEntity>>constraintViolations = validator.validate(staff);
        
            if(constraintViolations.isEmpty())
            {
                StaffEntity staffEntityToUpdate = retrieveStaffByStaffId(staff.getStaffId());

                if(staffEntityToUpdate.getUsername().equals(staff.getUsername()))
                {
                    staffEntityToUpdate.setName(staff.getName());
                    staffEntityToUpdate.setEmail(staff.getEmail());
                    staffEntityToUpdate.setContactNumber(staff.getContactNumber());
                }
                else
                {
                    throw new UpdateStaffException("Username of staff record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new StaffNotFoundException("Staff ID not provided for staff to be updated");
        }
    }

    @Override
    public void updateStaffPassword(Long staffId, String oldPassword, String newPassword, String reenteredNewPassword) throws StaffNotFoundException, PasswordMismatchException {
        try {
            StaffEntity staff = retrieveStaffByStaffId(staffId);
            
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + staff.getSalt()));
            if (staff.getPassword().equals(passwordHash)) {
                if (newPassword.equals(reenteredNewPassword)) {
                    staff.setPassword(newPassword);
                } else {
                    throw new PasswordMismatchException("Re-entered password does not match!");
                }
            } else {
                throw new PasswordMismatchException("Invalid password!"); 
            }
        } catch (StaffNotFoundException ex) {
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }    
    
    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException
    {
        StaffEntity staffEntityToRemove = retrieveStaffByStaffId(staffId);
        
        //If staff has articles -> throw Exception or cascade delete articles?
        
        em.remove(staffEntityToRemove);
        
    }
}
