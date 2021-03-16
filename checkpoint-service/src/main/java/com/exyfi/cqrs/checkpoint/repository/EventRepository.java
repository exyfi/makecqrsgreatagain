package com.exyfi.cqrs.checkpoint.repository;


import com.exyfi.cqrs.checkpoint.domain.Event;
import com.exyfi.cqrs.checkpoint.domain.EventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, EventId> {

    Optional<Event> getFirstByUid(String uid);
}
