package com.exyfi.cqrs.report.statistics;

import com.exyfi.cqrs.common.dto.report.StatResponse;
import com.exyfi.cqrs.common.events.ExitEvent;
import com.exyfi.cqrs.common.model.report.UserStat;
import com.exyfi.cqrs.report.domain.Direction;
import com.exyfi.cqrs.report.domain.GateEvent;
import com.exyfi.cqrs.report.repository.CheckpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatService {

    private final Map<String, UserStat> statMap = new ConcurrentHashMap<>();
    private final CheckpointRepository checkpointRepository;

    @EventHandler
    public void on(ExitEvent exitEvent) {
        log.info("accepted new exit event. calculate stat");
        GateEvent enterEvent = checkpointRepository.getFirstByEvent_UidAndDirectionOrderByEventTimeDesc(exitEvent.getUid(), Direction.ENTER);

        Duration visitDuration = Duration.between(enterEvent.getEventTime(), exitEvent.getExitTime());

        UserStat userStat = statMap.get(exitEvent.getUid());
        if (Objects.isNull(userStat)) {
            statMap.put(exitEvent.getUid(), new UserStat(visitDuration, 1));
        } else {
            Duration newDuration = userStat.getDurationInMinutes().plus(visitDuration);
            int newVisits = userStat.getVisits() + 1;
            statMap.put(exitEvent.getUid(), new UserStat(newDuration, newVisits));
        }
    }

    @PostConstruct
    void init() {
        log.info("aggregating stat");
        List<Object[]> resultList = checkpointRepository.collectStat();
        for (Object[] userStat : resultList) {
            String uid = (String) userStat[0];
            int visits = (Integer) userStat[1];
            statMap.put(uid, new UserStat(Duration.ofMillis(100), visits));
        }
        log.info("stat aggregated");
    }

    public StatResponse getStatForUserById(String uid) {
        UserStat userStat = statMap.get(uid);
        if (Objects.isNull(userStat)) {
            log.info("Stat info not found for user {}", uid);
            return StatResponse.builder()
                    .uid(uid)
                    .visits(0)
                    .totalMinutes(Duration.ofMinutes(0))
                    .build();
        }
        return StatResponse.builder()
                .uid(uid)
                .visits(userStat.getVisits())
                .totalMinutes(userStat.getDurationInMinutes())
                .build();

    }
}

