package org.example.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.example.constant.SymbolConstant.CLOSING_CURLY_BRACE;
import static org.example.constant.SymbolConstant.OPENING_CURLY_BRACE;
import static org.example.constant.SymbolConstant.SLASH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlConstant {
    public static final String API_URL = SLASH + "api";
    public static final String V1_URL = SLASH + "v1";
    public static final String WALLETS_URL = SLASH + "wallets";
    public static final String API_V1_WALLETS_URL = API_URL + V1_URL + WALLETS_URL;
    public static final String PATH_VARIABLE_WALLET_ID = "walletId";
    public static final String PATH_VARIABLE_WALLET_ID_URL = SLASH +
            OPENING_CURLY_BRACE + PATH_VARIABLE_WALLET_ID + CLOSING_CURLY_BRACE;
}
