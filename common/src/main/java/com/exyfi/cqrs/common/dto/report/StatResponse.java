package com.exyfi.cqrs.common.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
@AllArgsConstructor
public class StatResponse {

    String uid;
    Duration totalMinutes;
    int visits;
}
