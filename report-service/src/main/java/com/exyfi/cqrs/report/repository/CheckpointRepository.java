package com.exyfi.cqrs.report.repository;


import com.exyfi.cqrs.report.domain.Direction;
import com.exyfi.cqrs.report.domain.EventId;
import com.exyfi.cqrs.report.domain.GateEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckpointRepository extends JpaRepository<GateEvent, EventId> {

    GateEvent getFirstByEvent_UidAndDirectionOrderByEventTimeDesc(String uid, Direction direction);

    @Modifying
    @Query(value = "SELECT Gate_Event.user_id, cast(count(*) AS int) AS exits_count" +
            " FROM Gate_Event " +
            " WHERE Gate_Event.direction = 'EXIT' " +
            "GROUP BY gate_event.user_id", nativeQuery = true)
    List<Object[]> collectStat();

    interface Stat {
        String uid();

        int visits();
    }
}

