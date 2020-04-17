/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReviewEntity;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Pham The Dzung
 */
@Local
public interface ReviewEntitySessionBeanLocal {
    
    public Long createNewReview(ReviewEntity newReview, Long attractionId, String username) throws InputDataValidationException, UnknownPersistenceException;
}
