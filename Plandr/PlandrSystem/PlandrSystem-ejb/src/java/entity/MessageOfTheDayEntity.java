package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity

public class MessageOfTheDayEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long motdId;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String title;
    @Column(nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    private String message;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date messageDate;

    
    
    public MessageOfTheDayEntity()
    {
    }

    
    
    public MessageOfTheDayEntity(String title, String message, Date messageDate)
    {
        this.title = title;
        this.message = message;
        this.messageDate = messageDate;
    }
    
    

    public Long getMotdId() {
        return motdId;
    }

    public void setMotdId(Long motdId) {
        this.motdId = motdId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (motdId != null ? motdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the motdId fields are not set
        if (!(object instanceof MessageOfTheDayEntity)) {
            return false;
        }
        MessageOfTheDayEntity other = (MessageOfTheDayEntity) object;
        if ((this.motdId == null && other.motdId != null) || (this.motdId != null && !this.motdId.equals(other.motdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MessageOfTheDayEntity[ motdId=" + motdId + " ]";
    }

    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }    
}