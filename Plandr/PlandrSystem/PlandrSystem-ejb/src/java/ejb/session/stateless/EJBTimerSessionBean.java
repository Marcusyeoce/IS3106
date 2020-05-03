package ejb.session.stateless;

import entity.MemberEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import util.exception.MemberNotFoundException;

/**
 *
 * @author oimun
 */
@Stateless
public class EJBTimerSessionBean implements EJBTimerSessionBeanLocal {

    @EJB(name = "MemberEntitySessionBeanLocal")
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @Schedule(hour = "1", minute = "0", second="0", info = "memberSubscriptionTimer")
    public void memberSubscriptionTimer()
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        System.out.println("********** EjbTimerSession.memberSubscriptionTimer(): Timeout at " + timeStamp);
        
        List<MemberEntity> memberEntities = memberEntitySessionBeanLocal.retrieveAllMembers();
        Date current = new Date();
        
        for(MemberEntity member:memberEntities)
        {
            if(member.isSubscribed()){
                //If Member's subsription is over, update member's subscribe status
                if(current.after(member.getSubscribedUntil())){
                    memberEntitySessionBeanLocal.updateSubscriptionStatus(member);
                    System.out.println("********** Member " + member.getMemberId() + " subscription has ended. Subscription status have been updated successfully. ");
                
                }
            }
        }
    }
}
