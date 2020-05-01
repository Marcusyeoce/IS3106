/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author Pham The Dzung
 */
public class UpdateMemberPasswordReq {
    private String username;
    private String password;
    private String newPassword;
    private String rePassword;

    public UpdateMemberPasswordReq() {
    }

    public UpdateMemberPasswordReq(String username, String password, String newPassword, String rePassword) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.rePassword = rePassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
    
    
}
