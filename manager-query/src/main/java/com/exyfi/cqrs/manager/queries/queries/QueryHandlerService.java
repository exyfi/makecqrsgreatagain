package com.exyfi.cqrs.manager.queries.queries;

import com.exyfi.cqrs.common.events.SubscriptionRenewedEvent;
import com.exyfi.cqrs.common.events.UserCreatedEvent;
import com.exyfi.cqrs.common.model.manager.User;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QueryHandlerService {

    @Autowired
    private EventStore eventStore;
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, LocalDateTime> subscriptions = new ConcurrentHashMap<>();

    @EventHandler
    public void on(UserCreatedEvent event) {
        var user = User.builder()
                .uid(event.getUid())
                .created(event.getCreated())
                .build();

        users.put(event.getUid(), user);
    }

    @EventHandler
    public void on(SubscriptionRenewedEvent event) {
        subscriptions.put(event.getUid(), event.getSubscriptionUntil());
    }

    @QueryHandler
    public User handleGetUserQuery(GetUserQuery query) {
        var user = users.get(query.getUid());
        if (Objects.isNull(user)) {
            return null;
        }

        var userSubscriptionUntil = subscriptions.get(user.getUid());
        return User.builder()
                .uid(user.getUid())
                .created(user.getCreated())
                .subscriptionUntil(userSubscriptionUntil)
                .build();
    }
}
