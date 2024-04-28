package org.example.adapter;

import lombok.RequiredArgsConstructor;
import org.example.converter.WalletConverter;
import org.example.dto.TransactionRequest;
import org.example.dto.WalletResponse;
import org.example.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletAdapter {

    private final WalletService service;
    private final WalletConverter converter;

    public WalletResponse changeBalance(TransactionRequest request) {
        var wallet = switch (request.getOperationType()) {
            case DEPOSIT -> service.deposit(request.getWalletId(), request.getAmount());
            case WITHDRAW -> service.withdraw(request.getWalletId(), request.getAmount());
        };
        return converter.convert(wallet);
    }

    public WalletResponse getWallet(UUID walletId) {
        return converter.convert(
                service.getWallet(walletId)
        );
    }
}
