package entity;

import java.io.Serializable;
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
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    private String description;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String location;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private CompanyEntity companyEntity;
    
    @OneToOne(mappedBy = "attractionEntity")
    private PromotionEntity promotionEntity;
    @OneToOne(mappedBy = "attractionEntity")
    private TicketEntity ticketEntity;
    
    @ManyToMany(mappedBy = "attractionEntities")
    private List<TagEntity> tagEntities;
    
    @OneToMany(mappedBy = "attractionEntity")
    private List<ReviewEntity> reviewEntities;
    
    
    public AttractionEntity() {
        tagEntities = new ArrayList<>();
        reviewEntities = new ArrayList<>();
    }

    public AttractionEntity(String name, String description, String location) {
        this();
        
        this.name = name;
        this.description = description;
        this.location = location;
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
    
}