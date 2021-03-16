package com.exyfi.cqrs.report.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "SubscriptionRenewalsEvents")
@Table(name = "subscriptionrenewalsevents")
public class RenewalSubscriptionEvent {

    @EmbeddedId
    EventId event;

    @Column(name = "end_date")
    private LocalDateTime subscriptionUntil;

    public RenewalSubscriptionEvent() {

    }

    public RenewalSubscriptionEvent(EventId event, LocalDateTime subscriptionUntil) {
        this.event = event;
        this.subscriptionUntil = subscriptionUntil;
    }

    public void setEvent(EventId event) {
        this.event = event;
    }

    public EventId getEvent() {
        return event;
    }

    public LocalDateTime getSubscriptionUntil() {
        return subscriptionUntil;
    }

    public void setSubscriptionUntil(LocalDateTime subscriptionUntil) {
        this.subscriptionUntil = subscriptionUntil;
    }
}
