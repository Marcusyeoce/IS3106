package jsf.managedbean;

import ejb.session.stateless.MessageOfTheDayEntitySessionBeanLocal;
import entity.MessageOfTheDayEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;



@Named(value = "indexManagedBean")
@RequestScoped

public class IndexManagedBean 
{
    @EJB
    private MessageOfTheDayEntitySessionBeanLocal messageOfTheDayEntitySessionBeanLocal;
    
    private List<MessageOfTheDayEntity> messageOfTheDayEntities;
    
    
    
    public IndexManagedBean() 
    {
    }



    @PostConstruct
    public void postConstruct()
    {
        messageOfTheDayEntities = messageOfTheDayEntitySessionBeanLocal.retrieveAllMessagesOfTheDay();
    }

    
    
    public List<MessageOfTheDayEntity> getMessageOfTheDayEntities() {
        return messageOfTheDayEntities;
    }

    public void setMessageOfTheDayEntities(List<MessageOfTheDayEntity> messageOfTheDayEntities) {
        this.messageOfTheDayEntities = messageOfTheDayEntities;
    }
}
