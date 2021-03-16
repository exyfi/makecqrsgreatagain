package com.exyfi.cqrs.checkpoint.service;

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
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QueryHandlerService {

    private final EventRepository eventRepository;
    private final CheckpointRepository checkpointRepository;
    private final RenewSubscriptionRepository subscriptionRepository;

//    @EventHandler
//    @Transactional
//    public void on(EnterEvent event) {
//        eventRepository.getFirstByUid(event.getUid())
//                .orElseThrow(() -> new IllegalEnterAttemptException(
//                        String.format("User with provided id %s not exist. Something went wrong", event.getUid())));
//
//        RenewalSubscriptionEvent subscription = subscriptionRepository.findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(event.getUid())
//                .orElse(null);
//        if (Objects.isNull(subscription) || subscription.getSubscriptionUntil().isBefore(LocalDateTime.now())) {
//            throw new IllegalEnterAttemptException(String.format("Subscription for user %s has expired", event.getUid()));
//        }
//
//        GateEvent lastGateUserEvent = checkpointRepository.findGateEventByEvent_UidOrderByEventDesc(event.getUid())
//                .orElse(null);
//
//        if (Objects.nonNull(lastGateUserEvent) && lastGateUserEvent.getDirection() == Direction.ENTER) {
//            throw new IllegalEnterAttemptException(String.format("USER %S HAS ALREADY ENTERED", event.getUid()));
//        }
//
//
//        Event savedEnterEvent = eventRepository.save(new Event(event.getUid()));
//        checkpointRepository.save(new GateEvent(new EventId(savedEnterEvent.getUid(), savedEnterEvent.getId()), LocalDateTime.now(), Direction.ENTER));
//    }
//
//    @EventHandler
//    public void on(ExitEvent event) {
//        eventRepository.getFirstByUid(event.getUid())
//                .orElseThrow(() -> new IllegalEnterAttemptException(
//                        String.format("User with provided id %s not exist. Something went wrong", event.getUid())));
//
//        RenewalSubscriptionEvent subscription = subscriptionRepository.findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(event.getUid())
//                .orElse(null);
//        if (Objects.isNull(subscription) || !subscription.getSubscriptionUntil().isBefore(LocalDateTime.now())) {
//            throw new IllegalEnterAttemptException(String.format("Subscription for user %s has expired. Please call police, illegal enter", event.getUid()));
//        }
//
//        GateEvent lastGateUserEvent = checkpointRepository.findGateEventByEvent_UidOrderByEventDesc(event.getUid())
//                .orElse(null);
//
//        if (Objects.isNull(lastGateUserEvent) || lastGateUserEvent.getDirection() == Direction.EXIT) {
//            throw new IllegalEnterAttemptException("SYSTEM HAS CRASHED");
//        }
//
//
//        Event savedEnterEvent = eventRepository.save(new Event(event.getUid()));
//        checkpointRepository.save(new GateEvent(new EventId(savedEnterEvent.getUid(), savedEnterEvent.getId()), LocalDateTime.now(), Direction.ENTER));
//    }

}
