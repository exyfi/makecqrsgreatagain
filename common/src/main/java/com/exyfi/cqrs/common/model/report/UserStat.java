package com.exyfi.cqrs.common.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
@AllArgsConstructor
public class UserStat {

    Duration durationInMinutes;
    int visits;
}
