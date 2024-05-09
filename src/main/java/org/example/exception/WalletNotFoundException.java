package org.example.exception;

import java.util.UUID;

public class WalletNotFoundException extends WalletServiceException {

    public WalletNotFoundException(UUID walletId) {
        super("Wallet not found: " + walletId);
    }
}
