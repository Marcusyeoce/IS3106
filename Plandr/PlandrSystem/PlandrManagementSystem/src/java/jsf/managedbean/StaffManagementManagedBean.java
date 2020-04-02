/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.StaffEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.AccessRightEnum;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameExistException;

/**
 *
 * @author oimun
 */
@Named
@ViewScoped
public class StaffManagementManagedBean implements Serializable{

    @EJB(name ="staffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    
    private StaffEntity newStaffEntity;
    private AccessRightEnum[] accessRight;
    
    private List<StaffEntity> allStaffEntities;
    private StaffEntity staffEntityToView;
    private StaffEntity staffEntityToUpdate;

    public StaffManagementManagedBean() {
        newStaffEntity = new StaffEntity();
        accessRight = AccessRightEnum.values();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        allStaffEntities = staffEntitySessionBeanLocal.retrieveAllStaffs();
    }

    public void createNewStaff(ActionEvent event)
    {   
        try
        {
            Long newStaffId = staffEntitySessionBeanLocal.createNewStaff(getNewStaffEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"New Staff has been added succesfully! Staff's ID : " + newStaffId, null));
        }
        catch(InputDataValidationException | UsernameExistException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new staff: " + ex.getMessage(), null));
        }
    }
    
    public void updateStaff(ActionEvent event)
    {         
        try
        {
            staffEntitySessionBeanLocal.updateStaff(staffEntityToUpdate);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff updated successfully", null));
        }
        catch(StaffNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating staff: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteStaff(ActionEvent event)
    {
        try
        {
            StaffEntity staffEntityToDelete = (StaffEntity)event.getComponent().getAttributes().get("staffEntityToDelete");
            staffEntitySessionBeanLocal.deleteStaff(staffEntityToDelete.getStaffId());
            
            allStaffEntities.remove(staffEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff deleted successfully", null));
        }
        catch(StaffNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting staff: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public StaffEntity getNewStaffEntity() {
        return newStaffEntity;
    }

    public void setNewStaffEntity(StaffEntity newStaffEntity) {
        this.newStaffEntity = newStaffEntity;
    }
    
    public AccessRightEnum[] getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRightEnum[] accessRight) {
        this.accessRight = accessRight;
    }
    
    public List<StaffEntity> getAllStaffEntities() {
        return allStaffEntities;
    }

    public void setAllStaffEntities(List<StaffEntity> allStaffEntities) {
        this.allStaffEntities = allStaffEntities;
    }
    
    public StaffEntity getStaffEntityToView() {
        return staffEntityToView;
    }

    public void setStaffEntityToView(StaffEntity staffEntityToView) {
        this.staffEntityToView = staffEntityToView;
    }
    
    public StaffEntity getStaffEntityToUpdate() {
        return staffEntityToUpdate;
    }

    public void setStaffEntityToUpdate(StaffEntity staffEntityToUpdate) {
        this.staffEntityToUpdate = staffEntityToUpdate;
    }
}
