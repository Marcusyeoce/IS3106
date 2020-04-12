package jsf.managedbean;

import ejb.session.stateless.ArticleEntitySessionBeanLocal;
import entity.ArticleEntity;
import entity.StaffEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import util.exception.ArticleNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author oimun
 */
@Named
@ViewScoped
public class ArticleManagedBean implements Serializable{

    @EJB(name = "ArticleEntitySessionBeanLocal")
    private ArticleEntitySessionBeanLocal articleEntitySessionBeanLocal;
    
    private StaffEntity author;
    private ArticleEntity newArticleEntity;
    private List<ArticleEntity> allArticleEntities;
    private ArticleEntity articleEntityToView;
    private ArticleEntity articleEntityToUpdate;



    public ArticleManagedBean() {
        newArticleEntity = new ArticleEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setAllArticleEntities(articleEntitySessionBeanLocal.retrieveAllArticles());
    }

    public void createNewArticle(ActionEvent event)
    {   
        try
        {
            author = (StaffEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaffEntity");
            Long newArticleId = articleEntitySessionBeanLocal.createNewArticle(getAuthor().getStaffId(), getNewArticleEntity());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"New article has been added succesfully! Article's ID : " + newArticleId, null));
        }
        catch(InputDataValidationException | StaffNotFoundException  ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new article: " + ex.getMessage(), null));
        }
    }
    
    public void updateArticle(ActionEvent event)
    {         
        try
        {
            articleEntitySessionBeanLocal.updateArticle(getArticleEntityToUpdate());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article updated successfully", null));
        }catch(ArticleNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating the new article: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteArticle(ActionEvent event)
    {
        try
        {
            ArticleEntity articleEntityToDelete = (ArticleEntity)event.getComponent().getAttributes().get("articleEntityToDelete");
            articleEntitySessionBeanLocal.deleteArticle(articleEntityToDelete.getArticleId());

            getAllArticleEntities().remove(articleEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article deleted successfully", null));
        }
        catch(ArticleNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting article: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    /*public void handleFileUpload(FileUploadEvent event)
    {
        try
        {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();
            System.err.println("********** Demo03ManagedBean.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** Demo03ManagedBean.handleFileUpload(): newFilePath: " + newFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            getNewArticleEntity().setPicture(newFilePath);
            getArticleEntityToUpdate().setPicture(newFilePath);
            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true)
            {
                a = inputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "File uploaded successfully", ""));
        }
        catch(IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "File upload error: " + ex.getMessage(), ""));
        }
    }*/

    /**
     * @return the author
     */
    public StaffEntity getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(StaffEntity author) {
        this.author = author;
    }

    /**
     * @return the newArticleEntity
     */
    public ArticleEntity getNewArticleEntity() {
        return newArticleEntity;
    }

    /**
     * @param newArticleEntity the newArticleEntity to set
     */
    public void setNewArticleEntity(ArticleEntity newArticleEntity) {
        this.newArticleEntity = newArticleEntity;
    }

    /**
     * @return the allArticleEntities
     */
    public List<ArticleEntity> getAllArticleEntities() {
        return allArticleEntities;
    }

    /**
     * @param allArticleEntities the allArticleEntities to set
     */
    public void setAllArticleEntities(List<ArticleEntity> allArticleEntities) {
        this.allArticleEntities = allArticleEntities;
    }

    /**
     * @return the articleEntityToView
     */
    public ArticleEntity getArticleEntityToView() {
        return articleEntityToView;
    }

    /**
     * @param articleEntityToView the articleEntityToView to set
     */
    public void setArticleEntityToView(ArticleEntity articleEntityToView) {
        this.articleEntityToView = articleEntityToView;
    }

    /**
     * @return the articleEntityToUpdate
     */
    public ArticleEntity getArticleEntityToUpdate() {
        return articleEntityToUpdate;
    }

    /**
     * @param articleEntityToUpdate the articleEntityToUpdate to set
     */
    public void setArticleEntityToUpdate(ArticleEntity articleEntityToUpdate) {
        this.articleEntityToUpdate = articleEntityToUpdate;
    }
}
