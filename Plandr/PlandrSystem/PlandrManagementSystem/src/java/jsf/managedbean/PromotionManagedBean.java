/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.PromotionEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.AttractionNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;

/**
 *
 * @author oimun
 */
@Named(value = "promotionManagedBean")
@ViewScoped
public class PromotionManagedBean implements Serializable{

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;

    @EJB(name = "PromotionSessionBeanLocal")
    private PromotionEntitySessionBeanLocal promotionSessionBeanLocal;

    private PromotionEntity newPromotionEntity;
    private List<Long> attractionsToAdd;
    
    private List<AttractionEntity> allAttractions;
    private List<PromotionEntity> allPromotions;
    
    private PromotionEntity promotionToView;
    private PromotionEntity promotionToUpdate;
    private List<Long> updateAttractionIds;

    public PromotionManagedBean() {
        newPromotionEntity = new PromotionEntity();
    }
    
    @PostConstruct
    public void postConstruct(){
        allAttractions = attractionEntitySessionBeanLocal.retrieveAllAttractions();
        allPromotions = promotionSessionBeanLocal.retrieveAllPromotions();
    }

    public void createNewPromotion(){
        try
        {
            Long newPromotionId = promotionSessionBeanLocal.createNewPromotionEntity(getNewPromotionEntity(), getAttractionsToAdd());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"New Promotion has been added succesfully! Promotion's ID : " + newPromotionId, null));
        }
        catch(InputDataValidationException | AttractionNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new attraction: " + ex.getMessage(), null));
        }
    }
    
    public void updatePromotion(){
        try
        {
            promotionSessionBeanLocal.updatePromotion(getPromotionToUpdate(), getUpdateAttractionIds());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Promotion updated successfully", null));
        }
        catch(AttractionNotFoundException | PromotionNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating promotion: " + ex.getMessage(), null));
        }
        catch(InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deletePromotion(ActionEvent event)
    {
        try
        {
            PromotionEntity promotionEntityToDelete = (PromotionEntity)event.getComponent().getAttributes().get("promotionEntityToDelete");
            promotionSessionBeanLocal.deletePromotion(promotionEntityToDelete.getPromotionId());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Promotion deleted successfully", null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public PromotionEntity getNewPromotionEntity() {
        return newPromotionEntity;
    }

    public void setNewPromotionEntity(PromotionEntity newPromotionEntity) {
        this.newPromotionEntity = newPromotionEntity;
    }
    
    public List<Long> getAttractionsToAdd() {
        return attractionsToAdd;
    }

    public void setAttractionsToAdd(List<Long> AttractionsToAdd) {
        this.attractionsToAdd = AttractionsToAdd;
    }
    
    public List<AttractionEntity> getAllAttractions() {
        return allAttractions;
    }

    public void setAllAttractions(List<AttractionEntity> allAttractions) {
        this.allAttractions = allAttractions;
    }

    public List<PromotionEntity> getAllPromotions() {
        return allPromotions;
    }

    public void setAllPromotions(List<PromotionEntity> allPromotions) {
        this.allPromotions = allPromotions;
    }
    
    public PromotionEntity getPromotionToView() {
        return promotionToView;
    }

    public void setPromotionToView(PromotionEntity promotionToView) {
        this.promotionToView = promotionToView;
    }

    public PromotionEntity getPromotionToUpdate() {
        return promotionToUpdate;
    }

    public void setPromotionToUpdate(PromotionEntity promotionToUpdate) {
        this.promotionToUpdate = promotionToUpdate;
    }

    public List<Long> getUpdateAttractionIds() {
        return updateAttractionIds;
    }

    public void setUpdateAttractionIds(List<Long> updateAttractionIds) {
        this.updateAttractionIds = updateAttractionIds;
    }
    
}
