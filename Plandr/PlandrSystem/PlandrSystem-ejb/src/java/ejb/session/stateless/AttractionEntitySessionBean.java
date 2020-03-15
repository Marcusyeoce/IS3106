package ejb.session.stateless;

import entity.AttractionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author oimun
 */
@Stateless
public class AttractionEntitySessionBean implements AttractionEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    
    @Override
    public List<AttractionEntity> retrieveAttractionsByCompanyId(Long companyId){
        Query query = em.createQuery("SELECT a FROM AttractionEntity a WHERE a.companyEntity.companyId = :inCompanyId ORDER BY a.attractionId ASC");
        query.setParameter("inCompanyId", companyId);
        List<AttractionEntity> attractionEntities = query.getResultList();
        
        return attractionEntities;
    }
    
    
}
