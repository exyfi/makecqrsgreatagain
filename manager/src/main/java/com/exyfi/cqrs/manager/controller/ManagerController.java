package com.exyfi.cqrs.manager.controller;

import com.exyfi.cqrs.common.dto.manager.CreateUserDto;
import com.exyfi.cqrs.common.model.manager.User;
import com.exyfi.cqrs.manager.command.NewUserCommand;
import com.exyfi.cqrs.manager.command.UserSubscriptionRenewedCommand;
import com.exyfi.cqrs.manager.queries.GetUserQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/user/new")
    public String register(@RequestBody CreateUserDto dto) {
        return commandGateway.send(NewUserCommand.builder().uid(UUID.randomUUID().toString()).build()).toString();
    }

    @PostMapping("/user/renew")
    public ResponseEntity<String> renewSubscription(@RequestBody UserSubscriptionRenewedCommand command) {
        try {
            var result = commandGateway.sendAndWait(command, 3, TimeUnit.SECONDS);
            log.info(result.toString());
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            log.error("error while renew user subscription new user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public CompletableFuture<User> getUser(@PathVariable("id") String uid) {
        return queryGateway.query("getUser", new GetUserQuery(uid), User.class);
    }
}
