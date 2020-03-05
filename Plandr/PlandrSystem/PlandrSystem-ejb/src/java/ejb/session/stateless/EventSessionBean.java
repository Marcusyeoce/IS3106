/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Event;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author celes
 */
@Stateless
public class EventSessionBean implements EventSessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
public Long createNewEvent(Event newEvent) {
em.persist(newEvent);
em.flush(); //because its using IDENTITY generation, need to flush then can get the ID back from the db management system 
return newEvent.getEventId(); }

public List<Event> retrieveAllEvents(){
Query query = em.createQuery("SELECT e from Event e");
return query.getResultList(); 
}


    
}
