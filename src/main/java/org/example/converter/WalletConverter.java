package org.example.converter;

import org.example.dto.WalletResponse;
import org.example.entity.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletConverter {
    WalletResponse convert(Wallet wallet);
}
