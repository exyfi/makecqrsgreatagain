package com.exyfi.cqrs.checkpoint.repository;


import com.exyfi.cqrs.checkpoint.domain.EventId;
import com.exyfi.cqrs.checkpoint.domain.RenewalSubscriptionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RenewSubscriptionRepository extends JpaRepository<RenewalSubscriptionEvent, EventId> {

    Optional<RenewalSubscriptionEvent> findRenewalSubscriptionEventByEvent_UidOrderBySubscriptionUntilDesc(String uid);
}
