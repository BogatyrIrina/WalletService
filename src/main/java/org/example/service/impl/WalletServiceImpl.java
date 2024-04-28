package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Wallet;
import org.example.exception.InsufficientFundsException;
import org.example.exception.WalletNotFoundException;
import org.example.repository.WalletRepository;
import org.example.service.WalletService;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Retryable(retryFor = {StaleStateException.class, OptimisticLockException.class},
            maxAttempts = 5, backoff = @Backoff(delay = 100))
    @Transactional
    @Override
    public Wallet deposit(UUID walletId, BigDecimal amount) {
        var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }

    @Retryable(retryFor = {StaleStateException.class, OptimisticLockException.class},
            maxAttempts = 5, backoff = @Backoff(delay = 100))
    @Transactional
    @Override
    public Wallet withdraw(UUID walletId, BigDecimal amount) {
        var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }
}
