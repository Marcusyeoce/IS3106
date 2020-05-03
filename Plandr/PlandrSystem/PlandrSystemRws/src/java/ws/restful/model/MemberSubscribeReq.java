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
public class MemberSubscribeReq {
    private String username;
    private String password;
    private Integer subPackage;

    public MemberSubscribeReq() {
    }

    public MemberSubscribeReq(String username, String password, Integer subPackage) {
        this.username = username;
        this.password = password;
        this.subPackage = subPackage;
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

    public Integer getSubPackage() {
        return subPackage;
    }

    public void setSubPackage(Integer subPackage) {
        this.subPackage = subPackage;
    }
    
    
    
}
