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
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.AttractionTypeEnum;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;

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

    private AttractionTypeEnum event;
    private Date startDate;
    private Date endDate;
    
    private AttractionTypeEnum place;
    private Integer openingHour;
    private Integer openingMinute;
    private Integer closingHour;
    private Integer closingMinute;

    public AttractionManagedBean() {
        event = AttractionTypeEnum.EVENT;
        place = AttractionTypeEnum.PLACE;
        openingHour = 0;
        openingMinute = 0;
        closingHour = 0;
        closingMinute = 0;
        attractionTypes = AttractionTypeEnum.values();
    }
    
    @PostConstruct
    public void postConstruct(){
        allCompanies = companyEntitySessionBeanLocal.retrieveAllCompanies();
        allTags = tagEntitySessionBeanLocal.retrieveAllTags();
        allPromotions = promotionSessionBeanLocal.retrieveAllPromotions();
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
            ((EventEntity)newAttractionEntity).setStartDate(getStartDate());
            ((EventEntity)newAttractionEntity).setEndDate(getEndDate());
            createNewAttraction();
        }else{
            //Check if the timings are valid
            Integer openingTime = openingHour*100 + openingMinute;
            Integer closingTime = closingHour*100 + closingMinute;
            if(openingTime > closingTime){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Closing Time should be later than Opening Time!", null));
            }else{
                ((PlaceEntity)newAttractionEntity).setOpeningTime(openingTime);
                ((PlaceEntity)newAttractionEntity).setClosingTime(closingTime);
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
   
    public Integer getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(Integer openingHour) {
        this.openingHour = openingHour;
    }

    public Integer getOpeningMinute() {
        return openingMinute;
    }

    public void setOpeningMinute(Integer openingMinute) {
        this.openingMinute = openingMinute;
    }

    public Integer getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(Integer closingHour) {
        this.closingHour = closingHour;
    }

    public Integer getClosingMinute() {
        return closingMinute;
    }

    public void setClosingMinute(Integer closingMinute) {
        this.closingMinute = closingMinute;
    }
}
