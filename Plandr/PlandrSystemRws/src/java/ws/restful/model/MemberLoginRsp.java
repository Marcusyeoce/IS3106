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
public class MemberLoginRsp {
    private MemberEntity member;

    public MemberLoginRsp() {
    }

    public MemberLoginRsp(MemberEntity member) {
        this.member = member;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
    
    
}
