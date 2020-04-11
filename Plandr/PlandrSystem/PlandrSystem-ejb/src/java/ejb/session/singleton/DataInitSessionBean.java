package ejb.session.singleton;

import ejb.session.stateless.ArticleEntitySessionBeanLocal;
import ejb.session.stateless.AttractionEntitySessionBeanLocal;
import ejb.session.stateless.CompanyEntitySessionBeanLocal;
import ejb.session.stateless.MessageOfTheDayEntitySessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
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

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "PromotionSessionBeanLocal")
    private PromotionSessionBeanLocal promotionSessionBeanLocal;

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
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new EventEntity(new Date(120, 05, 01), new Date(120, 05, 30), "Prudential’s Carnival", "$$", "12A Bayfront Ave, Singapore 018970", "https://i.ibb.co/RhcFKD2/prudential.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(1l)), tags, promotions);
            tags = new ArrayList<>();
            tags.add(tag1.getTagId()); 
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new EventEntity(new Date(120, 05, 07), new Date(120, 06, 04), "Wartime Artists of Veitnam Exhibition", "FREE", "50 Kent Ridge Cres", "https://i.ibb.co/ZWx882x/wartime.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(2l)), tags, promotions);
            tags = new ArrayList<>();
            tags.add(tag2.getTagId()); 
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new PlaceEntity(new Date(120, 00, 01, 9, 30), new Date(120, 00, 01, 18, 30), "Pusheen Cafe", "$$", "8 Jln Klapa, Singapore 199320", "https://i.ibb.co/ZGm1f2V/pusheen.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(1l)), tags, promotions);
            tags = new ArrayList<>();
            attractionEntitySessionBeanLocal.createNewAttractionEntity(new PlaceEntity(new Date(120, 00, 01, 12, 0), new Date(120, 00, 01, 21, 0), "Odette Restaurant", "$$$", "1 St Andrew's Rd, Singapore 178957", "https://i.ibb.co/st23zkP/odette.jpg", companyEntitySessionBeanLocal.retrieveCompanyByCompanyId(3l)), tags, promotions);
          
            List<Long> attractions = new ArrayList<>();
            attractions.add(1l);
            attractions.add(2l);
            attractions.add(3l);
            attractions.add(4l);
            promotionSessionBeanLocal.createNewPromotionEntity(new PromotionEntity("First Time Users $5 OFF", new Date(120, 00, 01), new Date(121, 00, 01), new BigDecimal(5)), attractions);
           articleEntitySessionBeanLocal.createNewArticle(staffEntitySessionBeanLocal.retrieveStaffByStaffId(2L).getStaffId(), new ArticleEntity("Title1","https://bit.ly/tanjirooo","content1",today));
            articleEntitySessionBeanLocal.createNewArticle(staffEntitySessionBeanLocal.retrieveStaffByStaffId(2L).getStaffId(), new ArticleEntity("Title2","https://bit.ly/helloWorld1","content2",tomorrow));
            articleEntitySessionBeanLocal.createNewArticle(staffEntitySessionBeanLocal.retrieveStaffByStaffId(2L).getStaffId(), new ArticleEntity("Title3","https://bit.ly/articleV1","content3",theDayAfter));
        }catch(UsernameExistException | AttractionNotFoundException | CompanyExistException | TagNotFoundException | PromotionNotFoundException | CompanyNotFoundException | CreateNewTagException |  UnknownPersistenceException | InputDataValidationException | StaffNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
