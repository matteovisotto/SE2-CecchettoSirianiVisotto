package org.dream.forum.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="User", schema="dream_forum")
public class User implements Serializable {

    private long userId;
    private String name;
    private String surname;
    private String mail;
    private Date dateOfBirth;
    private String areaOfResidence;
    private String policyMakerID;
    private Timestamp createdAt;

    @Id
    @Column(columnDefinition="integer")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    @Column(nullable = true)
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
