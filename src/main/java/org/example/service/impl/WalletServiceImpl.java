package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Wallet;
import org.example.exception.InsufficientFundsException;
import org.example.exception.WalletNotFoundException;
import org.example.repository.WalletRepository;
import org.example.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public Wallet deposit(UUID walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByIdAndLock(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        wallet.setBalance(wallet.getBalance().add(amount));
        return wallet;
    }

    @Transactional
    @Override
    public Wallet withdraw(UUID walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByIdAndLock(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        return wallet;
    }


    @Override
    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }
}
