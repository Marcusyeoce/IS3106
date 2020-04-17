/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ArticleEntitySessionBeanLocal;
import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import ejb.session.stateless.BookingEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.ReviewEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;

/**
 *
 * @author Pham The Dzung
 */
public class SessionBeanLookup {

    public SessionBeanLookup() {
    }
    
    public ArticleEntitySessionBeanLocal lookupArticleEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ArticleEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/ArticleEntitySessionBean!ejb.session.stateless.ArticleEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public AttractionEntitySessionBeanLocal lookupAttractionEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AttractionEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/AttractionEntitySessionBean!ejb.session.stateless.AttractionEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public BookingEntitySessionBeanLocal lookupBookingEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BookingEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/BookingEntitySessionBean!ejb.session.stateless.BookingEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public MemberEntitySessionBeanLocal lookupMemberEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MemberEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/MemberEntitySessionBean!ejb.session.stateless.MemberEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public PromotionEntitySessionBeanLocal lookupPromotionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PromotionEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/PromotionSessionBean!ejb.session.stateless.PromotionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public ReviewEntitySessionBeanLocal lookupReviewEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/ReviewEntitySessionBean!ejb.session.stateless.ReviewEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public TagEntitySessionBeanLocal lookupTagEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TagEntitySessionBeanLocal) c.lookup("java:global/PlandrSystem/PlandrSystem-ejb/TagEntitySessionBean!ejb.session.stateless.TagEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
