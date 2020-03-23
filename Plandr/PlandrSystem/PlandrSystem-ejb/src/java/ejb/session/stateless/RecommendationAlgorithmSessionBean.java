package ejb.session.stateless;

import entity.AttractionEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Marcusyeoce
 */
@Stateless
public class RecommendationAlgorithmSessionBean implements RecommendationAlgorithmSessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public AttractionEntity getReccomendedAttractionByTags() {
        
    }

    public AttractionEntity getReccomendedAttractionByPopular() {
        
    }
    
}
