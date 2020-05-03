package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.GenderEnum;
import util.security.CryptographicHelper;

/**
 *
 * @author celes
 */
@Entity
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
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
//    @Column(columnDefinition = "CHAR(32) NOT NULL")
//    @NotNull
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 4, max = 32)
    private String username;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private GenderEnum gender;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dob;
    @Column(nullable = false)
    @NotNull
    private boolean subscribed;
//    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
//    @NotNull
    private Date subscribedUntil;
    @Column(columnDefinition = "CHAR(16) NOT NULL")
    @NotNull
    private String creditCard;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
//    @NotNull
    private String salt;

    @OneToMany
    private List<ReviewEntity> reviewEntities;

    @OneToMany(mappedBy = "memberEntity")
    private List<BookingEntity> bookingEntities;


    public MemberEntity() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);

        reviewEntities = new ArrayList<>();
        bookingEntities = new ArrayList<>();
        subscribed = false;
    }

    public MemberEntity(String name, String email, String contactNumber, String username, String password, GenderEnum gender, Date dob, String creditCard) {
        this();

        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.username = username;
        this.gender = gender;
        this.dob = dob;
//        this.subscribed = subscribed;
//        this.subscribedUntil = subscribedUntil;
        this.creditCard = creditCard;

        setPassword(password);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<ReviewEntity> getReviewEntities() {
        return reviewEntities;
    }

    public void setReviewEntities(List<ReviewEntity> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }

    public List<BookingEntity> getBookingEntities() {
        return bookingEntities;
    }

    public void setBookingEntities(List<BookingEntity> bookingEntities) {
        this.bookingEntities = bookingEntities;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else {
            this.password = null;
        }
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Date getSubscribedUntil() {
        return subscribedUntil;
    }

    public void setSubscribedUntil(Date subscribedUntil) {
        this.subscribedUntil = subscribedUntil;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberId != null ? memberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberId fields are not set
        if (!(object instanceof MemberEntity)) {
            return false;
        }
        MemberEntity other = (MemberEntity) object;
        if ((this.memberId == null && other.memberId != null) || (this.memberId != null && !this.memberId.equals(other.memberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MemberEntity[ id=" + memberId + " ]";
    }

}
