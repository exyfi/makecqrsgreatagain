package com.exyfi.cqrs.common.events;

import com.exyfi.cqrs.common.model.Direction;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class EnterEvent {
    String uid;
    LocalDateTime enterTime;
    Direction direction = Direction.ENTER;
}
