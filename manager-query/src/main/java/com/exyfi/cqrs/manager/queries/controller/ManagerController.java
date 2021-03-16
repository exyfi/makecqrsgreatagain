package com.exyfi.cqrs.manager.queries.controller;

import com.exyfi.cqrs.common.model.manager.User;
import com.exyfi.cqrs.manager.queries.queries.GetUserQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/")
@Slf4j
public class ManagerController {

    private final QueryGateway queryGateway;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer uid) {
        return queryGateway.query(new GetUserQuery(uid),
                ResponseTypes.instanceOf(User.class)).join();
    }
}
