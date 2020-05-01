/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.MemberEntity;
import java.util.Date;

/**
 *
 * @author Pham The Dzung
 */
public class MemberSubscribeRsp {
    private Date subscribedUntil;

    public MemberSubscribeRsp() {
    }

    public MemberSubscribeRsp(Date subscribedUntil) {
        this.subscribedUntil = subscribedUntil;
    }

    public Date getSubscribedUntil() {
        return subscribedUntil;
    }

    public void setSubscribedUntil(Date subscribedUntil) {
        this.subscribedUntil = subscribedUntil;
    }
    
    
    
}
