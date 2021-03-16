package com.exyfi.cqrs.manager.queries;

import lombok.Value;

@Value
public class GetUserQuery {
    String uid;
}
