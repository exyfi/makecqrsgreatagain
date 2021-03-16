package com.exyfi.cqrs.common.model.manager;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class User {

    String uid;
    LocalDateTime subscriptionUntil;
}
