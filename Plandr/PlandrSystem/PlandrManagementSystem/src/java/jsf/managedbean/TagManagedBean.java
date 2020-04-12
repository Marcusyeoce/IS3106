package jsf.managedbean;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.TagEntity;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author oimun
 */
@Named(value = "tagManagedBean")
@ViewScoped
public class TagManagedBean implements Serializable{

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;

    @EJB(name = "TagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;
    
    private TagEntity newTagEntity;
    private List<Long> attractionIdsNew;
    
    private List<AttractionEntity> attractionEntities;

    public TagManagedBean() {
        newTagEntity = new TagEntity();
    }
    
    @PostConstruct
    public void postConstruct(){
        attractionEntities = attractionEntitySessionBeanLocal.retrieveAllAttractions();
    }
    
    public void createNewTag(ActionEvent event)
    {   
        try
        {
            Long newTagId = tagEntitySessionBeanLocal.createNewTagEntity(getNewTagEntity(), attractionIdsNew);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"New Tag has been added succesfully! Tag's ID : " + newTagId, null));
        }
        catch(InputDataValidationException | CreateNewTagException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new tag: " + ex.getMessage(), null));
        }
    }
    
    public TagEntity getNewTagEntity() {
        return newTagEntity;
    }

    public void setNewTagEntity(TagEntity newTagEntity) {
        this.newTagEntity = newTagEntity;
    }

    public List<Long> getAttractionIdsNew() {
        return attractionIdsNew;
    }

    public void setAttractionIdsNew(List<Long> attractionIdsNew) {
        this.attractionIdsNew = attractionIdsNew;
    }

    public List<AttractionEntity> getAttractionEntities() {
        return attractionEntities;
    }

    public void setAttractionEntities(List<AttractionEntity> attractionEntities) {
        this.attractionEntities = attractionEntities;
    }

}
