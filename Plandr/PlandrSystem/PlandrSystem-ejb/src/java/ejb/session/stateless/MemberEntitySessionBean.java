/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MemberEntity;
import java.util.Calendar;
import java.util.Date;
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
import util.exception.MemberNotFoundException;
import util.exception.PasswordMismatchException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMemberException;
import util.exception.UsernameExistException;
import util.security.CryptographicHelper;

/**
 *
 * @author Pham The Dzung
 */
@Stateless
public class MemberEntitySessionBean implements MemberEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MemberEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewMember(MemberEntity newMemberEntity) throws UsernameExistException, UnknownPersistenceException, InputDataValidationException {
        try
        {
            Set<ConstraintViolation<MemberEntity>>constraintViolations = validator.validate(newMemberEntity);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newMemberEntity);
                em.flush();

                return newMemberEntity.getMemberId();
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
    
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MemberEntity>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    
    
    @Override
    public MemberEntity memberLogin(String username, String password) throws InvalidLoginCredentialException {
        try
        {
            MemberEntity member = retrieveMemberByUsername(username);            
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + member.getSalt()));
            
            if(member.getPassword().equals(passwordHash))
            {                
                member.getBookingEntities().size();
                member.getReviewEntities().size();
                return member;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(MemberNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public MemberEntity retrieveMemberByUsername(String username) throws MemberNotFoundException {
        Query query = em.createQuery("SELECT m FROM MemberEntity m WHERE m.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (MemberEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new MemberNotFoundException("Member username " + username + " does not exist!");
        }
    }
    
    @Override
    public List<MemberEntity> retrieveAllMembers()
    {
        Query query = em.createQuery("SELECT m FROM MemberEntity m ORDER BY m.name ASC");
        List<MemberEntity> memberEntities = query.getResultList();
        
        return memberEntities;
    }

    @Override
    public void updateMember(MemberEntity member) throws MemberNotFoundException, UpdateMemberException, InputDataValidationException {
        if(member != null && member.getMemberId() != null)
        {
            Set<ConstraintViolation<MemberEntity>>constraintViolations = validator.validate(member);
        
            if(constraintViolations.isEmpty())
            {
                MemberEntity memberEntityToUpdate = retrieveMemberById(member.getMemberId());

                if(memberEntityToUpdate.getUsername().equals(member.getUsername()))
                {
                    memberEntityToUpdate.setName(member.getName());
                    memberEntityToUpdate.setEmail(member.getEmail()); 
                    memberEntityToUpdate.setContactNumber(member.getContactNumber());
                    memberEntityToUpdate.setGender(member.getGender());
                    memberEntityToUpdate.setDob(member.getDob());
                    memberEntityToUpdate.setCreditCard(member.getCreditCard());
                }
                else
                {
                    throw new UpdateMemberException("Username of member record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new MemberNotFoundException("Member ID not provided for member to be updated");
        }
    }
    
    @Override
    public void updateSubscriptionStatus(MemberEntity member) {
        try{
            if(member != null && member.getMemberId() != null)
            {
                MemberEntity memberEntityToUpdate = retrieveMemberById(member.getMemberId());

                memberEntityToUpdate.setSubscribed(false);
                memberEntityToUpdate.setSubscribedUntil(null);
            }
        }catch(MemberNotFoundException ex){}
    }

    @Override
    public MemberEntity retrieveMemberById(Long memberId) throws MemberNotFoundException {
        MemberEntity member = em.find(MemberEntity.class, memberId);
        
        if(member != null) {
            member.getBookingEntities().size();
            member.getReviewEntities().size();
            
            return member;
        } else {
            throw new MemberNotFoundException("Member ID " + memberId + " does not exist!");
        }
    }

    @Override
    public void updateMemberPassword(String username, String oldPassword, String newPassword, String reenteredNewPassword) throws MemberNotFoundException, PasswordMismatchException {
        try {
            MemberEntity member = retrieveMemberByUsername(username);
            
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + member.getSalt()));
            if (member.getPassword().equals(passwordHash)) {
                if (newPassword.equals(reenteredNewPassword)) {
                    member.setPassword(newPassword);
                } else {
                    throw new PasswordMismatchException("Re-entered password does not match!");
                }
            } else {
                throw new PasswordMismatchException("Invalid password!"); 
            }
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Member does not exist!");
        }
    }
    
    

    @Override
    public void memberSubscribe(String username, int subPackage) throws MemberNotFoundException {
        try {
            MemberEntity member = retrieveMemberByUsername(username);
            
            Calendar c = Calendar.getInstance();
            member.setSubscribed(true);
            if (subPackage == 1) { // 1-month subscription
                if (member.getSubscribedUntil() == null) {
                    c.add(Calendar.DAY_OF_MONTH, 30);
                    member.setSubscribedUntil(c.getTime());
                } else {
                    c.setTime(member.getSubscribedUntil());
                    c.add(Calendar.DAY_OF_MONTH, 30);
                    member.setSubscribedUntil(c.getTime());
                }
            } else if (subPackage == 2) { // 3-month subscription
                if (member.getSubscribedUntil() == null) {
                    c.add(Calendar.DAY_OF_MONTH, 90);
                    member.setSubscribedUntil(c.getTime());
                } else {
                    c.setTime(member.getSubscribedUntil());
                    c.add(Calendar.DAY_OF_MONTH, 90);
                    member.setSubscribedUntil(c.getTime());
                }
            } else { // 6-month subscription
                if (member.getSubscribedUntil() == null) {
                    c.add(Calendar.DAY_OF_MONTH, 180);
                    member.setSubscribedUntil(c.getTime());
                } else {
                    c.setTime(member.getSubscribedUntil());
                    c.add(Calendar.DAY_OF_MONTH, 180);
                    member.setSubscribedUntil(c.getTime());
                }
            }
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Member does not exist!");
        }
    }
    
    
}
