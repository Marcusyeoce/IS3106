package ejb.session.singleton;

import ejb.session.stateless.ArticleEntitySessionBeanLocal;
import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import ejb.session.stateless.BookingEntitySessionBeanLocal;
import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.MessageOfTheDayEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.ArticleEntity;
import entity.CompanyEntity;
import entity.EventEntity;
import entity.MessageOfTheDayEntity;
import entity.PlaceEntity;
import entity.PromotionEntity;
import entity.StaffEntity;
import entity.TagEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AccessRightEnum;
import util.exception.AttractionNotFoundException;
import util.exception.CompanyExistException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.PromotionNotFoundException;
import util.exception.StaffNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UsernameExistException;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import entity.BookingEntity;
import entity.MemberEntity;
import util.enumeration.GenderEnum;
import util.exception.MemberNotFoundException;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "BookingEntitySessionBeanLocal")
    private BookingEntitySessionBeanLocal bookingEntitySessionBeanLocal;

    @EJB(name = "MemberEntitySessionBeanLocal")
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB(name = "PromotionSessionBeanLocal")
    private PromotionEntitySessionBeanLocal promotionSessionBeanLocal;

    @EJB(name = "AttractionEntitySessionBeanLocal")
    private AttractionEntitySessionBeanLocal attractionEntitySessionBeanLocal;

    @EJB(name = "TagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "CompanyEntitySessionBeanLocal")
    private CompanyEntitySessionBeanLocal companyEntitySessionBeanLocal;

    @PersistenceContext(unitName = "PlandrSystem-ejbPU")
    private EntityManager em;
    
    @EJB(name = "StaffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    @EJB
    private MessageOfTheDayEntitySessionBeanLocal motdSessionBeanLocal;
    @EJB(name ="ArticleEntitySessionBeanLocal")
    private ArticleEntitySessionBeanLocal articleEntitySessionBeanLocal;

    @PostConstruct
    public void postConstruct(){
        try
        {
            staffEntitySessionBeanLocal.retrieveStaffByUsername("admin");
        }
        catch(StaffNotFoundException ex)
        {
            initializeData();
        } 
    }
    
    public void initializeData(){
        try
        {
            staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Admin", "admin@gmail.com", "99999991", "admin", "password", AccessRightEnum.ADMIN)); 
            staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Employee", "employee@gmail.com", "99999992", "employee", "password", AccessRightEnum.EMPLOYEE));
            Date today = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
            Date theDayAfter = new Date(tomorrow.getTime() + (1000 * 60 * 60 * 24));
            motdSessionBeanLocal.createNewMessageOfTheDay(new MessageOfTheDayEntity("Zoom Meme","https://bit.ly/zoomMeme",today));
            motdSessionBeanLocal.createNewMessageOfTheDay(new MessageOfTheDayEntity("Programming Meme","https://bit.ly/programmingMeme", tomorrow));
            motdSessionBeanLocal.createNewMessageOfTheDay(new MessageOfTheDayEntity("SG-COVID Meme","https://bit.ly/pmLeememe", theDayAfter));
            
            companyEntitySessionBeanLocal.createNewCompany(new CompanyEntity("Company A", "companya@gmail.com", "84753843"));
            companyEntitySessionBeanLocal.createNewCompany(new CompanyEntity("Company B", "companyb@gmail.com", "29835852"));
            companyEntitySessionBeanLocal.createNewCompany(new CompanyEntity("Company C", "companyc@gmail.com", "89325773"));
            
            TagEntity tag1 = new TagEntity("Discount");
            tagEntitySessionBeanLocal.createNewTagEntity(tag1, null);
            TagEntity tag2 = new TagEntity("Popular");
            tagEntitySessionBeanLocal.createNewTagEntity(tag2, null);
            
            List<Long> tags = new ArrayList<>();
            tags.add(tag1.getTagId()); 
            tags.add(tag2.getTagId());
            List<Long> promotions = new ArrayList<>();
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new EventEntity(new Date(120, 05, 01), new Date(120, 05, 30), "Prudential’s Carnival", "$$", "12A Bayfront Ave, Singapore 018970", "https://i.ibb.co/RhcFKD2/prudential.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(1l), new BigDecimal("15.00")), tags, promotions);
            tags = new ArrayList<>();
            tags.add(tag1.getTagId()); 
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new EventEntity(new Date(120, 05, 07), new Date(120, 06, 04), "Wartime Artists of Vietnam Exhibition", "FREE", "50 Kent Ridge Cres", "https://i.ibb.co/ZWx882x/wartime.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(2l), new BigDecimal("0.00")), tags, promotions);
            tags = new ArrayList<>();
            tags.add(tag2.getTagId()); 
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new PlaceEntity(new Date(120, 00, 01, 9, 0), new Date(120, 00, 01, 18, 0), "Pusheen Cafe", "$$", "8 Jln Klapa, Singapore 199320", "https://i.ibb.co/ZGm1f2V/pusheen.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(1l), new BigDecimal("0.00")), tags, promotions);
            tags = new ArrayList<>();
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new PlaceEntity(new Date(120, 00, 01, 12, 0), new Date(120, 00, 01, 21, 0), "Odette Restaurant", "$$$", "1 St Andrew's Rd, Singapore 178957", "https://i.ibb.co/st23zkP/odette.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(3l), new BigDecimal("0.00")), tags, promotions);
          
            List<Long> attractions = new ArrayList<>();
            attractions.add(1l);
            attractions.add(2l);
            attractions.add(3l);
            attractions.add(4l);
            promotionSessionBeanLocal.createNewPromotionEntity(new PromotionEntity("First Time Users $5 OFF", new Date(120, 00, 01), new Date(121, 00, 01), new BigDecimal(5)), attractions);
            articleEntitySessionBeanLocal.createNewArticle(staffEntitySessionBeanLocal.retrieveStaffByStaffId(2L).getStaffId(), new ArticleEntity("Top 10 Cafes in Singapore","https://bit.ly/tolido","Looking for a cafe date with your friends or girlfriend? Tolido's Espresso Nook is the place to go! Great little cafe which serves tremendous coffee and very tasty breakfast options.",today));
            articleEntitySessionBeanLocal.createNewArticle(staffEntitySessionBeanLocal.retrieveStaffByStaffId(2L).getStaffId(), new ArticleEntity("Top 10 Waffle Ice Cream in Singapore","https://bit.ly/sundayFolks","Hot and sweating? Look no further because Sunday Folks is here to cool you down! Offering amazing waffle ice cream with an array of flavours is sure to satisfy your cravings!",tomorrow));
            articleEntitySessionBeanLocal.createNewArticle(staffEntitySessionBeanLocal.retrieveStaffByStaffId(2L).getStaffId(), new ArticleEntity("Gardens By The Bay Singapore","https://bit.ly/gardensbtb","With their many themed gardens, it never gets boring at gardens by the bay! Now their flower dome tickets are 1 for 1. Take this chance to bring someone along to admire the pretty flowers with you.",theDayAfter));
            
            memberEntitySessionBeanLocal.createNewMember(new MemberEntity("Member 1", "member1@gmail.com", "91234567", "member1", "password", GenderEnum.MALE, new Date(100, 2, 1), "1234123412341234"));
            memberEntitySessionBeanLocal.createNewMember(new MemberEntity("Member 2", "member2@gmail.com", "98765432", "member2", "password", GenderEnum.FEMALE, new Date(100, 9, 25), "9876543223456789"));
            
            attractions = new ArrayList<>();
            attractions.add(1l);
            attractions.add(3l);
            bookingEntitySessionBeanLocal.createNewBooking(new BookingEntity(new BigDecimal("25.00"), new Date(120, 05, 05), "Casual date for 2"), "member1", attractions);
            attractions = new ArrayList<>();
            attractions.add(2l);
            attractions.add(4l);
            bookingEntitySessionBeanLocal.createNewBooking(new BookingEntity(new BigDecimal("0.00"), new Date(120, 05, 06), "Romantic day for wedding anniversary"), "member2", attractions);
        }catch(UsernameExistException | AttractionNotFoundException | CompanyExistException | TagNotFoundException | PromotionNotFoundException | CompanyNotFoundException | CreateNewTagException |  UnknownPersistenceException | InputDataValidationException | StaffNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
