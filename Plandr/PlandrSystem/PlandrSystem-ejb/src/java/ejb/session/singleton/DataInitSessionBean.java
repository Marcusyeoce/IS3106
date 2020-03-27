package ejb.session.singleton;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.BookingEntity;
import entity.StaffEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AccessRightEnum;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameExistException;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    @EJB(name = "StaffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;

    @PostConstruct
    public void postConstruct(){
        try
        {
            staffEntitySessionBeanLocal.retrieveStaffByUsername("admin");
        }
        catch(StaffNotFoundException ex)
        {
            initializeData();
        } 
    }
    
    public void initializeData(){
        try
        {
            staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Admin", "admin@gmail.com", "99999991", "admin", "password", AccessRightEnum.ADMIN)); 
            staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Employee", "employee@gmail.com", "99999992", "employee", "password", AccessRightEnum.EMPLOYEE));
            
            
            
            
        }catch(UsernameExistException | UnknownPersistenceException | InputDataValidationException ex){
            ex.printStackTrace();
        }
            
    }
}
