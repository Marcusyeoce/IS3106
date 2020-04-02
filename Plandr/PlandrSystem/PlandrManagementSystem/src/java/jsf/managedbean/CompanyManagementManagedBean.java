/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import entity.CompanyEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CompanyExistException;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author oimun
 */
@Named
@ViewScoped
public class CompanyManagementManagedBean implements Serializable{

    @EJB(name = "CompanyEntitySessionBeanLocal")
    private CompanyEntitySessionBeanLocal companyEntitySessionBeanLocal;

    private CompanyEntity newCompanyEntity;
    private List<CompanyEntity> allCompanyEntities;
    private CompanyEntity companyEntityToView;
    private CompanyEntity companyEntityToUpdate;

    public CompanyManagementManagedBean() {
        newCompanyEntity = new CompanyEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setAllCompanyEntities(companyEntitySessionBeanLocal.retrieveAllCompanies());
    }

    public void createNewCompany(ActionEvent event)
    {   
        try
        {
            Long newCompanyId = companyEntitySessionBeanLocal.createNewCompany(getNewCompanyEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"New Company has been added succesfully! Company's ID : " + newCompanyId, null));
        }
        catch(InputDataValidationException | CompanyExistException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new company: " + ex.getMessage(), null));
        }
    }
    
    public void updateCompany(ActionEvent event)
    {         
        try
        {
            companyEntitySessionBeanLocal.updateCompany(getCompanyEntityToUpdate());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company updated successfully", null));
        }
        catch(CompanyNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating company: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteCompany(ActionEvent event)
    {
        try
        {
            CompanyEntity companyEntityToDelete = (CompanyEntity)event.getComponent().getAttributes().get("companyEntityToDelete");
            companyEntitySessionBeanLocal.deleteCompany(companyEntityToDelete.getCompanyId());

            getAllCompanyEntities().remove(companyEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company deleted successfully", null));
        }
        catch(CompanyNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting company: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the newCompanyEntity
     */
    public CompanyEntity getNewCompanyEntity() {
        return newCompanyEntity;
    }

    /**
     * @param newCompanyEntity the newCompanyEntity to set
     */
    public void setNewCompanyEntity(CompanyEntity newCompanyEntity) {
        this.newCompanyEntity = newCompanyEntity;
    }

    /**
     * @return the allCompanyEntities
     */
    public List<CompanyEntity> getAllCompanyEntities() {
        return allCompanyEntities;
    }

    /**
     * @param allCompanyEntities the allCompanyEntities to set
     */
    public void setAllCompanyEntities(List<CompanyEntity> allCompanyEntities) {
        this.allCompanyEntities = allCompanyEntities;
    }

    /**
     * @return the companyEntityToView
     */
    public CompanyEntity getCompanyEntityToView() {
        return companyEntityToView;
    }

    /**
     * @param companyEntityToView the companyEntityToView to set
     */
    public void setCompanyEntityToView(CompanyEntity companyEntityToView) {
        this.companyEntityToView = companyEntityToView;
    }

    /**
     * @return the companyEntityToUpdate
     */
    public CompanyEntity getCompanyEntityToUpdate() {
        return companyEntityToUpdate;
    }

    /**
     * @param companyEntityToUpdate the companyEntityToUpdate to set
     */
    public void setCompanyEntityToUpdate(CompanyEntity companyEntityToUpdate) {
        this.companyEntityToUpdate = companyEntityToUpdate;
    }
    
}
