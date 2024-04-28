package org.example.service;

import org.example.entity.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {
    Wallet deposit(UUID walletId, BigDecimal amount);
    Wallet withdraw(UUID walletId, BigDecimal amount);
    Wallet getWallet(UUID walletId );
}
