package jsf.managedbean;

import ejb.session.stateless.MemberEntitySessionBeanLocal;
import entity.MemberEntity;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author oimun
 */
@Named
@ViewScoped
public class CustomerManagedBean implements Serializable{

    @EJB(name = "MemberEntitySessionBeanLocal")
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;
    
    private List<MemberEntity> allMemberEntities;
    private MemberEntity memberToView;

    public CustomerManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setAllMemberEntities(memberEntitySessionBeanLocal.retrieveAllMembers());
    }

    public List<MemberEntity> getAllMemberEntities() {
        return allMemberEntities;
    }

    public void setAllMemberEntities(List<MemberEntity> allMemberEntities) {
        this.allMemberEntities = allMemberEntities;
    }
    
    public MemberEntity getMemberToView() {
        return memberToView;
    }

    public void setMemberToView(MemberEntity memberToView) {
        this.memberToView = memberToView;
    }

    
}
