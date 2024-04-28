package org.example.exception.handler;

import org.example.dto.ErrorResponse;
import org.example.exception.InsufficientFundsException;
import org.example.exception.WalletNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {InsufficientFundsException.class, WalletNotFoundException.class})
    public ErrorResponse handle(RuntimeException runtimeException) {
        return ErrorResponse.builder()
                .message(runtimeException.getMessage())
                .build();
    }
}
