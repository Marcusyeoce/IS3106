/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReviewEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@Stateless
public class ReviewEntitySessionBean implements ReviewEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    //for reviews only can create, but not edit & delete 
    //for retrieval also get by attraction rather than id
    @Override
    public Long createNewReview(ReviewEntity newReview) {
        em.persist(newReview);
        em.flush(); //because its using IDENTITY generation, need to flush then can get the ID back from the db management system 
        return newReview.getReviewId(); 
    }
}
