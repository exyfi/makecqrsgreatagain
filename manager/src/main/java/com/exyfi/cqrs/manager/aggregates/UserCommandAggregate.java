package com.exyfi.cqrs.manager.aggregates;

import com.exyfi.cqrs.common.events.SubscriptionRenewedEvent;
import com.exyfi.cqrs.common.events.UserCreatedEvent;
import com.exyfi.cqrs.common.exceptions.UserNotFoundException;
import com.exyfi.cqrs.manager.command.NewUserCommand;
import com.exyfi.cqrs.manager.command.UserSubscriptionRenewedCommand;
import com.exyfi.cqrs.manager.domain.Event;
import com.exyfi.cqrs.manager.domain.EventId;
import com.exyfi.cqrs.manager.domain.RenewalSubscriptionEvent;
import com.exyfi.cqrs.manager.repository.EventRepository;
import com.exyfi.cqrs.manager.repository.RenewSubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.UUID;

@Aggregate
@Slf4j
@Service
public class UserCommandAggregate {

    @AggregateIdentifier
    private String uid = UUID.randomUUID().toString();

    @CommandHandler
    public UserCommandAggregate(NewUserCommand command) {
        log.info("create user command: {}", command);
        var event = UserCreatedEvent.builder()
                .uid(command.getUid())
                .created(command.getCreateTime())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public UserCommandAggregate(UserSubscriptionRenewedCommand command) {
        log.info("renew subscription user command: {}", command);
        var event = SubscriptionRenewedEvent.builder()
                .uid(command.getUid())
                .subscriptionUntil(command.getSubscriptionUntil())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void handle(UserCreatedEvent event, @Autowired @Lazy EventRepository eventRepository) {
        eventRepository.save(new Event(event.getUid()));
    }

    @EventSourcingHandler
    @Transactional
    public void handle(SubscriptionRenewedEvent event, @Autowired @Lazy EventRepository eventRepository, @Autowired @Lazy RenewSubscriptionRepository renewSubscriptionRepository) {
        eventRepository.getFirstByUid(event.getUid()).orElseThrow(() ->
                new UserNotFoundException(String.format("User with provided id %s not exist. Something went wrong", event.getUid())));

        Event savedEvent = eventRepository.save(new Event(event.getUid()));
        renewSubscriptionRepository.save(new RenewalSubscriptionEvent(new EventId(event.getUid(), savedEvent.getId()), event.getSubscriptionUntil()));
    }

    protected UserCommandAggregate() {

    }
}
