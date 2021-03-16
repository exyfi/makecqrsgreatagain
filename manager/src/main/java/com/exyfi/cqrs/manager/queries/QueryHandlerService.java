package com.exyfi.cqrs.manager.queries;

import com.exyfi.cqrs.common.events.SubscriptionRenewedEvent;
import com.exyfi.cqrs.common.events.UserCreatedEvent;
import com.exyfi.cqrs.common.exceptions.UserNotFoundException;
import com.exyfi.cqrs.common.model.manager.User;
import com.exyfi.cqrs.manager.domain.Event;
import com.exyfi.cqrs.manager.domain.EventId;
import com.exyfi.cqrs.manager.domain.RenewalSubscriptionEvent;
import com.exyfi.cqrs.manager.repository.EventRepository;
import com.exyfi.cqrs.manager.repository.RenewSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QueryHandlerService {

    private final EventRepository eventRepository;
    private final RenewSubscriptionRepository subscriptionRepository;

    @EventHandler
    public void on(UserCreatedEvent event) {
        eventRepository.save(new Event(event.getUid()));
        //bikeStatusRepository.save(new UserStatus(event.getUid(), event.getCreated(), null));
    }

    @EventHandler
    @Transactional
    public void on(SubscriptionRenewedEvent event) {
        eventRepository.getFirstByUid(event.getUid()).orElseThrow(() ->
                new UserNotFoundException(String.format("User with provided id %s not exist. Something went wrong", event.getUid())));

        Event savedEvent = eventRepository.save(new Event(event.getUid()));
        subscriptionRepository.save(new RenewalSubscriptionEvent(new EventId(event.getUid(), savedEvent.getId()), event.getSubscriptionUntil()));

//        bikeStatusRepository.findById(event.getUid()).map(user -> {
//            user.setSubscriptionUntil(event.getSubscriptionUntil());
//            return user;
//        });
    }

    @QueryHandler(queryName = "getUser")
    public User getUserById(GetUserQuery query) {
        eventRepository.getFirstByUid(query.getUid()).orElseThrow(() ->
                new UserNotFoundException(String.format("User with provided id %s not exist. Something went wrong", query.getUid())));

        RenewalSubscriptionEvent subscription = subscriptionRepository.findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(query.getUid())
                .orElse(null);

        return User.builder()
                .uid(query.getUid())
                .subscriptionUntil(Objects.isNull(subscription) ? null : subscription.getSubscriptionUntil())
                .build();
    }
}
