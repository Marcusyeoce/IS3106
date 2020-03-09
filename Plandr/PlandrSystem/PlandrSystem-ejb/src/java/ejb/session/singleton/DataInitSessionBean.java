package ejb.session.singleton;

import entity.BookingEntity;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    public void postConstruct(){
        if(em.find(BookingEntity.class, 1l) == null) { 
            //then u initialise
        }         
    }
}
