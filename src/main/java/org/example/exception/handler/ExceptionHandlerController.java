package org.example.exception.handler;

import org.example.dto.ErrorResponse;
import org.example.exception.InsufficientFundsException;
import org.example.exception.WalletNotFoundException;
import org.example.exception.WalletServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {InsufficientFundsException.class})
    public ErrorResponse handle(InsufficientFundsException insufficientFundsException) {
        return ErrorResponse.builder()
                .message(insufficientFundsException.getMessage())
                .build();
    }

    @ExceptionHandler(value = {WalletNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(WalletNotFoundException walletNotFoundException) {
        return ErrorResponse.builder()
                .message(walletNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(value = {WalletServiceException.class})
    public ErrorResponse handle(WalletServiceException walletServiceException) {
        return ErrorResponse.builder()
                .message(walletServiceException.getMessage())
                .build();
    }
}
