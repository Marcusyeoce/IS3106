package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author celes
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class AttractionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attractionId;
    @Column(nullable = false, length = 52)
    @NotNull
    @Size(max = 52)
    private String name;
    @Column(nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    private String description;
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String location;
    @Column(length = 300)
    @Size(max = 300)
    private String picture;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal unitPrice;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private CompanyEntity companyEntity;

    @ManyToMany(mappedBy = "attractionEntities")
    private List<PromotionEntity> promotionEntities;
    
    @ManyToMany(mappedBy = "attractionEntities")
    private List<TagEntity> tagEntities;
    
    @OneToMany(mappedBy = "attractionEntity")
    private List<ReviewEntity> reviewEntities;

    
    public AttractionEntity() {
        tagEntities = new ArrayList<>();
        reviewEntities = new ArrayList<>();
        promotionEntities = new ArrayList<>();
    }

    public AttractionEntity(String name, String description, String location, String picture, CompanyEntity companyEntity, BigDecimal unitPrice) {
        this();
        this.name = name;
        this.description = description;
        this.location = location;
        this.picture = picture;
        this.companyEntity = companyEntity;
        this.unitPrice = unitPrice;
    }
    
    public void addTag(TagEntity tagEntity)
    {
        if(tagEntity != null)
        {
            if(!this.tagEntities.contains(tagEntity))
            {
                this.tagEntities.add(tagEntity);
                
                if(!tagEntity.getAttractionEntities().contains(this))
                {                    
                    tagEntity.getAttractionEntities().add(this);
                }
            }
        }
    }

    public void addPromotion(PromotionEntity promotionEntity)
    {
        if(promotionEntity != null)
        {
            if(!this.promotionEntities.contains(promotionEntity))
            {
                this.promotionEntities.add(promotionEntity);
                
                if(!promotionEntity.getAttractionEntities().contains(this))
                {                    
                    promotionEntity.getAttractionEntities().add(this);
                }
            }
        }
    }
    
    public void addReview(ReviewEntity reviewEntity)
    {
        if(reviewEntity != null)
        {
            if(!this.reviewEntities.contains(reviewEntity))
            {
                this.reviewEntities.add(reviewEntity);
                
                if(!reviewEntity.getAttractionEntity().equals(this))
                {                    
                    reviewEntity.setAttractionEntity(this);
                }
            }
        }
    }
    
     public void removePromotion(PromotionEntity promotionEntity)
    {
        if(promotionEntity != null)
        {
            if(this.promotionEntities.contains(promotionEntity))
            {
                this.promotionEntities.remove(promotionEntity);
                
                if(promotionEntity.getAttractionEntities().contains(this))
                {
                    promotionEntity.getAttractionEntities().remove(this);
                }
            }
        }
    }
    
    public Long getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<TagEntity> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(List<TagEntity> tagEntities) {
        this.tagEntities = tagEntities;
    }
    
    public List<ReviewEntity> getReviewEntities() {
        return reviewEntities;
    }

    public void setReviewEntities(List<ReviewEntity> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }
    
    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }
    
    public List<PromotionEntity> getPromotionEntities() {
        return promotionEntities;
    }

    public void setPromotionEntities(List<PromotionEntity> promotionEntities) {
        this.promotionEntities = promotionEntities;
    }
    
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attractionId != null ? attractionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the attractionId fields are not set
        if (!(object instanceof AttractionEntity)) {
            return false;
        }
        AttractionEntity other = (AttractionEntity) object;
        if ((this.attractionId == null && other.attractionId != null) || (this.attractionId != null && !this.attractionId.equals(other.attractionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AttractionEntity[ id=" + attractionId + " ]";
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
}
