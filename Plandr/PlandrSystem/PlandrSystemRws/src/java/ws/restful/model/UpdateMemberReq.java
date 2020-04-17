/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.MemberEntity;

/**
 *
 * @author Pham The Dzung
 */
public class UpdateMemberReq {
    private String username;
    private String password;
    private MemberEntity member;

    public UpdateMemberReq() {
    }

    public UpdateMemberReq(String username, String password, MemberEntity member) {
        this.username = username;
        this.password = password;
        this.member = member;
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

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
    
    
}
