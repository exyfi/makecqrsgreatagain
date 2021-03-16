package com.exyfi.cqrs.manager.queries;

import com.exyfi.cqrs.common.events.UserCreatedEvent;
import com.exyfi.cqrs.common.exceptions.UserNotFoundException;
import com.exyfi.cqrs.common.model.manager.User;
import com.exyfi.cqrs.manager.domain.Event;
import com.exyfi.cqrs.manager.domain.EventId;
import com.exyfi.cqrs.manager.domain.RenewalSubscriptionEvent;
import com.exyfi.cqrs.manager.repository.EventRepository;
import com.exyfi.cqrs.manager.repository.RenewSubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryHandlerServiceTest {

    @Mock
    private RenewSubscriptionRepository subscriptionRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private QueryHandlerService queryHandlerService;


    @Test
    public void testEventHandler() {
        var uuid = UUID.randomUUID().toString();
        queryHandlerService.on(UserCreatedEvent.builder().uid(uuid).created(LocalDateTime.now()).build());

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);

        verify(eventRepository, times(1)).save(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getUid(), uuid);
    }

    @Test
    public void testQueryHandler() {
        var uuid = UUID.randomUUID().toString();
        var sub = LocalDateTime.now();
        when(eventRepository.getFirstByUid(uuid)).thenReturn(Optional.of(new Event(uuid)));
        when(subscriptionRepository.findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(uuid)).thenReturn(Optional.of(new RenewalSubscriptionEvent(new EventId(uuid, 1), sub)));
        User userById = queryHandlerService.getUserById(new GetUserQuery(uuid));

        assertNotNull(userById);

        assertEquals(uuid, userById.getUid());
        assertEquals(sub, userById.getSubscriptionUntil());
    }

    @Test
    public void testQueryHandlerThrowsException() {
        var uuid = UUID.randomUUID().toString();
        when(eventRepository.getFirstByUid(uuid)).thenReturn(Optional.ofNullable(null));

        assertThrows(UserNotFoundException.class, () -> queryHandlerService.getUserById(new GetUserQuery(uuid)));
    }
}