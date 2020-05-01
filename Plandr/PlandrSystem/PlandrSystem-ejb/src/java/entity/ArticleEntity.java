package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author celes
 */
@Entity
public class ArticleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    @Column(nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    private String title;
    @Column(length = 300)
    @NotNull
    @Size(max = 300)
    private String picture; //save the file path as a string
    @Column(nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date publishedDate;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private StaffEntity staffEntity;

    public ArticleEntity() {
    }

    public ArticleEntity(String title, String picture, String content, Date publishedDate) {
        this();
        
        this.title = title;
        this.picture = picture;
        this.content = content;
        this.publishedDate = publishedDate;
    }

    public StaffEntity getStaffEntity() {
        return staffEntity;
    }

    public void setStaffEntity(StaffEntity staffEntity) {
        this.staffEntity = staffEntity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
   
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articleId != null ? articleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the articleId fields are not set
        if (!(object instanceof ArticleEntity)) {
            return false;
        }
        ArticleEntity other = (ArticleEntity) object;
        if ((this.articleId == null && other.articleId != null) || (this.articleId != null && !this.articleId.equals(other.articleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ArticleEntity[ id=" + articleId + " ]";
    }
    
}
