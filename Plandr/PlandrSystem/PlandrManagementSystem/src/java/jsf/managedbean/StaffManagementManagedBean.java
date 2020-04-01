/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.StaffEntity;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.AccessRightEnum;
import util.exception.InputDataValidationException;
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
    private String newAccessRight;
    
    public StaffManagementManagedBean() {
        newStaffEntity = new StaffEntity();
    }
    
    public void createNewStaff(ActionEvent event)
    {   
        if(newAccessRight.equals("1")){
            newStaffEntity.setAccessRightEnum(AccessRightEnum.ADMIN);
        }else{
            newStaffEntity.setAccessRightEnum(AccessRightEnum.EMPLOYEE);
        }
        
        try
        {
            Long newStaffId = staffEntitySessionBeanLocal.createNewStaff(getNewStaffEntity());
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"New Staff has been added succesfully! Staff's ID : " + newStaffId, null));
        }
        catch(InputDataValidationException | UsernameExistException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product: " + ex.getMessage(), null));
        }
    }

    public StaffEntity getNewStaffEntity() {
        return newStaffEntity;
    }

    public void setNewStaffEntity(StaffEntity newStaffEntity) {
        this.newStaffEntity = newStaffEntity;
    }

    public String getNewAccessRight() {
        return newAccessRight;
    }

    public void setNewAccessRight(String newAccessRight) {
        this.newAccessRight = newAccessRight;
    }
    
}
