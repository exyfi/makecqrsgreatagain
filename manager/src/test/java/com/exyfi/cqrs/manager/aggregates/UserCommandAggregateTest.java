package com.exyfi.cqrs.manager.aggregates;

import com.exyfi.cqrs.common.events.SubscriptionRenewedEvent;
import com.exyfi.cqrs.common.events.UserCreatedEvent;
import com.exyfi.cqrs.manager.command.NewUserCommand;
import com.exyfi.cqrs.manager.command.UserSubscriptionRenewedCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class UserCommandAggregateTest {

    private FixtureConfiguration<UserCommandAggregate> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(UserCommandAggregate.class);
    }

    @Test
    public void testUseCreateCommand() {
        String userId = UUID.randomUUID().toString();
        NewUserCommand command = NewUserCommand.builder().uid(userId).build();
        fixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(UserCreatedEvent.builder().uid(userId).created(command.getCreateTime()).build());
    }

    @Test
    public void testSubscriptionRenewedCommand() {
        String userId = UUID.randomUUID().toString();
        UserSubscriptionRenewedCommand command = UserSubscriptionRenewedCommand.builder()
                .uid(userId)
                .subscriptionUntil(LocalDateTime.now())
                .build();

        fixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(SubscriptionRenewedEvent.builder().uid(command.getUid()).subscriptionUntil(command.getSubscriptionUntil()).build());
    }
}