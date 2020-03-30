/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import entity.CompanyEntity;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CompanyExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;


@Named(value = "CreateNewCompanyManagedBean")
@RequestScoped
public class CreateNewCompanyManagedBean {

    @EJB(name ="CompanyEntitySessionBeanLocal")
    private CompanyEntitySessionBeanLocal companyEntitySessionBeanLocal;

    private CompanyEntity newCompany;
    private String name;
    private String email;
    private String contactNum;
    
    public CreateNewCompanyManagedBean() {
    newCompany = new CompanyEntity();
    }
    
    public void createNewCompany(ActionEvent event) throws CompanyExistException, UnknownPersistenceException, InputDataValidationException {
        Long newCompanyId = companyEntitySessionBeanLocal.createNewCompany(newCompany);
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"New Company has been added succesfully! Company's ID : " + newCompany.getCompanyId(),null));
    }

    public CompanyEntity getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(CompanyEntity newCompany) {
        this.newCompany = newCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
    
    
}
