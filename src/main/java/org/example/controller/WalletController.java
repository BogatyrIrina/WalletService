package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.adapter.WalletAdapter;
import org.example.dto.TransactionRequest;
import org.example.dto.WalletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.example.constant.UrlConstant.API_V1_WALLETS_URL;
import static org.example.constant.UrlConstant.PATH_VARIABLE_WALLET_ID;
import static org.example.constant.UrlConstant.PATH_VARIABLE_WALLET_ID_URL;

@RestController
@RequestMapping(API_V1_WALLETS_URL)
@RequiredArgsConstructor
public class WalletController {
    private final WalletAdapter adapter;

    @PostMapping
    public WalletResponse changeBalance(@RequestBody TransactionRequest request) {
        return adapter.changeBalance(request);
    }

    @GetMapping(PATH_VARIABLE_WALLET_ID_URL)
    public WalletResponse getWallet(@PathVariable(PATH_VARIABLE_WALLET_ID) UUID walletId) {
        return adapter.getWallet(walletId);
    }
}
