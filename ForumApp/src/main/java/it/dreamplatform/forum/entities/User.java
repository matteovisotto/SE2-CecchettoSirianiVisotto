package it.dreamplatform.forum.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "User", schema = "dream_forum")
@NamedQuery(name = "User.findByMail", query = "SELECT u FROM User u WHERE u.mail = :mail")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "integer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String surname;
    private String mail;
    private Date dateOfBirth;
    private String areaOfResidence;
    @Column(nullable = true)
    private String policyMakerID;
    private Timestamp createdAt;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAreaOfResidence() {
        return areaOfResidence;
    }

    public void setAreaOfResidence(String areaOfResidence) {
        this.areaOfResidence = areaOfResidence;
    }

    public String getPolicyMakerID() {
        return policyMakerID;
    }

    public void setPolicyMakerID(String policyMakerID) {
        this.policyMakerID = policyMakerID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
