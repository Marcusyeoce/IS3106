package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author celes
 */
@Entity
public class PlaceEntity extends AttractionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date openingTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date closingTime;

    public PlaceEntity() {
        super();
    }

    public PlaceEntity(Date openingTime, Date closingTime, String name, String description, String location, String picture, CompanyEntity companyEntity, BigDecimal unitPrice) {
        super(name, description, location, picture, companyEntity, unitPrice);
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }


    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

}
