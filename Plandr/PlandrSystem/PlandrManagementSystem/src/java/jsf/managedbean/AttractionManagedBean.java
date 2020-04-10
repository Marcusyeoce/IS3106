/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.AttractionEntity;
import entity.CompanyEntity;
import entity.EventEntity;
import entity.PlaceEntity;
import entity.PromotionEntity;
import entity.TagEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.AttractionTypeEnum;
import util.exception.AttractionNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;
import util.exception.TagNotFoundException;

/**
 *
 * @author oimun
 */
@Named(value = "attractionManagedBean")
@ViewScoped
public class AttractionManagedBean implements Serializable{

    @EJB(name = "PromotionSessionBeanLocal")
    private PromotionSessionBeanLocal promotionSessionBeanLocal;

    @EJB(name = "TagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "CompanyEntitySessionBeanLocal")
    private CompanyEntitySessionBeanLocal companyEntitySessionBeanLocal;

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;

    private AttractionTypeEnum[] attractionTypes;
    private AttractionTypeEnum newAttractionType;
    private AttractionEntity newAttractionEntity;
    private Long companyId;

    private List<Long> newTagIds;
    private List<Long> newPromotionIds;

    private List<CompanyEntity> allCompanies;
    private List<TagEntity> allTags;
    private List<PromotionEntity> allPromotions;
    private List<EventEntity> allEvents;
    private List<PlaceEntity> allPlaces;

    private AttractionTypeEnum event;
    private Date startDate;
    private Date endDate;
    
    private AttractionTypeEnum place;
    private Date openingTime;
    private Date closingTime;
    
    private AttractionEntity attractionToView;
    private AttractionEntity attractionToUpdate;
    private Date updatedStartDate;
    private Date updatedEndDate;
    private Date updatedOpeningTime;
    private Date updatedClosingTime;
    private Long updatedCompanyId;
    private List<Long> updatedTagIds;
    private List<Long> updatedPromotionIds;

    public AttractionManagedBean() {
        event = AttractionTypeEnum.EVENT;
        place = AttractionTypeEnum.PLACE;
        attractionTypes = AttractionTypeEnum.values();
    }
    
    @PostConstruct
    public void postConstruct(){
        allCompanies = companyEntitySessionBeanLocal.retrieveAllCompanies();
        allTags = tagEntitySessionBeanLocal.retrieveAllTags();
        allPromotions = promotionSessionBeanLocal.retrieveAllPromotions();
        allEvents = attractionEntitySessionBeanLocal.retrieveAllEventAttractions();
        allPlaces = attractionEntitySessionBeanLocal.retrieveAllPlaceAttractions();
    }
    
    public void constructAttraction(ActionEvent event)
    {   
        if(newAttractionType.equals(AttractionTypeEnum.EVENT)){
            newAttractionEntity = new EventEntity();
        }else{
            newAttractionEntity = new PlaceEntity();
        }
    }
    
    public void setNewAttraction(ActionEvent event){
        if(newAttractionType.equals(AttractionTypeEnum.EVENT)){
            //Check if the timings are valid
            if(getStartDate().after(getEndDate())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Closing Date should be later than Opening Date!", null));
            }else{
                ((EventEntity)newAttractionEntity).setStartDate(getStartDate());
                ((EventEntity)newAttractionEntity).setEndDate(getEndDate());
                createNewAttraction();
            }
        }else{
            //Check if the timings are valid
            if(getOpeningTime().after(getClosingTime())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Closing Time should be later than Opening Time!", null));
            }else{
                ((PlaceEntity)newAttractionEntity).setOpeningTime(getOpeningTime());
                ((PlaceEntity)newAttractionEntity).setClosingTime(getClosingTime());
                createNewAttraction();
            }
        }
    }
    
    public void createNewAttraction(){
        try
        {
            CompanyEntity companyEntity = companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(getCompanyId());
            newAttractionEntity.setCompanyEntity(companyEntity);
            Long newAttractionId = attractionEntitySessionBeanLocal.createNewAttractionEntity(getNewAttractionEntity(), getNewTagIds(), getNewPromotionIds());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"New Attraction has been added succesfully! Attraction's ID : " + newAttractionId, null));
        }
        catch(InputDataValidationException | TagNotFoundException | PromotionNotFoundException | CompanyNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new attraction: " + ex.getMessage(), null));
        }
    }
    
    public void deleteAttraction(ActionEvent event)
    {
        try
        {
            AttractionEntity attractionEntityToDelete = (AttractionEntity)event.getComponent().getAttributes().get("attractionEntityToDelete");
            attractionEntitySessionBeanLocal.deleteAttraction(attractionEntityToDelete.getAttractionId());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction deleted successfully", null));
        }
        catch(AttractionNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting company: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public Boolean isEvent(long attractionId){
        return attractionEntitySessionBeanLocal.isEvent(attractionId);
    }
    
    public Date retrieveStartDate(long attractionId){
        try{
        EventEntity event = (EventEntity)attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
        return event.getStartDate();
        }catch(AttractionNotFoundException ex){}
        return new Date();
    }
    
    public Date retrieveEndDate(long attractionId) {
        try{
        EventEntity event = (EventEntity)attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
        return event.getEndDate();
        }catch(AttractionNotFoundException ex){}
        return new Date();
    }
    
    public Date retrieveOpeningTime(long attractionId){
        try{
        PlaceEntity place = (PlaceEntity)attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
        return place.getOpeningTime();
        }catch(AttractionNotFoundException ex){}
        return new Date();
    }
    
    public Date retrieveClosingTime(long attractionId)  {
        try{
        PlaceEntity place = (PlaceEntity)attractionEntitySessionBeanLocal.retrieveAttractionByAttractionId(attractionId);
        return place.getClosingTime();
        }catch(AttractionNotFoundException ex){}
        return new Date();
    }
    
    
    public void updateAttraction(ActionEvent event)
    {    
        if(isEvent(attractionToUpdate.getAttractionId())){
            //Check if the timings are valid
            if(getUpdatedStartDate() != null && getUpdatedEndDate() != null && getUpdatedStartDate().after(getUpdatedEndDate())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Closing Date should be later than Opening Date!", null));
            }else{
                if(getUpdatedStartDate() != null){
                    ((EventEntity)attractionToUpdate).setStartDate(getUpdatedStartDate());
                }else if(getUpdatedEndDate() != null){
                    ((EventEntity)attractionToUpdate).setEndDate(getUpdatedEndDate());
                }
                updateAttraction();
            }
        }else{
            //Check if the timings are valid
            if(getUpdatedOpeningTime() != null && getUpdatedClosingTime() != null && getUpdatedOpeningTime().after(getUpdatedClosingTime())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Closing Time should be later than Opening Time!", null));
            }else{
                if(getUpdatedOpeningTime() != null){
                    ((PlaceEntity)attractionToUpdate).setOpeningTime(getUpdatedOpeningTime());
                }else if(getUpdatedClosingTime() != null){
                    ((PlaceEntity)attractionToUpdate).setClosingTime(getUpdatedClosingTime());
                }
                updateAttraction();
            }
        }
    }
    
    public void updateAttraction(){
        try
        {
            CompanyEntity companyEntity = companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(getUpdatedCompanyId());
            attractionToUpdate.setCompanyEntity(companyEntity); 
            attractionEntitySessionBeanLocal.updateAttraction(getAttractionToUpdate(), getUpdatedTagIds(), getUpdatedPromotionIds());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction updated successfully", null));
        }
        catch(CompanyNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating attrcation: " + ex.getMessage(), null));
        }
        catch(AttractionNotFoundException | InputDataValidationException | PromotionNotFoundException | TagNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    public AttractionTypeEnum[] getAttractionTypes() {
        return attractionTypes;
    }

    public void setAttractionTypes(AttractionTypeEnum[] attractionTypes) {
        this.attractionTypes = attractionTypes;
    }

    public AttractionTypeEnum getNewAttractionType() {
        return newAttractionType;
    }

    public void setNewAttractionType(AttractionTypeEnum newAttractionType) {
        this.newAttractionType = newAttractionType;
    }

    public AttractionEntity getNewAttractionEntity() {
        return newAttractionEntity;
    }

    public void setNewAttractionEntity(AttractionEntity newAttractionEntity) {
        this.newAttractionEntity = newAttractionEntity;
    }
    
    public List<CompanyEntity> getAllCompanies() {
        return allCompanies;
    }

    public void setAllCompanies(List<CompanyEntity> allCompanies) {
        this.allCompanies = allCompanies;
    }
    
    public List<TagEntity> getAllTags() {
        return allTags;
    }

    public void setAllTags(List<TagEntity> allTags) {
        this.allTags = allTags;
    }

    public AttractionTypeEnum getEvent() {
        return event;
    }

    public void setEvent(AttractionTypeEnum event) {
        this.event = event;
    }

    public AttractionTypeEnum getPlace() {
        return place;
    }

    public void setPlace(AttractionTypeEnum place) {
        this.place = place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public List<Long> getNewTagIds() {
        return newTagIds;
    }

    public void setNewTagIds(List<Long> newTagIds) {
        this.newTagIds = newTagIds;
    }

    public List<Long> getNewPromotionIds() {
        return newPromotionIds;
    }

    public void setNewPromotionIds(List<Long> newPromotionIds) {
        this.newPromotionIds = newPromotionIds;
    }
    
    public List<PromotionEntity> getAllPromotions() {
        return allPromotions;
    }

    public void setAllPromotions(List<PromotionEntity> allPromotions) {
        this.allPromotions = allPromotions;
    }
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public List<EventEntity> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<EventEntity> allEvents) {
        this.allEvents = allEvents;
    }

    public List<PlaceEntity> getAllPlaces() {
        return allPlaces;
    }

    public void setAllPlaces(List<PlaceEntity> allPlaces) {
        this.allPlaces = allPlaces;
    }
    
    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }
    
    public AttractionEntity getAttractionToView() {
        return attractionToView;
    }

    public void setAttractionToView(AttractionEntity attractionToView) {
        this.attractionToView = attractionToView;
    }

    public AttractionEntity getAttractionToUpdate() {
        return attractionToUpdate;
    }

    public void setAttractionToUpdate(AttractionEntity attractionToUpdate) {
        this.attractionToUpdate = attractionToUpdate;
    }

    
    public Date getUpdatedStartDate() {
        return updatedStartDate;
    }

    public void setUpdatedStartDate(Date updatedStartDate) {
        this.updatedStartDate = updatedStartDate;
    }

    public Date getUpdatedEndDate() {
        return updatedEndDate;
    }

    public void setUpdatedEndDate(Date updatedEndDate) {
        this.updatedEndDate = updatedEndDate;
    }

    public Date getUpdatedOpeningTime() {
        return updatedOpeningTime;
    }

    public void setUpdatedOpeningTime(Date updatedOpeningTime) {
        this.updatedOpeningTime = updatedOpeningTime;
    }

    public Date getUpdatedClosingTime() {
        return updatedClosingTime;
    }

    public void setUpdatedClosingTime(Date updatedClosingTime) {
        this.updatedClosingTime = updatedClosingTime;
    }

    public Long getUpdatedCompanyId() {
        return updatedCompanyId;
    }

    public void setUpdatedCompanyId(Long updatedCompanyId) {
        this.updatedCompanyId = updatedCompanyId;
    }

    public List<Long> getUpdatedTagIds() {
        return updatedTagIds;
    }

    public void setUpdatedTagIds(List<Long> updatedTagIds) {
        this.updatedTagIds = updatedTagIds;
    }

    public List<Long> getUpdatedPromotionIds() {
        return updatedPromotionIds;
    }

    public void setUpdatedPromotionIds(List<Long> updatedPromotionIds) {
        this.updatedPromotionIds = updatedPromotionIds;
    }

}
