package org.example.exception;

public class InsufficientFundsException extends WalletServiceException {

    public InsufficientFundsException() {
        super("Insufficient funds");
    }
}
