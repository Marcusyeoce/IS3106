package entity;

import java.io.Serializable;
import java.util.Date;
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
    @Column(nullable = false)
    @NotNull
    private Integer openingTime;
    @Column(nullable = false)
    @NotNull
    private Integer closingTime;

    public PlaceEntity() {
        super();
    }

    public PlaceEntity(String name, String description, String location) {
        super(name, description, location);
    }

    public Integer getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Integer openingTime) {
        this.openingTime = openingTime;
    }

    public Integer getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Integer closingTime) {
        this.closingTime = closingTime;
    }

}
