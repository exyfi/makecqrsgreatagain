package com.exyfi.cqrs.manager.controller.handler;

import com.exyfi.cqrs.common.dto.BaseResponse;
import com.exyfi.cqrs.common.dto.Error;
import com.exyfi.cqrs.common.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(UserNotFoundException ex) {
        return new ResponseEntity<>(BaseResponse.builder()
                .success(false)
                .error(Error.builder()
                        .error("USER NOT EXIST")
                        .message(ex.getMessage())
                        .build())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
