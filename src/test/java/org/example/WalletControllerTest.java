package org.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.SneakyThrows;
import org.example.constant.UrlConstant;
import org.example.dto.ErrorResponse;
import org.example.dto.TransactionRequest;
import org.example.dto.WalletResponse;
import org.example.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.UUID;

import static org.example.constant.UrlConstant.PATH_VARIABLE_WALLET_ID_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/wallet_init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/wallet_drop.sql")
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MyApplication.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Transactional
    @SneakyThrows
    @Test
    void changeBalance_whenOperationTypeEqDeposit() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        var walletId = UUID.fromString("6e8e2314-a1ec-4e0f-938a-3014a61e8018");
        var request = TransactionRequest.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(200))
                .operationType(OperationType.DEPOSIT);
        var response = WalletResponse.builder()
                .id(walletId)
                .balance(BigDecimal.valueOf(1200))
                .build();

        var requestPath = MockMvcRequestBuilders.post(UrlConstant.API_V1_WALLETS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));
        mockMvc.perform(requestPath)
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Transactional
    @SneakyThrows
    @Test
    void changeBalance_whenOperationTypeEqWithdraw() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        var walletId = UUID.fromString("6e8e2314-a1ec-4e0f-938a-3014a61e8018");
        var request = TransactionRequest.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(200))
                .operationType(OperationType.WITHDRAW);
        var response = WalletResponse.builder()
                .id(walletId)
                .balance(BigDecimal.valueOf(800))
                .build();

        var requestPath = MockMvcRequestBuilders.post(UrlConstant.API_V1_WALLETS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));
        mockMvc.perform(requestPath)
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @Test
    void changeBalance_whenOperationTypeEqWithdrawAndInsufficientFunds() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        var walletId = UUID.fromString("6e8e2314-a1ec-4e0f-938a-3014a61e8018");
        var request = TransactionRequest.builder()
                .walletId(walletId)
                .amount(BigDecimal.valueOf(1200))
                .operationType(OperationType.WITHDRAW);
        var response = ErrorResponse.builder()
                .message("Insufficient funds")
                .build();
        var requestPath = MockMvcRequestBuilders.post(UrlConstant.API_V1_WALLETS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));
        mockMvc.perform(requestPath)
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @Test
    void getWallet_whenWalletFound() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        var walletId = UUID.fromString("6e8e2314-a1ec-4e0f-938a-3014a61e8018");
        var response = WalletResponse.builder()
                .id(walletId)
                .balance(BigDecimal.valueOf(1000))
                .build();
        var requestPath = MockMvcRequestBuilders.get(UrlConstant.API_V1_WALLETS_URL
                + PATH_VARIABLE_WALLET_ID_URL, walletId);
        mockMvc.perform(requestPath)
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @Test
    void getWallet_whenWalletNotFound() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        var walletId = UUID.fromString("7e8e2314-a1ec-4e0f-938a-3014a61e8018");
        var response = ErrorResponse.builder()
                .message("Wallet not found: " + walletId)
                .build();
        var requestPath = MockMvcRequestBuilders.get(UrlConstant.API_V1_WALLETS_URL
                + PATH_VARIABLE_WALLET_ID_URL, walletId);
        mockMvc.perform(requestPath)
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }
}
