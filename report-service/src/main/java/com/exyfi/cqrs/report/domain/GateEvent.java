package com.exyfi.cqrs.report.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
public class GateEvent {

    @EmbeddedId
    private EventId event;

    private LocalDateTime eventTime;
    @Enumerated(EnumType.STRING)
    private Direction direction;

    public GateEvent() {
    }

    public GateEvent(EventId event, LocalDateTime eventTime, Direction direction) {
        this.event = event;
        this.eventTime = eventTime;
        this.direction = direction;
    }

    public EventId getEvent() {
        return event;
    }

    public void setEvent(EventId event) {
        this.event = event;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}