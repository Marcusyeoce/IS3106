package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author celes
 */
@Entity
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    @Email
    private String email;
    @Column(nullable = false, unique = true, length = 10)
    @NotNull
    @Size(max = 10)
    private String contactNumber;
    
    @OneToMany(mappedBy = "companyEntity")
    private List<AttractionEntity> attractionsEntities;

    public CompanyEntity() {
        attractionsEntities = new ArrayList<>();
    }

    public CompanyEntity(String name, String email, String contactNumber) {
        this();
        
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public List<AttractionEntity> getAttractionsEntities() {
        return attractionsEntities;
    }

    public void setAttractionsEntities(List<AttractionEntity> attractionsEntities) {
        this.attractionsEntities = attractionsEntities;
    }
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the companyId fields are not set
        if (!(object instanceof CompanyEntity)) {
            return false;
        }
        CompanyEntity other = (CompanyEntity) object;
        if ((this.companyId == null && other.companyId != null) || (this.companyId != null && !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompanyEntity[ id=" + companyId + " ]";
    }
    
}
