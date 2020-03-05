/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Event;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author celes
 */
@Local
public interface EventSessionBeanLocal {
    public Long createNewEvent(Event newEvent);
    public List<Event> retrieveAllEvents();
}
