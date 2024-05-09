package org.example.exception;

public class WalletServiceException extends RuntimeException {
    public WalletServiceException(String message) {
        super(message);
    }
}
