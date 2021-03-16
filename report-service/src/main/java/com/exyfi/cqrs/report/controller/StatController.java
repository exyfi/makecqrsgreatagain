package com.exyfi.cqrs.report.controller;

import com.exyfi.cqrs.common.dto.report.StatResponse;
import com.exyfi.cqrs.report.statistics.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/report")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/{uid}")
    public ResponseEntity<StatResponse> getStatVisit(@PathVariable("uid") String uid) {
        log.info("accepted request to get stat info for user {}", uid);
        StatResponse statResponse = statService.getStatForUserById(uid);

        return ResponseEntity.ok(statResponse);
    }
}
