package com.exyfi.cqrs.manager.domain;

import org.springframework.context.annotation.Profile;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class UserStatus {

    @Id
    private String uid;
    private LocalDateTime creationDate;
    private LocalDateTime subscriptionUntil;

    public UserStatus() {
    }

    public UserStatus(String uid, LocalDateTime creationDate, LocalDateTime subscriptionUntil) {
        this.uid = uid;
        this.creationDate = creationDate;
        this.subscriptionUntil = subscriptionUntil;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getSubscriptionUntil() {
        return subscriptionUntil;
    }

    public void setSubscriptionUntil(LocalDateTime subscriptionUntil) {
        this.subscriptionUntil = subscriptionUntil;
    }
}