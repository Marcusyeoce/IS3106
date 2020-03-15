/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CompanyEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author oimun
 */
@Local
public interface CompanyEntitySessionBeanLocal {

    public Long createNewCompany(CompanyEntity newCompanyEntity) throws CompanyExistException, UnknownPersistenceException, InputDataValidationException;

    public CompanyEntity retrieveCompanyByCompanyId(Long companyId) throws CompanyNotFoundException;

    public List<CompanyEntity> retrieveAllComapnies();

    public void updateComapny(CompanyEntity company) throws CompanyNotFoundException, InputDataValidationException;

    public void deleteComapny(Long companyId) throws CompanyNotFoundException, DeleteCompanyException;
    
}
