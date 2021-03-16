package com.exyfi.cqrs.checkpoint.aggregates;

import com.exyfi.cqrs.checkpoint.command.EnterCommand;
import com.exyfi.cqrs.checkpoint.command.ExitCommand;
import com.exyfi.cqrs.checkpoint.domain.Direction;
import com.exyfi.cqrs.checkpoint.domain.Event;
import com.exyfi.cqrs.checkpoint.domain.EventId;
import com.exyfi.cqrs.checkpoint.domain.GateEvent;
import com.exyfi.cqrs.checkpoint.domain.RenewalSubscriptionEvent;
import com.exyfi.cqrs.checkpoint.repository.CheckpointRepository;
import com.exyfi.cqrs.checkpoint.repository.EventRepository;
import com.exyfi.cqrs.checkpoint.repository.RenewSubscriptionRepository;
import com.exyfi.cqrs.common.events.EnterEvent;
import com.exyfi.cqrs.common.events.ExitEvent;
import com.exyfi.cqrs.common.exceptions.IllegalEnterAttemptException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "checkpointSnapshotDefinition")
@Slf4j
public class CheckpointCommandAggregate {

    protected CheckpointCommandAggregate() {

    }

    @AggregateIdentifier
    private String uid = UUID.randomUUID().toString();

    @CommandHandler
    public CheckpointCommandAggregate(EnterCommand command) {
        log.info("checkpoint service command: {}", command);
        var event = EnterEvent.builder()
                .uid(command.getUid())
                .enterTime(command.getEnterTime())
                .build();
        apply(event);
    }

    @CommandHandler
    public CheckpointCommandAggregate(ExitCommand command) {
        log.info("checkpoint service command: {}", command);
        var event = ExitEvent.builder()
                .uid(command.getUid())
                .exitTime(command.getExitTime())
                .build();
        apply(event);
    }

    @EventSourcingHandler
    @Transactional
    public void handle(ExitEvent event, @Autowired EventRepository eventRepository, @Autowired RenewSubscriptionRepository subscriptionRepository,
                       @Autowired CheckpointRepository checkpointRepository) {
        eventRepository.getFirstByUid(event.getUid())
                .orElseThrow(() -> new IllegalEnterAttemptException(
                        String.format("User with provided id %s not exist. Something went wrong", event.getUid())));

        RenewalSubscriptionEvent subscription = subscriptionRepository.findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(event.getUid())
                .orElse(null);
        if (Objects.isNull(subscription) || subscription.getSubscriptionUntil().isBefore(LocalDateTime.now())) {
            throw new IllegalEnterAttemptException(String.format("Subscription for user %s has expired. Please call police, illegal enter", event.getUid()));
        }

        GateEvent lastGateUserEvent = checkpointRepository.getFirstByEvent_UidOrderByEventTimeDesc(event.getUid())
                .orElse(null);

        if (Objects.isNull(lastGateUserEvent) || lastGateUserEvent.getDirection() == Direction.EXIT) {
            throw new IllegalEnterAttemptException("SYSTEM HAS CRASHED");
        }


        Event savedEnterEvent = eventRepository.save(new Event(event.getUid()));
        checkpointRepository.save(new GateEvent(new EventId(savedEnterEvent.getUid(), savedEnterEvent.getId()), LocalDateTime.now(), Direction.EXIT));
    }

    @EventSourcingHandler
    @Transactional
    public void handle(EnterEvent event, @Autowired EventRepository eventRepository, @Autowired RenewSubscriptionRepository subscriptionRepository,
                       @Autowired CheckpointRepository checkpointRepository) {
        eventRepository.getFirstByUid(event.getUid())
                .orElseThrow(() -> new IllegalEnterAttemptException(
                        String.format("User with provided id %s not exist. Something went wrong", event.getUid())));

        RenewalSubscriptionEvent subscription = subscriptionRepository.findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(event.getUid())
                .orElse(null);
        if (Objects.isNull(subscription) || subscription.getSubscriptionUntil().isBefore(LocalDateTime.now())) {
            throw new IllegalEnterAttemptException(String.format("Subscription for user %s has expired", event.getUid()));
        }

        GateEvent lastGateUserEvent = checkpointRepository.getFirstByEvent_UidOrderByEventTimeDesc(event.getUid())
                .orElse(null);

        if (Objects.nonNull(lastGateUserEvent) && lastGateUserEvent.getDirection() == Direction.ENTER) {
            throw new IllegalEnterAttemptException(String.format("USER %S HAS ALREADY ENTERED", event.getUid()));
        }


        Event savedEnterEvent = eventRepository.save(new Event(event.getUid()));
        checkpointRepository.save(new GateEvent(new EventId(savedEnterEvent.getUid(), savedEnterEvent.getId()), LocalDateTime.now(), Direction.ENTER));
    }
}
