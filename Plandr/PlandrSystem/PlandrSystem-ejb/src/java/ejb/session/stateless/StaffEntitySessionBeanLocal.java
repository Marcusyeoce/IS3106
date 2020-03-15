/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordMismatchException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStaffException;
import util.exception.UsernameExistException;

/**
 *
 * @author oimun
 */
@Local
public interface StaffEntitySessionBeanLocal {

    public Long createNewStaff(StaffEntity newStaffEntity) throws UsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public StaffEntity staffLogin(String username, String password) throws InvalidLoginCredentialException, StaffNotFoundException;

    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException;

    public void updateStaff(StaffEntity staff) throws StaffNotFoundException, UpdateStaffException, InputDataValidationException;

    public void updateStaffPassword(Long staffId, String oldPassword, String newPassword, String reenteredNewPassword) throws StaffNotFoundException, PasswordMismatchException;

    public StaffEntity retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException;

    public void deleteStaff(Long staffId) throws StaffNotFoundException;

    public List<StaffEntity> retrieveAllStaffs();
    
}
