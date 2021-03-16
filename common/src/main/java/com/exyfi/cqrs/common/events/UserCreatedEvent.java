package com.exyfi.cqrs.common.events;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserCreatedEvent {
    String uid;
    LocalDateTime created;
}
