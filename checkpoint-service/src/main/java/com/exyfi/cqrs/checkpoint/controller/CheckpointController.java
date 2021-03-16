package com.exyfi.cqrs.checkpoint.controller;

import com.exyfi.cqrs.checkpoint.command.EnterCommand;
import com.exyfi.cqrs.checkpoint.command.ExitCommand;
import com.exyfi.cqrs.common.dto.checkpoint.CheckpointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkpoint")
@Slf4j
public class CheckpointController {

    private final CommandGateway commandGateway;

    @PostMapping("/enter")
    public CompletableFuture<String> enter(@RequestBody CheckpointDto dto) {
        return commandGateway.send(EnterCommand.builder().uid(dto.getUid()).build());
    }

    @PostMapping("/exit")
    public CompletableFuture<String> exit(@RequestBody CheckpointDto dto) {
        return commandGateway.send(ExitCommand.builder().uid(dto.getUid()).build());
    }

}
