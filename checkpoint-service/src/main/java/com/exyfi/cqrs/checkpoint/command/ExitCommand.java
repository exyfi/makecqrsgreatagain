package com.exyfi.cqrs.checkpoint.command;

import com.exyfi.cqrs.common.model.Direction;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Value
@Builder
public class ExitCommand {

    @TargetAggregateIdentifier
    String uid;

    LocalDateTime exitTime = LocalDateTime.now();

    Direction direction = Direction.EXIT;

}
