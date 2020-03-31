
package jsf.managedbean;

import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import entity.CompanyEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "ViewAllCompanyManagedBean")
@RequestScoped
public class ViewAllCompanyManagedBean {

    @EJB(name="CompanyEntitySessionBeanLocal")
    private CompanyEntitySessionBeanLocal companyEntitySessionBeanLocal;
    
    private List<CompanyEntity> companies;

    @PostConstruct 
    public void postConstruct() {
    companies = companyEntitySessionBeanLocal.retrieveAllCompanies(); 
    }
       
    public ViewAllCompanyManagedBean() {
    }

    public List<CompanyEntity> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyEntity> companies) {
        this.companies = companies;
    }
    
}
