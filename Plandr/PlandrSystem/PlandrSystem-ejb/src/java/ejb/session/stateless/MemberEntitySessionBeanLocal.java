/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MemberEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;
import util.exception.PasswordMismatchException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMemberException;
import util.exception.UsernameExistException;

/**
 *
 * @author Pham The Dzung
 */
@Local
public interface MemberEntitySessionBeanLocal {

    Long createNewMember(MemberEntity newMemberEntity) throws UsernameExistException, UnknownPersistenceException, InputDataValidationException;

    MemberEntity memberLogin(String username, String password) throws InvalidLoginCredentialException;

    MemberEntity retrieveMemberByUsername(String username) throws MemberNotFoundException;

    void updateMember(MemberEntity member) throws MemberNotFoundException, UpdateMemberException, InputDataValidationException;

    MemberEntity retrieveMemberById(Long memberId) throws MemberNotFoundException;

    void updateMemberPassword(String username, String oldPassword, String newPassword, String reenteredNewPassword) throws MemberNotFoundException, PasswordMismatchException;

    void memberSubscribe(String username, int subPackage) throws MemberNotFoundException;

    public List<MemberEntity> retrieveAllMembers();
    
}
