package com.exyfi.cqrs.manager.command;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Value
@Builder
public class NewUserCommand {

    @TargetAggregateIdentifier
    String uid;

    LocalDateTime createTime = LocalDateTime.now();
}
