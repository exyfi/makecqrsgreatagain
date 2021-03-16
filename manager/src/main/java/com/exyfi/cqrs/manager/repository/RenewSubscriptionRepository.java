package com.exyfi.cqrs.manager.repository;

import com.exyfi.cqrs.manager.domain.Event;
import com.exyfi.cqrs.manager.domain.EventId;
import com.exyfi.cqrs.manager.domain.RenewalSubscriptionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RenewSubscriptionRepository extends JpaRepository<RenewalSubscriptionEvent, EventId> {


    Optional<RenewalSubscriptionEvent> findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(String uid);
}
