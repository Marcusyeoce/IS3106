package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author celes
 */
@Entity
public class PromotionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date startDate;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date endDate;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal discount;
    
    @ManyToMany
    private List<AttractionEntity> attractionEntities;
    
    public PromotionEntity() {
    }

    public PromotionEntity(String name, Date startDate, BigDecimal discount, List<AttractionEntity> attractionEntities) {
        this();
        this.name = name;
        this.startDate = startDate;
        this.discount = discount;
        this.attractionEntities = attractionEntities;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
    public List<AttractionEntity> getAttractionEntities() {
        return attractionEntities;
    }

    public void setAttractionEntities(List<AttractionEntity> attractionEntities) {
        this.attractionEntities = attractionEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionId != null ? promotionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the promotionId fields are not set
        if (!(object instanceof PromotionEntity)) {
            return false;
        }
        PromotionEntity other = (PromotionEntity) object;
        if ((this.promotionId == null && other.promotionId != null) || (this.promotionId != null && !this.promotionId.equals(other.promotionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PromotionEntity[ id=" + promotionId + " ]";
    }
    
}
