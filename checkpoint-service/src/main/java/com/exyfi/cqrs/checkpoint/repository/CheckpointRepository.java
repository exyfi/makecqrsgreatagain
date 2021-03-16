package com.exyfi.cqrs.checkpoint.repository;

import com.exyfi.cqrs.checkpoint.domain.EventId;
import com.exyfi.cqrs.checkpoint.domain.GateEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckpointRepository extends JpaRepository<GateEvent, EventId> {

    // Optional<GateEvent> findGateEventByEvent_UidOrderByEventDesc(String uid);
    Optional<GateEvent> getFirstByEvent_UidOrderByEventTimeDesc(String uid);
}

