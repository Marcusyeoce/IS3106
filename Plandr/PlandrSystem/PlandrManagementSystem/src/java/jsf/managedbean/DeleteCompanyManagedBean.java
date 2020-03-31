
package jsf.managedbean;

import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import entity.CompanyEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;

/**
 *
 * @author celes
 */
@Named(value = "DeleteCompanyManagedBean")
@RequestScoped
public class DeleteCompanyManagedBean {

    @EJB(name ="CompanyEntitySessionBeanLocal")
    private CompanyEntitySessionBeanLocal companyEntitySessionBeanLocal;
    
    private CompanyEntity companyEntityToDelete;
    private List<CompanyEntity> companies;

    
    public DeleteCompanyManagedBean() {
    }
    
    public void deleteCompany(ActionEvent event)
    {
        try
        {
            CompanyEntity companyEntityToDelete = (CompanyEntity)event.getComponent().getAttributes().get("companyEntityToDelete");
            companyEntitySessionBeanLocal.deleteCompany(companyEntityToDelete.getCompanyId());
            
            companies.remove(companyEntityToDelete);
            
            /*if(companies != null)
            {
                companies.remove(companyEntityToDelete);
            }*/

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product deleted successfully", null));
        }
        catch(CompanyNotFoundException | DeleteCompanyException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
}
