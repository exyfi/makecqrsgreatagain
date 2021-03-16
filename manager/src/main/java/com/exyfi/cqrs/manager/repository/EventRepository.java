package com.exyfi.cqrs.manager.repository;

import com.exyfi.cqrs.manager.domain.Event;
import com.exyfi.cqrs.manager.domain.EventId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, EventId> {

    Optional<Event> getFirstByUid(String uid);
}
