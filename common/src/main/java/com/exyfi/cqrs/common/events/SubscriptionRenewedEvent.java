package com.exyfi.cqrs.common.events;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class SubscriptionRenewedEvent {

    String uid;
    LocalDateTime subscriptionUntil;
}
